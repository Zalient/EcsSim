package facilities.buildings;
import facilities.Facility;
public class Theatre extends Facility implements Building {
    private final int baseCost;
    private final int baseCapacity;
    private final int maxLevel;
    private int level;
    public Theatre(String name) {
        super(name);
        level = 1;
        maxLevel = Constants.THEATRE.getValuesArray()[0];
        baseCapacity = Constants.THEATRE.getValuesArray()[1];
        baseCost = Constants.THEATRE.getValuesArray()[2];
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
