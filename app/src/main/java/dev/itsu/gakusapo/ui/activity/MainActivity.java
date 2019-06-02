package dev.itsu.gakusapo.ui.activity;

import android.app.Activity;
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
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.presenter.InformationPresenter;
import dev.itsu.gakusapo.presenter.contract.MainActivityContract;
import dev.itsu.gakusapo.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;

    private static InformationPresenter informationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.presenter = new MainActivityPresenter(this);
        presenter.checkPermission();
        presenter.initialize();

        NavController controller = Navigation.findNavController(findViewById(R.id.nav_fragment));
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.navigation), controller);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
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
