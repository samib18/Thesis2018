package edu.samibialozynski2washcoll.thesis2018;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<String> extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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

        extra = getIntent().getStringArrayListExtra("extra");

        List<List<String>> partitions = new ArrayList<>();


        for(int i = 0; i < extra.size(); i += 4)
        {
            if(extra.get(i).contains("Thesis")){
                partitions.add(extra.subList(i, Math.min(i, extra.size())));
            }else {
                partitions.add(extra.subList(i, Math.min(i + 4, extra.size())));;
            }
        }

        for (List<String> list : partitions)
        {
            for(int i = 0; i < list.size(); i++) {
                if (list.get(i).contains("Dunning")) {
                    LatLng dunning = new LatLng(39.215924, -76.068073);
                    mMap.addMarker(new MarkerOptions().position(dunning).title(list.get(i)));
                }

                if (list.get(i).contains("JFC")) {
                    LatLng jfc = new LatLng(39.215816, -76.070777);
                    mMap.addMarker(new MarkerOptions().position(jfc).title(list.get(i)));
                }

                if (list.get(i).contains("Cain")) {
                    LatLng cain = new LatLng(39.216425, -76.070214);
                    mMap.addMarker(new MarkerOptions().position(cain).title(list.toString()));
                }
            }
        }

        Log.d("Error", partitions.toString());


        LatLng wac = new LatLng(39.2176, -76.0678);
        CameraPosition position = CameraPosition.builder()
                .target(wac)
                .zoom(17f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }
}
