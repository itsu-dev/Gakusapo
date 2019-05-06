package dev.itsu.gakusapo.api.task.train;

import android.os.AsyncTask;
import dev.itsu.gakusapo.presenter.contract.InformationContract;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetTrainInfoTask extends AsyncTask<Void, Void, String> {

    private WeakReference<InformationContract.Presenter> presenter;

    public GetTrainInfoTask(InformationContract.Presenter presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    @Override
    protected String doInBackground(Void... data) {
        URL url;
        try {
            url = new URL("https://rti-giken.jp/fhc/api/train_tetsudo/delay.json");
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
        presenter.get().onTrainInfoGot(json);
    }
}