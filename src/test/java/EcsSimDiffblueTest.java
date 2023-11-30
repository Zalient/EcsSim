import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import facilities.Facility;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import university.Estate;
import university.Staff;
import university.University;

class EcsSimDiffblueTest {
  /** Method under test: default or parameterless constructor of {@link EcsSim} */
  @Test
  void testConstructor() {
    EcsSim actualEcsSim = new EcsSim();
    University university = actualEcsSim.getUniversity();
    assertEquals(0.0f, university.getEstate().getMaintenanceCost());
    assertEquals(0.0f, university.getBudget());
    assertTrue(actualEcsSim.getStaffMarket().isEmpty());
  }

  /** Method under test: {@link EcsSim#simulate()} */
  @Test
  void testSimulate() {
    EcsSim ecsSim = new EcsSim();
    ecsSim.simulate();
    assertEquals(0.0f, ecsSim.getUniversity().getBudget());
    assertTrue(ecsSim.getStaffMarket().isEmpty());
  }

  /** Method under test: {@link EcsSim#simulate(int)} */
  @Test
  void testSimulate2() {
    EcsSim ecsSim = new EcsSim();
    ecsSim.simulate(1);
    assertEquals(0.0f, ecsSim.getUniversity().getBudget());
    assertTrue(ecsSim.getStaffMarket().isEmpty());
  }

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link EcsSim#getStaffMarket()}
   *   <li>{@link EcsSim#getUniversity()}
   * </ul>
   */
  @Test
  void testGetStaffMarket() {
    EcsSim ecsSim = new EcsSim();
    ArrayList<Staff> actualStaffMarket = ecsSim.getStaffMarket();
    University actualUniversity = ecsSim.getUniversity();
    Estate estate = actualUniversity.getEstate();
    assertEquals(0, estate.getNumberOfStudents());
    assertEquals(0, actualUniversity.getReputation());
    assertEquals(0, estate.getFacilities().length);
    assertEquals(0.0f, estate.getMaintenanceCost());
    assertEquals(0.0f, actualUniversity.getBudget());
    HashMap<String, Integer> facilityCapacityMap = estate.getFacilityCapacityMap();
    assertEquals(3, facilityCapacityMap.size());
    assertTrue(actualStaffMarket.isEmpty());
    ArrayList<Facility> lowestLevelFacilityTypes = estate.getLowestLevelFacilityTypes();
    assertTrue(lowestLevelFacilityTypes.isEmpty());
    Integer expectedGetResult = new Integer(0);
    assertEquals(expectedGetResult, facilityCapacityMap.get("Hall"));
    Integer expectedGetResult2 = new Integer(0);
    assertEquals(expectedGetResult2, facilityCapacityMap.get("Lab"));
    Integer expectedGetResult3 = new Integer(0);
    assertEquals(expectedGetResult3, facilityCapacityMap.get("Theatre"));
    assertEquals(actualStaffMarket, lowestLevelFacilityTypes);
  }
}
