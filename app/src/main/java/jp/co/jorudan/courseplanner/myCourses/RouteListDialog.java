package jp.co.jorudan.courseplanner.myCourses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class RouteListDialog extends AppCompatDialogFragment{

    String originName;
    String destinationName;
    List<MyRoute> routeList = new ArrayList<>();
    private String cacheFileName;
    public String routePosition;
    String fName;
    private RouteListDialogListener listener;

    private SelectRouteAdapter.OnRouteClickListener onRouteClickListener = new SelectRouteAdapter.OnRouteClickListener() {
        @Override
        public void onRouteClick(String routeData, int p) {

            if ( routeData.equals("Shinjuku")) {

                Long tsLong = System.currentTimeMillis();
                cacheFileName = tsLong.toString();
                routePosition = String.valueOf(p);
                fName = "shinjuku-to-shin-osaka.json";
                listener.sendRouteInfo(cacheFileName, routePosition, fName);
                dismiss();
            }
            else if ( routeData.equals("Haneda-Airport International Terminal(Keikyu)")) {
                Long tsLong = System.currentTimeMillis();
                cacheFileName = tsLong.toString();
                routePosition = String.valueOf(p);
                fName = "haneda-to-narita.json";
                listener.sendRouteInfo(cacheFileName, routePosition, fName);
                dismiss();
            }
            else if ( routeData.equals("Shinagawa")) {
                Long tsLong = System.currentTimeMillis();
                cacheFileName = tsLong.toString();
                routePosition = String.valueOf(p);
                fName = "shinagawa-to-koshigoe.json";
                listener.sendRouteInfo(cacheFileName, routePosition, fName);
                dismiss();
            }
            else if ( routeData.equals("Tokyo")) {
                Long tsLong = System.currentTimeMillis();
                cacheFileName = tsLong.toString();
                routePosition = String.valueOf(p);
                fName = "tokyo-to-taura.json";
                listener.sendRouteInfo(cacheFileName, routePosition, fName);
                dismiss();
            }
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.route_list_dialog, null);
        builder.setView(view);

        createRouteList("shinjuku-to-shin-osaka.json");
        createRouteList("haneda-to-narita.json");
        createRouteList("shinagawa-to-koshigoe.json");
        createRouteList("tokyo-to-taura.json");

        RecyclerView routeRecyclerView;
        RecyclerView.Adapter routeAdapter;
        routeRecyclerView = view.findViewById( R.id.select_route_recycler_view );
        routeRecyclerView.setLayoutManager( new LinearLayoutManager( this.getActivity() ) );
        routeAdapter = new SelectRouteAdapter(routeList, getActivity(), onRouteClickListener );
        routeRecyclerView.setAdapter(routeAdapter);

        return builder.create();
    }

    public void createRouteList( String filename ) {
        String jsonString;

        try{
            InputStream inputStream = getActivity().getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer);

            JSONObject rootObj = new JSONObject( jsonString );
            JSONArray resultsArray = rootObj.getJSONArray("results");

            for ( int i = 0; i < 1; i++ ) {
                JSONObject resultObj = resultsArray.getJSONObject( i );
                JSONArray pathsArray = resultObj.getJSONArray("paths");

                /*------------ start : origin node ------------*/
                for ( int j = 0; j < 1; j++ ) {
                    JSONObject pathsObj = pathsArray.getJSONObject(j);
                    JSONArray nodesArray = pathsObj.getJSONArray("nodes");
                    for ( int k = 0; k < 1; k++ ){
                        JSONObject nodeObj = nodesArray.getJSONObject( k );
                        String originNames = nodeObj.getString("name");
                        String[] separated = originNames.split(",");
                        originName = separated[0].replaceAll("[\"\\[\\]]", "");
                    }
                }
                /*------------ end : origin node ------------*/

                /*------------ start : destination node ------------*/

                int path_size = pathsArray.length();
                for ( int j = path_size-1; j > (path_size-2); j-- ) {
                    JSONObject pathsObj = pathsArray.getJSONObject(j);
                    JSONArray nodesArray = pathsObj.getJSONArray("nodes");
                    int node_size = nodesArray.length();
                    for ( int k = node_size-1; k > (node_size-2); k-- ){
                        JSONObject nodeObj = nodesArray.getJSONObject( k );
                        String destinationNames = nodeObj.getString("name");
                        String[] separated = destinationNames.split(",");
                        destinationName = separated[0].replaceAll("[\"\\[\\]]", "");
                    }
                }
                /*------------ end : destination node ------------*/


                MyRoute myRoute = new MyRoute(originName, destinationName);
                routeList.add(myRoute);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RouteListDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement RouteListDialogListener");
        }
    }

    public interface RouteListDialogListener {
        void sendRouteInfo (String cacheFileName, String routePosition, String fName);
    }
}
