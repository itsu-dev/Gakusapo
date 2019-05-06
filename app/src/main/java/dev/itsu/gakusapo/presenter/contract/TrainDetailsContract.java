package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;
import dev.itsu.gakusapo.ui.adapter.TrainDetailsListAdapter;

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
