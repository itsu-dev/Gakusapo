package gakusapo.android.itsu.information;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.Map;

public interface InformationContract {

    interface View {
        Activity getActivity();
        void setWeatherCity(String title);
        void setMaxTemp(String temp);
        void setMinTemp(String temp);
        void setTemp(String temp);
        void setWeather(String weather);
        void setIcon(Bitmap icon);
        void setImage(Bitmap image);
    }

    interface Presenter {
        void reloadWeatherForecast();
        Map<String, Integer> getPosition();
        void onWeatherLoaded(String json);
        void onWeatherIconGot(Bitmap bitmap);
        void onWeatherCityGot(String json);
        void onGPSPermissionGranted();
        void onGPSPermissionNotGranted();
    }

}
