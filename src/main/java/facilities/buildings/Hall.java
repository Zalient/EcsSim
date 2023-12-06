package facilities.buildings;
import facilities.Facility;
public class Hall extends Facility implements Building {
    private final int baseCost;
    private final int baseCapacity;
    private final int maxLevel;
    private int level;
    public Hall(String name) {
        super(name);
        level = 1;
        maxLevel = Constants.HALL.getValuesArray()[0];
        baseCapacity = Constants.HALL.getValuesArray()[1];
        baseCost = Constants.HALL.getValuesArray()[2];
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
    public void setLevel(int _level) {
        level = _level;
    }
}
