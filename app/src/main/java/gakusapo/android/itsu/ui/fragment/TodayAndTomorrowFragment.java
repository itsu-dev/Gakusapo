package gakusapo.android.itsu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.DateEventDBService;
import gakusapo.android.itsu.entity.Subject;
import gakusapo.android.itsu.presenter.TodayAndTomorrowPresenter;
import gakusapo.android.itsu.presenter.contract.TodayAndTomorrowContract;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.List;

public class TodayAndTomorrowFragment extends Fragment implements TodayAndTomorrowContract.View {

    private TodayAndTomorrowContract.Presenter presenter;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today_and_tomorrow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        presenter = new TodayAndTomorrowPresenter(this);
        presenter.reloadData();

        setDate(DateEventDBService.getDate());

        view.findViewById(R.id.todayMemoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMemoButtonClicked();
            }
        });

        view.findViewById(R.id.todayHomeworkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContentButtonClicked(TodayAndTomorrowPresenter.TYPE_HOMEWORK);
            }
        });

        view.findViewById(R.id.todaySubmissionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContentButtonClicked(TodayAndTomorrowPresenter.TYPE_SUBMISSION);
            }
        });

        view.findViewById(R.id.todayTestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContentButtonClicked(TodayAndTomorrowPresenter.TYPE_TEST);
            }
        });

        view.findViewById(R.id.todayClassButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContentButtonClicked(TodayAndTomorrowPresenter.TYPE_CLASS);
            }
        });

        view.findViewById(R.id.todayEventButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContentButtonClicked(TodayAndTomorrowPresenter.TYPE_EVENT);
            }
        });
    }

    @Override
    public void setTitle(int id) {
        TextView textView = view.findViewById(R.id.todayTitle);
        textView.setText(id);
    }

    @Override
    public void setDate(String date) {
        TextView textView = view.findViewById(R.id.todayDate);
        textView.setText(date);
    }

    @Override
    public void setMemo(String memo) {
        TextView textView = view.findViewById(R.id.todayMemo);
        textView.setText(memo);
    }

    @Override
    public void setHomeworks(List<String> data) {
        setData(R.id.todayHomeworkLayout, data);
    }

    @Override
    public void setSubmissions(List<String> data) {
        setData(R.id.todaySubmissionLayout, data);
    }

    @Override
    public void setTests(List<String> data) {
        setData(R.id.todayTestLayout, data);
    }

    @Override
    public void setClasses(List<String> data) {
        setData(R.id.todayClassLayout, data);
    }

    @Override
    public void setEvents(List<String> data) {
        setData(R.id.todayEventLayout, data);
    }

    @Override
    public void setHoliday(boolean bool) {
        if (bool) {
            view.findViewById(R.id.todayTimetableTitle).setVisibility(View.GONE);
            view.findViewById(R.id.todayScrollView).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.todayTimetableTitle).setVisibility(View.VISIBLE);
            view.findViewById(R.id.todayScrollView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTimetables(List<Subject> subjects) {
        LinearLayout layout = view.findViewById(R.id.todayTimetableLayout);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int padding8 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_8);
        int padding16 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_16);

        Space space1 = new Space(getActivity());
        space1.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));

        Space space2 = new Space(getActivity());
        space2.setLayoutParams(new LinearLayout.LayoutParams(padding8, padding8));

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

    private void setData(int id, List<String> data) {
        LinearLayout layout = view.findViewById(id);
        layout.removeAllViews();

        int bottom = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_8);
        int leftRight = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_16);

        for (String s : data) {
            TextView textView = new TextView(getActivity());
            textView.setPadding(leftRight, 0, leftRight, bottom);
            textView.setText("・" + s);
            layout.addView(textView);
        }
    }
}
