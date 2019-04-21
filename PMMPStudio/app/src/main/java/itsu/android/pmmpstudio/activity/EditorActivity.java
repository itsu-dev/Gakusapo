package jp.mirm.pmmpstudio.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.vic797.syntaxhighlight.LineCountLayout;
import com.vic797.syntaxhighlight.SyntaxHighlighter;
import jp.mirm.pmmpstudio.R;
import jp.mirm.pmmpstudio.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class EditorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        ((TextView) navigationView
                .getHeaderView(R.layout.nav_header_editor)
                .findViewById(R.id.drawer_project_name))//TODO
                .setText(bundle.getString("name"));

        ((TextView) navigationView
                .getHeaderView(R.layout.nav_header_editor)
                .findViewById(R.id.drawer_project_author))
                .setText(bundle.getString("author"));*/

        //editor.setText(IOUtils.readFile(new File(bundle.getString("source"))));

        try {
            WebView webView1 = findViewById(R.id.editorWebView);
            webView1.getSettings().setJavaScriptEnabled(true);
            webView1.loadDataWithBaseURL("file:///android_asset/", IOUtils.readFile(new File(bundle.getString("source"))), "text/html", "UTF-8", null);
            //webView1.loadUrl(new File(bundle.getString("source")).toURI().toURL().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_save) {
            // Handle the camera action
        } else if (id == R.id.nav_edit) {

        } else if (id == R.id.nav_debug) {

        } else if (id == R.id.nav_inflate) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_open) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
