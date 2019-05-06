package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;
import dev.itsu.gakusapo.ui.adapter.DateEventContentAdapter;

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
