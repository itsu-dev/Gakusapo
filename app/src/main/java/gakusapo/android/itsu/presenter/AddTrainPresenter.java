package gakusapo.android.itsu.presenter;

import android.widget.ArrayAdapter;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.task.train.ReadTrainJsonTask;
import gakusapo.android.itsu.db.DatabaseDAO;
import gakusapo.android.itsu.presenter.contract.AddTrainDialogContract;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;

import java.util.LinkedList;
import java.util.List;

public class AddTrainPresenter implements AddTrainDialogContract.Presenter {

    private AddTrainDialogContract.View view;
    private TrainDetailsContract.Presenter presenter;

    private LinkedList<String> trains;
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
            String[] data = selectedTrain.split(" ");
            presenter.onAddedTrain(data[0], data[1]);
            view.dismiss();
        }
    }

    @Override
    public void onListClicked(String name) {
        selectedTrain = name;
        String trainName = name.split(" ")[1];
        if (DatabaseDAO.existsTrain(trainName)) {
            view.setAlreadyExists(true);
            view.setButtonText(view.getActivity().getResources().getString(R.string.add_train_button_add_train, trainName));
        } else {
            view.setAlreadyExists(false);
            view.setButtonText(view.getActivity().getResources().getString(R.string.add_train_button_add_train, trainName));
        }
    }

    @Override
    public void onKeyTyped(String text) {
        List<String> newData = new LinkedList<>();
        for (String train : trains) {
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
    public void onJsonReaded(LinkedList<String> data) {
        this.trains = data;
        view.setListAdapter(new ArrayAdapter<>(view.getActivity(), android.R.layout.simple_list_item_1, data));
    }

}
