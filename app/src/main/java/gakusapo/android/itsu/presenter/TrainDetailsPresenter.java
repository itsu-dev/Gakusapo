package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.db.DatabaseDAO;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;
import gakusapo.android.itsu.ui.activity.TrainDetailsActivity;
import gakusapo.android.itsu.ui.adapter.TrainDetailsListAdapter;
import gakusapo.android.itsu.ui.fragment.AddTrainDialogFragment;

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
        DatabaseDAO.addTrain(companyName, name);
        reloadAddedTrains();
    }
}
