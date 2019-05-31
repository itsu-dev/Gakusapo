package dev.itsu.gakusapo.api.task;

import android.os.AsyncTask;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.TimetablePresenter;

public class LoadTimetableTask extends AsyncTask<String, Void, Timetable> {

    private TimetablePresenter presenter;

    public LoadTimetableTask(TimetablePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Timetable doInBackground(String... name) {
        return DatabaseDAO.getTimetable(name[0]);
    }

    @Override
    protected void onPostExecute(Timetable timetable) {
        presenter.onTimetableLoadedFromDatabase(timetable);
    }
}