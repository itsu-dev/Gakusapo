package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.Map;

public interface InformationContract {

    interface View {
        Activity getActivity();
        void setWeatherCity(String title);
        void setMaxTemp(String temp);
        void setMinTemp(String temp);
        void setTemp(String temp);
        void setIcon(Bitmap icon);
        void setImage(Bitmap image);
        void addTrainInfo(String trainName, int trainStatus);
        void removeAllTrainInfo();
        void setRefreshing(boolean bool);
        void showRefreshingToast();
        void showRefreshedToast();
        void showRefreshErrorToast();
    }

    interface Presenter {
        void reloadWeatherForecast();
        void reloadTrainInfo();
        void onWeatherDetailsButtonClicked();
        void onWeatherLoaded(String json);
        void onWeatherIconGot(Bitmap bitmap);
        void onWeatherCityGot(String json);
        void onGPSPermissionGranted();
        void onGPSPermissionNotGranted();
        void onLocationChanged(Location location);
        void onTrainInfoGot(String json);
        void onTrainInfoSettingButtonClicked();
        void onRefresh();
        void onRefreshed();
        void onRefreshError();
    }

}
