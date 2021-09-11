package jp.co.jorudan.courseplanner;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import jp.co.jorudan.courseplanner.area.ViewPagerAdapter;

public class ImageSliderActivity extends AppCompatActivity {

    private String[] imageURLs = new String[] {
            "http://www.bangladeshallnews.com/wp-content/uploads/2017/11/Inanni-Beach-5.jpg",
            "https://media-cdn.tripadvisor.com/media/photo-s/0e/86/34/74/himchori-waterfall.jpg",
            "http://priobangla.net/responsive/images/spot/50/4.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.setFillViewport(true);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageURLs);
        viewPager.setAdapter(adapter);
    }
}
