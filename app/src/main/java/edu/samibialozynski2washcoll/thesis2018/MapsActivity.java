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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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
                    mMap.addMarker(new MarkerOptions().position(cain).title(list.get(i)));
                }

                if (list.get(i).contains("Toll")) {
                    LatLng toll = new LatLng(39.215652, -76.068342);
                    mMap.addMarker(new MarkerOptions().position(toll).title(list.get(i)));
                }

                if (list.get(i).contains("Smith")) {
                    LatLng smith = new LatLng(39.216851, -76.068505);
                    mMap.addMarker(new MarkerOptions().position(smith).title(list.get(i)));
                }

                if (list.get(i).contains("Daly")) {
                    LatLng daly = new LatLng(39.217221, -76.069455);
                    mMap.addMarker(new MarkerOptions().position(daly).title(list.get(i)));
                }

                if (list.get(i).contains("Gibson")) {
                    LatLng gibson = new LatLng(39.217591, -76.069771);
                    mMap.addMarker(new MarkerOptions().position(gibson).title(list.get(i)));
                }

                if (list.get(i).contains("Cromwell")) {
                    LatLng cromwell = new LatLng(39.214669, -76.065894);
                    mMap.addMarker(new MarkerOptions().position(cromwell).title(list.get(i)));
                }

                if (list.get(i).contains("Studio Art")) {
                    LatLng studioArt = new LatLng(39.220097, -76.068745);
                    mMap.addMarker(new MarkerOptions().position(studioArt).title(list.get(i)));
                }

                if (list.get(i).contains("Swim Center")) {
                    LatLng swimCenter = new LatLng(39.216891, -76.070596);
                    mMap.addMarker(new MarkerOptions().position(swimCenter).title(list.get(i)));
                }

                if (list.get(i).contains("Boathouse")) {
                    LatLng boathouse = new LatLng(39.204314, -76.067570);
                    mMap.addMarker(new MarkerOptions().position(boathouse).title(list.get(i)));
                }
            }
        }

        Log.d("Error", partitions.toString());

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
