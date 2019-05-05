package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;

public interface WebContract {

    interface View {
        Activity getActivity();
        void loadPage(String url);
    }

    interface Presenter {
        void onCloseButtonClicked();
    }
}
