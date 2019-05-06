package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import android.widget.ArrayAdapter;
import gakusapo.android.itsu.ui.adapter.TrainDetailsListAdapter;

public interface TrainDetailsContract {

    interface View {
        Activity getActivity();
        void setAddedTrains(TrainDetailsListAdapter adapter);
    }

    interface Presenter {
        void reloadAddedTrains();
        void removeTrain(String trainName);
        void onAddTrainButtonClicked();
        void onAddedTrain(String companyName, String name);
    }
}
