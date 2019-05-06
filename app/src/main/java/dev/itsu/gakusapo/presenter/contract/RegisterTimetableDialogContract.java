package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;

public interface RegisterTimetableDialogContract {

    interface View {
        Activity getActivity();
        String getTimetableName();
        String getDayType();
        String getTimeType();
        void showErrorText(int id);
        void dismiss();
    }

    interface Presenter {
        void onCreateButtonClicked();
    }
}
