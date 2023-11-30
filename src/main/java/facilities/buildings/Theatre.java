package facilities.buildings;
import facilities.Facility;
public class Theatre extends Facility implements Building
{
    public Theatre(String name)
    {
        super(name);
        level = 1;
        maxLevel = Constants.THEATRE.getValuesArray()[0];
        baseCapacity = Constants.THEATRE.getValuesArray()[1];
        baseCost = Constants.THEATRE.getValuesArray()[2];
    }
}
