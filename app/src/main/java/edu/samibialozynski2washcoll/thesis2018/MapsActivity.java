package edu.samibialozynski2washcoll.thesis2018;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<String> extra;
    MapMarkers mapMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Get schedule from Main Activity
        extra = getIntent().getStringArrayListExtra("extra");

        //Create an instance of MapMarkers
        mapMarkers = new MapMarkers(extra);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();

        for(Map.Entry<LatLng, List> data : mapMarkers.addlatlong().entrySet()){
            markerOptions.position(data.getKey());
            markerOptions.title(data.getValue().toString());
            mMap.addMarker(markerOptions);
        }

        LatLng wac = new LatLng(39.2176, -76.0678);
        CameraPosition position = CameraPosition.builder()
                .target(wac)
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }
}
