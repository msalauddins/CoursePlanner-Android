package jp.co.jorudan.courseplanner.area;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class AreaViewModel extends AndroidViewModel  {

    private MutableLiveData<List<Area>> areaList;

    StringRequest stringRequest;

    public AreaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Area>> getArea() {

        loadArea();

        if (areaList == null) {
            areaList = new MutableLiveData<List<Area>>();
        }
        return areaList;
    }

    private static final String url_data = "https://api.myjson.com/bins/168kgg";

    public void loadArea(){

        stringRequest = new StringRequest( Request.Method.GET, url_data,  new Response.Listener<String>() {
            // If success while sending, this method will execute
            @Override
            public void onResponse(String response) {
                try {

                    // setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);  this portion is used when the variable is not same as gson datas serializable needs not to be used here
                    GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    Gson gson = builder.create();
                    List<Area> areaList1 = Arrays.asList(gson.fromJson(response, Area[].class));
                    //Log.i("JSONfromASSET3", areaList1.toString());
                    areaList.setValue(areaList1);

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.i( "Error", e.toString() );
                }
            }
        },
                new Response.ErrorListener() {
                    // If some error has occurred while sending, this method will execute
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add( stringRequest );
    }

}
