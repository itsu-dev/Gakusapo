package dev.itsu.gakusapo.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.WebPresenter;
import dev.itsu.gakusapo.presenter.contract.WebContract;

public class WebActivity extends AppCompatActivity implements WebContract.View {

    private WebContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        presenter = new WebPresenter(this);

        Bundle bundle = getIntent().getExtras();
        loadPage(bundle.getString("url"));

        findViewById(R.id.webCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCloseButtonClicked();
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void loadPage(String url) {
        WebView webView = findViewById(R.id.webWebView);
        webView.loadUrl(url);
    }
}
