package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;

public interface SettingContract {

    interface View {
        Activity getActivity();
        void setTimetableItem(String[] item);
        void setNotificationTime(String time);
        void setScheduleTime(String time);
        String getNotificationTime();
        String getScheduleTime();
        void showNotificationError(boolean bool);
        void showScheduleError(boolean bool);
    }

    interface Presenter {
        void reloadData();
        void save();
        void onSaveButtonClicked();
        void onOSLicenseButtonClicked();
        void onLicenseButtonClicked();
        void onFormulaButtonClicked();
        void onTimetableSpinnerSelected(String name);
    }
}
