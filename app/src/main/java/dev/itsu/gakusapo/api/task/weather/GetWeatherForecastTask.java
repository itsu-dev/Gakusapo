package dev.itsu.gakusapo.api.task.weather;

import android.os.AsyncTask;
import dev.itsu.gakusapo.api.service.WeatherService;
import dev.itsu.gakusapo.presenter.contract.InformationContract;

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
