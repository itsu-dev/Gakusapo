package dev.itsu.gakusapo.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.RegisterSubjectDialogPresenter;
import dev.itsu.gakusapo.presenter.contract.RegisterSubjectDialogContract;
import dev.itsu.gakusapo.presenter.contract.TimetableContract;

public class RegisterSubjectDialogFragment extends DialogFragment implements RegisterSubjectDialogContract.View {

    private RegisterSubjectDialogContract.Presenter presenter;
    private TimetableContract.Presenter timetablePresenter;
    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.presenter = new RegisterSubjectDialogPresenter(this, timetablePresenter);

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_subject_setting);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.dialogRegisterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClicked();
                dialog.dismiss();
            }
        });

        setColorButtonListener();

        return dialog;
    }

    @Override
    public void setBackgroundColor(int color) {
        View view = dialog.findViewById(R.id.dialogBackgroundView);
        view.setBackgroundColor(getActivity().getResources().getColor(color));
    }

    @Override
    public String getName() {
        TextView textView = dialog.findViewById(R.id.dialogName);
        return textView.getText().toString();
    }

    @Override
    public String getClassName() {
        TextView textView = dialog.findViewById(R.id.dialogClassName);
        return textView.getText().toString();
    }

    @Override
    public void setName(String name) {
        TextView textView = dialog.findViewById(R.id.dialogName);
        textView.setText(name);
    }

    @Override
    public void setClassName(String name) {
        TextView textView = dialog.findViewById(R.id.dialogClassName);
        textView.setText(name);
    }

    @Override
    public String getDescription() {
        TextView textView = dialog.findViewById(R.id.dialogDescription);
        return textView.getText().toString();
    }

    @Override
    public void setDescription(String description) {
        TextView textView = dialog.findViewById(R.id.dialogDescription);
        textView.setText(description);
    }

    private void setTimetablePresenter(TimetableContract.Presenter presenter) {
        this.timetablePresenter = presenter;
    }

    public static RegisterSubjectDialogFragment newInstance(TimetableContract.Presenter presenter) {
        RegisterSubjectDialogFragment fragment = new RegisterSubjectDialogFragment();
        fragment.setTimetablePresenter(presenter);
        return fragment;
    }

    private void setColorButtonListener() {
        dialog.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectBrown);
            }
        });
        dialog.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDarkBlue);
            }
        });
        dialog.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDarkGreen);
            }
        });
        dialog.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDarkOrange);
            }
        });
        dialog.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDarkPurple);
            }
        });
        dialog.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDarkYellow);
            }
        });
        dialog.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectDiamond);
            }
        });
        dialog.findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectForest);
            }
        });
        dialog.findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectGray);
            }
        });
        dialog.findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectIndigo);
            }
        });
        dialog.findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectLightBlue);
            }
        });
        dialog.findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectLightGreen);
            }
        });
        dialog.findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectLightOrange);
            }
        });
        dialog.findViewById(R.id.button15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectLightPurple);
            }
        });
        dialog.findViewById(R.id.button16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectMagenta);
            }
        });
        dialog.findViewById(R.id.button17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectOrange);
            }
        });
        dialog.findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClicked(R.color.subjectRed);
            }
        });
    }

}
