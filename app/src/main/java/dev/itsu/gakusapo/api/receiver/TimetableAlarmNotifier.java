package dev.itsu.gakusapo.api.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import dev.itsu.gakusapo.MainApplication;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Subject;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.ui.activity.MainActivity;
import dev.itsu.gakusapo.utils.TimetableUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class TimetableAlarmNotifier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sendIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, sendIntent, 0);

        Timetable timetable = DatabaseDAO.getTimetable(PreferencesService.getCurrentTimetable());
        int day = TimetableUtils.dayToWeek(TimetableUtils.getDate());

        if (timetable == null || day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY)) return;

        List<Subject> subjects = TimetableUtils.getDaySubjects(timetable, day);
        String text = "";

        for (Subject subject : subjects) {
            text += "・" + subject.getName();
        }

        Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.notification_title))
                .setSubText(context.getResources().getString(R.string.notification_message))
                .setContentText(text.substring(1))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) manager.notify(PreferencesService.getNotificationId(), notification);
    }

    public static void set(Calendar trigger) {
        NotificationManager nm = (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(PreferencesService.getNotificationId());

        PreferencesService.setNotificationId(createId());

        Intent intent = new Intent(MainApplication.getContext(), TimetableAlarmNotifier.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainApplication.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) MainApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, trigger.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
    }

    private static int createId() {
        return new Random().nextInt(99999999) + 1;
    }

}
