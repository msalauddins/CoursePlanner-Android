package jp.co.jorudan.courseplanner.myCourses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.jorudan.courseplanner.R;

public class AddCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private boolean startDateOrEndDate = true;
    EditText addTitle;
    TextView selectStartDate;
    TextView selectEndDate;
    Button createCourse;
    Context context;
    Date dateStart;
    Date dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Add Course");
        //setSupportActionBar(toolbar);

        context = getApplicationContext();

        addTitle = findViewById(R.id.add_title);
        selectStartDate = findViewById(R.id.select_start_date);
        selectEndDate = findViewById(R.id.select_end_date);
        createCourse = findViewById(R.id.create_course);

        selectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDateOrEndDate = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDateOrEndDate = false;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                String title = addTitle.getText().toString();
                String startDate = selectStartDate.getText().toString();
                String endDate = selectEndDate.getText().toString();

                if ( title.equals("") || endDate.equals("") || startDate.equals("") ){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isFinishing()){
                                new AlertDialog.Builder(AddCourseActivity.this)
                                        .setTitle("Alert")
                                        .setMessage("Field is empty")
                                        .setCancelable(false)
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).show();
                            }
                        }
                    });

                }

                else if (dateEnd.before(dateStart)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isFinishing()){
                                new AlertDialog.Builder(AddCourseActivity.this)
                                        .setTitle("Alert")
                                        .setMessage("End date must be after the start date")
                                        .setCancelable(false)
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).show();
                            }
                        }
                    });
                }

                else {
                    resultIntent.putExtra("title",  title);
                    resultIntent.putExtra("startDate", startDate );
                    resultIntent.putExtra("endDate", endDate );
                    setResult( Activity.RESULT_OK, resultIntent );
                    finish();
                }
            }
        });

    }

    String sDate;
    String eDate;
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        month = month+1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = day + "/" + month + "/" + year;

        if ( startDateOrEndDate ) {

            selectStartDate.setText(currentDateString);
            sDate = currentDateString;
            try {
                dateStart = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        else
        {
            selectEndDate.setText(currentDateString);
            eDate = currentDateString;
            try {
                dateEnd = new SimpleDateFormat("dd/MM/yyyy").parse(eDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
