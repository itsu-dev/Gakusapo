package gakusapo.android.itsu.information;

import android.os.AsyncTask;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherForecastTask extends AsyncTask<Double, Void, String> {

    private InformationContract.Presenter presenter;

    GetWeatherForecastTask(InformationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Double... data) {
        URL url;
        try {
            url = new URL(String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&APPID=%s", data[0], data[1], "000d34efd13d2d874b623c232aa563cb"));
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
        presenter.onWeatherLoaded(json);
    }

}
