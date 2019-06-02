package dev.itsu.gakusapo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.MemoDialogPresenter;
import dev.itsu.gakusapo.presenter.contract.MemoDialogContract;
import dev.itsu.gakusapo.presenter.contract.TodayAndTomorrowContract;

import java.lang.ref.WeakReference;

public class MemoDialogFragment extends DialogFragment implements MemoDialogContract.View {

    private MemoDialogContract.Presenter presenter;
    private TodayAndTomorrowContract.Presenter todayAndTomorrowPresenter;
    private WeakReference<Dialog> dialog;
    private String memo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.presenter = new MemoDialogPresenter(this, todayAndTomorrowPresenter);

        dialog = new WeakReference<>(new Dialog(getActivity()));
        dialog.get().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.get().setContentView(R.layout.dialog_today_memo);
        dialog.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.get().findViewById(R.id.memoCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCloseButtonClicked();
                dialog.get().dismiss();
            }
        });

        setMemo(memo);

        return dialog.get();
    }

    @Override
    public void setMemo(String memo) {
        TextView textView = dialog.get().findViewById(R.id.dialogMemoField);
        textView.setText(memo);
    }

    @Override
    public String getMemo() {
        TextView textView = dialog.get().findViewById(R.id.dialogMemoField);
        return textView.getText().toString();
    }

    public static MemoDialogFragment newInstance(TodayAndTomorrowContract.Presenter presenter, String memo) {
        MemoDialogFragment fragment = new MemoDialogFragment();
        fragment.todayAndTomorrowPresenter = presenter;
        fragment.memo = memo;
        return fragment;
    }

}
