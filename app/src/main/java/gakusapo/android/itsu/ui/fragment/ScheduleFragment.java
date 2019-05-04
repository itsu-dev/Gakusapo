package gakusapo.android.itsu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Subject;
import gakusapo.android.itsu.presenter.SchedulePresenter;
import gakusapo.android.itsu.presenter.contract.ScheduleContract;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ScheduleFragment extends Fragment implements ScheduleContract.View {

    private ScheduleContract.Presenter presenter;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flagment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        this.presenter = new SchedulePresenter(this);
        presenter.reloadCalendar();
        presenter.reloadSubjects();

        final CompactCalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        calendarView.setShouldDrawDaysHeader(true);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                presenter.onCalendarDateClicked(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                presenter.onCalendarScrolled(firstDayOfNewMonth);
            }
        });
    }

    @Override
    public void addEvent(DateEvent event) {

    }

    @Override
    public void setDateText(String dateText) {
        TextView textView = view.findViewById(R.id.calendarMonth);
        textView.setText(dateText);
    }

    @Override
    public void setSubjects(List<Subject> subjects) {
        LinearLayout layout = view.findViewById(R.id.scheduleTimetableLayout);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int padding8 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_8);
        int padding16 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_16);

        Space space1 = new Space(getActivity());
        space1.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));

        Space space2 = new Space(getActivity());
        space2.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));

        layout.removeAllViews();
        layout.addView(space1);
        layout.addView(space2);

        for (Subject subject : subjects) {
            View view = inflater.inflate(R.layout.part_timetable_cell, null);
            view.findViewById(R.id.timetableCellLayout).setBackgroundColor(getActivity().getResources().getColor(subject.getBackground()));
            view.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getResources().getDimensionPixelSize(R.dimen.padding_60), getActivity().getResources().getDimensionPixelSize(R.dimen.padding_60)));

            TextView nameView = view.findViewById(R.id.timetableSubject);
            nameView.setText(subject.getName());

            TextView classView = view.findViewById(R.id.timetableClass);
            classView.setText(subject.getClassName());

            Space space = new Space(getActivity());
            space.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));

            layout.addView(view);
            layout.addView(space);
        }

        Space space3 = new Space(getActivity());
        space3.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));
        layout.addView(space3);
    }

    @Override
    public void setHoliday(boolean bool) {
        if (bool) {
            view.findViewById(R.id.scheduleScrollView).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.scheduleScrollView).setVisibility(View.VISIBLE);
        }
    }

}
