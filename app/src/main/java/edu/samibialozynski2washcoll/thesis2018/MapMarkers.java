package edu.samibialozynski2washcoll.thesis2018;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samibialozynski on 3/15/18.
 * MapMarkers creates a HashMap of the Schedule data which contains LatLong of a building
 * and the information of the class (Class name, DOW, Time of day, Meeting Location)
 */

public class MapMarkers  {

    ArrayList<String> schedule;

    //Adds all the buildings of Washington College with the information that goes along with it
    Building [] buildings = {
            new Building("Dunning", 39.215924, -76.068073, 0),
            new Building("Goldstein", 39.215605, -76.069212, 0),
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

    //Creates an arraylist that gets data from the intent that is in MapsActivity()
    public MapMarkers(ArrayList<String> schedule){
        this.schedule = schedule;
    }

    /**
     * Splits the data which is an Array List into a list that contains 4 parts
     * if the class is thesis it notes that it should only have one part the title name thesis
     * else the schedule piece contains 4 parts (Class name, DOW, Time of day, Meeting Location)
     */

    private List<List<String>> splitData(){

        List<List<String>> partitions = new ArrayList<>();

        for(int i = 0; i < this.schedule.size(); i += 4)
        {
            partitions.add(this.schedule.subList(i, Math.min(i + 4, this.schedule.size())));
        }
        return partitions;
    }

    /**
     * Addlatlong processes the data from splitdata and building to create the Hashmap alldata which contains
     * the Latlong of the classes building and the rest of the information for the class
     * @return Hashmap
     */

    public Map<LatLng, List> addlatlong(){
        //Increment to add if there is more than one marker in a building
        double l = 0.00005;
        //Initialization for the Hashmap that will be returned
        Map<LatLng, List> allData = new HashMap<>();
        //Initialization for a Hashmap that contains the pair the string name for a building and
        //how many times its in a schedule
        Map<String, Building> buildingMap = new HashMap<>();

        //Creates the count for how many times a building is shown up
        for(int i = 0; i < buildings.length; i++){
            buildingMap.put(buildings[i].name, buildings[i]);
        }

        //Processing the list classes in the schedule from the method splitData()
        for (List<String> stringList : splitData()) {
            //To check the size of each class in the schedule is of size 4
            //To take out if there is a thesis class
            if (stringList.size() == 4) {
                // Meeting location is stored in 4th position
                //Building name and room
                String meetingLocation = stringList.get(3);
                //Splits meeting location to only use building
                String buildingName = meetingLocation.split(" ")[0];
                //Initialization to get the string name and the matching lat and long
                Building buildingData = buildingMap.get(buildingName);

                //Check to make sure that the building name exists
                if(buildingData != null){
                    //If there is more than one class in the building
                    if(buildingData.count > 0) {
                        //Add l (the increment to add if more than one class is in a building) * the number of classes in the building
                        allData.put(new LatLng(buildingData.lat + l * buildingData.count,
                                buildingData.lon + l * buildingData.count), stringList);
                    }else{
                        //The first time through with multiple classes or one one class in the building
                        allData.put(new LatLng(buildingData.lat, buildingData.lon), stringList);
                    }
                    //Increase the count so that each time through if there is more than one class in a building
                    //it increases so that you can multiple by the next number and the markers won't be overlapped
                    buildingData.count ++;

                } else {
                    //Error if the building is not found
                    Log.e("Error", "Building not found: " + buildingData);
                }
            }
        }

        //Return hashmap
        return allData;
    }
}
