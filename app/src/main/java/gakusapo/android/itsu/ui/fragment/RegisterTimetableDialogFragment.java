package gakusapo.android.itsu.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.presenter.RegisterTimetableDialogPresenter;
import gakusapo.android.itsu.presenter.contract.RegisterTimetableDialogContract;
import gakusapo.android.itsu.presenter.contract.TimetableContract;

public class RegisterTimetableDialogFragment extends DialogFragment implements RegisterTimetableDialogContract.View {

    private RegisterTimetableDialogContract.Presenter presenter;
    private TimetableContract.Presenter timetablePresenter;
    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.presenter = new RegisterTimetableDialogPresenter(this, timetablePresenter);

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_timetable_type);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.dialogTimetableCreateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateButtonClicked();
                dialog.dismiss();
            }
        });

        return dialog;
    }

    @Override
    public String getTimetableName() {
        TextView textView = dialog.findViewById(R.id.dialogTimetableNewName);
        return textView.getText().toString();
    }

    @Override
    public String getDayType() {
        Spinner spinner = dialog.findViewById(R.id.dialogDayTypeSpinner);
        return String.valueOf(spinner.getSelectedItem());
    }

    @Override
    public String getTimeType() {
        Spinner spinner = dialog.findViewById(R.id.dialogTimeTypeSpinner);
        return String.valueOf(spinner.getSelectedItem());
    }

    @Override
    public void showErrorText() {
        dialog.findViewById(R.id.dialogErrorAlreadyExists).setVisibility(View.VISIBLE);
    }

    private void setTimetablePresenter(TimetableContract.Presenter presenter) {
        this.timetablePresenter = presenter;
    }

    public static RegisterTimetableDialogFragment newInstance(TimetableContract.Presenter presenter) {
        RegisterTimetableDialogFragment fragment = new RegisterTimetableDialogFragment();
        fragment.setTimetablePresenter(presenter);
        return fragment;
    }

}
