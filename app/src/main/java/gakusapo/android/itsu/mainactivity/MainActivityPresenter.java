package gakusapo.android.itsu.mainactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;

    MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.INTERNET}, 1001);
        }
    }

}
