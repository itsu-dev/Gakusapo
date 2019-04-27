package gakusapo.android.itsu.presenter;

import android.view.View;
import android.widget.AdapterView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.TimetableEditService;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.entity.Subject;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.presenter.contract.TimetableContract;
import gakusapo.android.itsu.ui.activity.MainActivity;
import gakusapo.android.itsu.ui.adapter.TimetableAdapter;
import gakusapo.android.itsu.ui.fragment.RegisterSubjectDialogFragment;
import gakusapo.android.itsu.ui.fragment.RegisterTimetableDialogFragment;
import gakusapo.android.itsu.ui.fragment.SubjectDetailsDialogFragment;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.*;

public class TimetablePresenter implements TimetableContract.Presenter {

    private TimetableContract.View view;

    private String currentTimetableName;
    private Timetable currentTimetable;

    private TimetableEditService editService;
    private boolean editMode;
    private boolean selectMode;

    public TimetablePresenter(TimetableContract.View view) {
        this.view = view;
        this.currentTimetableName = "test";
    }

    @Override
    public void reloadTimetable() {
        Timetable timetable;

        if (currentTimetableName != null) {
            TimetableDBService service = new TimetableDBService(view.getActivity());
            timetable = service.getTimetables().get(currentTimetableName);
        } else {
            timetable = TimetableUtils.createNewTimetable(view.getActivity().getResources().getString(R.string.timetable_primary_title));
        }

        reloadTimetable(timetable);
    }

    @Override
    public void reloadTimetable(Timetable timetable) {
        this.currentTimetable = timetable;
        this.currentTimetableName = timetable.getName();

        List<Subject> subjects = TimetableUtils.sortByPosition(timetable.getSubjects());
        view.setSubjects(new TimetableAdapter(view.getActivity(), subjects));
        view.setTimetableTitle(timetable.getName());
        view.setTimetableType(timetable.getDayType(), timetable.getTimeType());
    }

    @Override
    public void setCurrentTimetableName(String name) {
        this.currentTimetableName = name;
    }

    @Override
    public void onSubjectClicked(AdapterView<?> parent, View view, int position, long id) {
        if (editMode) {
            if (selectMode) {
                if (editService.isSelectedSubject(position)) {
                    view.findViewById(R.id.timetableCellLayout).setBackgroundColor(this.view.getActivity().getResources().getColor(editService.getTimetable().getSubjects().get(position).getBackground()));
                    editService.removeSelectedSubject(position);

                } else {
                    view.findViewById(R.id.timetableCellLayout).setBackgroundColor(this.view.getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    editService.addSelectedSubject(position);
                }

            } else {
                if (editService.getOneSubject() != null) {
                    if (editService.getOneSubject().getPosition() == position) {
                        view.findViewById(R.id.timetableCellLayout).setBackgroundColor(this.view.getActivity().getResources().getColor(editService.getOneSubject().getBackground()));
                        editService.setOneSubject(null);

                    } else {
                        this.view.setSubjectBackground(editService.getOneSubject().getPosition(), editService.getOneSubject().getBackground());
                        view.findViewById(R.id.timetableCellLayout).setBackgroundColor(this.view.getActivity().getResources().getColor(R.color.colorPrimaryDark));
                        editService.setOneSubject(position);
                    }

                } else {
                    view.findViewById(R.id.timetableCellLayout).setBackgroundColor(this.view.getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    editService.setOneSubject(position);
                }
            }

        } else {
            MainActivity activity = (MainActivity) this.view.getActivity();
            Subject subject = currentTimetable.getSubjects().get(position);

            SubjectDetailsDialogFragment fragment = SubjectDetailsDialogFragment.newInstance(
                    TimetableUtils.positionToDayAndTime(position, currentTimetable.getDayType(), currentTimetable.getTimeType()),
                    subject.getName(),
                    subject.getClassName(),
                    subject.getDescription()
            );

            fragment.show(activity.getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void onTimetableEditButtonClicked() {
        if (editMode) setEditMode(false);
        else setEditMode(true);
    }

    @Override
    public void onTimetableAddButtonClicked() {
        MainActivity activity = (MainActivity) view.getActivity();
        RegisterTimetableDialogFragment fragment = RegisterTimetableDialogFragment.newInstance(this);
        fragment.show(activity.getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onTimetableSelectButtonClicked() {
        if (selectMode) {
            selectMode = false;

            resetSelectedSubjectColor();
            editService.setOneSubject(null);

            editService.clearSelectedSubject();
            view.setSelectButtonClicked(false);
            view.showSelectToast(false);

        } else {
            selectMode = true;

            resetSelectedSubjectColor();
            editService.setOneSubject(null);

            view.setSelectButtonClicked(true);
            view.showSelectToast(true);
        }
    }

    @Override
    public void onTimetableEditSubjectButtonClicked() {
        if ((selectMode && editService.getSelectedSubject().size() > 0) || editService.getOneSubject() != null) {
            RegisterSubjectDialogFragment fragment = RegisterSubjectDialogFragment.newInstance(this);
            MainActivity activity = (MainActivity) view.getActivity();
            fragment.show(activity.getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void onRegisterSubjectDialogCallback(String name, String className, String description, int background) {
        if (selectMode) {
            editService.setSelectedSubjectData(name, className, description, background);
            editService.clearSelectedSubject();
        } else {
            editService.setOneSubjectData(name, className, description, background);
        }
        reloadTimetable(editService.getTimetable());
    }

    @Override
    public void onCreateTimetableDialogCallback(String name, int dayType, int timeType) {
        reloadTimetable(TimetableUtils.createNewTimetable(name, dayType, timeType));
        setEditMode(true);
    }

    private void setEditMode(boolean bool) {
        if (bool) {
            editMode = true;
            selectMode = false;
            editService = new TimetableEditService(currentTimetable);
            view.setEditmode(true);
            view.showEditmodeToast(true);

        } else {
            resetSelectedSubjectColor();

            editService.save(view.getActivity());

            editMode = false;
            selectMode = false;
            editService = null;
            view.setEditmode(false);
            view.showEditmodeToast(false);
            view.setTimetableTitle(currentTimetableName);
        }
    }

    private void resetSelectedSubjectColor() {
        if (editService != null) {
            for (Subject subject : editService.getSelectedSubject()) {
                this.view.setSubjectBackground(subject.getPosition(), subject.getBackground());
            }

            if (editService.getOneSubject() != null) {
                this.view.setSubjectBackground(editService.getOneSubject().getPosition(), editService.getOneSubject().getBackground());
            }
        }
    }
}
