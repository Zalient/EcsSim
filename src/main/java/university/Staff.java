package university;

public class Staff {
    private String name;
    private int skill;
    private int yearsOfTeaching;
    private int stamina;
    public Staff(String _name, int _skill) {
        name = _name;
        skill = _skill;
        yearsOfTeaching = 0;
        stamina = 100;
    }
    // Assign a number of students that a staff will teach
    public int instruct(int numberOfStudents) {
        // If skill is already 100 then do not go above it
        skill = (skill == 100) ? 100 : skill + 1;
        // Stamina decrease formula
        stamina = stamina - (int)java.lang.Math.ceil((double) numberOfStudents /(20 + skill)) * 20;
        // Reputation points according to numberOfStudents
        int reputationIncrease = (100 * skill) / (100 + numberOfStudents);
        System.out.println(name + " (" + skill + "): " + stamina
                + " (STA), " + yearsOfTeaching + " (TEA) instructs " +
                numberOfStudents + " gains " + reputationIncrease);
        return reputationIncrease;
    }
    public void replenishStamina()
    {
        stamina = (stamina == 100) ? 100 : stamina + 20;
    }
    public void increaseYearsOfTeaching()
    {
        yearsOfTeaching++;
    }
    public int getSkill()
    {
        return skill;
    }
    public String getName()
    {
        return name;
    }
    public int getStamina()
    {
        return stamina;
    }
    public int getYearsOfTeaching()
    {
        return yearsOfTeaching;
    }
}
