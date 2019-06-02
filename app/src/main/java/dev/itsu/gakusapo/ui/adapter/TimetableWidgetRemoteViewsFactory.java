package dev.itsu.gakusapo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Subject;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.utils.TimetableUtils;

import java.util.LinkedList;

public class TimetableWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private LinkedList<Subject> subjects;

    public TimetableWidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        reloadTimetable();
    }

    @Override
    public void onDestroy() {
        if (subjects != null) {
            subjects.clear();
        }
    }

    @Override
    public int getCount() {
        return subjects == null ? 0 : subjects.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (subjects == null || position > subjects.size() - 1) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_cell);
        remoteViews.setTextViewText(R.id.widgetTimetableCellSubject, subjects.get(position).getName());
        remoteViews.setTextViewText(R.id.widgetTimetableCellClassName, subjects.get(position).getClassName());
        remoteViews.setTextViewText(R.id.widgetTimetableCellTime, String.valueOf(subjects.get(position).getTime()));
        remoteViews.setImageViewResource(R.id.widgetTimetableCellColor, subjects.get(position).getBackground());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return subjects != null ? subjects.get(position).getTime() : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void reloadTimetable() {
        if (PreferencesService.getCurrentTimetable() == null) return;

        int day = TimetableUtils.dayToWeek(TimetableUtils.getDate());
        Timetable timetable = DatabaseDAO.getTimetable(PreferencesService.getCurrentTimetable());

        if (!(day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY))) {
            subjects = new LinkedList<>(TimetableUtils.getDaySubjects(timetable, day));
        }
    }

}
