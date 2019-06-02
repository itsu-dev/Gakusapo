package dev.itsu.gakusapo.api.receiver;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.api.service.TimetableWidgetRemoteViewsService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.ui.widget.TimetableWidget;
import dev.itsu.gakusapo.utils.TimetableUtils;

public class TimetableWidgetIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) return;

        switch (intent.getAction()) {
            case TimetableWidget.ACTION_RELOAD: {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_timetable);
                views.setTextViewText(R.id.widgetMonth, TimetableWidget.getMonth());
                views.setTextViewText(R.id.widgetDate, TimetableWidget.getDate());
                views.setTextViewText(R.id.widgetWeek, TimetableWidget.getWeek());
                views.setOnClickPendingIntent(R.id.widgetReloadButton, TimetableWidget.onReloadButtonClick(context));
                views.setRemoteAdapter(R.id.widgetTimetableList, new Intent(context, TimetableWidgetRemoteViewsService.class));

                if (TimetableUtils.isTomorrow()) {
                    views.setTextViewText(R.id.widgetStatus, context.getText(R.string.today_and_tomorrow_tomorrow));
                } else {
                    views.setTextViewText(R.id.widgetStatus, context.getText(R.string.today_and_tomorrow_today));
                }

                int day = TimetableUtils.dayToWeek(TimetableUtils.getDate());
                Timetable timetable = DatabaseDAO.getTimetable(PreferencesService.getCurrentTimetable());

                if (PreferencesService.getCurrentTimetable() == null) {
                    views.setViewVisibility(R.id.widgetTimetableList, View.GONE);
                    views.setViewVisibility(R.id.widgetErrorText, View.VISIBLE);
                    views.setTextViewText(R.id.widgetErrorText, context.getText(R.string.widget_notimetable));

                } else if (day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY)) {
                    views.setViewVisibility(R.id.widgetTimetableList, View.GONE);
                    views.setViewVisibility(R.id.widgetErrorText, View.VISIBLE);
                    views.setTextViewText(R.id.widgetErrorText, context.getText(R.string.widget_noschedule));

                } else {
                    views.setViewVisibility(R.id.widgetTimetableList, View.VISIBLE);
                    views.setViewVisibility(R.id.widgetErrorText, View.GONE);
                }

                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(new ComponentName(context, TimetableWidget.class)), R.id.widgetTimetableList);

                TimetableWidget.pushWidgetUpdate(context, views);
            }
        }
    }

}
