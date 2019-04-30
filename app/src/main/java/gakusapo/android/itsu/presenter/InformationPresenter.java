package gakusapo.android.itsu.presenter;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.TrainDBService;
import gakusapo.android.itsu.api.task.train.GetTrainInfoTask;
import gakusapo.android.itsu.api.task.weather.GetWeatherForecastIconTask;
import gakusapo.android.itsu.api.task.weather.GetWeatherForecastTask;
import gakusapo.android.itsu.entity.Train;
import gakusapo.android.itsu.presenter.contract.InformationContract;
import gakusapo.android.itsu.ui.activity.MainActivity;
import gakusapo.android.itsu.ui.activity.TrainDetailsActivity;
import gakusapo.android.itsu.utils.LocationProviderClient;
import gakusapo.android.itsu.utils.TrainInfoFormatter;
import gakusapo.android.itsu.utils.WeatherFormatter;

import java.util.*;

public class InformationPresenter implements InformationContract.Presenter {

    private InformationContract.View view;

    private boolean isRefreshing;

    public InformationPresenter(InformationContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadWeatherForecast() {
        MainActivity.setInformationPresenter(this);
        if (ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            onGPSPermissionGranted();
        }
    }

    @Override
    public void onGPSPermissionGranted() {
        try {
            if (ActivityCompat.checkSelfPermission(view.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(view.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationProviderClient client = new LocationProviderClient(view.getActivity()) {
                    @Override
                    public void onSuccess(Location location) {
                        onLocationChanged(location);
                    }
                };
                client.startListener();
            }
        } catch (SecurityException e) {
            onGPSPermissionNotGranted();
        }
    }

    @Override
    public void onGPSPermissionNotGranted() {
        Toast.makeText(view.getActivity(), R.string.mainactivity_permission_error, Toast.LENGTH_SHORT).show();
        view.getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    @Override
    public void onLocationChanged(Location location) {
        GetWeatherForecastTask task = new GetWeatherForecastTask(this);
        task.execute(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onWeatherLoaded(Map<String, Object> data) {
        try {
            Map<String, Object> weatherData = (Map<String, Object>) data.get("latest");
            view.setTemp(view.getActivity().getResources().getString(R.string.information_forecast_temp, String.valueOf(weatherData.get("temp"))));
            view.setHumidity(Integer.parseInt(String.valueOf(weatherData.get("humidity"))));
            view.setSunset(String.valueOf(weatherData.get("sunset")));
            view.setWeatherCity(view.getActivity().getResources().getString(R.string.information_forecast_city, String.valueOf(((Map<String, Object>) data.get("city")).get("name")), String.valueOf(weatherData.get("time"))));
            view.setImage(WeatherFormatter.getImage(String.valueOf(weatherData.get("name"))));

            GetWeatherForecastIconTask task = new GetWeatherForecastIconTask(this);
            task.execute(String.valueOf(weatherData.get("icon")));

            onRefreshed();
        } catch (Exception e) {
            view.setWeatherCity("Error");
            onRefreshError();
        }
    }

    @Override
    public void onWeatherIconGot(Bitmap bitmap) {
        view.setIcon(bitmap);
    }

    @Override
    public void onWeatherCityGot(String json) {
        view.setWeatherCity(WeatherFormatter.getCity(json));
    }

    @Override
    public void reloadTrainInfo() {
        TrainDBService service = new TrainDBService(view.getActivity());
        service.addTrain("JR東日本", "山手線");
        service.addTrain("JR西日本", "芸備線");

        view.removeAllTrainInfo();
        GetTrainInfoTask task = new GetTrainInfoTask(this);
        task.execute();
    }

    @Override
    public void onTrainInfoGot(String json) {
        List<Map<String, Object>> data = TrainInfoFormatter.getData(json);
        TrainDBService service = new TrainDBService(view.getActivity());
        Collection<Train> registeredTrain = service.getTrains().values();

        for (Train train : registeredTrain) {
            int status = R.string.information_train_available;
            String trainName = train.getName();
            for (Map<String, Object> entry : data) {
                if (String.valueOf(entry.get("name")).contains(trainName)) {
                    status = R.string.information_train_unavailable;
                }
                view.addTrainInfo(train.getCompany() + " " + train.getName(), status);
            }
        }
    }

    @Override
    public void onTrainInfoSettingButtonClicked() {
        ImageView trainImage = view.getActivity().findViewById(R.id.trainImage);
        TextView trainTitle = view.getActivity().findViewById(R.id.trainTitle);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                view.getActivity(),
                Pair.create((View) trainImage, "trainImage"),
                Pair.create((View) trainTitle, "trainTitle")
        );

        view.getActivity().startActivity(new Intent(view.getActivity(), TrainDetailsActivity.class), options.toBundle());
    }

    @Override
    public void onWeatherDetailsButtonClicked() {
        //TODO
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            view.showRefreshingToast();
            return;
        }

        isRefreshing = true;
        reloadTrainInfo();
        reloadWeatherForecast();
    }

    @Override
    public void onRefreshed() {
        isRefreshing = false;
        view.showRefreshedToast();
        view.setRefreshing(false);
    }

    @Override
    public void onRefreshError() {
        isRefreshing = false;
        view.showRefreshErrorToast();
        view.setRefreshing(false);
    }

}
