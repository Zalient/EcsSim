import university.Staff;
import university.University;
import java.util.ArrayList;

public class EcsSim {
    private University university;
    private ArrayList<Staff> staffMarket;

    public EcsSim() {
        // funding can be 0 for now
        university = new University(0);
        staffMarket = new ArrayList<Staff>();
    }

    public void simulate() {
        //simulate a year of running the university
        // 1a
        university.buildOrUpgrade();
        // 1b
        university.increaseBudget(university.getEstate().getNumberOfStudents() * 10);
        // 1c
        // assigning to staffMarket to update number of staff available in market after hiring
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
        // 3e
        university.handleStaffLeaving();
        // 3f
        university.replenishStaffStamina();
        //could maybe do a print block to output end of each year
    }
    public void simulate(int years)
    {
        for (int i = 0; i < years; i++)
        {
            try {
                Thread.sleep(500) ;
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
}
