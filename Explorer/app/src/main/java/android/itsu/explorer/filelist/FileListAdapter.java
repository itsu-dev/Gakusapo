package android.itsu.explorer.filelist;

import android.itsu.explorer.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FileListAdapter1 extends RecyclerView.Adapter<FileViewHolder> {

    private List<FileItem> list;

    public FileListAdapter1(List<FileItem> list) {
        this.list = list;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_filelist_normal, parent,false);
        FileViewHolder vh = new FileViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        holder.fileCellName.setText(list.get(position).getFileName());
        holder.fileCellIcon.setImageDrawable(list.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public FileItem getItem(int position) { return list.get(position); }
}