package gakusapo.android.itsu.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.entity.Train;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainDetailsListAdapter extends RecyclerView.Adapter<TrainDetailsListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Train> trains;
    private Context context;

    public TrainDetailsListAdapter(Context context, Collection<Train> trains) {
        this.context = context;
        this.trains = new ArrayList<>(trains);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public TrainDetailsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.part_traindetail_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrainDetailsListAdapter.ViewHolder viewHolder, int i) {
        if (trains != null && trains.size() > i && trains.get(i) != null) {
            viewHolder.name.setText(trains.get(i).getName());
            viewHolder.company.setText(trains.get(i).getCompany());
        }
    }

    @Override
    public int getItemCount() {
        return trains != null ? trains.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView company;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trainDetailTrainName);
            company = itemView.findViewById(R.id.trainDetailCompany);
        }
    }
}

