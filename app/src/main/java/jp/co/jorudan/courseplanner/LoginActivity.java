package jp.co.jorudan.courseplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginBtn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        //sp.edit().putBoolean("logged",false).apply();

        if(sp.getBoolean("logged",true)){
            goToSettings();
        }

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Login");
        //setSupportActionBar(toolbar);

        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.email_sign_in_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

    }

    public void login() {

        if(username.getText().toString().equals("admin") && password.getText().toString().equals("123")){

            goToSettings();

            sp.edit().putBoolean("logged",true).apply();

        }

        else if (!"admin".equals(username.getText().toString()) && password.getText().toString().equals("123")) {
                Toast.makeText(getApplicationContext(), "Wrong User ID",Toast.LENGTH_SHORT).show();
            }

        else if (!"123".equals(password.getText().toString()) && username.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
        }

    }

    public void goToSettings() {

        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
        finish();

    }
}
