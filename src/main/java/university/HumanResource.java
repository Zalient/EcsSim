package university;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class HumanResource {
    HashMap<Staff, Float> staffSalary;
    public HumanResource()
    {
        staffSalary = new HashMap<>();
    }
    public void addStaff(Staff staff) {
        float randomPercentage = (float) (Math.random() + 9.5);
        float salary = staff.getSkill() * (randomPercentage / 100);
        staffSalary.put(staff, salary);
    }
    public Iterator<Staff> getStaff()
    {
        return staffSalary.keySet().iterator();
    }
    public float getTotalSalary() {
        Collection<Float> salaries = staffSalary.values();
        float totalSalary = 0;
        for (float salary : salaries) {
            totalSalary += salary;
        }
        return totalSalary;
    }
    public HashMap<Staff, Float> getStaffSalary()
    {
        return staffSalary;
    }
}
