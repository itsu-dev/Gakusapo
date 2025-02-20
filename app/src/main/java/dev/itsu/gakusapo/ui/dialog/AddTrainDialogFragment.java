package dev.itsu.gakusapo.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.AddTrainPresenter;
import dev.itsu.gakusapo.presenter.contract.AddTrainDialogContract;
import dev.itsu.gakusapo.presenter.contract.TrainDetailsContract;

import java.lang.ref.WeakReference;

public class AddTrainDialogFragment extends DialogFragment implements AddTrainDialogContract.View {

    private AddTrainDialogContract.Presenter presenter;
    private TrainDetailsContract.Presenter trainPresenter;
    private WeakReference<Dialog> dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new WeakReference<>(new Dialog(getActivity()));
        dialog.get().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.get().setContentView(R.layout.dialog_trainchooser);
        dialog.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        presenter = new AddTrainPresenter(this, trainPresenter);
        presenter.reloadJson();

        dialog.get().findViewById(R.id.addTrainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddButtonClicked();
            }
        });

        final ListView listView = dialog.get().findViewById(R.id.addTrainList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onListClicked(String.valueOf(listView.getAdapter().getItem(position)));
            }
        });

        final EditText search = dialog.get().findViewById(R.id.addTrainTextField);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                presenter.onKeyTyped(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return dialog.get();
    }

    @Override
    public void setNotFound(boolean bool) {
        if (bool) {
            dialog.get().findViewById(R.id.addTrainNotFoundError).setVisibility(View.VISIBLE);
            //dialog.get().findViewById(R.id.addTrainList).setVisibility(View.VISIBLE);
            Button button = dialog.get().findViewById(R.id.addTrainButton);
            button.setText(R.string.add_train_button_add);
            button.setClickable(false);

        } else {
            dialog.get().findViewById(R.id.addTrainNotFoundError).setVisibility(View.GONE);
            //dialog.get().findViewById(R.id.addTrainList).setVisibility(View.INVISIBLE);
            Button button = dialog.get().findViewById(R.id.addTrainButton);
            button.setText(R.string.add_train_button_add);
            button.setClickable(true);
        }
    }

    @Override
    public void setAlreadyExists(boolean bool) {
        if (bool) {
            dialog.get().findViewById(R.id.addTrainExistsError).setVisibility(View.VISIBLE);
            Button button = dialog.get().findViewById(R.id.addTrainButton);
            button.setClickable(false);

        } else {
            dialog.get().findViewById(R.id.addTrainExistsError).setVisibility(View.GONE);
            Button button = dialog.get().findViewById(R.id.addTrainButton);
            button.setClickable(true);
        }
    }

    @Override
    public void setListAdapter(ArrayAdapter<String> adapter) {
        ListView listView = dialog.get().findViewById(R.id.addTrainList);
        listView.setAdapter(adapter);
    }

    @Override
    public void setAddButtonEnabled(boolean bool) {
        Button button = dialog.get().findViewById(R.id.addTrainButton);
        button.setClickable(bool);
    }

    @Override
    public void setButtonText(String text) {
        Button button = dialog.get().findViewById(R.id.addTrainButton);
        button.setText(text);
    }

    public static AddTrainDialogFragment newInstance(TrainDetailsContract.Presenter presenter) {
        AddTrainDialogFragment fragment = new AddTrainDialogFragment();
        fragment.trainPresenter = presenter;
        return fragment;
    }
}
