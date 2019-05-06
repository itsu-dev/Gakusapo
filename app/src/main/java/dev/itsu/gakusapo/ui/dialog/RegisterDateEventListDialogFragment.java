package dev.itsu.gakusapo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.RegisterDateEventListDialogPresenter;
import dev.itsu.gakusapo.presenter.contract.RegisterDateEventListDialogContract;
import dev.itsu.gakusapo.presenter.contract.onDestroy;
import dev.itsu.gakusapo.ui.adapter.DateEventContentAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public class RegisterDateEventListDialogFragment extends DialogFragment implements RegisterDateEventListDialogContract.View {

    private RegisterDateEventListDialogContract.Presenter presenter;
    private onDestroy.Presenter todayAndTomorrowPresenter;
    private WeakReference<Dialog> dialog;

    private List<String> data;
    private int titleId;
    private int type;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.presenter = new RegisterDateEventListDialogPresenter(this, todayAndTomorrowPresenter, type, data);

        dialog = new WeakReference<>(new Dialog(getActivity()));
        dialog.get().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.get().setContentView(R.layout.dialog_today_list);
        dialog.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.get().findViewById(R.id.dialogListCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onListCloseButtonClicked();
                dialog.get().dismiss();
            }
        });

        dialog.get().findViewById(R.id.dialogAddContentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddContentButtonClicked();
            }
        });

        setTitle(titleId);
        setListContents(new DateEventContentAdapter(getActivity(), data, presenter));

        data = null;

        return dialog.get();
    }

    @Override
    public void setListContents(DateEventContentAdapter adapter) {
        RecyclerView listView = dialog.get().findViewById(R.id.dialogListView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(adapter);
    }

    @Override
    public void setTitle(int id) {
        TextView textView = dialog.get().findViewById(R.id.dialogListTitle);
        textView.setText(id);
    }

    @Override
    public void setContentNameField(String text) {
        TextView textView = dialog.get().findViewById(R.id.dialogContentFIeld);
        textView.setText(text);
    }

    @Override
    public String getContentNameField() {
        TextView textView = dialog.get().findViewById(R.id.dialogContentFIeld);
        return textView.getText().toString();
    }

    public static RegisterDateEventListDialogFragment newInstance(onDestroy.Presenter presenter, List<String> data, int titleId, int type) {
        RegisterDateEventListDialogFragment fragment = new RegisterDateEventListDialogFragment();
        fragment.todayAndTomorrowPresenter = presenter;
        fragment.data = data;
        fragment.titleId = titleId;
        fragment.type = type;
        return fragment;
    }

}