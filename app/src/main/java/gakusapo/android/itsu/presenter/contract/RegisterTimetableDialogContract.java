package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;

public interface RegisterTimetableDialogContract {

    interface View {
        Activity getActivity();
        String getTimetableName();
        String getDayType();
        String getTimeType();
        void showErrorText();
    }

    interface Presenter {
        void onCreateButtonClicked();
    }
}
