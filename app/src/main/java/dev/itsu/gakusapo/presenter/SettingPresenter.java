package dev.itsu.gakusapo.presenter;

import android.content.Intent;
import android.os.Bundle;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.contract.SettingContract;
import dev.itsu.gakusapo.ui.activity.WebActivity;

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
    public void onOSLicenseButtonClicked() {
        openWeb("file:///android_asset/opensourcelicense.html");
    }

    @Override
    public void onLicenseButtonClicked() {
        openWeb("file:///android_asset/license.html");
    }

    @Override
    public void onFormulaButtonClicked() {
        openWeb("file:///android_asset/formula.html");
    }

    @Override
    public void onTimetableSpinnerSelected(String name) {
        if (name != null && !name.isEmpty()) PreferencesService.setCurrentTimetable(name);
    }

    private void openWeb(String url) {
        Intent intent = new Intent(view.getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        view.getActivity().startActivity(intent);
    }
}
