package dev.itsu.gakusapo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.SettingPresenter;
import dev.itsu.gakusapo.presenter.contract.SettingContract;

public class SettingFragment extends Fragment implements SettingContract.View {

    private SettingContract.Presenter presenter;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        presenter = new SettingPresenter(this);
        presenter.reloadData();

        final Spinner spinner = view.findViewById(R.id.settingTimetableSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onTimetableSpinnerSelected(String.valueOf(spinner.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.view.findViewById(R.id.settingSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSaveButtonClicked();
            }
        });

        this.view.findViewById(R.id.settingHowToUseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onHowToUseButtonClicked();
            }
        });

        this.view.findViewById(R.id.settingOSLicenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOSLicenseButtonClicked();
            }
        });

        this.view.findViewById(R.id.settingLicenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPrivacyPolicyButtonClicked();
            }
        });

        this.view.findViewById(R.id.settingFormulaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFormulaButtonClicked();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        ((AdView) view.findViewById(R.id.settingAdView)).loadAd(adRequest);
    }

    @Override
    public void setTimetableItem(String[] item) {
        if (item == null) return;
        Spinner spinner = view.findViewById(R.id.settingTimetableSpinner);
        spinner.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, item));
    }

    @Override
    public void setNotificationTime(String time) {
        TextView textView = view.findViewById(R.id.settingNotificationTime);
        textView.setText(time);
    }

    @Override
    public void setScheduleTime(String time) {
        TextView textView = view.findViewById(R.id.settingScheduleTime);
        textView.setText(time);
    }

    @Override
    public String getNotificationTime() {
        TextView textView = view.findViewById(R.id.settingNotificationTime);
        return textView.getText().toString();
    }

    @Override
    public String getScheduleTime() {
        TextView textView = view.findViewById(R.id.settingScheduleTime);
        return textView.getText().toString();
    }

    @Override
    public void showNotificationError(boolean bool) {
        if (bool) {
            view.findViewById(R.id.settingNotificationError).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.settingNotificationError).setVisibility(View.GONE);
        }
    }

    @Override
    public void showScheduleError(boolean bool) {
        if (bool) {
            view.findViewById(R.id.settingScheduleError).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.settingScheduleError).setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
