package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;

public interface MainActivityContract {

    interface View {
        Activity getActivity();
    }

    interface Presenter {
        void checkPermission();
        void initialize();
    }
}
