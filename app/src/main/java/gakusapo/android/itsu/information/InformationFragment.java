package gakusapo.android.itsu.information;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import gakusapo.andoid.itsu.R;
import org.w3c.dom.Text;

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
        presenter.reloadTrainInfo();

        this.view.findViewById(R.id.trainInfoSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTrainInfoSettingButtonClicked();
            }
        });
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
    public void setIcon(Bitmap icon) {
        ImageView imageView = view.findViewById(R.id.weatherIcon);
        imageView.setImageBitmap(icon);
    }

    @Override
    public void setImage(Bitmap image) {
        ImageView imageView = view.findViewById(R.id.weatherImage);
        imageView.setImageBitmap(image);
    }

    @Override
    public void addTrainInfo(String trainName, int statusId) {
        LinearLayout layout = view.findViewById(R.id.trainInfoLayout);
        View view;

        for (int i = 0; i < layout.getChildCount(); i++) {
            view = layout.getChildAt(i);
            if (!(view instanceof TextView)) continue;

            TextView trainNameView = (TextView) view;
            if (trainNameView.getText().toString().contains(trainName)) {
                trainNameView.setVisibility(View.GONE);
                TextView trainStatusView = (TextView) layout.getChildAt(i + 1);
                trainStatusView.setVisibility(View.GONE);
            }
        }

        int padding = getActivity().getResources().getDimensionPixelSize(R.dimen.padding_8);

        TextView name = new TextView(getActivity());
        name.setPadding(padding, padding, padding, padding);
        name.setText(trainName);

        TextView status = new TextView(getActivity());
        status.setPadding(padding, 0, padding, padding);
        status.setText(statusId);

        if (statusId == R.string.information_train_unavailable) status.setTextColor(getActivity().getColor(R.color.red));
        else status.setTextColor(getActivity().getColor(R.color.black));

        layout.addView(name);
        layout.addView(status);
    }

    @Override
    public void removeAllTrainInfo() {
        LinearLayout layout = view.findViewById(R.id.trainInfoLayout);
        View view;

        for (int i = 0; i < layout.getChildCount(); i++) {
            view = layout.getChildAt(i);
            if (!(view instanceof TextView)) continue;
            layout.removeView(view);
        }
    }
}
