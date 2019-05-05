package gakusapo.android.itsu.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.entity.Train;
import gakusapo.android.itsu.presenter.TrainDetailsPresenter;
import gakusapo.android.itsu.presenter.contract.TrainDetailsContract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainDetailsListAdapter extends RecyclerView.Adapter<TrainDetailsListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Train> trains;

    private TrainDetailsContract.Presenter presenter;

    public TrainDetailsListAdapter(Context context, Collection<Train> trains, TrainDetailsContract.Presenter presenter) {
        this.trains = new ArrayList<>(trains);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public TrainDetailsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.part_dateevent_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TrainDetailsListAdapter.ViewHolder viewHolder, int i) {
        if (trains != null && trains.size() > i && trains.get(i) != null) {
            viewHolder.name.setText(trains.get(i).getName());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.removeTrain(viewHolder.name.getText().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trains != null ? trains.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        private Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contentName);
            deleteButton = itemView.findViewById(R.id.contentDeleteButton);
        }
    }
}

