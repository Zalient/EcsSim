import university.Staff;
import university.University;
import java.util.ArrayList;

public class EcsSim {
    private University university;
    private ArrayList<Staff> staffMarket;

    public EcsSim() {
        // Funding can be 0 for now
        university = new University(10000);
        staffMarket = new ArrayList<Staff>();
    }
    public void simulate() {
        // Simulate a year of running the university - methods should always go in this order
        // 1a
        university.buildOrUpgrade();
        // 1b
        university.increaseBudget(university.getEstate().getNumberOfStudents() * 10);
        // 1c
        // Assigning to staffMarket to update number of staff available in market after hiring
        staffMarket = university.hireStaff(staffMarket);
        // 2
        university.allocateStaff();
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
        university.handleStaffLeaving();
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
                Thread.sleep(500);
                System.out.println("***** Year " + i + " *****");
                simulate();
            } catch (InterruptedException e) {
                // Terminate the simulation
            }
        }
    }
    public University getUniversity()
    {
        return university;
    }
    public ArrayList<Staff> getStaffMarket()
    {
        return staffMarket;
    }
    public void printStaffMarketInfo() {
        System.out.println("\nMarket");
        for (Staff staff : staffMarket) {
            System.out.println(staff.getName() + "(" + staff.getSkill() + "): " + staff.getStamina() +
                    " (STA), " + staff.getYearsOfTeaching() + " (TEA)");
        }
    }
}
