package facilities;

// All buildings are facilities, but not all facilities are buildings
public class Facility {
    protected int baseCost;
    protected int baseCapacity;
    protected int maxLevel;
    protected int level;
    private final String name;
    public Facility(String _name)
    {
        name = _name;
    }
    public String getName()
    {
        return name;
    }
}
