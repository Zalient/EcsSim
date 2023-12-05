package facilities;

// All buildings are facilities, but not all facilities are buildings
public class Facility {
    // A facility does not have levels, capacities, and costs - just a name
    // This means since all buildings are facilities, all buildings have a name
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
