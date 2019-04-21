package gakusapo.android.itsu.information;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherForecastCityTask extends AsyncTask<Double, Void, String> {

    private InformationContract.Presenter presenter;

    GetWeatherForecastCityTask(InformationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Double... data) {
        URL url;
        try {
            url = new URL(String.format("http://geoapi.heartrails.com/api/json?method=searchByGeoLocation&x=%s&y=%s", data[1], data[0]));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            String temp;
            StringBuffer buffer = new StringBuffer();
            BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((temp = stream.readLine()) != null) {
                buffer.append(temp);
            }

            stream.close();
            return buffer.toString();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String json) {
        presenter.onWeatherCityGot(json);
    }

}
