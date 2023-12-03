import university.Staff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class StaffReader {
    BufferedReader reader;
    public StaffReader(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ignored) {}
    }
    private String getLine() {
        try {
            return reader.readLine();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Staff> readStaffMarket() {
        ArrayList<Staff> staffMarket = new ArrayList<>();
        String line;
        while ((line = getLine()) != null) {
            String[] parts = line.split(" ");
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) {
                name.append(parts[0]);
                if (parts.length > 2 && i != parts.length - 2) {
                    name.append(" ");
                }
            }
            String skillWithBrackets = parts[parts.length - 1];
            int skill = Integer.parseInt(skillWithBrackets.split("")[1]);
            staffMarket.add(new Staff(name.toString(), skill));
        }
        return staffMarket;
    }
}
