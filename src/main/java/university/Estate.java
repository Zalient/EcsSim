package university;

import facilities.Facility;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Estate {
    private ArrayList<Facility> facilities;
    public Estate()
    {
        facilities = new ArrayList<Facility>();
    }
    public Facility[] getFacilities() { return facilities.toArray(new Facility[0]); }
    public Facility addFacility(String type, String name) {
        Facility newFacility = switch (type) {
            case "Hall" -> new Hall(name);
            case "Lab" -> new Lab(name);
            case "Theatre" -> new Theatre(name);
            default -> null;
        };
        if (newFacility != null) {
            facilities.add(newFacility);
        }
        return newFacility;
    }
    public float getMaintenanceCost() {
        float maintenanceCost = 0;
        for (Facility facility : facilities) {
            maintenanceCost += (float) (facility.getCapacity() * 0.1);
        }
        return maintenanceCost;
    }
    public HashMap<String, Integer> getFacilityCapacityMap() {
        HashMap<String, Integer> facilityCapacityMap = new HashMap<String, Integer>();
        int totalCapacityHalls = 0;
        int totalCapacityLabs = 0;
        int totalCapacityTheatres = 0;
        for (Facility facility : facilities) {
            if (facility instanceof Hall) {
                totalCapacityHalls += facility.getCapacity();
            } else if (facility instanceof Lab) {
                totalCapacityLabs += facility.getCapacity();
            } else if (facility instanceof Theatre) {
                totalCapacityTheatres += facility.getCapacity();
            }
        }
        // There should only ever be 3 items in this hashmap, all with distinct keys
        facilityCapacityMap.put("Hall", totalCapacityHalls);
        facilityCapacityMap.put("Lab", totalCapacityLabs);
        facilityCapacityMap.put("Theatre", totalCapacityTheatres);
        return facilityCapacityMap;
    }
    public int getNumberOfStudents() {
        HashMap<String, Integer> facilityCapacityMap = getFacilityCapacityMap();
        ArrayList<Integer> capacityList = new ArrayList<Integer>(facilityCapacityMap.values());
        // Sort the list lowest to highest
        Collections.sort(capacityList);
        // The first element will now be the lowest capacity of all the facilities
        // There could be multiple minimum values but that does not matter for simply getting the number
        // of students
        return capacityList.get(0);
    }
    public ArrayList<Facility> getLowestLevelFacilityTypes() {
        HashMap<Facility, Integer> facilityToLevelMap = new HashMap<Facility, Integer>();
        HashMap<Facility, Integer> sortedMap = new HashMap<Facility, Integer>();
        for (Facility facility : facilities) {
            facilityToLevelMap.put(facility, facility.getLevel());
        }
        ArrayList<Integer> levels = new ArrayList<Integer>(facilityToLevelMap.values());
        Collections.sort(levels);
        for (Integer level : levels) {
            for (Map.Entry<Facility, Integer> entry : facilityToLevelMap.entrySet()) {
                if (entry.getValue().equals(level)) {
                    sortedMap.put(entry.getKey(), level);
                }
            }
        }
        ArrayList<Facility> sortedFacilities = new ArrayList<Facility>(sortedMap.keySet());
        ArrayList<Facility> lowestLevelFacilityTypes = new ArrayList<Facility>();
        int hallCount = 0;
        int labCount = 0;
        int theatreCount = 0;
        // Get a new list with the lowest level facility of each type of facility
        for (Facility facility : sortedFacilities) {
            if (facility instanceof Hall && hallCount < 1) {
                lowestLevelFacilityTypes.add(facility);
                hallCount++;
            } else if (facility instanceof Lab && labCount < 1) {
                lowestLevelFacilityTypes.add(facility);
                labCount++;
            } else if (facility instanceof Theatre && theatreCount < 1) {
                lowestLevelFacilityTypes.add(facility);
                theatreCount++;
            }
        }
        // The lowestLevelFacilityTypes should have a size of 3 at this point (assuming there is at
        // least 1 of each facility type)
        return lowestLevelFacilityTypes;
    }
}
