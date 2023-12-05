import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import university.Staff;

public class StaffReader {
    private BufferedReader reader;
    public StaffReader(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Staff configuration file not found");
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
        } catch (java.io.IOException e)
        {
            throw new RuntimeException(e);
        }
        return staffMarket;
    }
}
