package dev.itsu.gakusapo.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dev.itsu.gakusapo.R;

public class SubjectDetailsDialogFragment extends DialogFragment {

    private Dialog dialog;

    private String time;
    private String name;
    private String className;
    private String description;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_subject_details);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.dialogDetailCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        setTime(time);
        setName(name);
        setClassName(className);
        setDescription(description);

        return dialog;
    }

    public void setTime(String time) {
        TextView textView = dialog.findViewById(R.id.dialogDetailTime);
        textView.setText(time);
    }

    public void setName(String name) {
        TextView textView = dialog.findViewById(R.id.dialogDetailName);
        textView.setText(name);
    }

    public void setClassName(String name) {
        TextView textView = dialog.findViewById(R.id.dialogDetailClassName);
        textView.setText(name);
    }

    public void setDescription(String description) {
        TextView textView = dialog.findViewById(R.id.dialogDetailDescription);
        textView.setText(description);
    }

    public static SubjectDetailsDialogFragment newInstance(String time, String name, String className, String description) {
        SubjectDetailsDialogFragment fragment = new SubjectDetailsDialogFragment();
        fragment.time = time;
        fragment.name = name;
        fragment.className = className;
        fragment.description = description;

        return fragment;
    }

}
