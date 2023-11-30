/*
 * Copyright (c) 2023 University of Southampton.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Contributor:
 *   University of Southampton - Initial API and implementation
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test Part 1 of the Coursework.
 *
 * <ul>
 *   <li>{@link TestFacilitySignature}: Test the signature of the facilities.Facility class.
 *   <li>{@link TestFacilitySpecification}: Test the specification of the facilities.Facility class.
 *   <li>{@link TestBuildingSignature}: Test the signature of the facilities.buildings.Building
 *       class.
 *   <li>{@link TestHallSignature}: Test the signature of the facilities.buildings.Hall class.
 *   <li>{@link TestHallSpecification}: Test the specification of the facilities.buildings.Hall
 *       class.
 *   <li>{@link TestTheatreSignature}: Test the signature of the facilities.buildings.Theatre class.
 *   <li>{@link TestTheatreSpecification}: Test the specification of the
 *       facilities.buildings.Theatre class.
 * </ul>
 *
 * @author htson - v1.0 - Initial API and implementation
 * @version 1.0
 */
@DisplayName("Test Part 1 for ECS Sim Coursework")
public class TestEcsSimPart1 extends TestEcsSimCoursework {

  /**
   * Test class for <code>facilities.Facility</code>'s signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestFacility
   */
  @Nested
  @DisplayName("Test facilities.Facility's signature")
  public class TestFacilitySignature extends AbstractTestFacility {

    /**
     * Test the field and method signatures of <code>facilities.Facility</code> class.
     *
     * <ul>
     *   <li>Facility is a class.
     *   <li>Field <code>name</code> of the <code>String</code> type.
     *   <li>Has a constructor with argument <code>Facility(String)</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.Facility's field and method signatures")
    public void testFacility_Signature() {
      assertClass("");
      assertDeclaredField("", "name", String.class);
      assertConstructor("", String.class);
      assertMethod("", String.class, "getName");
    }
  }

  /**
   * Test class for <code>facilities.Facility</code>'s specification.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestFacility
   */
  @Nested
  @DisplayName("Test facilities.Facility's specification")
  public class TestFacilitySpecification extends AbstractTestFacility {

    /**
     * Test <code>facilities.Facility</code>'s <code>name</code> field and <code>getName()</code>
     * method. The precondition is having a facility created with constructor <code>Facility(String)
     * </code> with value <code>"Zepler"</code>.
     *
     * <p>The test checks if the <code>name</code> field and the returned value of the </code><code>
     * getName()</code> is <code>"Zepler"</code>.
     */
    @Test
    @DisplayName("Test facilities.Facility's name and getName()")
    public void testFacility_name() {
      // PRECONDITION
      Constructor<?> facilityConstructor = assertConstructor(PRECONDITION, String.class);
      Field nameField = assertDeclaredField(PRECONDITION, "name", String.class);
      Method getNameMethod = assertMethod(PRECONDITION, String.class, "getName");

      // Test the constructor itself
      Object zepler =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a facility using Facility(String) constructor",
                  facilityConstructor,
                  "Zepler");

      // Test the "name" field.
      new TestField() {
        @Override
        public void assertions() {
          assertEquals("Zepler", value, ": Incorrect facility's name from getName()");
        }
      }.run(": Failed to execute Facility.getName() method", nameField, zepler);

