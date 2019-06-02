package dev.itsu.gakusapo.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
import dev.itsu.gakusapo.utils.TimetableUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimetableWidget extends AppWidgetProvider {

    public static final String ACTION_RELOAD = "ACTION_RELOAD";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_timetable);
        views.setTextViewText(R.id.widgetMonth, getMonth());
        views.setTextViewText(R.id.widgetDate, getDate());
        views.setTextViewText(R.id.widgetWeek, getWeek());
        views.setOnClickPendingIntent(R.id.widgetReloadButton, onReloadButtonClick(context));
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

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_timetable);
            Intent intent = new Intent(context, TimetableWidgetRemoteViewsService.class);
            remoteViews.setRemoteAdapter(R.id.widgetTimetableList, intent);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static String getMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public static String getDate() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
    }

    public static String getWeek() {
        return new SimpleDateFormat("(EEEE)", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static PendingIntent onReloadButtonClick(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_RELOAD);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, TimetableWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}

