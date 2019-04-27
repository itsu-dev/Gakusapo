package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import gakusapo.android.itsu.ui.adapter.DateEventContentAdapter;

public interface RegisterDateEventListDialogContract {

    interface View {
        Activity getActivity();
        void setListContents(DateEventContentAdapter adapter);
        void setTitle(int id);
        void setContentNameField(String text);
        String getContentNameField();
    }

    interface Presenter {
        void onAddContentButtonClicked();
        void onListCloseButtonClicked();
        void onContentDeleteButtonClicked(int position);
    }
}
