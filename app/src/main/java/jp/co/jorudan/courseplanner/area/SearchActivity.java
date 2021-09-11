package jp.co.jorudan.courseplanner.area;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jp.co.jorudan.courseplanner.R;

public class SearchActivity extends AppCompatActivity {

    EditText writeOrigin;
    Button saveOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        writeOrigin = findViewById(R.id.write_origin);
        saveOrigin = findViewById(R.id.save_origin);

        Log.i("ORIGIN", writeOrigin.getText().toString());

        saveOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("origin", writeOrigin.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
