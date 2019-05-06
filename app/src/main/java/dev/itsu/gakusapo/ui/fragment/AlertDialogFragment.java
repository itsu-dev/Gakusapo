package dev.itsu.gakusapo.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.AlertDialogPresenter;
import dev.itsu.gakusapo.presenter.contract.AlertDialogContract;

public class AlertDialogFragment extends DialogFragment implements AlertDialogContract.View {

    private AlertDialogContract.Presenter presenter;
    private Dialog dialog;

    private int titleId;
    private int contentId;
    private int positiveId;
    private int negativeId;
    private boolean cancellable;

    private AlertDialogFragment.OnClickListener negativeButtonListener;
    private AlertDialogFragment.OnClickListener positiveButtonListener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        presenter = new AlertDialogPresenter(this);

        dialog = new Dialog(getActivity());

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.dialogAlertNegative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNegativeButtonClicked();
            }
        });

        dialog.findViewById(R.id.dialogAlertPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPositiveButtonClicked();
            }
        });

        setTitle(titleId);
        setContent(contentId);
        setNegativeButton(negativeId);
        setPositiveButton(positiveId);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(cancellable);
    }

    @Override
    public void setTitle(int id) {
        TextView textView = dialog.findViewById(R.id.dialogAlertTitle);
        textView.setText(id);
    }

    @Override
    public void setContent(int id) {
        TextView textView = dialog.findViewById(R.id.dialogAlertContent);
        textView.setText(id);
    }

    @Override
    public void setNegativeButton(int id) {
        Button button = dialog.findViewById(R.id.dialogAlertNegative);
        button.setText(id);
    }

    @Override
    public void setPositiveButton(int id) {
        Button button = dialog.findViewById(R.id.dialogAlertPositive);
        button.setText(id);
    }

    public static AlertDialogFragment newInstance(int titleId, int contentId, int negativeId, int positiveId, boolean cancelable) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.titleId = titleId;
        fragment.contentId = contentId;
        fragment.negativeId = negativeId;
        fragment.positiveId = positiveId;
        fragment.cancellable = cancelable;
        return fragment;
    }

    public void setNegativeButtonListener(OnClickListener negativeButtonListener) {
        this.negativeButtonListener = negativeButtonListener;
    }

    public void setPositiveButtonListener(OnClickListener positiveButtonListener) {
        this.positiveButtonListener = positiveButtonListener;
    }

    @Override
    public void onNegativeButtonClicked() {
        if (negativeButtonListener != null) negativeButtonListener.onClicked();
    }

    @Override
    public void onPositiveButtonClicked() {
        if (positiveButtonListener != null) positiveButtonListener.onClicked();
    }

    public interface OnClickListener {
        void onClicked();
    }

}
