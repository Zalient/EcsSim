import university.*;
import facilities.*;

import java.util.Iterator;

public class Main {
    public static void main(String[] args)
    {
        // Creating a HumanResource object
        HumanResource humanResource = new HumanResource();

        // Creating several Staff objects
        Staff staff1 = new Staff("John Doe", 80);
        Staff staff2 = new Staff("Jane Smith", 90);
        Staff staff3 = new Staff("Bob Johnson", 70);

        // Adding staff to the university
        humanResource.addStaff(staff1);
        humanResource.addStaff(staff2);
        humanResource.addStaff(staff3);

        // Testing getStaff() method
        System.out.println("University Staff:");
        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext()) {
            Staff staff = staffIterator.next();
            System.out.println("Name: " + staff.getName() + ", Skill: " + staff.getSkill());
        }

        // Testing getTotalSalary() method
        float totalSalary = humanResource.getTotalSalary();
        System.out.println("Total Salary of all staff: $" + totalSalary);

        // Testing staff methods
        System.out.println("\nInstructing students:");
        int numberOfStudents = 50;
        int reputationPoints = staff1.instruct(numberOfStudents);
        System.out.println(staff1.getName() + " instructed " + numberOfStudents + " students, earning " +
                reputationPoints + " reputation points.");

        System.out.println("\nReplenishing stamina:");
        staff1.replenishStamina();
        System.out.println(staff1.getName() + "'s stamina after replenishing: " + staff1.getStamina());

        System.out.println("\nIncreasing years of teaching:");
        staff1.increaseYearsOfTeaching();
        System.out.println(staff1.getName() + "'s years of teaching after increase: " + staff1.getYearsOfTeaching());
    }
}