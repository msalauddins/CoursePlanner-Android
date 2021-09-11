package jp.co.jorudan.courseplanner.myCourses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class AllRoutesDialog extends BottomSheetDialogFragment {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter2;
    private List<String> allRouteList = new ArrayList<>();
    String cacheFileName3;
    private AllRoutesDialogListener listener2;

    private ViewPagerAdapter.OnAllRouteClickListener onAllRouteClickListener = new ViewPagerAdapter.OnAllRouteClickListener() {
        @Override
        public void onAllRouteClick(String routeInfo, int rp2) {
            Long tsLong = System.currentTimeMillis();
            cacheFileName3 = tsLong.toString();

            listener2.sendRoute(routeInfo, rp2, cacheFileName3);
            dismiss();
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.all_routes_dialog, container, false);
        viewPager = v.findViewById(R.id.all_routes_pager);

        String fn = getArguments().getString("fileName");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
               /*p = position;
               res = allRouteList.get(p);
               String[] lines = res.split("\r\n|\r|\n");
               linesOfRoute = lines.length;
               LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               params.height = ((linesOfRoute*60)+230);
               viewPager.setLayoutParams(params);*/

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        createAllRouteList(fn);

        adapter2 = new ViewPagerAdapter(getContext(), allRouteList, onAllRouteClickListener);
        viewPager.setAdapter(adapter2);

        return v;
    }

    public void createAllRouteList(String fName){

        String jsonString;
        try {
            InputStream inputStream = getActivity().getAssets().open(fName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer);

            JSONObject rootObj = new JSONObject( jsonString );
            JSONArray resultsArray = rootObj.getJSONArray("results");

            for ( int i = 0; i < resultsArray.length(); i++ ) {
                String route = "";
                JSONObject resultObj = resultsArray.getJSONObject(i);
                JSONArray pathsArray = resultObj.getJSONArray("paths");

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

                allRouteList.add(route);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
             listener2 = (AllRoutesDialogListener) context;
            }
            catch (ClassCastException e)
            {
                throw new ClassCastException(context.toString() + "Must implement AllRoutesDialogListener");
            }
    }
        public interface AllRoutesDialogListener {
            void sendRoute (String r, int rp2, String cfn);
        }
    }

