package dev.itsu.gakusapo.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import dev.itsu.gakusapo.MainApplication;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.receiver.TimetableAlarmNotifier;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.contract.SettingContract;
import dev.itsu.gakusapo.ui.activity.MainActivity;
import dev.itsu.gakusapo.ui.activity.WebActivity;
import dev.itsu.gakusapo.ui.widget.TimetableWidget;

import java.util.Calendar;
import java.util.Map;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View view;

    public SettingPresenter(SettingContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadData() {
        Map<String, Timetable> timetables = DatabaseDAO.getTimetables();

        if (timetables.size() != 0) {
            String[] data = new String[timetables.size()];

            data[0] = PreferencesService.getCurrentTimetable();
            int index = 1;

            if (data.length > 1) {
                for (Timetable value : timetables.values()) {
                    if (value.getName().equals(PreferencesService.getCurrentTimetable())) continue;
                    data[index] = value.getName();
                    index++;
                }
            }

            view.setTimetableItem(data);
        }

        view.setNotificationTime(String.valueOf(PreferencesService.getNotificationtime()));
        view.setScheduleTime(String.valueOf(PreferencesService.getScheduleReloadTime()));
    }

    @Override
    public void save() {
        try {
            int notificationTime = Integer.parseInt(view.getNotificationTime());
            if (notificationTime < 0 || notificationTime > 24) {
                view.showNotificationError(true);
            } else {
                PreferencesService.setNotificationTime(notificationTime);
                view.showNotificationError(false);

                Calendar triggerTime = Calendar.getInstance();
                triggerTime.set(Calendar.HOUR_OF_DAY, notificationTime);
                triggerTime.set(Calendar.MINUTE, 0);
                triggerTime.set(Calendar.SECOND, 0);

                TimetableAlarmNotifier.set(triggerTime);
            }
        } catch (NumberFormatException e) {
            view.showNotificationError(true);
        }

        try {
            int scheduleTime = Integer.parseInt(view.getScheduleTime());
            if (scheduleTime < 0 || scheduleTime > 24) {
                view.showScheduleError(true);
            } else {
                PreferencesService.setScheduleReloadTime(scheduleTime);
                view.showScheduleError(false);
            }
        } catch (NumberFormatException e) {
            view.showScheduleError(true);
        }
    }

    @Override
    public void onSaveButtonClicked() {
        save();
    }

    @Override
    public void onHowToUseButtonClicked()  {
        openWeb("https://gakusapo.itsu.dev/how_to_use.html");
    }

    @Override
    public void onOSLicenseButtonClicked() {
        openWeb("https://gakusapo.itsu.dev/license.html");
    }

    @Override
    public void onPrivacyPolicyButtonClicked() {
        openWeb("https://gakusapo.itsu.dev/privacy_policy.html");
    }

    @Override
    public void onFormulaButtonClicked() {
        openWeb("https://gakusapo.itsu.dev/formula.html");
    }

    @Override
    public void onTimetableSpinnerSelected(String name) {
        if (name != null && !name.isEmpty()) {
            PreferencesService.setCurrentTimetable(name);

            AppWidgetManager manager = AppWidgetManager.getInstance(MainApplication.getContext());
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(new ComponentName(MainApplication.getContext(), TimetableWidget.class)), R.id.widgetTimetableList);
        }
    }

    private void openWeb(String url) {
        Intent intent = new Intent(view.getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        view.getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        view.setTimetableItem(null);
        view = null;
    }
}
