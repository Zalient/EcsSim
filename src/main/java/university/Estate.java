package university;

import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Estate {
    private final ArrayList<Facility> facilities;
    public Estate()
    {
        facilities = new ArrayList<>();
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
            maintenanceCost += (float) (((Building) facility).getCapacity() * 0.1);
        }
        return maintenanceCost;
    }
    public HashMap<String, Integer> getBuildingCapacityMap() {
        HashMap<String, Integer> buildingCapacityMap = new HashMap<>();
        int totalCapacityHalls = 0;
        int totalCapacityLabs = 0;
        int totalCapacityTheatres = 0;
        for (Facility facility : facilities) {
            if (facility instanceof Hall) {
                totalCapacityHalls += ((Building) facility).getCapacity();
            } else if (facility instanceof Lab) {
                totalCapacityLabs += ((Building) facility).getCapacity();
            } else if (facility instanceof Theatre) {
                totalCapacityTheatres += ((Building) facility).getCapacity();
            }
        }
        // There should only ever be 3 items in this hashmap, all with distinct keys
        buildingCapacityMap.put("Hall", totalCapacityHalls);
        buildingCapacityMap.put("Lab", totalCapacityLabs);
        buildingCapacityMap.put("Theatre", totalCapacityTheatres);
        return buildingCapacityMap;
    }
    public int getNumberOfStudents() {
        HashMap<String, Integer> buildingCapacityMap = getBuildingCapacityMap();
        ArrayList<Integer> capacityList = new ArrayList<>(buildingCapacityMap.values());
        // Sort the list lowest to highest
        Collections.sort(capacityList);
        // The first element will now be the lowest capacity of all the facilities
        // There could be multiple minimum values but that does not matter for simply getting the number
        // of students
        return capacityList.get(0);
    }
    public Building getHighestLevelBuilding(String minCapacityBuildingType) {
        HashMap<Building, Integer> buildingToLevelMap = new HashMap<>();
        HashMap<Building, Integer> sortedMap = new HashMap<>();
        for (Facility facility : facilities) {
            if (minCapacityBuildingType.equals(facility.getClass().getSimpleName())) {
                if (((Building) facility).getLevel() != ((Building) facility).getMaxLevel()) {
                    buildingToLevelMap.put((Building) facility, ((Building) facility).getLevel());
                }
            }
        }
        ArrayList<Integer> levels = new ArrayList<>(buildingToLevelMap.values());
        // Lowest to highest
        Collections.sort(levels);
        for (Integer level : levels) {
            for (Map.Entry<Building, Integer> entry : buildingToLevelMap.entrySet()) {
                if (entry.getValue().equals(level)) {
                    sortedMap.put(entry.getKey(), level);
                }
            }
        }
        ArrayList<Building> sortedBuildings = new ArrayList<>(sortedMap.keySet());
        if (sortedBuildings.isEmpty()) {
            return null;
        }
        else {
            return sortedBuildings.get(sortedBuildings.size() - 1);
        }
    }
}
