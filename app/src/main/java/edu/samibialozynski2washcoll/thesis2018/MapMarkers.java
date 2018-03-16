package edu.samibialozynski2washcoll.thesis2018;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samibialozynski on 3/15/18.
 */

public class MapMarkers extends FragmentActivity {

    ArrayList<String> extra;
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
        for (List<String> stringList : splitData())
        {
            // Meeting location is stored in 4th position
            //Building name and room
            String meetingLocation = stringList.get(3);
            //Splits meeting location to only use building
            String buildingName = meetingLocation.split(" ")[0];
            Building buildingData = buildingMap.get(buildingName);

            allData.put(new LatLng(buildingData.lat , buildingData.lon), stringList);

            /*//for(int i = 0; i < stringList.size(); i++){
            if (building.contains("Dunning")) {
                allData.put(dunning,stringList);
            }
            else if (building.contains("JFC")) {
                allData.put(jfc, stringList);
            } else if (building.contains("Cain")) {


                if(allData.containsKey(cain))
                {
                    if(allData.containsKey(new LatLng(39.216425 + l, -76.070214 + l)))
                    {
                        allData.put(new LatLng(39.216425 + l * 2, -76.070214 + l * 2), stringList);
                    }else{
                        allData.put(new LatLng(39.216425 + l , -76.070214 + l), stringList);
                    }
                } else{
                    allData.put(cain, stringList);
                }

                //mMap.addMarker(new MarkerOptions().position(new LatLng(cainLat + 0.00005, cainLong + 0.00005)).title(list.get(i)));
                //mMap.addMarker(new MarkerOptions().position(cain).title(list.get(i)));

            }
            //}*/

        }

        Log.e("Error", allData.toString());

        return allData;
    }
}
