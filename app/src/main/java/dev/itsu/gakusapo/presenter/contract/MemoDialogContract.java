package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;

public interface MemoDialogContract {

    interface View {
        Activity getActivity();
        void setMemo(String memo);
        String getMemo();
    }

    interface Presenter {
        void onCloseButtonClicked();
    }
}
