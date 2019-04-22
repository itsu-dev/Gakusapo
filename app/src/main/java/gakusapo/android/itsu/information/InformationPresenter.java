package gakusapo.android.itsu.information;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import gakusapo.andoid.itsu.R;
import gakusapo.android.itsu.mainactivity.MainActivity;
import gakusapo.android.itsu.utils.TrainInfoFormatter;
import gakusapo.android.itsu.utils.WeatherFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformationPresenter implements InformationContract.Presenter, LocationListener {

    private InformationContract.View view;

    private Location location;

    InformationPresenter(InformationContract.View view) {
        this.view = view;
    }

    @Override
    public Map<String, Integer> getPosition() {
        return null;
    }

    @Override
    public void reloadWeatherForecast() {
        MainActivity.setInformationPresenter(this);
        if (ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            onGPSPermissionGranted();
        }
    }

    @Override
    public void onGPSPermissionGranted() {
        LocationManager manager = (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (manager != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);
            } catch (SecurityException e) {
                Toast toast = Toast.makeText(view.getActivity(), R.string.mainactivity_permission_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onGPSPermissionNotGranted() {
        Toast.makeText(view.getActivity(), R.string.mainactivity_permission_error, Toast.LENGTH_SHORT).show();
        view.getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        GetWeatherForecastTask task = new GetWeatherForecastTask(this);
        task.execute(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onWeatherLoaded(String json) {
        try {
            Map<String, Long> temps = WeatherFormatter.getTemps(json);
            view.setTemp(view.getActivity().getResources().getString(R.string.information_forecast_temp, temps.get("temp")));
            view.setMaxTemp(view.getActivity().getResources().getString(R.string.information_forecast_temp, temps.get("temp_max")));
            view.setMinTemp(view.getActivity().getResources().getString(R.string.information_forecast_temp, temps.get("temp_min")));

            Map<String, Object> weather = WeatherFormatter.getWeather(json);
            GetWeatherForecastIconTask task = new GetWeatherForecastIconTask(this);
            task.execute(String.valueOf(weather.get("icon")));

            view.setImage(WeatherFormatter.getImage(String.valueOf(weather.get("main"))));

            if (location != null) {
                GetWeatherForecastCityTask task1 = new GetWeatherForecastCityTask(this);
                task1.execute(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {
            view.setWeatherCity("Error");
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
        view.removeAllTrainInfo();
        GetTrainInfoTask task = new GetTrainInfoTask(this);
        task.execute();
    }

    @Override
    public void onTrainInfoGot(String json) {
        List<Map<String, Object>> data = TrainInfoFormatter.getData(json);
        List<String> registeredTrain = new ArrayList<>();
        registeredTrain.add("JR東日本:山手線");
        registeredTrain.add("JR東日本:五日市線");
        registeredTrain.add("JR北海道:函館本線");
        registeredTrain.add("JR西日本:芸備線");

        for (String str : registeredTrain) {
            int status = R.string.information_train_available;
            String trainName = str.split(":")[1];
            for (Map<String, Object> entry : data) {
                if (String.valueOf(entry.get("name")).contains(trainName)) {
                    status = R.string.information_train_unavailable;
                }
                view.addTrainInfo(str.split(":")[0] + " " + str.split(":")[1], status);
            }
        }
    }

    @Override
    public void onTrainInfoSettingButtonClicked() {

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
