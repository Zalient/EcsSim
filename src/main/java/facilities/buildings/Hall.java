package facilities.buildings;
import facilities.Facility;
public class Hall extends Facility implements Building {
    public Hall(String name) {
        super(name);
        level = 1;
        maxLevel = Constants.HALL.getValuesArray()[0];
        baseCapacity = Constants.HALL.getValuesArray()[1];
        baseCost = Constants.HALL.getValuesArray()[2];
    }
}
