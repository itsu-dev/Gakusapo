package dev.itsu.gakusapo.presenter;

import android.widget.ArrayAdapter;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.task.train.ReadTrainJsonTask;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.presenter.contract.AddTrainDialogContract;
import dev.itsu.gakusapo.presenter.contract.TrainDetailsContract;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class AddTrainPresenter implements AddTrainDialogContract.Presenter {

    private AddTrainDialogContract.View view;
    private TrainDetailsContract.Presenter presenter;

    private LinkedHashMap<String, String> trains;
    private String selectedTrain;

    public AddTrainPresenter(AddTrainDialogContract.View view, TrainDetailsContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void reloadJson() {
        ReadTrainJsonTask task = new ReadTrainJsonTask(this);
        task.execute();
    }

    @Override
    public void onAddButtonClicked() {
        if (selectedTrain != null) {
            presenter.onAddedTrain(selectedTrain, trains.get(selectedTrain));
            view.dismiss();
        }
    }

    @Override
    public void onListClicked(String name) {
        selectedTrain = name;
        if (DatabaseDAO.existsTrain(name)) {
            view.setAlreadyExists(true);
            view.setButtonText(view.getActivity().getResources().getString(R.string.add_train_button_add_train, name));
        } else {
            view.setAlreadyExists(false);
            view.setButtonText(view.getActivity().getResources().getString(R.string.add_train_button_add_train, name));
        }
    }

    @Override
    public void onKeyTyped(String text) {
        List<String> newData = new LinkedList<>();
        for (String train : trains.keySet()) {
            if (train.trim().contains(text)) newData.add(train);
        }
        if (!newData.isEmpty()) {
            view.setNotFound(false);
            view.setListAdapter(new ArrayAdapter<>(view.getActivity(), android.R.layout.simple_list_item_1, newData));
        } else {
            view.setNotFound(true);
        }
    }

    @Override
    public void onJsonReaded(LinkedHashMap<String, String> data) {
        this.trains = data;
        view.setListAdapter(new ArrayAdapter<>(view.getActivity(), android.R.layout.simple_list_item_1, data.keySet().toArray(new String[0])));
    }

}
