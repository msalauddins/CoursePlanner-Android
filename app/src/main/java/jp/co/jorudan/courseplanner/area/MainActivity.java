package jp.co.jorudan.courseplanner.area;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.jorudan.courseplanner.ErrorMessage;
import jp.co.jorudan.courseplanner.LoginActivity;
import jp.co.jorudan.courseplanner.R;
import jp.co.jorudan.courseplanner.myCourses.MyPageActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Area> areaList = new ArrayList<>();
    private Context context;
    private LinearLayout errLayout;
    BottomNavigationView bottomNavigationView;
    List<Area> areaList2;
    private View proBar;

    private final String channel_id = "personal_notification";
    private final int notification_id = 1;

    private AreaViewModel areaViewModel;

    // Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        /*ImageDownloaderTask downloaderTask = new ImageDownloaderTask( url, imageView );
        downloaderTask.execute();*/

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Area");

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        recyclerView = findViewById( R.id.recycler_view );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        errLayout = findViewById(R.id.err_layout);
        errLayout.setVisibility( View.INVISIBLE );

        proBar = findViewById(R.id.loading_progress);

        loadRecyclerViewData();

        reorderItem();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch( item.getItemId())
                {
                    case R.id.nav_area:
                        break;
                    case R.id.nav_my_home:
                        context = getApplicationContext();
                        startActivity(new Intent(context, MyPageActivity.class));
                        break;
                    case R.id.nav_settings:
                        //openDialogue();
                        context = getApplicationContext();
                        startActivity(new Intent(context, LoginActivity.class));
                        break;
                }

                return false;
            }
        });

    }

    public void loadRecyclerViewData(){

        areaViewModel = ViewModelProviders.of(this).get(AreaViewModel.class);

        final Observer<List<Area>> nameObserver = new Observer<List<Area>>() {
            @Override
            public void onChanged(@Nullable final List<Area> newName) {

                if (newName!=null){
                    areaList2 = new ArrayList<Area>(newName);
                    adapter = new MyAdapter(areaList2,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    proBar.setVisibility( View.GONE );
                    recyclerView.setVisibility( View.VISIBLE );
                }

                else {
                    Toast.makeText(getApplication(), "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                    //displayNotification();
                    //openDialogue();
                }
            }
        };
        areaViewModel.getArea().observe(this, nameObserver);
    }

    public void displayNetworkNotification() {

        //Create notification channel for android version greater than 8.0
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id );
        builder.setSmallIcon( R.drawable.notification );
        builder.setContentTitle( "Network Notification" );
        builder.setContentText( "No internet, data cannot be fetched" );
        builder.setPriority( NotificationCompat.PRIORITY_DEFAULT );
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify( notification_id, builder.build() );
    }

    public void displayNotification() {

        //Create notification channel for android version greater than 8.0
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id );
        builder.setSmallIcon( R.drawable.notification );
        builder.setContentTitle( "Re-order Notification" );
        builder.setContentText( "Areas have been re-ordered" );
        builder.setPriority( NotificationCompat.PRIORITY_DEFAULT );
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify( notification_id, builder.build() );
    }

    private void createNotificationChannel() {

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel( channel_id, name, importance );
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
            notificationManager.createNotificationChannel( notificationChannel );
        }
    }

    private void openDialogue() {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.show(getSupportFragmentManager(), "Error Dialogue");
    }

    private void reorderItem() {

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(areaList2, position_dragged, position_target);
                adapter.notifyItemMoved(position_dragged, position_target);

                displayNotification();

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                areaList2.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

}
