import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import facilities.Facility;
import facilities.buildings.Building;
import university.Staff;
import university.University;

public class Reader {
    private final BufferedReader reader;
    public Reader(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
    }
    public ArrayList<Staff> readStaffMarket() {
        ArrayList<Staff> staffMarket = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                StringBuilder name = new StringBuilder();
                for (int i = 0; i < parts.length - 1; i++) {
                    name.append(parts[i]);
                    if (parts.length > 2 && i != parts.length - 2) {
                        name.append(" ");
                    }
                }
                String skillWithBrackets = parts[parts.length - 1];
                String[] skillWithBracketsArray = skillWithBrackets.split("");
                StringBuilder skillString = new StringBuilder();

                for (int i = 1; i < skillWithBracketsArray.length - 1; i++) {
                    skillString.append(skillWithBracketsArray[i]);
                }
                int skill = Integer.parseInt(String.valueOf(skillString));
                staffMarket.add(new Staff(name.toString(), skill));
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return staffMarket;
    }
    public ArrayList<Staff> readSavedStaff() {
        // Name, skill, stamina, years of teaching format
        ArrayList<Staff> staffList = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int skill = Integer.parseInt(parts[1]);
                int stamina = Integer.parseInt(parts[2]);
                int yearsOfTeaching = Integer.parseInt(parts[3]);
                Staff staff = new Staff(name, skill);
                staff.setStamina(stamina);
                staff.setYearsOfTeaching(yearsOfTeaching);
                staffList.add(staff);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return staffList;
    }
    public void readSavedFacilities(University university) {
        // Type, name, level format
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String name = parts[1];
                int level = -1;
                // Check if the facility is a building
                if (parts.length == 3) {
                    level = Integer.parseInt(parts[2]);
                }
                Facility facility = university.getEstate().addFacility(type, name);
                if (level != -1) {
                    ((Building) facility).setLevel(level);
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int[] readSavedYears() {
        int[] years = new int[2];
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentYear = Integer.parseInt(parts[0]);
                int totalYears = Integer.parseInt(parts[1]);
                years[0] = currentYear;
                years[1] = totalYears;
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return years;
    }
    public float readSavedBudget() {
        float budget = -1;
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                budget = Float.parseFloat(line);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return budget;
    }
    public int readSavedReputation() {
        int reputation = -1;
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                reputation = Integer.parseInt(line);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return reputation;
    }
}
