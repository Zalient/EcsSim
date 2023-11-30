package facilities;

public class Facility
{
    public enum Constants
    {
        //maxLevel, baseCapacity, then baseCost
        //useful when it is known whether a facility is a hall, lab, or theatre
        HALL(new int[]{4, 6, 100}),
        LAB(new int[]{5, 5, 300}),
        THEATRE(new int[]{6, 10, 200});
        private final int[] valuesArray;
        Constants(final int[] _valuesArray)
        {
            valuesArray = _valuesArray;
        }
        public int[] getValuesArray() { return valuesArray; }
    }
    protected int baseCost;
    protected int baseCapacity;
    protected int maxLevel;
    protected int level;
    private String name;
    public Facility(String _name)
    {
        name = _name;
    }
    public String getName()
    {
        return name;
    }

    public int getLevel()
    {
        return level;
    }
    public void increaseLevel()
    {
        level++;
    }
    public int getUpgradeCost()
    {
        if (level == maxLevel)
        {
            return -1;
        }
        else
        {
            return baseCost * (level + 1);
        }
    }
    public int getCapacity()
    {
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
    public String getType()
    {
        return getClass().getSimpleName();
    }
}
