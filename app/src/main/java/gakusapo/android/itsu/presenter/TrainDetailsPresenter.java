package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.db.DatabaseDAO;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;
import gakusapo.android.itsu.ui.adapter.TrainDetailsListAdapter;

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
}
