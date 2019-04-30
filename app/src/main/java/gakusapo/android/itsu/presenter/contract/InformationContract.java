package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.Map;

public interface InformationContract {

    interface View {
        Activity getActivity();
        void setWeatherCity(String title);
        void setHumidity(int humidity);
        void setSunset(String time);
        void setTemp(String temp);
        void setIcon(Bitmap icon);
        void setImage(Bitmap image);
        void setWeatherName(String name);
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
        void onWeatherLoaded(Map<String, Object> data);
        void onWeatherIconGot(Bitmap bitmap);
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
