package facilities.buildings;

public interface Building {
    // Read-only store of data to keep values maintained
    enum Constants {
        // The order is maxLevel, baseCapacity, then baseCost
        // Useful when it is known whether a facility is a hall, lab, or theatre
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
    int getLevel();
    void increaseLevel();
    int getUpgradeCost();
    int getCapacity();
    int getMaxLevel();
    int getBaseCapacity();
    int getBaseCost();
}
