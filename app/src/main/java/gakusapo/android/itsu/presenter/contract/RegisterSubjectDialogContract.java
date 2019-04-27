package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;

public interface RegisterSubjectDialogContract {

    interface View {
        Activity getActivity();
        String getName();
        String getClassName();
        String getDescription();
        void setName(String name);
        void setClassName(String name);
        void setDescription(String description);
        void setBackgroundColor(int colorId);
    }

    interface Presenter {
        void onColorButtonClicked(int colorId);
        void onRegisterButtonClicked();
    }
}
