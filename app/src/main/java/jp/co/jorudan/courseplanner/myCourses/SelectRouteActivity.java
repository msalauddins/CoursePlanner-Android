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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class SelectRouteActivity extends AppCompatActivity implements RouteListDialog.RouteListDialogListener,AllRoutesDialog.AllRoutesDialogListener {

    private Context context;

    private RecyclerView selectedRouteRecyclerView;
    private RecyclerView.Adapter selectedRouteAdapter;

    private List<SelectedRouteItems> selectedRouteList = new ArrayList<>();
    private List<CacheListItem> cacheList;

    TextView addRoute;
    Button addItem;
    LinearLayout routeOrEventLayout;
    String coursePosition;
    String forCache;

    public static final String SHARED_PREF2 = "sharedPrefs2";
    private String cacheFileName2;
    public String routePosition2;

    File folder;
    File myFile;

    int cp;
    String fName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        context = getApplicationContext();

        Intent intent = getIntent();
        cp = intent.getIntExtra("cp", 1);
        String cName = intent.getStringExtra("courseName");
        coursePosition = String.valueOf(cp);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle(cName);

        addRoute = findViewById(R.id.add_route);
        routeOrEventLayout = findViewById(R.id.route_or_event_layout);
        addItem = findViewById(R.id.add_item);

        selectedRouteRecyclerView = findViewById( R.id.selected_routes_recycler_view );
        selectedRouteRecyclerView.setLayoutManager( new LinearLayoutManager( SelectRouteActivity.this ) );

        loadList();
        reorderItem();

        selectedRouteAdapter = new SelectedRoutesAdapter(selectedRouteList, SelectRouteActivity.this) ;
        selectedRouteRecyclerView.setAdapter(selectedRouteAdapter);

        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogue();

            }
        });
    }

    private void saveSelectedRoute( String filename ) {

        String jsonString;
        try {
            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer);
            forCache = jsonString;

            writeInCache();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInCache() {

        folder = context.getCacheDir();
        myFile = new File(folder, cacheFileName2);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myFile);
            fos.write(forCache.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( fos != null ) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        CacheListItem cacheListItem = new CacheListItem(coursePosition, myFile, routePosition2, cacheFileName2);
        cacheList.add(cacheListItem);

    }

    public void readJson(String c, File f, int r, String cf2) {

        FileInputStream fis ;
        try {
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            String js = sb.toString();

            try {
                JSONObject rootObj = new JSONObject( js );
                JSONArray resultsArray = rootObj.getJSONArray("results");

                String route = "";

                for ( int i = 0; i < resultsArray.length(); i++ ) {
                    if (i == r) {
                        JSONObject resultObj = resultsArray.getJSONObject(i);
                        JSONArray pathsArray = resultObj.getJSONArray("paths");

                        /*----------- start : last page data --------------*/

                        int path_size = pathsArray.length();
                        for ( int j = 0; j < path_size-1; j++ ) {
                            JSONObject pathsObj = pathsArray.getJSONObject(j);
                            JSONArray nodesArray = pathsObj.getJSONArray("nodes");
                            for ( int k = 0; k < 1; k++ ){
                                JSONObject nodeObj = nodesArray.getJSONObject( k );
                                String nodeNames = nodeObj.getString("name");
                                String[] separatedName = nodeNames.split(",");
                                String separatedNameTrimmed = separatedName[0].replaceAll("[\"\\[\\]]", "");

                                JSONObject jikokuObj = nodeObj.getJSONObject("jikoku");

                                String nodeTimeTemp;
                                String nodeTime = "";
                                int type = jikokuObj.getInt("type");
                                if (type<0){
                                    nodeTime = "    -    ";
                                }
                                else{
                                    nodeTimeTemp = jikokuObj.getString("time");
                                    for (int m = 0; m < 2; m++){
                                        char s = nodeTimeTemp.charAt(m);
                                        nodeTime += s;
                                    }
                                    nodeTime += " : ";
                                    for (int m = 2; m < 4; m++){
                                        char s = nodeTimeTemp.charAt(m);
                                        nodeTime += s ;
                                    }
                                }
                                route += nodeTime + "     " + separatedNameTrimmed + "\n";
                            }
                        }


                        for ( int j = path_size-1; j > (path_size-2); j-- ) {
                            JSONObject pathsObj = pathsArray.getJSONObject(j);
                            JSONArray nodesArray = pathsObj.getJSONArray("nodes");
                            for ( int k = 0; k < nodesArray.length(); k++ ){
                                JSONObject nodeObj = nodesArray.getJSONObject( k );
                                String nodeNames = nodeObj.getString("name");
                                String[] separatedName = nodeNames.split(",");
                                String separatedNameTrimmed = separatedName[0].replaceAll("[\"\\[\\]]", "");

                                JSONObject jikokuObj = nodeObj.getJSONObject("jikoku");

                                String nodeTimeTemp;
                                String nodeTime = "";
                                int type = jikokuObj.getInt("type");
                                if (type<0){
                                    nodeTime = "    -    ";
                                }
                                else{
                                    nodeTimeTemp = jikokuObj.getString("time");
                                    for (int m = 0; m < 2; m++){
                                        char s = nodeTimeTemp.charAt(m);
                                        nodeTime += s;
                                    }
                                    nodeTime += " : ";
                                    for (int m = 2; m < 4; m++){
                                        char s = nodeTimeTemp.charAt(m);
                                        nodeTime += s ;
                                    }
                                }

                                route += nodeTime + "     " + separatedNameTrimmed+ "\n";
                            }
                        }

                        /*----------- end : last page data --------------*/
                    }
                }

                SelectedRouteItems items = new SelectedRouteItems(route, c, String.valueOf(r), cf2);
                selectedRouteList.add(items);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveList() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF2, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(cacheList);

        editor.putString("key", jsonText);
        editor.apply();
    }

    public void loadList() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF2, MODE_PRIVATE);
        String jsonText = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<CacheListItem>>(){}.getType();
        cacheList = gson.fromJson(jsonText, type);

        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }

        for (int i = 0; i<cacheList.size(); i++ ){
            if(cacheList.get(i).getCacheCourseId().equals(coursePosition)){
                File f2 = cacheList.get(i).getCacheRouteFileName();
                String cid = cacheList.get(i).getCacheCourseId();
                String rp = cacheList.get(i).getCacheRouteIndex();
                int rp_int = Integer.parseInt(rp);
                String cf = cacheList.get(i).getUniqueId();
                readJson(cid, f2, rp_int, cf);
            }
        }

        selectedRouteRecyclerView.setVisibility(View.VISIBLE);
    }

    private void openDialogue() {

        AddRouteOrEventDialog addRouteOrEventDialog = new AddRouteOrEventDialog();
        addRouteOrEventDialog.show(getSupportFragmentManager(), "Add Route or Event Dialog");
    }

    public void reorderItem() {

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();

                int cacheDragged = 0;
                int cacheTarget = 0;

                String uniqueIdDragged = selectedRouteList.get(position_dragged).getUniqueRouteId();
                String uniqueIdTarget = selectedRouteList.get(position_target).getUniqueRouteId();

                for ( int i = 0; i<cacheList.size(); i++ ) {
                    if ( (cacheList.get(i).getUniqueId()).equals(uniqueIdDragged) ) {
                        cacheDragged = i;
                    }
                }
                for ( int i = 0; i<cacheList.size(); i++ ) {
                    if ( (cacheList.get(i).getUniqueId()).equals(uniqueIdTarget) ) {
                        cacheTarget = i;
                    }
                }
                Collections.swap(cacheList, cacheDragged, cacheTarget);
                saveList();

                Collections.swap(selectedRouteList, position_dragged, position_target);

                selectedRouteAdapter.notifyItemMoved(position_dragged, position_target);

                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()){
                            new AlertDialog.Builder(SelectRouteActivity.this)
                                    .setTitle("Alert")
                                    .setMessage("Delete this Route ?")
                                    .setCancelable(false)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            selectedRouteAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                        }
                                    })
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            int position = viewHolder.getAdapterPosition();
                                            String a = selectedRouteList.get(position).getRoutePos();
                                            for ( int j = 0; j<cacheList.size(); j++ ) {
                                                if ( (cacheList.get(j).getCacheRouteIndex()).equals(a) ) {

                                                    cacheList.remove(j);
                                                }
                                            }
                                            saveList();
                                            selectedRouteList.remove(position);
                                            selectedRouteAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .show();
                        }
                    }
                });

            }
        });

        helper.attachToRecyclerView(selectedRouteRecyclerView);
    }

    public void openAllRoutesDialogue(String fN){
        AllRoutesDialog allRoutesDialog = new AllRoutesDialog();
        AllRoutesFragment allRoutesFragment = new AllRoutesFragment();

        Bundle bundle = new Bundle();
        bundle.putString("fileName", fN);
        allRoutesDialog.setArguments(bundle);
        allRoutesDialog.show(getSupportFragmentManager(), "All Routes Dialog");
    }

    @Override
    public void sendRouteInfo(String cacheFileName, String routePosition, String fName) {
        cacheFileName2 = cacheFileName;
        fName2 = fName;
        openAllRoutesDialogue(fName);
    }

    @Override
    public void sendRoute(String r, int rp2, String cacheFileName3) {

        routePosition2 = String.valueOf(rp2);
        saveSelectedRoute(fName2);
        readJson(String.valueOf(cp), myFile, rp2, cacheFileName3);
        saveList();
        selectedRouteAdapter.notifyDataSetChanged();
    }
}
