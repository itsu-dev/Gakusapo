package gakusapo.android.itsu.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.presenter.MemoDialogPresenter;
import gakusapo.android.itsu.presenter.contract.MemoDialogContract;
import gakusapo.android.itsu.presenter.contract.TodayAndTomorrowContract;

public class MemoDialogFragment extends DialogFragment implements MemoDialogContract.View {

    private MemoDialogContract.Presenter presenter;
    private TodayAndTomorrowContract.Presenter todayAndTomorrowPresenter;
    private Dialog dialog;
    private String memo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.presenter = new MemoDialogPresenter(this, todayAndTomorrowPresenter);

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_today_memo);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.memoCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCloseButtonClicked();
                dialog.dismiss();
            }
        });

        setMemo(memo);

        return dialog;
    }

    @Override
    public void setMemo(String memo) {
        TextView textView = dialog.findViewById(R.id.dialogMemoField);
        textView.setText(memo);
    }

    @Override
    public String getMemo() {
        TextView textView = dialog.findViewById(R.id.dialogMemoField);
        return textView.getText().toString();
    }

    public static MemoDialogFragment newInstance(TodayAndTomorrowContract.Presenter presenter, String memo) {
        MemoDialogFragment fragment = new MemoDialogFragment();
        fragment.todayAndTomorrowPresenter = presenter;
        fragment.memo = memo;
        return fragment;
    }

}
