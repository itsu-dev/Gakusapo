package gakusapo.android.itsu.api.task.weather;

import android.os.AsyncTask;
import gakusapo.android.itsu.api.service.WeatherService;
import gakusapo.android.itsu.presenter.contract.InformationContract;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class GetWeatherForecastTask extends AsyncTask<Double, Void, Map<String, Object>> {

    private InformationContract.Presenter presenter;

    public GetWeatherForecastTask(InformationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Map<String, Object> doInBackground(Double... data) {
        WeatherService service = new WeatherService();
        return service.getWeather(data[0], data[1]);
    }

    @Override
    protected void onPostExecute(Map<String, Object> data) {
        presenter.onWeatherLoaded(data);
    }

}
