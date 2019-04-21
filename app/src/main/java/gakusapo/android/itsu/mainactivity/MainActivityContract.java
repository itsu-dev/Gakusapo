package gakusapo.android.itsu.mainactivity;

import android.app.Activity;

public interface MainActivityContract {

    interface View {
        Activity getActivity();
    }

    interface Presenter {
        void checkPermission();
    }
}
