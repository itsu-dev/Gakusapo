
package jp.mirm.pmmpstudio.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import jp.mirm.pmmpstudio.MainActivity;
import jp.mirm.pmmpstudio.R;
import jp.mirm.pmmpstudio.project.Project;
import jp.mirm.pmmpstudio.utils.ProjectBuilder;
import jp.mirm.pmmpstudio.utils.ProjectGetter;

public class MakeProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_project);

        final RadioButton beginner = findViewById(R.id.project_make_beginner);
        final RadioButton pro = findViewById(R.id.project_make_pro);

        findViewById(R.id.project_make_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.project_make_name);
                EditText author = findViewById(R.id.project_make_author);

                if (name.getText().toString().equals("")|| author.getText().toString().equals("")) {
                    findViewById(R.id.project_make_attention).setVisibility(View.VISIBLE);

                } else {
                    findViewById(R.id.project_make_attention).setVisibility(View.GONE);

                    if (ProjectGetter.getProjectNames().contains(name.getText().toString())) {
                        findViewById(R.id.project_make_attention_1).setVisibility(View.VISIBLE);
                        return;
                    }

                    Project project = ProjectBuilder.createProject(MakeProjectActivity.this, name.getText().toString(), author.getText().toString(), beginner.isChecked() ? Project.MODE_BEGINNER : Project.MODE_PROFESSIONAL);

                    Bundle bundle = new Bundle();
                    bundle.putString("source", project.getData("mainClass"));
                    bundle.putString("name", project.getData("name"));
                    bundle.putString("author", project.getData("author"));

                    Intent intent = new Intent(MakeProjectActivity.this, EditorActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    MakeProjectActivity.this.finish();
                }
            }
        });

        beginner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) pro.setChecked(false);
                else pro.setChecked(true);
            }
        });

        pro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) beginner.setChecked(false);
                else beginner.setChecked(true);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                findViewById(R.id.make_project_layout).requestFocus();
            }
        }
        return false;
    }
}
