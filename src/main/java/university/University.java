package university;
import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.util.*;

public class University {
    private final HumanResource humanResource;
    private float budget;
    private final Estate estate;
    private int reputation;
    private int instructedStudents;

    public University(int funding) {
        budget = funding;
        estate = new Estate();
        humanResource = new HumanResource();
    }
    private Facility build(String type, String name) {
        HashMap<String, Integer> buildCosts = new HashMap<>();
        buildCosts.put("Hall", Building.Constants.HALL.getValuesArray()[2]);
        buildCosts.put("Lab", Building.Constants.LAB.getValuesArray()[2]);
        buildCosts.put("Theatre", Building.Constants.THEATRE.getValuesArray()[2]);
        Facility builtFacility = estate.addFacility(type, name);
        if (builtFacility != null)
        {
            budget -= buildCosts.get(type);
            reputation += 100;
            System.out.println("Built Building (" + type + "): level 1");
        }
        return estate.addFacility(type, name);
    }
    private void upgrade(Building building) throws Exception {
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
    public void increaseBudget(int amount)
    {
        budget += amount;
    }
    public void buildOrUpgrade()
    {
        // There should at least be 1 lab, 1 theatre, and 1 hall as a prerequisite
        if (estate.getFacilities().length == 0) {
            build("Hall", "H");
            build("Lab", "L");
            build("Theatre", "T");
        }
        // Build or upgrade algorithm:
        // Total hall, lab, theatre capacities mapped to hall, lab theatre strings respectively
        // (there is never multiple of 1 type)
        HashMap<String, Integer> buildingCapacityMap = estate.getBuildingCapacityMap();
        // This is a set as there could be two buildings with the same minimum value in which case
        // they are both bottlenecks
        HashSet<String> minCapacityBuildings =
                Helper.getKeysByValue(buildingCapacityMap, estate.getNumberOfStudents());
        // Look at each type of building that is causing problems for capacity
        // (e.g. lab and theatre)
        for (String minCapacityBuildingType : minCapacityBuildings) {
            // Since there will be multiple buildings of a single type (e.g. 5 halls),
            // do all necessary builds/upgrades until this type no longer causes problems
            while (estate.getBuildingCapacityMap().get(minCapacityBuildingType) ==
                    estate.getNumberOfStudents()) {
                // I need to prioritise the highest level buildings as buildings
                // should most of the time be upgraded to max level as soon as possible
                Building highestLevelBuilding =
                        estate.getHighestLevelBuilding(minCapacityBuildingType);
                // Check if building is max level (when max level
                // getHighestLevelBuilding() returns null)
                if (highestLevelBuilding == null) {
                    build(minCapacityBuildingType,
                            minCapacityBuildingType.substring(0, 1));
                }
                else {
                    // Create a dummy building that can be upgraded
                    Building simulateUpgrade = null;
                    if (highestLevelBuilding instanceof Hall) {
                        simulateUpgrade = new Hall("S");
                    }
                    else if (highestLevelBuilding instanceof Lab) {
                        simulateUpgrade = new Lab("S");
                    }
                    else if (highestLevelBuilding instanceof Theatre) {
                        simulateUpgrade = new Theatre("S");
                    }
                    if (simulateUpgrade == null) {
                        return;
                    }
                    // Look ahead and see if upgrading to max level is worth it
                    int levelCombinationsCapacity =
                            getLevelCombinationsCapacity(simulateUpgrade);
                    // If it is worth, do an upgrade
                    if (simulateUpgrade.getCapacity() >= levelCombinationsCapacity) {
                        try {
                            if (budget >= highestLevelBuilding.getUpgradeCost())
                                upgrade(highestLevelBuilding);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // If not, build
                    else {
                        if (budget >= highestLevelBuilding.getBaseCost()) {
                            build(minCapacityBuildingType,
                                    minCapacityBuildingType.substring(0, 1));
                        }
                    }
                }
            }
        }
    }
    private int getLevelCombinationsCapacity(Building simulateUpgrade) {
        // Combinations algorithm - find out how much capacity I can get from a
        // mixture of building and upgrading as opposed to just upgrading to max level
        // Create a map of cost to capacity which only stores the costs that win on
        // capacity at a certain level
        HashMap<Integer, Integer> winningCapacityMap = new HashMap<>();
        // It will have the baseCost by default as upgrading is weighted against just
        // building loads of level 1 versions
        winningCapacityMap.put(simulateUpgrade.getBaseCost(),
                simulateUpgrade.getBaseCapacity());
        int totalUpgradeCost = simulateUpgrade.getBaseCost();
        // Get the dummy building to max level and see if any of its levels on the way
        // there win on capacity (for the same cost of course)
        for (int i = 1; i < simulateUpgrade.getMaxLevel(); i++) {
            totalUpgradeCost += simulateUpgrade.getUpgradeCost();
            simulateUpgrade.increaseLevel();
            // By default, exclude the last winning capacity as it is the max level
            // and I cannot use the max level version of a building in combinations
            // that would total the cost of building the max level
            // building in the first place
            if (simulateUpgrade.getLevel() != simulateUpgrade.getMaxLevel()) {
                // E.g. if I have 1500 cost and 100 base cost to build hall, then I can
                // build 15 halls
                int numberOfPossibleLevel1Buildings = totalUpgradeCost /
                        simulateUpgrade.getBaseCost();
                // Get the corresponding total capacity of the building
                int level1BuildingsCumulativeCapacity = numberOfPossibleLevel1Buildings *
                        simulateUpgrade.getBaseCapacity();
                // If my building's capacity at a certain level beats the cumulative
                // capacity then it is a winning capacity
                if (simulateUpgrade.getCapacity() > level1BuildingsCumulativeCapacity) {
                    winningCapacityMap.put(totalUpgradeCost, simulateUpgrade.getCapacity());
                }
            }
        }
        // Now look through winning capacities and figure out what
        // combinations gives the best total capacity
        int levelCombinationsCapacity = 0;
        ArrayList<Integer> winningCapacityCosts =
                new ArrayList<>(winningCapacityMap.keySet());
        // I want to go backwards through the capacities, as the last capacity that was
        // added is the best
        winningCapacityCosts.sort(Collections.reverseOrder());
        int bestCapacityCost = winningCapacityCosts.get(0);
        // Work out how many of this best capacity I can afford
        int howManyTimes = totalUpgradeCost / bestCapacityCost;
        levelCombinationsCapacity += winningCapacityMap.get(bestCapacityCost) * howManyTimes;
        // Work out if I have any left over for other winning capacity combinations
        int amountForCombinations = totalUpgradeCost - (bestCapacityCost * howManyTimes);
        // A loop for going through the other winning capacity combinations,
        // basically the same thing as before
        for (int i = 1; i < winningCapacityCosts.size() &&
                amountForCombinations > 0; i++) {
            int nextBestCapacityCost = winningCapacityCosts.get(i);
            howManyTimes = amountForCombinations / nextBestCapacityCost;
            if (howManyTimes > 0) {
                levelCombinationsCapacity +=
                        winningCapacityMap.get(nextBestCapacityCost) * howManyTimes;
                amountForCombinations -= nextBestCapacityCost * howManyTimes;
            }
        }
        return levelCombinationsCapacity;
    }
    public ArrayList<Staff> hireStaff(ArrayList<Staff> staffMarket) {
        return staffMarket;
    }
    public void allocateStaff() {

    }
    /*public ArrayList<Staff> hireStaff(ArrayList<Staff> staffMarket) {
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
    }*/
    /*public void allocateStaff() {
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
    }*/
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
                    ((Building) facility).getLevel());
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
    public int getReputation()
    {
        return reputation;
    }
    public float getBudget()
    {
        return budget;
    }
}
