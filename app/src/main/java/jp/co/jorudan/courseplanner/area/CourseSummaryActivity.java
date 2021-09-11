package jp.co.jorudan.courseplanner.area;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.jorudan.courseplanner.Legs;
import jp.co.jorudan.courseplanner.R;
import jp.co.jorudan.courseplanner.Spots;

public class CourseSummaryActivity extends AppCompatActivity {

    CourseSummaryAdapter adapter;

    final int STATIC_INTEGER_VALUE_ORIGIN = 10;
    final int STATIC_INTEGER_VALUE_DESTINATION = 30;
    TextView selectOrigin;
    TextView selectDestination;
    Button continueBtn;
    private ArrayList<String> spotImages = new ArrayList<>();
    private ArrayList<String> legTypes = new ArrayList<>();
    private ArrayList<String> spotTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__summary_);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Course Summary");
        //setSupportActionBar(toolbar);

        final Context context = getApplicationContext();

        Intent intent = getIntent();
        CourseList course = intent.getParcelableExtra("course");

        ScrollView scrollView = findViewById(R.id.course_summary_scroll_view);
        scrollView.setFocusableInTouchMode(true);
        scrollView.fullScroll(ScrollView.FOCUS_UP);

        List<Legs> legs = course.getLegs();
        for ( int i = 0; i < legs.size(); i++ ) {
            Legs leg = legs.get( i );
            legTypes.add(leg.getType());
        }

        List<Spots> spots = course.getSpots();
        for ( int i = 0; i < spots.size(); i++ ) {
            Spots spot = spots.get( i );
            spotImages.add(spot.getImage());
            spotTitles.add(spot.getName());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.course_summary_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CourseSummaryAdapter(spotImages, legTypes, spotTitles,this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(spotImages, position_dragged, position_target);
                Collections.swap(legTypes, position_dragged, position_target);
                Collections.swap(spotTitles, position_dragged, position_target);
                adapter.notifyItemMoved(position_dragged, position_target);

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        helper.attachToRecyclerView(recyclerView);




        selectOrigin = findViewById(R.id.select_origin);
        selectOrigin.setClickable(true);

        selectDestination = findViewById(R.id.select_destination);

        selectOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, MapsActivity.class);
                startActivityForResult(i, STATIC_INTEGER_VALUE_ORIGIN);
            }
        });

        selectDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, SearchActivity.class);
                startActivityForResult(i, STATIC_INTEGER_VALUE_DESTINATION);
            }
        });

        continueBtn = findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOrigin.setVisibility(View.GONE);
                selectDestination.setVisibility(View.GONE);
                continueBtn.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (STATIC_INTEGER_VALUE_ORIGIN) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newText = data.getStringExtra("origin");
                    selectOrigin.setText(newText);

                }
                break;
            }

            case (STATIC_INTEGER_VALUE_DESTINATION) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newText = data.getStringExtra("origin");
                    selectDestination.setText(newText);

                }
                break;
            }
        }
    }
}
