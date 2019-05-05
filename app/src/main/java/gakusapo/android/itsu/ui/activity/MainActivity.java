package gakusapo.android.itsu.ui.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.notification.TimetableAlarmNotifier;
import gakusapo.android.itsu.api.service.PreferencesService;
import gakusapo.android.itsu.db.DatabaseDAO;
import gakusapo.android.itsu.presenter.InformationPresenter;
import gakusapo.android.itsu.presenter.contract.MainActivityContract;
import gakusapo.android.itsu.presenter.MainActivityPresenter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;

    private static InformationPresenter informationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.presenter = new MainActivityPresenter(this);
        presenter.checkPermission();

        NavController controller = Navigation.findNavController(findViewById(R.id.nav_fragment));
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.navigation), controller);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        //TODO
        Calendar triggerTime = Calendar.getInstance();
        triggerTime.set(Calendar.HOUR_OF_DAY, 16);
        triggerTime.set(Calendar.MINUTE, 5);
        triggerTime.set(Calendar.SECOND, 0);

        Intent intent = new Intent(MainActivity.this, TimetableAlarmNotifier.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(findViewById(R.id.nav_fragment)).navigateUp();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public static void setInformationPresenter(InformationPresenter presenter) {
        informationPresenter = presenter;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (informationPresenter != null) {
                    informationPresenter.onGPSPermissionNotGranted();
                    informationPresenter = null;
                }
            } else {
                if (informationPresenter != null) {
                    informationPresenter.onGPSPermissionGranted();
                    informationPresenter = null;
                }
            }

        } else if (requestCode == 1001) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast toast = Toast.makeText(this, R.string.mainactivity_permission_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseDAO.closeDatabase();
    }
}
