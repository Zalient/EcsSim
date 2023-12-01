package university;
import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.util.*;

public class University {
    private HumanResource humanResource;
    private float budget;
    private Estate estate;
    private int reputation;
    private int instructedStudents;

    public University(int funding) {
        budget = funding;
        estate = new Estate();
        humanResource = new HumanResource();
    }
    public Facility build(String type, String name) {
        HashSet<String> allowedTypes = new HashSet<>(Set.of("Hall", "Lab", "Theatre"));
        HashMap<String, Integer> buildCosts = new HashMap<String, Integer>();
        buildCosts.put("Hall", Facility.Constants.HALL.getValuesArray()[2]);
        buildCosts.put("Lab", Facility.Constants.LAB.getValuesArray()[2]);
        buildCosts.put("Theatre", Facility.Constants.THEATRE.getValuesArray()[2]);
        // Only allow "Hall","Lab","Theatre" types through
        if (!allowedTypes.contains(type)) {
            return null;
        }
        if (budget < buildCosts.get(type)) {
            return null;
        }
        budget -= buildCosts.get(type);
        reputation += 100;
        System.out.println("Built Building (" + type + "): level 1");
        return estate.addFacility(type, name);
    }
    public void upgrade(Building building) throws Exception {
        int upgradeCost = building.getUpgradeCost();
        if (!Arrays.asList(estate.getFacilities()).contains(building)) {
            throw new Exception("The building is not part of the university.");
        }
        if (building.getLevel() == building.getMaxLevel()) {
            throw new Exception("The building is already at the maximum level.");
        }
        if (upgradeCost > budget) {
            throw new Exception("Cannot upgrade building due to insufficient budget.");
        }
        building.increaseLevel();
        budget -= upgradeCost;
        reputation += 50;
        System.out.println("Upgraded Building (" + building.getClass().getSimpleName() + "): " +
                "level " + (building.getLevel() - 1) + " -> " + building.getLevel());
    }
    public float getBudget()
    {
        return budget;
    }
    public int getReputation()
    {
        return reputation;
    }
    public void increaseBudget(int amount)
    {
        budget += amount;
    }
    public void buildOrUpgrade() {
        // There should at least be 1 lab, 1 theatre, and 1 hall as a prerequisite
        if (estate.getFacilities().length == 0) {
            build("Hall", "H");
            build("Lab", "L");
            build("Theatre", "T");
        }
        // Build or upgrade algorithm:
        else {
            // Is it cheaper to build new facilities or upgrade for the same capacity increase?
            // To consider reputation fully, would need to know what 1 point of rep should be worth in
            // terms of costPerCapacity
            // Total hall, lab, theatre capacities mapped to hall, lab theatre strings respectively
            // (there is never multiple of 1 type)
            HashMap<String, Integer> facilityCapacityMap = estate.getFacilityCapacityMap();
            // This is a set as there could be two facilities with the same minimum value in which case
            // they are both bottlenecks
            HashSet<String> minCapacityFacilities =
                    Helper.getKeysByValue(facilityCapacityMap, estate.getNumberOfStudents());
            for (String minCapacityFacilityType : minCapacityFacilities) {
                // Need to consider the lowest level facilities as they are what should be upgraded
                // Now that I have the type of the facility, the new bottleneck becomes the lowest level
                // facility in this type of facility
                ArrayList<Facility> lowestLevelFacilityTypes = estate.getLowestLevelFacilityTypes();
                // Define a variable to store the found facility
                Facility facility = null;
                // Iterate through the lowestLevelFacilityTypes and find the facility by name
                // e.g. if my total hall capacity is the minimum, I want to find the lowest level hall
                // and upgrade it or build another if it already has a high enough level
                for (Facility lowLevelFacilityType : lowestLevelFacilityTypes) {
                    // Make sure im getting the right match
                    if (lowLevelFacilityType.getType().equals(minCapacityFacilityType)) {
                        facility = lowLevelFacilityType;
                        // Exit the loop once the facility is found
                        break;
                    }
                }
                // Check if the facility was found
                if (facility == null) {
                    return;
                }
                // Create a dummy facility that can be upgraded
                Facility simulateUpgrade = null;
                if (facility instanceof Hall) {
                    simulateUpgrade = new Hall("S");
                }
                else if (facility instanceof Lab) {
                    simulateUpgrade = new Lab("S");
                }
                else if (facility instanceof Theatre) {
                    simulateUpgrade = new Theatre("S");
                }
                if (simulateUpgrade == null) {
                    return;
                }
                for (int i = 1; i < facility.getLevel(); i++) {
                    simulateUpgrade.increaseLevel();
                }
                // Make costs relative to capacity for easy comparison
                int upgradeCostPerCapacity = facility.getUpgradeCost() / simulateUpgrade.getCapacity();
                int buildCostPerCapacity = facility.getBaseCost() / facility.getBaseCapacity();
                // Check if getUpgradeCost() return -1 which means facility is' max level
                if (upgradeCostPerCapacity < 0) {
                    build(minCapacityFacilityType, minCapacityFacilityType
                            .substring(0, 1));
                }
                // Facility is not max level so need to consider upgrades still
                else {
                    // Upgrading greater than or equal to price of building for same capacity
                    if (upgradeCostPerCapacity >= buildCostPerCapacity) {
                        // Then it's clear we should make a new building instead!
                        // (since build gives more rep)
                        build(minCapacityFacilityType, minCapacityFacilityType
                                .substring(0, 1));
                    }
                    // Upgrading less than price of building for same capacity
                    else {
                        // Then let's upgrade
                        try {
                            if (Arrays.asList(estate.getFacilities()).contains(facility)
                                    && facility.getLevel() != facility.getMaxLevel()
                                    && facility.getUpgradeCost() <= budget)
                                upgrade((Building) facility);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
    public ArrayList<Staff> hireStaff(ArrayList<Staff> staffMarket) {
        Facility[] facilities = estate.getFacilities();
        int hallCount = 0;
        for (Facility facility : facilities) {
            if (facility instanceof Hall) {
                hallCount++;
            }
        }
        // Not including number of halls as no staff required for hall facilities
        int staffedFacilitiesSize = facilities.length - hallCount;
        int currentStaffSize = humanResource.getNumberOfStaff();
        // If number of facilities - number of staff <= 0 then don't need more staff
        if (staffedFacilitiesSize - currentStaffSize > 0) {
            ArrayList<Staff> labStaffList = new ArrayList<Staff>();
            ArrayList<Staff> theatreStaffList = new ArrayList<Staff>();
            for (Staff staff : staffMarket) {
                // Medium skill for lab and high skill for theatre
                if (staff.getSkill() >= 50 && staff.getSkill() < 80) {
                    labStaffList.add(staff);
                }
                else if (staff.getSkill() >= 80) {
                    theatreStaffList.add(staff);
                }
            }
            int labCount = 0;
            int theatreCount = 0;
            for (int i = 0; i < staffedFacilitiesSize - currentStaffSize; i++) {
                // Only add staff to lab and theatres
                // Amount of labs and theatres may differ so do not want medium skill assigned to theatres
                // Therefore need to access the different lists for each
                if (facilities[i] instanceof Lab && labCount < labStaffList.size()) {
                    humanResource.addStaff(labStaffList.get(labCount));
                    staffMarket.remove(labStaffList.get(labCount));
                    labCount++;
                }
                else if (facilities[i] instanceof Theatre && theatreCount < theatreStaffList.size()) {
                    humanResource.addStaff(theatreStaffList.get(theatreCount));
                    staffMarket.remove(theatreStaffList.get(theatreCount));
                    theatreCount++;
                }
            }
        }
        return staffMarket;
    }
    public void allocateStaff() {
        // High skill -> theatres and medium skill -> labs
        // So lab and theatre capacities considered separately
        Facility[] facilities = estate.getFacilities();
        ArrayList<Integer> labCapacityList = new ArrayList<Integer>();
        ArrayList<Integer> theatreCapacityList = new ArrayList<Integer>();
        for (Facility facility : facilities) {
            if (facility instanceof Lab) {
                labCapacityList.add(facility.getCapacity());
            }
            else if (facility instanceof Theatre) {
                theatreCapacityList.add(facility.getCapacity());
            }
        }
        Iterator<Staff> staffIterator = humanResource.getStaff();
        int labCount = 0;
        int theatreCount = 0;
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            if (staff.getSkill() >= 50 && staff.getSkill() < 80) {
                staff.instruct(labCapacityList.get(labCount));
                instructedStudents += labCapacityList.get(labCount);
                labCount++;
            }
            else if (staff.getSkill() >= 80) {
                staff.instruct(theatreCapacityList.get(theatreCount));
                instructedStudents += theatreCapacityList.get(theatreCount);
                theatreCount++;
            }
        }
    }
    public void payMaintenanceCost()
    {
        budget -= estate.getMaintenanceCost();
    }
    public void payStaffSalary()
    {
        budget -= humanResource.getTotalSalary();
    }
    public void increaseStaffTeachingYears() {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            staffIterator.next().increaseYearsOfTeaching();
        }
    }
    public void deductReputationForUninstructedStudents() {
        // For each uninstructed student deduct 1 reputation point
        int numberOfStudents = estate.getNumberOfStudents();
        if (instructedStudents < numberOfStudents) {
            reputation -= numberOfStudents - instructedStudents;
        }
        System.out.println("University (" + reputation + "): " + budget + " (BUD)");
    }
    public void handleStaffLeaving() {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        HashMap<Staff, Float> staffSalary = humanResource.getStaffSalary();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            // On 30th year staff quits
            if (staff.getYearsOfTeaching() == 30) {
                staffSalary.remove(staff);
            }
            else {
                // Chance staff stays is staff's stamina so 100 stamina => staff stays
                int staffStamina = staff.getStamina();
                // 1 to 100
                double random = Math.random() * 100 + 1;
                // E.g. when staffStamina = 100 (100%), random will always be lower or equal, so
                // it will be false and staff will stay
                // When staffStamina = 0 (0%), random will always be higher, so it will be true
                // and staff will leave
                if (random > staffStamina) {
                    staffSalary.remove(staff);
                }
            }
        }
    }
    public void replenishStaffStamina() {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            staffIterator.next().replenishStamina();
        }
    }
    public Estate getEstate() {
        return estate;
    }
    public void printEstateInfo() {
        System.out.println("*Estate*");
        System.out.println("Number of students: " + estate.getNumberOfStudents());
        for (Facility facility : estate.getFacilities()) {
            System.out.println("Building (" + facility.getClass().getSimpleName() + "): level " +
                    facility.getLevel());
        }
    }
    public void printHRInfo() {
        System.out.println("*Human Resource*");
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            System.out.println(staff.getName() + " (" + staff.getSkill() + "): " + staff.getStamina()
                    + " (STA), " + staff.getYearsOfTeaching() + " (TEA) => " +
                    humanResource.getStaffSalary().get(staff));
        }
    }
}
