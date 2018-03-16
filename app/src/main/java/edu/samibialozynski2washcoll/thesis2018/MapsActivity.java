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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<String> extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    Building [] buildings = {
            new Building("Dunning", 39.215924, -76.068073, 0),
            new Building("JFC", 39.215816, -76.070777, 0),
            new Building("Cain", 39.216425, -76.070214, 0),
            new Building("Toll", 39.215652, -76.068342, 0),
            new Building("Smith", 39.216851, -76.068505, 0),
            new Building("Daly", 39.217221, -76.069455, 0),
            new Building("Gibson", 39.217591, -76.069771, 0),
            new Building("Cromwell", 39.214669, -76.065894, 0),
            new Building("Studio Art", 39.220097, -76.068745, 0),
            new Building("Swim Center", 39.216891, -76.070596, 0),
            new Building("Boathouse", 39.204314, -76.067570, 0)

    };

    public List<List<String>> splitData(){

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
        return partitions;
    }

    public Map<LatLng, List> addlatlong(){
        double l = 0.00005;
        Map<LatLng, List> allData = new HashMap<>();

        Map<String, Building> buildingMap = new HashMap<>();
        for(int i = 0; i < buildings.length; i++){
            buildingMap.put(buildings[i].name, buildings[i]);
        }

        //Processing the list classes in the schedule
        for (List<String> stringList : splitData()) {
            //To check the size of each class in the schedule is of size 4
            if (stringList.size() == 4) {
                // Meeting location is stored in 4th position
                //Building name and room
                String meetingLocation = stringList.get(3);
                //Splits meeting location to only use building
                String buildingName = meetingLocation.split(" ")[0];
                Building buildingData = buildingMap.get(buildingName);

                if(buildingData.count > 0) {
                    allData.put(new LatLng(buildingData.lat + l * buildingData.count, buildingData.lon + l * buildingData.count), stringList);
                }else{
                    allData.put(new LatLng(buildingData.lat, buildingData.lon), stringList);
                }

                buildingData.count ++;
            }
        }

        Log.e("Error", allData.toString());

        return allData;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapMarkers mapMarkers = new MapMarkers();

        MarkerOptions markerOptions = new MarkerOptions();
        for(Map.Entry<LatLng, List> data : addlatlong().entrySet()){
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
