package dev.itsu.gakusapo.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.ads.MobileAds;
import dev.itsu.gakusapo.api.notification.TimetableAlarmNotifier;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.presenter.contract.MainActivityContract;

import java.util.Calendar;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.INTERNET}, 1001);
        }
    }

    @Override
    public void initialize() {
        DatabaseDAO.openDatabase();
        MobileAds.initialize(view.getActivity(), "ca-app-pub-5096422984251090~5581770338");

        if (PreferencesService.getNotificationId() == 0) {
            Calendar triggerTime = Calendar.getInstance();
            triggerTime.set(Calendar.HOUR_OF_DAY, PreferencesService.getNotificationtime());
            triggerTime.set(Calendar.MINUTE, 0);
            triggerTime.set(Calendar.SECOND, 0);

            TimetableAlarmNotifier.set(triggerTime);
        }
    }
}
