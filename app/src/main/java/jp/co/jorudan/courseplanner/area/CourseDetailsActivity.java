package jp.co.jorudan.courseplanner.area;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.Legs;
import jp.co.jorudan.courseplanner.R;
import jp.co.jorudan.courseplanner.Spots;
import jp.co.jorudan.courseplanner.Transits;

public class CourseDetailsActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<String> spotImages = new ArrayList<>();
    private ArrayList<String> spotNames = new ArrayList<>();

    private TextView locTitle;
    private TextView locDesc;
    private TextView courseTime;
    private TextView leg;
    private TextView spot;
    private TextView transit;
    private TextView tag;

    CourseList course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Course Details");
        //setSupportActionBar(toolbar);

        context = getApplicationContext();

        locTitle = findViewById(R.id.titles);
        locDesc = findViewById(R.id.loc_description);
        courseTime = findViewById(R.id.loc_course_time);
        leg = findViewById(R.id.legs);
        spot = findViewById(R.id.spots);
        transit = findViewById(R.id.transits);
        tag = findViewById(R.id.tags);

        Intent intent = getIntent();
        course = intent.getParcelableExtra("course");

        getIncomingIntent();

        selectBtnFunction();

    }

    private void getIncomingIntent(){
            String spotsString = "";
            String legsString = "";
            String tagsString = "";
            String transitString = "";

            locTitle.setText(course.getTitle());
            locDesc.setText(course.getDescription());

            String courseTimeString = course.getCourse_time();
            int f = Integer.parseInt( courseTimeString );
            int hours, minutes;
            hours = f / 3600;
            minutes = (f % 3600) / 60;
            String courseTimeStringFinal = "course Time : " + hours + " hours " + minutes + " minutes ";

            courseTime.setText(courseTimeStringFinal);

            List<Legs> legs = course.getLegs();
            for ( int i = 0; i < legs.size(); i++ ) {
                Legs leg = legs.get( i );
                legsString +=  "Leg Type : " + leg.getType()+ "\n" + "Leg Time : " + leg.getLeg_time() + "\n";
            }
            leg.setText(legsString);

            //start: image slider

            /*String imageURLs[] = new String[spots.size()];
            LinearLayout gallery = findViewById(R.id.gallery);
            LayoutInflater inflater = LayoutInflater.from(this);
            for ( int i = 0; i < spots.size(); i++ )
            {
                Spots spot = spots.get( i );
                imageURLs[i] = spot.getImage();
                View view = inflater.inflate(R.layout.gallery_item, gallery, false);
                ImageView imageView = view.findViewById(R.id.scroll_image);
                //imageView.setImageResource(R.drawable.image_placeholder);
                Picasso.get()
                        .load(imageURLs[i])
                        .resize(700,300)
                        .into(imageView);
                gallery.addView(view);
            }*/

            //end: image slider

            List<Spots> spots = course.getSpots();
            for ( int i = 0; i < spots.size(); i++ ) {
                Spots spot = spots.get( i );
                spotImages.add(spot.getImage());
                spotNames.add(spot.getName());
                //spotsString += imageURLs[i] + "\n";
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = findViewById(R.id.spot_image_recycler_view);
            recyclerView.setLayoutManager(linearLayoutManager);
            SpotImageAdapter adapter = new SpotImageAdapter(spotImages,spotNames, this);
            recyclerView.setAdapter(adapter);

            //spot.setText(spotsString);

            List<Transits> transits = course.getTransits();
            transitString = "";
            for ( int i = 0; i < transits.size(); i++ ) {
                Transits transit = transits.get( i );
                transitString += "Transit Origin : \n\n" + "Name : "+ transit.getOrigin().getName() + "\n" + "Type : " + transit.getOrigin().getType() + "\n\n" +
                        "Transit Destination : \n\n" + "Name : "+ transit.getDestination().getName() + "\n" + "Type : " + transit.getDestination().getType() + "\n\n";
            }
            transit.setText(transitString);

            List<String> tags = course.getTags();
            tagsString = "Tags : \n";
            for ( int i = 0; i < tags.size(); i++ ) {

                tagsString += tags.get(i) + "\n";
            }
            tag.setText(tagsString);

        /*ScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.setFillViewport(true);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageURLs);
        viewPager.setAdapter(adapter);*/

    }

    public void selectBtnFunction() {
        Button selectBtn;
        selectBtn = findViewById(R.id.select_btn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CourseSummaryActivity.class);
                intent.putExtra("course", course);
                context.startActivity(intent);

            }
        });
    }
}
