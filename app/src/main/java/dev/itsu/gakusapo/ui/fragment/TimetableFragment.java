package dev.itsu.gakusapo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.TimetablePresenter;
import dev.itsu.gakusapo.presenter.contract.TimetableContract;
import dev.itsu.gakusapo.ui.adapter.TimetableAdapter;

public class TimetableFragment extends Fragment implements TimetableContract.View {

    private TimetableContract.Presenter presenter;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        this.presenter = new TimetablePresenter(this);
        presenter.reloadTimetable();


        GridView gridView = view.findViewById(R.id.timetableLayout);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSubjectClicked(parent, view, position, id);
            }
        });

        this.view.findViewById(R.id.timetableEditButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTimetableEditButtonClicked();
            }
        });

        this.view.findViewById(R.id.timetableAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTimetableAddButtonClicked();
            }
        });

        this.view.findViewById(R.id.timetableSelectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTimetableSelectButtonClicked();
            }
        });

        this.view.findViewById(R.id.timetableEditSubjectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTimetableEditSubjectButtonClicked();
            }
        });
    }

    @Override
    public void setSubjects(TimetableAdapter adapter) {
        GridView gridView = view.findViewById(R.id.timetableLayout);
        gridView.setAdapter(adapter);
    }

    @Override
    public void setTimetableTitle(String title) {
        TextView textView = view.findViewById(R.id.timetableTitle);
        textView.setText(title);
    }

    @Override
    public void setSelectButtonClicked(boolean bool) {
        if (bool) this.view.findViewById(R.id.timetableSelectButton).setBackground(getActivity().getDrawable(R.drawable.ic_select_blue));
        else this.view.findViewById(R.id.timetableSelectButton).setBackground(getActivity().getDrawable(R.drawable.dr_select_ripple));
    }

    @Override
    public void setSubjectBackground(int position, int colorId) {
        GridView gridView = view.findViewById(R.id.timetableLayout);
        gridView.getChildAt(position).findViewById(R.id.timetableCellLayout).setBackgroundColor(getActivity().getResources().getColor(colorId));
    }

    @Override
    public void setEditmode(boolean bool) {
        if (bool) {
            this.view.findViewById(R.id.timetableSelectButton).setVisibility(View.VISIBLE);
            this.view.findViewById(R.id.timetableEditSubjectButton).setVisibility(View.VISIBLE);
            this.view.findViewById(R.id.timetableAddButton).setVisibility(View.GONE);
            this.view.findViewById(R.id.timetableEditButton).setBackground(getActivity().getDrawable(R.drawable.ic_edit_blue));

        } else {
            this.view.findViewById(R.id.timetableSelectButton).setVisibility(View.GONE);
            this.view.findViewById(R.id.timetableEditSubjectButton).setVisibility(View.GONE);
            this.view.findViewById(R.id.timetableAddButton).setVisibility(View.VISIBLE);
            this.view.findViewById(R.id.timetableEditButton).setBackground(getActivity().getDrawable(R.drawable.dr_editmode_ripple));
        }
    }

    @Override
    public void setTimetableType(int dayType, int timeType) {
        GridView gridView = view.findViewById(R.id.timetableLayout);

        if (dayType == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY) {
            this.view.findViewById(R.id.saturdayLabel).setVisibility(View.GONE);
            gridView.setNumColumns(5);
        } else {
            this.view.findViewById(R.id.saturdayLabel).setVisibility(View.VISIBLE);
            gridView.setNumColumns(6);
        }

        switch (timeType) {
            case Timetable.TIME_TYPE_5: {
                this.view.findViewById(R.id.sixLabel).setVisibility(View.GONE);
                this.view.findViewById(R.id.sixSpace).setVisibility(View.GONE);
            }

            case Timetable.TIME_TYPE_6: {
                this.view.findViewById(R.id.sevenLabel).setVisibility(View.GONE);
                this.view.findViewById(R.id.sevenSpace).setVisibility(View.GONE);
            }

            case Timetable.TIME_TYPE_7: {
                this.view.findViewById(R.id.eightLabel).setVisibility(View.GONE);
                this.view.findViewById(R.id.eightSpace).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void showEditmodeToast(boolean bool) {
        if (bool) Toast.makeText(view.getContext(), R.string.timetable_editmode_toast, Toast.LENGTH_SHORT).show();
        else Toast.makeText(view.getContext(), R.string.timetable_uneditmode_toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSelectToast(boolean bool) {
        if (bool) Toast.makeText(view.getContext(), R.string.timetable_select, Toast.LENGTH_SHORT).show();
        else Toast.makeText(view.getContext(), R.string.timetable_unselect, Toast.LENGTH_SHORT).show();
    }
}
