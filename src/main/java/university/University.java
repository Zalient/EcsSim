package university;
import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.util.*;

public class University
{
    private HumanResource humanResource;
    private float budget;
    private Estate estate;
    private int reputation;
    private int instructedStudents;

    public University(int funding)
    {
        budget = funding;
        estate = new Estate();
        humanResource = new HumanResource();
    }
    public Facility build(String type, String name)
    {
        HashSet<String> allowedTypes = new HashSet<>(Set.of("Hall", "Lab", "Theatre"));
        HashMap<String, Integer> buildCosts = new HashMap<String, Integer>();
        buildCosts.put("Hall", Facility.Constants.HALL.getValuesArray()[2]);
        buildCosts.put("Lab", Facility.Constants.LAB.getValuesArray()[2]);
        buildCosts.put("Theatre", Facility.Constants.THEATRE.getValuesArray()[2]);
        //only allow "Hall","Lab","Theatre" types through
        if (!allowedTypes.contains(type)) {
            return null;
        }
        if (budget < buildCosts.get(type))
        {
            return null;
        }
        budget -= buildCosts.get(type);
        reputation += 100;
        return estate.addFacility(type, name);
    }
    public void upgrade(Building building) throws Exception
    {
        int upgradeCost = building.getUpgradeCost();
        if (!Arrays.asList(estate.getFacilities()).contains(building))
        {
            throw new Exception("The building is not part of the university.");
        }
        if (building.getLevel() == building.getMaxLevel())
        {
            throw new Exception("The building is already at the maximum level.");
        }
        if (upgradeCost > budget)
        {
            throw new Exception("Cannot upgrade building due to insufficient budget.");
        }
        building.increaseLevel();
        budget -= upgradeCost;
        reputation += 50;
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
    public void buildOrUpgrade()
    {
        // there should at least be 1 lab, 1 theatre, and 1 hall as a prerequisite
        int hallCount = 0;
        int labCount = 0;
        int theatreCount = 0;
        for (Facility facility : estate.getFacilities()) {
            if (facility instanceof Hall) {
                hallCount++;
            } else if (facility instanceof Lab) {
                labCount++;
            } else if (facility instanceof Theatre) {
                theatreCount++;
            }
        }
        if (hallCount == 0) {
            build("Hall", "H");
        }
        if (labCount == 0) {
            build("Lab", "L");
        }
        if (theatreCount == 0) {
            build("Theatre", "T");
        }
        // is it cheaper to build new facilities or upgrade for the same capacity increase?
        // do we want to consider reputation increases as well? +50 for upgrade and +100 for build
        // if considering rep, would need to know what 1 point of rep should be worth in terms of costpercapacity
        // total hall, lab, theatre capacities mapped to hall, lab theatre strings respectively (there
        // is never multiple of 1 type)
        HashMap<String, Integer> facilityCapacityMap = estate.getFacilityCapacityMap();
        // This is a set as there could be two facilities with the same minimum value in which case they
        // are both bottlenecks
        HashSet<String> minCapacityFacilities =
                Helper.getKeysByValue(facilityCapacityMap, estate.getNumberOfStudents());
        for (String minCapacityFacilityType : minCapacityFacilities) {
            // need to consider lowest level facilities as they are what should be upgraded
            // now that i have the type of the facility, the new bottleneck becomes the lowest level
            // facility in this type of facility
            ArrayList<Facility> lowestLevelFacilityTypes =
                    estate.getLowestLevelFacilityTypes();
            // define a variable to store the found facility
            Facility facility = null;
            // iterate through the lowestLevelFacilityTypes and find the facility by name
            // e.g. if my total hall capacity is the minimum, i want to find the lowest level hall
            // and upgrade it or build another if it already has a high enough level
            for (Facility lowLevelFacilityType : lowestLevelFacilityTypes) {
                // make sure im getting the right match
                if (lowLevelFacilityType.getType().equals(minCapacityFacilityType)) {
                    facility = lowLevelFacilityType;
                    // exit the loop once the facility is found
                    break;
                }
            }
            // check if the facility was found
            if (facility != null) {
                // code for the found facility
                // create a dummy facility that can be upgraded
                Facility simulateUpgrade = facility;
                simulateUpgrade.increaseLevel();
                // make costs relative to capacity for easy comparison
                int upgradeCostPerCapacity = facility.getUpgradeCost() / simulateUpgrade.getCapacity();
                int buildCostPerCapacity = facility.getBaseCost() / facility.getBaseCapacity();
                // upgrading greater than or equal to price of building for same capacity
                if (upgradeCostPerCapacity >= buildCostPerCapacity) {
                    // then it's clear we should make a new building instead! (since build gives more rep)
                    build(minCapacityFacilityType, minCapacityFacilityType.substring(0, 1));
                }
                // upgrading less than price of building for same capacity
                else {
                    // then let's upgrade
                    try {
                        upgrade((Building) facility);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public ArrayList<Staff> hireStaff(ArrayList<Staff> staffMarket)
    {
        Facility[] facilities = estate.getFacilities();
        int hallCount = 0;
        for (Facility facility : facilities)
        {
            if (facility instanceof Hall)
            {
                hallCount++;
            }
        }
        //not including number of halls as no staff required for hall facilities
        int staffedFacilitiesSize = facilities.length - hallCount;
        int currentStaffSize = humanResource.getNumberOfStaff();
        // if number of facilities - number of staff <= 0 then dont need more staff
        if (staffedFacilitiesSize - currentStaffSize > 0)
        {
            ArrayList<Staff> labStaffList = new ArrayList<Staff>();
            ArrayList<Staff> theatreStaffList = new ArrayList<Staff>();
            for (Staff staff : staffMarket)
            {
                // medium skill for lab and high skill for theatre
                if (staff.getSkill() >= 50 && staff.getSkill() < 80)
                {
                    labStaffList.add(staff);
                }
                else if (staff.getSkill() >= 80)
                {
                    theatreStaffList.add(staff);
                }
            }
            int labCount = 0;
            int theatreCount = 0;
            for (int i = 0; i < staffedFacilitiesSize - currentStaffSize; i++)
            {
                // only add staff to lab and theatres
                // amount of labs and theatres may differ so do not want medium skill assigned to theatres
                // therefore need to access the different lists for each
                if (facilities[i] instanceof Lab)
                {
                    humanResource.addStaff(labStaffList.get(labCount));
                    staffMarket.remove(labStaffList.get(labCount));
                    labCount++;
                }
                else if (facilities[i] instanceof Theatre)
                {
                    humanResource.addStaff(theatreStaffList.get(theatreCount));
                    staffMarket.remove(theatreStaffList.get(theatreCount));
                    theatreCount++;
                }
            }
        }
        return staffMarket;
    }
    public void allocateStaff()
    {
        // high skill -> theatres and medium skill -> labs
        // so lab and theatre capacities considered separately
        Facility[] facilities = estate.getFacilities();
        ArrayList<Integer> labCapacityList = new ArrayList<Integer>();
        ArrayList<Integer> theatreCapacityList = new ArrayList<Integer>();
        for (Facility facility : facilities)
        {
            if (facility instanceof Lab)
            {
                labCapacityList.add(facility.getCapacity());
            }
            else if (facility instanceof Theatre)
            {
                theatreCapacityList.add(facility.getCapacity());
            }
        }
        Iterator<Staff> staffIterator = humanResource.getStaff();
        int labCount = 0;
        int theatreCount = 0;
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            if (staff.getSkill() >= 50 && staff.getSkill() < 80)
            {
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
    public void increaseStaffTeachingYears()
    {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            staffIterator.next().increaseYearsOfTeaching();
        }
    }
    public void deductReputationForUninstructedStudents()
    {
        //for each uninstructed student deduct 1 reputation point
        int numberOfStudents = estate.getNumberOfStudents();
        if (instructedStudents < numberOfStudents)
        {
            reputation -= numberOfStudents - instructedStudents;
        }
    }
    public void handleStaffLeaving()
    {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        HashMap<Staff, Float> staffSalary = humanResource.getStaffSalary();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            // on 30th year staff quits
            if (staff.getYearsOfTeaching() == 30)
            {
                staffSalary.remove(staff);
            }
            else {
                //chance staff stays is staff's stamina so 100 stamina => staff stays
                int staffStamina = staff.getStamina();
                //1 to 100
                double random = Math.random() * 100 + 1;
                //e.g. when staffStamina = 100 (100%), random will always be lower or equal so
                //it will be false and staff will stay
                //when staffStamina = 0 (0%), random will always be higher so it will be true
                //and staff will leave
                if (random > staffStamina)
                {
                    staffSalary.remove(staff);
                }
            }
        }
    }
    public void replenishStaffStamina()
    {
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            staffIterator.next().replenishStamina();
        }
    }
    public Estate getEstate() {
        return estate;
    }
}
