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
import gakusapo.android.itsu.presenter.contract.RegisterDateEventListDialogContract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DateEventContentAdapter extends RecyclerView.Adapter<DateEventContentAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> data;
    private RegisterDateEventListDialogContract.Presenter presenter;

    public DateEventContentAdapter(Context context, Collection<String> data, RegisterDateEventListDialogContract.Presenter presenter) {
        this.data = new ArrayList<>(data);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DateEventContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DateEventContentAdapter.ViewHolder(inflater.inflate(R.layout.part_dateevent_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DateEventContentAdapter.ViewHolder viewHolder, int i) {
        if (data != null && data.size() > i && data.get(i) != null) {
            final int position = i;
            viewHolder.name.setText(data.get(i));
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onContentDeleteButtonClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contentName);
            delete = itemView.findViewById(R.id.contentDeleteButton);
        }
    }
}
