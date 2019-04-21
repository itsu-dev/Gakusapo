package gakusapo.android.itsu.information;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import gakusapo.andoid.itsu.R;

public class InformationFragment extends Fragment implements InformationContract.View {

    private InformationContract.Presenter presenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        this.presenter = new InformationPresenter(this);
        presenter.reloadWeatherForecast();
    }

    @Override
    public void setWeatherCity(String title) {
        TextView textView = view.findViewById(R.id.weatherCity);
        textView.setText(title);
    }

    @Override
    public void setMaxTemp(String temp) {
        TextView textView = view.findViewById(R.id.weatherMaxTemp);
        textView.setText(temp);
    }

    @Override
    public void setMinTemp(String temp) {
        TextView textView = view.findViewById(R.id.weatherMinTemp);
        textView.setText(temp);
    }

    @Override
    public void setTemp(String temp) {
        TextView textView = view.findViewById(R.id.weatherTemp);
        textView.setText(temp);
    }

    @Override
    public void setWeather(String weather) {
        //TextView textView = view.findViewById(R.id.weatherForecast);
        //textView.setText(weather);
    }

    @Override
    public void setIcon(Bitmap icon) {
        ImageView imageView = view.findViewById(R.id.weatherIcon);
        imageView.setImageBitmap(icon);
    }

    @Override
    public void setImage(Bitmap image) {
        ImageView imageView = view.findViewById(R.id.weatherImage);
        imageView.setImageBitmap(image);
    }



}
