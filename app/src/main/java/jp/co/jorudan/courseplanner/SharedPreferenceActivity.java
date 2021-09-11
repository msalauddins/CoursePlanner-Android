package jp.co.jorudan.courseplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPreferenceActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Switch switch1;

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "swith1";

    private String text;
    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        Toolbar toolbar;
        Button applyBtn;
        Button saveBtn;

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.text1);
        editText = findViewById(R.id.edittext1);
        applyBtn = findViewById(R.id.apply_btn);
        switch1 = findViewById(R.id.switch1);
        saveBtn = findViewById(R.id.save_btn);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        loadData();
        updateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings :
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
             default :
                 return super.onOptionsItemSelected(item);

        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textView.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();

        Context context = getApplicationContext();
        CharSequence text = "Data Saved";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "Text will be here");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews() {
        textView.setText(text);
        switch1.setChecked(switchOnOff);
    }
}
