package dev.itsu.gakusapo.presenter;

import android.view.View;
import android.widget.AdapterView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.api.service.TimetableEditService;
import dev.itsu.gakusapo.api.task.LoadTimetableTask;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Subject;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.contract.TimetableContract;
import dev.itsu.gakusapo.ui.activity.MainActivity;
import dev.itsu.gakusapo.ui.adapter.TimetableAdapter;
import dev.itsu.gakusapo.ui.dialog.AlertDialogFragment;
import dev.itsu.gakusapo.ui.dialog.RegisterSubjectDialogFragment;
import dev.itsu.gakusapo.ui.dialog.RegisterTimetableDialogFragment;
import dev.itsu.gakusapo.ui.dialog.SubjectDetailsDialogFragment;
import dev.itsu.gakusapo.utils.TimetableUtils;

import java.util.*;

public class TimetablePresenter implements TimetableContract.Presenter {

    private TimetableContract.View view;

    private  String currentTimetableName;
    private  Timetable currentTimetable;

    private TimetableEditService editService;
    private boolean editMode;
    private boolean selectMode;

    public TimetablePresenter(TimetableContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        if (DatabaseDAO.getTimetables().size() == 0) {
            view.setTimetableButtonVisible(false);
        }
    }

    @Override
    public void reloadTimetable() {
        if (PreferencesService.isEditing() && PreferencesService.getEditdata() != null) {
            MainActivity activity = (MainActivity) view.getActivity();
            AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.notice, R.string.timetable_edited_date_exists, R.string.close, R.string.yes, false);

            fragment.setPositiveButtonListener(new AlertDialogFragment.OnClickListener() {
                @Override
                public void onClicked() {
                    reloadTimetable(TimetableEditService.getEditingTimetable());
                    setEditMode(true);
                }
            });

            fragment.setNegativeButtonListener(new AlertDialogFragment.OnClickListener() {
                @Override
                public void onClicked() {
                    TimetableEditService.deleteEditingTimetable();
                }
            });

            fragment.show(activity.getSupportFragmentManager(), "dialog");
        }

        Timetable timetable = null;
        currentTimetableName = PreferencesService.getCurrentTimetable();

        if (currentTimetableName != null) {
            //timetable = DatabaseDAO.getTimetable(currentTimetableName);
            loadTimetableFromDatabase(currentTimetableName);
        } else {
            timetable = TimetableUtils.createNewTimetable(view.getActivity().getResources().getString(R.string.timetable_primary_title));
        }

        if (timetable != null) reloadTimetable(timetable);
    }

    @Override
    public void reloadTimetable(Timetable timetable) {
        currentTimetable = timetable;
        currentTimetableName = timetable.getName();

        List<Subject> subjects = TimetableUtils.sortByPosition(currentTimetable.getSubjects());
        view.setSubjects(new TimetableAdapter(view.getActivity(), subjects));
        view.setTimetableTitle(currentTimetable.getName());
        view.setTimetableType(currentTimetable.getDayType(), currentTimetable.getTimeType());
    }

    @Override
    public void setCurrentTimetableName(String name) {
        currentTimetableName = name;
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
            RegisterSubjectDialogFragment fragment = RegisterSubjectDialogFragment.newInstance(
                    this,
                    editService.getSelectedSubject().size() == 0 ? editService.getOneSubject().getName() : null,
                    editService.getSelectedSubject().size() == 0 ? editService.getOneSubject().getClassName() : null,
                    editService.getSelectedSubject().size() == 0 ? editService.getOneSubject().getDescription() : null,
                    editService.getSelectedSubject().size() == 0 ? editService.getOneSubject().getBackground() : -1
            );
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
        if (name.isEmpty()) return;
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
            view.setTimetableButtonVisible(true);

        } else {
            resetSelectedSubjectColor();

            editService.save();

            PreferencesService.setCurrentTimetable(currentTimetable.getName());

            editMode = false;
            selectMode = false;
            editService = null;
            view.setEditmode(false);
            view.showEditmodeToast(false);
            view.setTimetableTitle(currentTimetable.getName());
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

    private void loadTimetableFromDatabase(String name) {
        LoadTimetableTask task = new LoadTimetableTask(this);
        task.execute(name);
    }

    public void onTimetableLoadedFromDatabase(Timetable timetable) {
        reloadTimetable(timetable);
    }

    @Override
    public void onDestroy() {
        view.setSubjects(null);
        view = null;
        editService = null;
        currentTimetable = null;
        currentTimetableName = null;
    }
}
