package gakusapo.android.itsu.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.presenter.TrainDetailsPresenter;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;
import gakusapo.android.itsu.ui.adapter.TrainDetailsListAdapter;

public class TrainDetailsActivity extends AppCompatActivity implements TrainDetailsContract.View {

    private TrainDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);

        this.presenter = new TrainDetailsPresenter(this);
        presenter.reloadAddedTrains();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setAddedTrains(TrainDetailsListAdapter adapter) {
        RecyclerView view = findViewById(R.id.trainDetailsAddedTrainList);
        view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(adapter);
    }
}
