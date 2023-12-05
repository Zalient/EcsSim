package facilities.buildings;
import facilities.Facility;
public class Lab extends Facility implements Building {
    private final int baseCost;
    private final int baseCapacity;
    private final int maxLevel;
    private int level;
    public Lab(String name) {
        super(name);
        level = 1;
        maxLevel = Constants.LAB.getValuesArray()[0];
        baseCapacity = Constants.LAB.getValuesArray()[1];
        baseCost = Constants.LAB.getValuesArray()[2];
    }
    public int getLevel()
    {
        return level;
    }
    public void increaseLevel()
    {
        level++;
    }
    public int getUpgradeCost() {
        if (level == maxLevel) {
            return -1;
        }
        else {
            return baseCost * (level + 1);
        }
    }
    public int getCapacity() {
        return baseCapacity * (int)Math.pow(2, level - 1);
    }
    public int getMaxLevel()
    {
        return maxLevel;
    }
    public int getBaseCapacity() { return baseCapacity; }
    public int getBaseCost()
    {
        return baseCost;
    }
}
