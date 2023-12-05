package university;
import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.util.*;

public class University {
    int years = 0;
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
        return builtFacility;
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
                            else {
                                break;
                            }
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
                        else {
                            break;
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
    private HashMap<Staff, Integer> hireStaff(ArrayList<Staff> staffMarket) {
        // Fire all staff before starting - e.g. if I have 2 staff already (because 1 of them was taking
        // the load while the other rested) then I want it to go back down to 1 staff if the
        // staff that rested is back to 100 stamina
        if (years == 76) {
            System.out.println();
        }
        years++;

        HashMap<Staff, Integer> staffAllocationMap = new HashMap<>();
        int totalNumberInstructed = 0;
        // Count variable to account for scalability e.g. as more students get added,
        // the stamina decrease will inevitably continue to increase due to the finite pool of staff
        // Additionally, 20 + staff.getSkill() provides a nice way to give higher skill staff more
        // students but to make it scalable, it is necessary to multiply it by a scaling factor
        // (the count) which means I do not get stuck in the loop where numStudents > totalNumberInstructed
        int count = 0;
        float averageStamina = getAverageStamina(staffMarket);
        // When all available staff have no stamina, the simulation has to stop
        if (averageStamina != 0) {
            while (estate.getNumberOfStudents() > totalNumberInstructed) {
                Iterator<Staff> staffIterator = humanResource.getStaff();
                while (staffIterator.hasNext()) {
                    Staff staff = staffIterator.next();
                    staffMarket.add(staff);
                    staffIterator.remove();
                }
                // Lowest to highest skill - minimising salary costs
                staffMarket.sort(Comparator.comparing(Staff::getSkill));
                totalNumberInstructed = 0;
                count++;
                Iterator<Staff> staffMarketIterator = staffMarket.iterator();
                while (staffMarketIterator.hasNext()) {
                    Staff staff = staffMarketIterator.next();
                    if (!(humanResource.getStaffSalary().containsKey(staff))
                            && staff.getYearsOfTeaching() < 30) {
                        // If number to instruct <= 20 + staff.get(skill) then stamina decrease is 20,
                        // simulateNumber
                        // is a dummy variable to simulate the changes in number of instructed students
                        int simulateNumberInstructed =
                                Math.min(20 + staff.getSkill(), estate.getNumberOfStudents());
                        // Stamina decrease formula specified in specification
                        int staminaDecrease =
                                (int)
                                        java.lang.Math.ceil(
                                                (double) simulateNumberInstructed / (20 + staff.getSkill()))
                                        * 20;
                        // Add staff that have relatively low stamina so that they can recharge
                        // If the staff aren't going to rest, they need to have a low stamina decrease, and it
                        // must be checked that numStudents < totalNumberInstructed so that the entire staff
                        // market doesn't get added
                        if ((staminaDecrease <= 20 * count
                                && estate.getNumberOfStudents() > totalNumberInstructed)
                                || staff.getStamina() < averageStamina) {
                            humanResource.addStaff(staff);
                            staffMarketIterator.remove();
                            // Only working staff have students allocated to them
                            if (staff.getStamina() * count >= averageStamina) {
                                // Scale the number instructed by count
                                totalNumberInstructed += simulateNumberInstructed * count;
                                staffAllocationMap.put(staff, simulateNumberInstructed * count);
                            }
                        }
                    }
                }
            }
        }
        else {
            System.out.println("\nNo stamina left on available staff => simulation stopped");
            System.exit(0);
        }
        return staffAllocationMap;
    }
    public HashMap<Staff, Integer> hireAndAllocateStaff(ArrayList<Staff> staffMarket) {
        HashMap<Staff, Integer> staffAllocationMap = hireStaff(staffMarket);
        int totalNumberInstructed = 0;
        for (Integer allocatedStudents : staffAllocationMap.values())
        {
            totalNumberInstructed += allocatedStudents;
        }
        // The totalNumberInstructed is always greater than estate.getNumberOfStudents() by this point so
        // the difference needs to be split by the number of working staff and subtracted from what they
        // would have been allocated
        int difference = totalNumberInstructed - estate.getNumberOfStudents();
        int amountToRemoveFromEach = difference / staffAllocationMap.size();
        // If difference = 5 and size = 3 then amount = 1 with 2 remainder
        int leftover = difference % staffAllocationMap.size();
        for (Staff staff : staffAllocationMap.keySet())
        {
            instructedStudents += staff.instruct(staffAllocationMap.get(staff) -
                    amountToRemoveFromEach - leftover);
            // Update staff allocation map
            staffAllocationMap.put(staff, staffAllocationMap.get(staff) -
                    amountToRemoveFromEach - leftover);
            // Only one staff can get rid of the leftover amount
            leftover = 0;
        }
        return staffAllocationMap;
    }
    private float getAverageStamina(ArrayList<Staff> staffMarket) {
        ArrayList<Staff> combinedStaff = new ArrayList<>();
        combinedStaff.addAll(humanResource.getStaffSalary().keySet());
        combinedStaff.addAll(staffMarket);
        int totalStamina = 0;
        for (Staff staff : combinedStaff) {
            if (staff.getYearsOfTeaching() < 30) {
                totalStamina += staff.getStamina();
            }
        }
        return (float) totalStamina / combinedStaff.size();
    }
    public void payMaintenanceCost() {
        budget -= estate.getMaintenanceCost();
    }
    public void payStaffSalary() {
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
    public void handleStaffLeaving(ArrayList<Staff> staffMarket) {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            // Chance staff stays is staff's stamina so 100 stamina => staff stays
            int staffStamina = staff.getStamina();
            // 1 to 100
            double random = Math.random() * 100 + 1;
            // E.g. when staffStamina = 100 (100%), random will always be lower or equal, so
            // it will be false and staff will stay
            // When staffStamina = 0 (0%), random will always be higher, so it will be true
            // and staff will leave

            // On 30th year staff quits OR staff's stamina as a percentage chance of staying
            if (staff.getYearsOfTeaching() >= 30 || random > staffStamina) {
                staffMarket.add(staff);
                staffIterator.remove();
                System.out.println(staff.getName() + " (" + staff.getSkill() + "): " +
                        staff.getStamina() + " (STA), " + staff.getYearsOfTeaching() +
                        " (TEA) has left the university");
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
            if (facility instanceof Building) {
                System.out.println("Building (" + facility.getClass().getSimpleName() + "): level " +
                        ((Building) facility).getLevel());
            }
            else {
                System.out.println("Facility (" + facility.getClass().getSimpleName() + ")");
            }
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
    public void printHiredStaff() {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            System.out.println("Hired " + staff.getName() + " (" + staff.getSkill() + "): " +
                    staff.getStamina() + " (STA), " + staff.getYearsOfTeaching() + " (TEA)");
        }
    }
    public void printStaffInstructing(HashMap<Staff, Integer> staffAllocationMap) {
        for (Staff staff : staffAllocationMap.keySet()) {
            int reputationIncrease = (100 * staff.getSkill()) / (100 + staffAllocationMap.get(staff));
            System.out.println(staff.getName() + " (" + staff.getSkill() + "): " + staff.getStamina()
                    + " (STA), " + staff.getYearsOfTeaching() + " (TEA) instructs " +
                    staffAllocationMap.get(staff) + " gains " + reputationIncrease);
        }
    }
    public HumanResource getHumanResource() {
        return humanResource;
    }
}
