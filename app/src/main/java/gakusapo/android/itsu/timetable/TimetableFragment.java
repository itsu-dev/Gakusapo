package gakusapo.android.itsu.timetable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import gakusapo.andoid.itsu.R;

public class TimetableFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TableLayout layout = view.findViewById(R.id.timetableTable);
        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.part_timetable_row, null);
        ((TextView) row.findViewById(R.id.day1Title)).setText("物理");
        ((TextView) row.findViewById(R.id.day2Title)).setText("物理");
        ((TextView) row.findViewById(R.id.day3Title)).setText("物理");
        ((TextView) row.findViewById(R.id.day4Title)).setText("物理");
        ((TextView) row.findViewById(R.id.day5Title)).setText("物理");

        ((TextView) row.findViewById(R.id.day1Teacher)).setText("SEX");
        ((TextView) row.findViewById(R.id.day1Teacher)).setText("SEX");
        ((TextView) row.findViewById(R.id.day1Teacher)).setText("SEX");
        ((TextView) row.findViewById(R.id.day1Teacher)).setText("SEX");
        ((TextView) row.findViewById(R.id.day1Teacher)).setText("SEX");

        ((LinearLayout) row.findViewById(R.id.day1Layout)).setBackground(getActivity().getResources().getDrawable(R.color.subjectMagenta));
        ((LinearLayout) row.findViewById(R.id.day2Layout)).setBackground(getActivity().getResources().getDrawable(R.color.subjectForest));
        ((LinearLayout) row.findViewById(R.id.day3Layout)).setBackground(getActivity().getResources().getDrawable(R.color.subjectIndigo));
        ((LinearLayout) row.findViewById(R.id.day4Layout)).setBackground(getActivity().getResources().getDrawable(R.color.subjectDarkYellow));
        ((LinearLayout) row.findViewById(R.id.day5Layout)).setBackground(getActivity().getResources().getDrawable(R.color.subjectLightBlue));
        ((LinearLayout) row.findViewById(R.id.day6Layout)).setVisibility(View.GONE);
        layout.addView(row);
    }
}
