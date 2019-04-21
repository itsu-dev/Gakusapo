package jp.mirm.pmmpstudio.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import jp.mirm.pmmpstudio.R;
import jp.mirm.pmmpstudio.model.list.ProjectListCell;

import java.util.ArrayList;
import java.util.List;

public class ProjectListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    List<ProjectListCell> projectList;

    public ProjectListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setProjectList(List<ProjectListCell> foodList) {
        this.projectList = foodList;
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return projectList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.project_list_cell, parent, false);

        ((TextView) convertView.findViewById(R.id.project_list_name)).setText(projectList.get(position).getName());
        ((TextView) convertView.findViewById(R.id.project_list_date)).setText(String.valueOf(projectList.get(position).getDate()));

        return convertView;
    }
}