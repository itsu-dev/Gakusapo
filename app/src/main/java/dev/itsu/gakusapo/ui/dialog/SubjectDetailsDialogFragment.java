package dev.itsu.gakusapo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dev.itsu.gakusapo.R;

import java.lang.ref.WeakReference;

public class SubjectDetailsDialogFragment extends DialogFragment {

    private WeakReference<Dialog> dialog;

    private String time;
    private String name;
    private String className;
    private String description;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new WeakReference<>(new Dialog(getActivity()));
        dialog.get().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.get().setContentView(R.layout.dialog_subject_details);
        dialog.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.get().findViewById(R.id.dialogDetailCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.get().dismiss();
            }
        });

        setTime(time);
        setName(name);
        setClassName(className);
        setDescription(description);

        return dialog.get();
    }

    public void setTime(String time) {
        TextView textView = dialog.get().findViewById(R.id.dialogDetailTime);
        textView.setText(time);
    }

    public void setName(String name) {
        TextView textView = dialog.get().findViewById(R.id.dialogDetailName);
        textView.setText(name);
    }

    public void setClassName(String name) {
        TextView textView = dialog.get().findViewById(R.id.dialogDetailClassName);
        textView.setText(name);
    }

    public void setDescription(String description) {
        TextView textView = dialog.get().findViewById(R.id.dialogDetailDescription);
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
