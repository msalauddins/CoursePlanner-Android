package jp.co.jorudan.courseplanner.myCourses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import jp.co.jorudan.courseplanner.R;

public class MyPageActivity extends AppCompatActivity {

    private Context context;
    ArrayList<MyCourse> myCourseList;
    private RecyclerView myCourseRecyclerView;
    private RecyclerView.Adapter myCourseAdapter;

    public static final String SHARED_PREF = "sharedPrefs";

    String titleText;
    String startDateText;
    String endDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("My Page");
        //setSupportActionBar(toolbar);

        loadList();


        Button addNewCourseBtn = findViewById(R.id.add_new_course_btn);

        addNewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = getApplicationContext();
                Intent i = new Intent(context, AddCourseActivity.class);
                startActivityForResult(i, 1);

            }
        });

        myCourseRecyclerView = findViewById( R.id.my_course_recycler_view );
        myCourseRecyclerView.setHasFixedSize( true );
        myCourseRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        myCourseAdapter = new MyCourseAdapter(myCourseList,MyPageActivity.this);
        myCourseRecyclerView.setAdapter(myCourseAdapter);

        reorderItem();

    }

    public void saveList() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(myCourseList);

        editor.putString("key", jsonText);
        editor.apply();

    }

    public void loadList() {

        Gson gson = new Gson();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        String jsonText = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<MyCourse>>(){}.getType();
        myCourseList = gson.fromJson(jsonText, type);
        if (myCourseList == null) {
            myCourseList = new ArrayList<>();
        }
    }

    private void reorderItem() {

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(myCourseList, position_dragged, position_target);
                myCourseAdapter.notifyItemMoved(position_dragged, position_target);
                saveList();

                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()){
                            new AlertDialog.Builder(MyPageActivity.this)
                                    .setTitle("Alert")
                                    .setMessage("Delete this Course ?")
                                    .setCancelable(false)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            myCourseAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                        }
                                    })
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            int position = viewHolder.getAdapterPosition();
                                            myCourseList.remove(position);
                                            myCourseAdapter.notifyDataSetChanged();
                                            saveList();
                                        }
                                    })
                                    .show();

                        }
                    }
                });


            }
        });

        helper.attachToRecyclerView(myCourseRecyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            titleText = data.getStringExtra("title");
            startDateText = data.getStringExtra("startDate");
            endDateText = data.getStringExtra("endDate");

            MyCourse myCourse = new MyCourse();
            myCourse.setMyCourseTitle(titleText);
            myCourse.setMyCourseStartDate(startDateText);
            myCourse.setMyCourseEndDate(endDateText);
            myCourseList.add(myCourse);
            myCourseAdapter.notifyDataSetChanged();

            saveList();
        }
    }

}
