package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;

public interface AlertDialogContract {

    interface View {
        Activity getActivity();
        void setTitle(int id);
        void setContent(int id);
        void setNegativeButton(int id);
        void setPositiveButton(int id);
        void onNegativeButtonClicked();
        void onPositiveButtonClicked();
        void dismiss();
    }

    interface Presenter {
        void onNegativeButtonClicked();
        void onPositiveButtonClicked();
    }

}
