package gakusapo.android.itsu.information;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherForecastIconTask extends AsyncTask<String, Void, Bitmap> {

    private InformationContract.Presenter presenter;

    GetWeatherForecastIconTask(InformationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Bitmap doInBackground(String... data) {
        URL url;
        try {
            url = new URL(String.format("http://openweathermap.org/img/w/%s.png", data[0]));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            InputStream stream = connection.getInputStream();
            ByteArrayOutputStream dataOutStream = new ByteArrayOutputStream();
            byte[] b = new byte[4096];
            int readByte = 0;

            while (-1 != (readByte = stream.read(b))) {
                dataOutStream.write(b, 0, readByte);
            }

            stream.close();
            dataOutStream.close();

            return BitmapFactory.decodeByteArray(dataOutStream.toByteArray(), 0, dataOutStream.toByteArray().length);

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        presenter.onWeatherIconGot(bitmap);
    }
}