      // Test the "getName" method.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Zepler", returnedValue, ": Incorrect facility's name from getName()");
        }
      }.run(": Failed to execute Facility.getName() method", getNameMethod, zepler);
    }
  }

  /**
   * Test class for <code>facilities.buildings.Building</code>'s signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestBuilding
   */
  @Nested
  @DisplayName("Test facilities.buildings.Building's signature")
  public class TestBuildingSignature extends AbstractTestBuilding {

    /**
     * Test the method signatures of <code>facilities.buildings.Building</code> interface.
     *
     * <ul>
     *   <li>Check <code>Building</code> is an interface.
     *   <li>Check <code>Building</code> has a method <code>increaseLevel()</code>.
     *   <li>Check <code>Building</code> has a method <code>int getLevel()</code>.
     *   <li>Check <code>Building</code> has a method <code>int getUpgradeCost()</code>.
     *   <li>Check <code>Building</code> has a method <code>int getCapacity()</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Building's method signatures")
    public void testBuilding_Signature() {
      assertInterface("");
      assertMethod("", null, "increaseLevel");
      assertMethod("", int.class, "getLevel");
      assertMethod("", int.class, "getUpgradeCost");
      assertMethod("", int.class, "getCapacity");
    }
  }

  /**
   * Test class for <code>facilities.buildings.Hall</code>'s signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestHall
   */
  @Nested
  @DisplayName("Test facilities.buildings.Hall's signature")
  public class TestHallSignature extends AbstractTestHall {

    private Class<?> facilityClass;

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      facilityClass = getClassOrInterface("facilities.Facility");
      assertClass(PRECONDITION, facilityClass);
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
      assertInterface(PRECONDITION, buildingInterface);
    }

    /**
     * Test the class structure, field and method signatures of the <code>facilities.buildings.Hall
     * </code> class.
     *
     * <ul>
     *   <li>Check <code>Hall</code> extends <code>Facility</code>.
     *   <li>Check <code>Hall</code> implements <code>Building</code>.
     *   <li>Check <code>Hall</code> has a field <code>level</code> of type <code>int</code>.
     *   <li>Check <code>Hall</code> has a constructor <code>Hall(String)</code>.
     *   <li>Check <code>Hall</code> has a method <code>increaseLevel()</code>.
     *   <li>Check <code>Hall</code> has a method <code>int getLevel()</code>.
     *   <li>Check <code>Hall</code> has a method <code>int getUpgradeCost()</code>.
     *   <li>Check <code>Hall</code> has a method <code>int getCapacity()</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Hall's class structure, field and method signatures")
    public void testHall_Signature() {
      assertClass("");
      assertImplementOrExtend("", facilityClass);
      assertImplementOrExtend("", buildingInterface);
      assertInheritedField("", "level", int.class);
      assertConstructor("", String.class);
      assertAccessibleMethod("", null, "increaseLevel");
      assertAccessibleMethod("", int.class, "getLevel");
      assertAccessibleMethod("", int.class, "getUpgradeCost");
      assertAccessibleMethod("", int.class, "getCapacity");
    }
  }

  /**
   * Test class for <code>facilities.buildings.Hall</code>'s specification.
   *
   * @version 1.0
   * @see AbstractTestHall
   */
  @Nested
  @DisplayName("Test facilities.buildings.Hall's specification")
  public class TestHallSpecification extends AbstractTestHall {

    /**
     * Test the constructor of <code>facilities.buildings.Hall</code> class. Create a <code>Hall
     * </code> named <code>"Wessex"</code> and check if the name returned from <code>getName()
     * </code> is correct.
     */
    @Test
    @DisplayName("Test facilities.buildings.Hall's constructor")
    public void testHall_Constructor() {
      // PRECONDITION
      Constructor<?> hallConstructor = assertConstructor(PRECONDITION, String.class);
      Method getNameMethod = assertMethod(PRECONDITION, String.class, "getName");
      getNameMethod.setAccessible(true);

      // Test the constructor.
      Object wessex =
          new TestConstructor()
              .run(
                  ": Failed to create a hall using Hall(String) constructor",
                  hallConstructor,
                  "Wessex");

      // Test the return of getName method.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Wessex", returnedValue, ": Incorrect hall's name");
        }
      }.run(": Failed to execute Hall.getName() method", getNameMethod, wessex);
    }

    /**
     * Test the level, getLevel(), and increaseLevel() of <code>facilities.buildings.Hall</code>
     * class. The precondition is to have a <code>Hall</code> (named <code>"Wessex"</code>). The
     * test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the initial level is 1 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 2 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 3 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 4 (using <code>getLevel</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Hall's level, getLevel(), and increaseLevel()")
    public void testHall_level() {
      Constructor<?> hallConstructor = assertConstructor(PRECONDITION, String.class);
      Method getLevelMethod = assertMethod(PRECONDITION, int.class, "getLevel");
      getLevelMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);

      Object wessex =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a hall using Hall(String) constructor",
                  hallConstructor,
                  "Wessex");

      // getLevel returns 1
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1, returnedValue, ": Incorrect hall's initial level");
        }
      }.run(": Failed to invoke method getLeve()", getLevelMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getLevel returns 2
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(2, returnedValue, ": Incorrect hall's level after increasing to Level 2");
        }
      }.run(": Failed to invoke method getLeve()", getLevelMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getLevel returns 3
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(3, returnedValue, ": Incorrect hall's level after increasing to Level 3");
        }
      }.run(": Failed to invoke method getLeve()", getLevelMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getLevel returns 4
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(4, returnedValue, ": Incorrect hall's level after increasing to Level 4");
        }
      }.run(": Failed to invoke method getLeve()", getLevelMethod, wessex);
    }

    /**
     * Test the <code>getUpgradeCost()</code> method of the <code>facilities.buildings.Hall</code>
     * class. The precondition is to have a <code>Hall</code> (named <code>"Wessex"</code>). The
     * test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the upgrade cost is 200 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 300 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 400 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is -1 (using <code>getUpgradeCost</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Hall's getUpgradeCost()")
    public void testHall_getUpgradeCost() {
      // PRECONDITION
      Constructor<?> hallConstructor = assertConstructor(PRECONDITION, String.class);
      Method getUpgradeCostMethod = assertMethod(PRECONDITION, int.class, "getUpgradeCost");
      getUpgradeCostMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object wessex =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a hall using Hall(String) constructor",
                  hallConstructor,
                  "Wessex");

      // getUpgradeCost = 200
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(200, returnedValue, ": Incorrect hall's upgrade cost from Level 1 to 2");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getUpgradeCost = 300
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(300, returnedValue, ": Incorrect hall's upgrade cost from Level 2 to 3");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getUpgradeCost = 400
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(400, returnedValue, ": Incorrect hall's upgrade cost from Level 3 to 4");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getUpgradeCost = -1 (i.e., cannot upgrade)
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(-1, returnedValue, ": Incorrect hall's upgrade cost at Level 4");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, wessex);
    }

    /**
     * Test the <code>getCapacity()</code> method of the <code>facilities.buildings.Hall</code>
     * class. The precondition is to have a <code>Hall</code> (named <code>"Wessex"</code>). The
     * test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the capacity is 6 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 12 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 24 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 48 (using <code>getCapacity</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Hall's getCapacity()")
    public void testHall_getCapacity() {
      // PRECONDITION
      Constructor<?> hallConstructor = assertConstructor(PRECONDITION, String.class);
      Method getCapacityMethod = assertMethod(PRECONDITION, int.class, "getCapacity");
      getCapacityMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object wessex =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a hall using Hall(String) constructor",
                  hallConstructor,
                  "Wessex");

      // getCapacity = 6
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(6, returnedValue, ": Incorrect hall's capacity at Level 1");
        }
      }.run(": Failed to invoke getCapacity() method", getCapacityMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getCapacity = 12
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(12, returnedValue, ": Incorrect hall's capacity at Level 2");
        }
      }.run(": Failed to invoke getCapacity() method", getCapacityMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getCapacity = 24
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(24, returnedValue, ": Incorrect hall's capacity at Level 3");
        }
      }.run(": Failed to invoke getCapacity() method", getCapacityMethod, wessex);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, wessex);

      // getCapacity = 48
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(48, returnedValue, ": Incorrect hall's capacity at Level 4");
        }
      }.run(": Failed to invoke getCapacity() method", getCapacityMethod, wessex);
    }
  }

  /**
   * Test class for <code>facilities.buildings.Theatre</code>'s signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestTheatre
   */
  @Nested
  @DisplayName("Test facilities.buildings.Theatre's signature")
  public class TestTheatreSignature extends AbstractTestTheatre {

    private Class<?> facilityClass;

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      facilityClass = getClassOrInterface("facilities.Facility");
      assertClass(PRECONDITION, facilityClass);
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
      assertInterface(PRECONDITION, buildingInterface);
    }

    /**
     * Test the class structure, field and method signatures of the <code>
     * facilities.buildings.Theatre</code> class.
     *
     * <ul>
     *   <li>Check <code>Theatre</code> extends <code>Facility</code>.
     *   <li>Check <code>Theatre</code> implements <code>Building</code>.
     *   <li>Check <code>Theatre</code> has a field <code>level</code> of type <code>int</code>.
     *   <li>Check <code>Theatre</code> has a constructor <code>Hall(String)</code>.
     *   <li>Check <code>Theatre</code> has a method <code>increaseLevel()</code>.
     *   <li>Check <code>Theatre</code> has a method <code>int getLevel()</code>.
     *   <li>Check <code>Theatre</code> has a method <code>int getUpgradeCost()</code>.
     *   <li>Check <code>Theatre</code> has a method <code>int getCapacity()</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Theatre's class structure, field and method signatures")
    public void testTheatre_Signature() {
      assertClass("");
      assertImplementOrExtend("", facilityClass);
      assertImplementOrExtend("", buildingInterface);
      assertInheritedField("", "level", int.class);
      assertConstructor("", String.class);
      assertAccessibleMethod("", null, "increaseLevel");
      assertAccessibleMethod("", int.class, "getLevel");
      assertAccessibleMethod("", int.class, "getUpgradeCost");
      assertAccessibleMethod("", int.class, "getCapacity");
    }
  }

  /**
   * Test class for <code>facilities.buildings.Theatre</code>'s specification.
   *
   * @version 1.0
   * @see AbstractTestTheatre
   */
  @Nested
  @DisplayName("Test facilities.buildings.Theatre's specification")
  public class TestTheatreSpecification extends AbstractTestTheatre {

    /**
     * Test the constructor of <code>facilities.buildings.Theatre</code> class. Create a <code>
     * Theatre</code> named <code>"Murray"</code> and check if the name returned from <code>
     * getName()</code> is correct.
     */
    @Test
    @DisplayName("Test facilities.buildings.Theatre's constructor")
    public void testTheatre_Constructor() {
      // PRECONDITION
      Constructor<?> theatreConstructor = assertConstructor(PRECONDITION, String.class);
      Method getNameMethod = assertMethod(PRECONDITION, String.class, "getName");
      getNameMethod.setAccessible(true);

      // Test constructor
      Object murray =
          new TestConstructor()
              .run(
                  ": Failed to create a theatre using Theatre(String) constructor",
                  theatreConstructor,
                  "Murray");

      // getName = "Murray"
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Murray", returnedValue, ": Incorrect theatre's name");
        }
      }.run(": Failed to run getName() method", getNameMethod, murray);
    }

    /**
     * Test the level field, getLevel(), and increaseLevel() methods of the <code>
     * facilities.buildings.Theatre</code> class. The precondition is to have a <code>Theatre</code>
     * (named <code>"Murray"</code>). The test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the initial level is 1 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 2 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 3 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 4 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 5 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 6 (using <code>getLevel</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Theatre's level, getLevel(), and increaseLevel()")
    public void testTheatre_level() {
      Constructor<?> theatreConstructor = assertConstructor(PRECONDITION, String.class);
      Method getLevelMethod = assertMethod(PRECONDITION, int.class, "getLevel");
      getLevelMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object murray =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a theatre using Theatre(String) constructor",
                  theatreConstructor,
                  "Murray");

      // getLevel = 1
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1, returnedValue, ": Incorrect theatre's initial level");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getLevel = 2
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(2, returnedValue, ": Incorrect theatre's level after increasing to Level 2");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getLevel = 3
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(3, returnedValue, ": Incorrect theatre's level after increasing to Level 3");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getLevel = 4
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(4, returnedValue, ": Incorrect theatre's level after increasing to Level 4");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getLevel = 5
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(5, returnedValue, ": Incorrect theatre's level after increasing to Level 5");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getLevel = 6
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(6, returnedValue, ": Incorrect theatre's level after increasing to Level 6");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, murray);
    }

    /**
     * Test the <code>getUpgradeCost()</code> method of the <code>facilities.buildings.Theatre
     * </code> class. The precondition is to have a <code>Theatre</code> (named <code>"Murray"
     * </code>). The test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the upgrade cost is 400 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 600 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 800 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 1000 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 1200 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is -1 (using <code>getUpgradeCost</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Theatre's getUpgradeCost()")
    public void testTheatre_getUpgradeCost() {
      // PRECONDITION
      Constructor<?> theatreConstructor = assertConstructor(PRECONDITION, String.class);
      Method getUpgradeCostMethod = assertMethod(PRECONDITION, int.class, "getUpgradeCost");
      getUpgradeCostMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object murray =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a theatre using Theatre(String) constructor",
                  theatreConstructor,
                  "Murray");

      // getUpgradeCost = 400
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(400, returnedValue, ": Incorrect theatre's upgrade cost from Level 1 to 2");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getUpgradeCost = 600
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(600, returnedValue, ": Incorrect theatre's upgrade cost from Level 2 to 3");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getUpgradeCost = 800
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(800, returnedValue, ": Incorrect theatre's upgrade cost from Level 3 to 4");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getUpgradeCost = 1000
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1000, returnedValue, ": Incorrect theatre's upgrade cost from Level 4 to 5");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getUpgradeCost = 1200
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1200, returnedValue, ": Incorrect theatre's upgrade cost from Level 5 to 6");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getUpgradeCost = -1
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(-1, returnedValue, ": Incorrect theatre's upgrade cost at Level 6");
        }
      }.run(": Failed to run getUpgradeCost() method", getUpgradeCostMethod, murray);
    }

    /**
     * Test the <code>getCapacity()</code> method of the <code>facilities.buildings.Theatre</code>
     * class. The precondition is to have a <code>Theatre</code> (named <code>"Murray"</code>). The
     * test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the capacity is 10 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 20 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 40 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 80 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 160 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 320 (using <code>getCapacity</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Theatre's getCapacity()")
    public void testTheatre_getCapacity() {
      // PRECONDITION
      Constructor<?> theatreConstructor = assertConstructor(PRECONDITION, String.class);
      Method getCapacityMethod = assertMethod(PRECONDITION, int.class, "getCapacity");
      getCapacityMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object murray =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a theatre using Theatre(String) constructor",
                  theatreConstructor,
                  "Murray");

      // getCapacity = 10
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(10, returnedValue, ": Incorrect theatre's capacity at Level 1");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getCapacity = 20
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(20, returnedValue, ": Incorrect theatre's capacity at Level 2");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getCapacity = 40
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(40, returnedValue, ": Incorrect theatre's capacity at Level 3");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getCapacity = 80
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(80, returnedValue, ": Incorrect theatre's capacity at Level 4");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getCapacity = 160
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(160, returnedValue, ": Incorrect theatre's capacity at Level 5");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, murray);

      // getCapacity = 320
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(320, returnedValue, ": Incorrect theatre's capacity at Level 320");
        }
      }.run(": Failed to run getCapacity() method", getCapacityMethod, murray);
    }
  }

  /**
   * Test class for <code>facilities.buildings.Lab</code>'s signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestLab
   */
  @Nested
  @DisplayName("Test facilities.buildings.Lab's signature")
  public class TestLabSignature extends AbstractTestLab {

    private Class<?> facilityClass;

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      facilityClass = getClassOrInterface("facilities.Facility");
      assertClass(PRECONDITION, facilityClass);
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
      assertInterface(PRECONDITION, buildingInterface);
    }

    /**
     * Test the class structure, field and method signatures of the <code>facilities.buildings.Lab
     * </code> class.
     *
     * <ul>
     *   <li>Check <code>Lab</code> extends <code>Facility</code>.
     *   <li>Check <code>Lab</code> implements <code>Building</code>.
     *   <li>Check <code>Lab</code> has a field <code>level</code> of type <code>int</code>.
     *   <li>Check <code>Lab</code> has a constructor <code>Hall(String)</code>.
     *   <li>Check <code>Lab</code> has a method <code>increaseLevel()</code>.
     *   <li>Check <code>Lab</code> has a method <code>int getLevel()</code>.
     *   <li>Check <code>Lab</code> has a method <code>int getUpgradeCost()</code>.
     *   <li>Check <code>Lab</code> has a method <code>int getCapacity()</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Lab's class structure, field and method signatures")
    public void testLab_Signature() {
      assertClass("");
      assertImplementOrExtend("", facilityClass);
      assertImplementOrExtend("", buildingInterface);
      assertInheritedField("", "level", int.class);
      assertConstructor("", String.class);
      assertAccessibleMethod("", null, "increaseLevel");
      assertAccessibleMethod("", int.class, "getLevel");
      assertAccessibleMethod("", int.class, "getUpgradeCost");
      assertAccessibleMethod("", int.class, "getCapacity");
    }
  }

  /**
   * Test class for <code>facilities.buildings.Lab</code>'s specification.
   *
   * @version 1.0
   * @see AbstractTestLab
   */
  @Nested
  @DisplayName("Test facilities.buildings.Lab's specification")
  public class TestLabSpecification extends AbstractTestLab {

    /**
     * Test the constructor of the <code>facilities.buildings.Lab</code> class. Create a <code>Lab
     * </code> named <code>"Zepler"</code> and check if the name returned from <code>getName()
     * </code> is correct.
     */
    @Test
    @DisplayName("Test facilities.buildings.Lab's constructor")
    public void testLab_Constructor() {
      // PRECONDITION
      Constructor<?> labConstructor = assertConstructor(PRECONDITION, String.class);
      Method getNameMethod = assertMethod(PRECONDITION, String.class, "getName");
      getNameMethod.setAccessible(true);

      // Test the constructor
      Object zepler =
          new TestConstructor()
              .run(
                  ": Failed to create a lab using Lab(String) constructor",
                  labConstructor,
                  "Zepler");

      // getName = "Zepler"
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Zepler", returnedValue, ": Incorrect lab's name");
        }
      }.run(": Failed to execute Lab.getName() method", getNameMethod, zepler);
    }

    /**
     * Test the level field, getLevel(), and increaseLevel() methods of the <code>
     * facilities.buildings.Lab</code> class. The precondition is to have a <code>Lab</code> (named
     * <code>"Zepler"</code>). The test sequence is as follows:
     *
     * <ul>
     *   <li>Check if the initial level is 1 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 2 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 3 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 4 (using <code>getLevel</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the initial level is 5 (using <code>getLevel</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Lab's level, getLevel(), and increaseLevel()")
    public void testLab_level() {
      Constructor<?> labConstructor = assertConstructor(PRECONDITION, String.class);
      Method getLevelMethod = assertMethod(PRECONDITION, int.class, "getLevel");
      getLevelMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object zepler =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a lab using Lab(String) constructor",
                  labConstructor,
                  "Zepler");

      // getLevel = 1
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1, returnedValue, ": Incorrect lab's initial level");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getLevel = 2
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(2, returnedValue, ": Incorrect lab's level after increasing to Level 2");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getLevel = 3
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(3, returnedValue, ": Incorrect lab's level after increasing to Level 3");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getLevel = 4
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(4, returnedValue, ": Incorrect lab's level after increasing to Level 4");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getLevel = 5
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(5, returnedValue, ": Incorrect lab's level after increasing to Level 5");
        }
      }.run(": Failed to run getLevel() method", getLevelMethod, zepler);
    }

    /**
     * Test the <code>getUpgradeCost()</code> method of the <code>facilities.buildings.Lab</code>
     * class. The precondition is to have a <code>Lab</code> (named <code>"Zepler"</code>). The test
     * sequence is as follows:
     *
     * <ul>
     *   <li>Check if the upgrade cost is 600 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 900 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 1200 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is 1500 (using <code>getUpgradeCost</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the upgrade cost is -1 (using <code>getUpgradeCost</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Lab's getUpgradeCost()")
    public void testLab_getUpgradeCost() {
      // PRECONDITION
      Constructor<?> labConstructor = assertConstructor(PRECONDITION, String.class);
      Method getUpgradeCostMethod = assertMethod(PRECONDITION, int.class, "getUpgradeCost");
      getUpgradeCostMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object zepler =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a lab using Lab(String) constructor",
                  labConstructor,
                  "Zepler");

      // getUpgradeCost = 600
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(600, returnedValue, ": Incorrect lab's upgrade cost from Level 1 to 2");
        }
      }.run(": Failed to execute getUpgradeCost() method", getUpgradeCostMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getUpgradeCost = 900
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(900, returnedValue, ": Incorrect lab's upgrade cost from Level 2 to 3");
        }
      }.run(": Failed to execute getUpgradeCost() method", getUpgradeCostMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getUpgradeCost = 1200
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1200, returnedValue, ": Incorrect lab's upgrade cost from Level 3 to 4");
        }
      }.run(": Failed to execute getUpgradeCost() method", getUpgradeCostMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getUpgradeCost = 1500
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1500, returnedValue, ": Incorrect lab's upgrade cost from Level 4 to 5");
        }
      }.run(": Failed to execute getUpgradeCost() method", getUpgradeCostMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getUpgradeCost = -1
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(-1, returnedValue, ": Incorrect lab's upgrade cost at Level 5");
        }
      }.run(": Failed to execute getUpgradeCost() method", getUpgradeCostMethod, zepler);
    }

    /**
     * Test the <code>getCapacity()</code> method of the <code>facilities.buildings.Lab</code>
     * class. The precondition is to have a <code>Lab</code> (named <code>"Zepler"</code>). The test
     * sequence is as follows:
     *
     * <ul>
     *   <li>Check if the capacity is 5 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 10 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 20 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 40 (using <code>getCapacity</code>.
     *   <li>Increase the level (using <code>increaseLevel</code>.
     *   <li>Check if the capacity is 80 (using <code>getCapacity</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test facilities.buildings.Lab's getCapacity()")
    public void testLab_getCapacity() {
      Constructor<?> labConstructor = assertConstructor(PRECONDITION, String.class);
      Method getCapacityMethod = assertMethod(PRECONDITION, int.class, "getCapacity");
      getCapacityMethod.setAccessible(true);
      Method increaseLevelMethod = assertMethod(PRECONDITION, null, "increaseLevel");
      increaseLevelMethod.setAccessible(true);
      Object zepler =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a lab using Lab(String) constructor",
                  labConstructor,
                  "Zepler");

      // getCapacity = 5
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(5, returnedValue, ": Incorrect lab's capacity at Level 1");
        }
      }.run(": Failed to execute getCapacity() method", getCapacityMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getCapacity = 10
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(10, returnedValue, ": Incorrect lab's capacity at Level 2");
        }
      }.run(": Failed to execute getCapacity() method", getCapacityMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getCapacity = 20
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(20, returnedValue, ": Incorrect lab's capacity at Level 3");
        }
      }.run(": Failed to execute getCapacity() method", getCapacityMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getCapacity = 40
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(40, returnedValue, ": Incorrect lab's capacity at Level 4");
        }
      }.run(": Failed to execute getCapacity() method", getCapacityMethod, zepler);

      // increaseLevel
      new TestMethod().run("Failed to invoke method increaseLevel()", increaseLevelMethod, zepler);

      // getCapacity = 80
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(80, returnedValue, ": Incorrect lab's capacity at Level 5");
        }
      }.run(": Failed to execute getCapacity() method", getCapacityMethod, zepler);
    }
  }
}
