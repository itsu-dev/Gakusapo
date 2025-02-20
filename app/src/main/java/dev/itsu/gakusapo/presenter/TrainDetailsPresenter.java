package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.presenter.contract.TrainDetailsContract;
import dev.itsu.gakusapo.ui.activity.TrainDetailsActivity;
import dev.itsu.gakusapo.ui.adapter.TrainDetailsListAdapter;
import dev.itsu.gakusapo.ui.dialog.AddTrainDialogFragment;

public class TrainDetailsPresenter implements TrainDetailsContract.Presenter {

    private TrainDetailsContract.View view;

    public TrainDetailsPresenter(TrainDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadAddedTrains() {
        view.setAddedTrains(new TrainDetailsListAdapter(view.getActivity(), DatabaseDAO.getTrains().values(), this));
    }

    @Override
    public void removeTrain(String trainName) {
        DatabaseDAO.removeTrain(trainName);
        reloadAddedTrains();
    }

    @Override
    public void onAddTrainButtonClicked() {
        AddTrainDialogFragment fragment = AddTrainDialogFragment.newInstance(this);
        TrainDetailsActivity activity = (TrainDetailsActivity) view.getActivity();
        fragment.show(activity.getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onAddedTrain(String companyName, String name) {
        PreferencesService.setTrainReloadTime(0);
        DatabaseDAO.addTrain(companyName, name);
        reloadAddedTrains();
    }
}
