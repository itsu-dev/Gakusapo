package dev.itsu.gakusapo.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.TrainDetailsPresenter;
import dev.itsu.gakusapo.presenter.contract.TrainDetailsContract;
import dev.itsu.gakusapo.ui.adapter.TrainDetailsListAdapter;

public class TrainDetailsActivity extends AppCompatActivity implements TrainDetailsContract.View {

    private TrainDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);

        this.presenter = new TrainDetailsPresenter(this);
        presenter.reloadAddedTrains();

        findViewById(R.id.trainDetailsTrainAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddTrainButtonClicked();
            }
        });
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
