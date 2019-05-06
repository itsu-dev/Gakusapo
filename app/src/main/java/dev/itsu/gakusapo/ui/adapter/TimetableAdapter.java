package dev.itsu.gakusapo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.entity.Subject;

import java.util.List;

public class TimetableAdapter extends BaseAdapter {

    private Context context;
    private List<Subject> subjects;
    private LayoutInflater inflater;

    public TimetableAdapter(Context context, List<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subjects != null ? subjects.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return subjects != null ? subjects.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.part_timetable_cell, null);

            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.timetableSubject);
            viewHolder.className = convertView.findViewById(R.id.timetableClass);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(subjects.get(position).getName());
        viewHolder.className.setText(subjects.get(position).getClassName());

        convertView.findViewById(R.id.timetableCellLayout).setBackgroundColor(context.getResources().getColor(subjects.get(position).getBackground()));
        convertView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.padding_60)));

        return convertView;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView className;
    }

}
