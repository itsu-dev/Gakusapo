package dev.itsu.gakusapo;

import android.app.Application;
import android.content.Context;
import dev.itsu.gakusapo.api.receiver.TimetableAlarmNotifier;
import dev.itsu.gakusapo.api.service.PreferencesService;

import java.util.Calendar;

public class MainApplication extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = getApplicationContext();

        Calendar triggerTime = Calendar.getInstance();
        triggerTime.set(Calendar.HOUR_OF_DAY, PreferencesService.getNotificationtime());
        triggerTime.set(Calendar.MINUTE, 0);
        triggerTime.set(Calendar.SECOND, 0);

        TimetableAlarmNotifier.set(triggerTime);
    }

    public static Context getContext() {
        return instance;
    }
}
