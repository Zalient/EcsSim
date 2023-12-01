package facilities.buildings;
import facilities.Facility;
public class Lab extends Facility implements Building {
    public Lab(String name) {
        super(name);
        level = 1;
        maxLevel = Constants.LAB.getValuesArray()[0];
        baseCapacity = Constants.LAB.getValuesArray()[1];
        baseCost = Constants.LAB.getValuesArray()[2];
    }
}
