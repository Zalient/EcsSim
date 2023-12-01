package university;

import static org.junit.jupiter.api.Assertions.assertThrows;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import org.junit.jupiter.api.Test;

class UniversityDiffblueTest {
  /** Method under test: {@link University#upgrade(Building)} */
  @Test
  void testUpgrade() throws Exception {
    University university = new University(1);
    assertThrows(Exception.class, () -> university.upgrade(new Hall("Name")));
  }
}
