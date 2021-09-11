package jp.co.jorudan.courseplanner.area;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.jorudan.courseplanner.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Geocoder geocoder;
    private LatLng latLng;
    private Marker marker;
    TextView originAddress;
    Button saveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        geocoder = new Geocoder(this, Locale.getDefault());
        originAddress = findViewById(R.id.origin_address);
        saveAddress = findViewById(R.id.save_address);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point).title("From OnMapClick"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                List <Address> addressList = new ArrayList<>();
                try {
                    addressList = geocoder.getFromLocation(point.latitude, point.longitude,1);

                    String address = addressList.get(0).getAddressLine(0);
                    String country = addressList.get(0).getCountryName();
                    String city = addressList.get(0).getAdminArea();
                    String area = addressList.get(0).getLocality();

                    String fullAddress = address +", "+ area+", " + city+", " + country;

                    originAddress.setText(fullAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("origin", originAddress.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}
