import facilities.Facility;
import facilities.buildings.Building;
import university.Staff;
import university.University;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EcsSim implements Serializable {
    private static University university = null;
    private final ArrayList<Staff> staffMarket;
    public EcsSim(int initialFunding, ArrayList<Staff> _staffMarket) {
        university = new University(initialFunding);
        staffMarket = _staffMarket;
    }
    private void simulate() {
        // Simulate a year of running the university - methods should always go in this order
        // 1a
        university.buildOrUpgrade();
        // 1b
        university.increaseBudget(university.getEstate().getNumberOfStudents() * 10);
        // 1c and 2
        HashMap<Staff, Integer> staffAllocationMap = university.hireAndAllocateStaff(staffMarket);
        university.printHiredStaff();
        university.printStaffInstructing(staffAllocationMap);
        // 3a - careful of budget dropping to negative
        university.payMaintenanceCost();
        // 3b
        university.payStaffSalary();
        // 3c
        university.increaseStaffTeachingYears();
        // 3d
        university.deductReputationForUninstructedStudents();
        // Print estate info before moving on to next part (just for formatting purposes)
        university.printEstateInfo();
        // 3e
        // Update number of staff available in market after leaving
        university.handleStaffLeaving(staffMarket);
        // 3f
        university.replenishStaffStamina();
        // Print human resource info at end of year
        university.printHRInfo();
        // Print market after the year's events (to be seen before moving to next year)
        printStaffMarketInfo();
        System.out.println();
    }
    public void simulate(int years) {
        for (int i = 0; i < years; i++) {
            try {
                Thread.sleep(200);
                int count = 0;
                ArrayList<Staff> combinedStaff = new ArrayList<>();
                combinedStaff.addAll(staffMarket);
                combinedStaff.addAll(university.getHumanResource().getStaffSalary().keySet());
                for (Staff staff : combinedStaff) {
                    if (staff.getYearsOfTeaching() == 30) {
                        count++;
                    }
                }
                if (count == combinedStaff.size()) {
                    System.out.println("No more staff available => simulation stopped");
                    break;
                }
                else {
                    System.out.println("***** Year " + i + " *****");
                    simulate();
                    save(i + 1, years);
                }
            } catch (InterruptedException e) {
                // Terminate the simulation
            }
        }
    }
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java EcsSim <staffConfigurationFile> <initialFunding> " +
                    "<simulationYears>");
            System.exit(1);
        }
        String staffConfigFile = args[0];
        int initialFunding = Integer.parseInt(args[1]);
        int simulationYears = Integer.parseInt(args[2]);
        Toolbox myToolbox = new Toolbox();
        System.out.println("Would you like to load or start a new simulation? (L/S)");
        String choice = myToolbox.readStringFromCmd();
        try {
            if (Objects.equals(choice, "S")) {
                Reader staffReader = new Reader(staffConfigFile);
                EcsSim ecsSim = new EcsSim(initialFunding, staffReader.readStaffMarket());
                ecsSim.simulate(simulationYears);
            }
            else {
                load();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void printStaffMarketInfo() {
        System.out.println("\nMarket");
        for (Staff staff : staffMarket) {
            System.out.println(staff.getName() + " (" + staff.getSkill() + "): " + staff.getStamina() +
                    " (STA), " + staff.getYearsOfTeaching() + " (TEA)");
        }
    }
    public void save(int currentYear, int totalYears) {
        try {
            // Save staff
            PrintStream ps = new PrintStream(new FileOutputStream("staffSave.txt"));
            ArrayList<Staff> combinedStaff = new ArrayList<>();
            combinedStaff.addAll(university.getHumanResource().getStaffSalary().keySet());
            combinedStaff.addAll(staffMarket);
            for (Staff staff : combinedStaff) {
                ps.println(staff.getName() + "," + staff.getSkill() + "," + staff.getStamina() + ","
                        + staff.getYearsOfTeaching());
            }
            ps.close();
            // Save facilities
            ps = new PrintStream(new FileOutputStream("facilitiesSave.txt"));
            for (Facility facility : university.getEstate().getFacilities()) {
                ps.println(
                        facility.getClass().getSimpleName() + "," + facility.getName() + ","
                                + ((Building) facility).getLevel());
            }
            ps.close();
            // Save years
            ps = new PrintStream(new FileOutputStream("yearsSave.txt"));
            ps.println(currentYear + "," + totalYears);
            ps.close();
            // Save budget
            ps = new PrintStream(new FileOutputStream("budgetSave.txt"));
            ps.println(university.getBudget());
            ps.close();
            // Save reputation
            ps = new PrintStream(new FileOutputStream("reputationSave.txt"));
            ps.println(university.getReputation());
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void load() {
        Reader loadReader = new Reader("yearsSave.txt");
        int[] years = loadReader.readSavedYears();
        int currentYear = years[0];
        int totalYears = years[1];

        loadReader = new Reader("budgetSave.txt");
        float budget = loadReader.readSavedBudget();

        loadReader = new Reader("reputationSave.txt");
        int reputation = loadReader.readSavedReputation();

        loadReader = new Reader("staffSave.txt");
        ArrayList<Staff> savedStaff = loadReader.readSavedStaff();

        EcsSim ecsSim = new EcsSim((int) budget, savedStaff);
        university.setReputation(reputation);

        loadReader = new Reader("facilitiesSave.txt");
        loadReader.readSavedFacilities(university);

        ecsSim.simulate(totalYears - currentYear);
    }
}
