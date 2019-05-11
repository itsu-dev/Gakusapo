package dev.itsu.gakusapo.api.task.train;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.entity.Train;
import dev.itsu.gakusapo.presenter.contract.InformationContract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetTrainInfoTask extends AsyncTask<Train, Void, Map<String, String>> {

    private WeakReference<InformationContract.Presenter> presenter;

    public GetTrainInfoTask(InformationContract.Presenter presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    @Override
    protected Map<String, String> doInBackground(Train... data) {
        if (System.currentTimeMillis() - PreferencesService.getTrainReloadTime() < 600000) {//10min
            return new Gson().fromJson(PreferencesService.getTrainCache(), new TypeToken<Map<String, String>>(){}.getType());
        }

        Map<String, String> result = new LinkedHashMap<>();

        for (Train train : data) {
            String description = analyze(train.getURL());
            if (description != null) {
                result.put(train.getName(), description);
            } else {
                return null;
            }
        }

        PreferencesService.setTrainCache(new Gson().toJson(result));
        PreferencesService.setTrainReloadTime(System.currentTimeMillis());
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, String> data) {
        presenter.get().onTrainInfoGot(data);
    }

    private static String analyze(String urlText) {
        try {
            URL url = new URL(urlText);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Language", "ja,en-US;q=0.9,en;q=0.8");
            connection.setDoOutput(false);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String temp;

            while ((temp = reader.readLine()) != null) {
                buffer.append(temp + "\n");
            }

            reader.close();

            Document document = Jsoup.parse(buffer.toString());
            Elements elements = document.select(".traininfo li");
            if (elements.select("p").size() == 0) {
                return elements.text();
            } else {
                return elements.select(".description").text() + "（" + elements.select(".time").text() + "）";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}