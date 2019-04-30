package gakusapo.android.itsu.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.presenter.contract.InformationContract;
import gakusapo.android.itsu.presenter.InformationPresenter;

public class InformationFragment extends Fragment implements InformationContract.View, SwipeRefreshLayout.OnRefreshListener {

    private InformationContract.Presenter presenter;
    private View view;
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        this.presenter = new InformationPresenter(this);
        presenter.reloadWeatherForecast();
        presenter.reloadTrainInfo();

        this.swipe = this.view.findViewById(R.id.informationRefresh);
        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        this.view.findViewById(R.id.trainInfoSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTrainInfoSettingButtonClicked();
            }
        });

        this.view.findViewById(R.id.weatherDetailsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onWeatherDetailsButtonClicked();
            }
        });
    }

    @Override
    public void setWeatherCity(String title) {
        TextView textView = view.findViewById(R.id.weatherCity);
        textView.setText(title);
    }

    @Override
    public void setHumidity(int humidity) {
        TextView textView = view.findViewById(R.id.weatherHumidity);
        textView.setText(humidity + "%");
    }

    @Override
    public void setSunset(String time) {
        TextView textView = view.findViewById(R.id.weatherSunset);
        textView.setText(time);
    }

    @Override
    public void setTemp(String temp) {
        TextView textView = view.findViewById(R.id.weatherTemp);
        textView.setText(temp);
    }

    @Override
    public void setIcon(Bitmap icon) {
        ImageView imageView = view.findViewById(R.id.weatherIcon);
        imageView.setImageBitmap(icon);
    }

    @Override
    public void setImage(Bitmap image) {
        ImageView imageView = view.findViewById(R.id.weatherImage);
        imageView.setImageBitmap(image);
    }

    @Override
    public void addTrainInfo(String trainName, int statusId) {
        LinearLayout layout = view.findViewById(R.id.trainInfoLayout);
        View view;

        for (int i = 0; i < layout.getChildCount(); i++) {
            view = layout.getChildAt(i);
            if (!(view instanceof TextView)) continue;

            TextView trainNameView = (TextView) view;
            if (trainNameView.getText().toString().contains(trainName)) {
                trainNameView.setVisibility(View.GONE);
                TextView trainStatusView = (TextView) layout.getChildAt(i + 1);
                trainStatusView.setVisibility(View.GONE);
            }
        }

        int padding8 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_8);
        int padding16 = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_16);

        TextView name = new TextView(getActivity());
        name.setPadding(padding16, padding8, padding16, padding8);
        name.setText(trainName);

        TextView status = new TextView(getActivity());
        status.setPadding(padding16, 0, padding16, padding8);
        status.setText(statusId);

        if (statusId == R.string.information_train_unavailable) status.setTextColor(getActivity().getColor(R.color.red));
        else status.setTextColor(getActivity().getColor(R.color.black));

        layout.addView(name);
        layout.addView(status);
    }

    @Override
    public void removeAllTrainInfo() {
        LinearLayout layout = view.findViewById(R.id.trainInfoLayout);
        View view;

        for (int i = 0; i < layout.getChildCount(); i++) {
            view = layout.getChildAt(i);
            if (!(view instanceof TextView)) continue;
            layout.removeView(view);
        }
    }

    @Override
    public void showRefreshingToast() {
        Toast.makeText(this.getActivity(), R.string.information_now_refreshing, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshedToast() {
        Toast.makeText(this.getActivity(), R.string.information_refreshed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshErrorToast() {
        Toast.makeText(this.getActivity(), R.string.information_refresh_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRefreshing(boolean bool) {
        swipe.setRefreshing(bool);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }
}
