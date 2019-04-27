package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.api.service.TrainDBService;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;
import gakusapo.android.itsu.ui.adapter.TrainDetailsListAdapter;

public class TrainDetailsPresenter implements TrainDetailsContract.Presenter {

    private TrainDetailsContract.View view;

    public TrainDetailsPresenter(TrainDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadAddedTrains() {
        TrainDBService service = new TrainDBService(view.getActivity());
        view.setAddedTrains(new TrainDetailsListAdapter(view.getActivity(), service.getTrains().values()));
    }

    @Override
    public void removeTrain(String trainName) {
        System.out.println("=======================ANAL");
    }
}
