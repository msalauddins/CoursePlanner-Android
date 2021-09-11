package jp.co.jorudan.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jp.co.jorudan.courseplanner.area.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    Button logoutBtn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");

        logoutBtn = findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("logged",false).apply();
                Toast.makeText(getApplicationContext(), "Logged Out",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        }
    }
}
