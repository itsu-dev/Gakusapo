package dev.itsu.gakusapo.api.task.train;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.itsu.gakusapo.MainApplication;
import dev.itsu.gakusapo.presenter.contract.AddTrainDialogContract;

import java.io.*;
import java.util.LinkedList;

public class ReadTrainJsonTask extends AsyncTask<Void, Void, String> {

    private AddTrainDialogContract.Presenter presenter;

    public ReadTrainJsonTask(AddTrainDialogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Void... data) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(MainApplication.getContext().getAssets().open("trains.json")));
            StringBuffer buf = new StringBuffer();
            String temp;
            while((temp = reader.readLine()) != null) {
                buf.append(temp);
            }
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String json) {
        if (json != null) presenter.onJsonReaded((LinkedList<String>) new Gson().fromJson(json, new TypeToken<LinkedList>(){}.getType()));
    }
}