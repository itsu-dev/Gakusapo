package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.LinkedList;
import java.util.List;

public interface AddTrainDialogContract {

    interface View {
        Activity getActivity();
        void setNotFound(boolean bool);
        void setAlreadyExists(boolean bool);
        void setListAdapter(ArrayAdapter<String> adapter);
        void setAddButtonEnabled(boolean bool);
        void setButtonText(String text);
        void dismiss();
    }

    interface Presenter {
        void reloadJson();
        void onAddButtonClicked();
        void onListClicked(String name);
        void onKeyTyped(String text);
        void onJsonReaded(LinkedList<String> data);
    }

}
