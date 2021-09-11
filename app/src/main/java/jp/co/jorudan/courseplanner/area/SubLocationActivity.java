package jp.co.jorudan.courseplanner.area;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class SubLocationActivity extends AppCompatActivity {

    private View proBar;
    private static final String DHAKA_LIST = "https://api.myjson.com/bins/y100w";
    private static final String COX_BAZAR_LIST = "https://api.myjson.com/bins/12ndzs";
    String subUrl = "";
    String AreaId = "";
    private List<CourseList> courseLists = new ArrayList<>();
    ArrayList<CourseList> courseLists2;

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_location);

        proBar = findViewById(R.id.loading_progress);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Sub Location");
        //setSupportActionBar(toolbar);

        recyclerView2 = findViewById(R.id.recycler_view_2);
        recyclerView2.setHasFixedSize( true );
        recyclerView2.setLayoutManager( new LinearLayoutManager( this ) );
        AreaId = getIntent().getStringExtra("areaId");

        if(AreaId.equals("BD001")) {  //To match string in if condition String.equal(Target String) is used.

            subUrl = DHAKA_LIST;
        }
        else {
            subUrl = COX_BAZAR_LIST;
        }

        loadCourseList();
        reorderItem();
    }

    private  void loadCourseList(){

        StringRequest stringRequest = new StringRequest( Request.Method.GET, subUrl,  new Response.Listener<String>() {
            // If success while sending, this method will execute
            @Override
            public void onResponse(String response) {
                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    courseLists = Arrays.asList(gson.fromJson(response, CourseList[].class));
                    courseLists2 = new ArrayList<CourseList>(courseLists);

                    adapter2 = new SubLocationAdapter(courseLists2,SubLocationActivity.this);
                    recyclerView2.setAdapter(adapter2);

                    proBar.setVisibility( View.GONE );
                    recyclerView2.setVisibility( View.VISIBLE );

                }
                catch (Exception e) {
                    e.printStackTrace();

                    Log.i( "Error", e.toString() );
                    Log.i( "Error", "hello" );
                }
                //Log.i("JSON" , response.toString());
            }
        },
                new Response.ErrorListener() {
                    // If some error has occurred while sending, this method will execute
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this );
        requestQueue.add( stringRequest );
    }

    private void reorderItem() {

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(courseLists2, position_dragged, position_target);
                adapter2.notifyItemMoved(position_dragged, position_target);

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                courseLists2.remove(position);
                adapter2.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(recyclerView2);
    }
}
