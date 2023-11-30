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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test Part 2 of the Coursework.
 *
 * <ul>
 *   <li>{@link TestUniversitySignature}: Test the signature of the
 *       university.University class.
 *   <li>{@link TestUniversitySpecification}: Test the specification of the
 *       university.University class.
 * </ul>
 *
 * @author htson - v1.0 - Initial API and implementation
 * @version 1.0
 */
@DisplayName("Test Part 3 for ECS Sim Coursework")
public class TestEcsSimPart3 extends TestEcsSimCoursework {

  /**
   * Test class for university.University's signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestUniversity
   */
  @Nested
  @DisplayName("Test university.University's signature")
  public class TestUniversitySignature extends AbstractTestUniversity {

    private Class<?> estateClass;

    private Class<?> facilityClass;

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      estateClass = getClassOrInterface("university.Estate");
      facilityClass = getClassOrInterface("facilities.Facility");
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
    }

    /**
     * Test the signature of the university.University class.
     *
     * <ul>
     *   <li>Check <code>University</code> is a class.
     *   <li>Check <code>University</code> has a field <code>budget</code> of type <code>float
     *       </code>.
     *   <li>Check <code>University</code> has a field <code>estate</code> of type <code>Estate
     *       </code>.
     *   <li>Check <code>University</code> has a field <code>reputation</code> of type <code>int
     *       </code>.
     *   <li>Check <code>University</code> has a constructor with arguments <code>University(int)
     *       </code>.
     *   <li>Check <code>University</code> has a method <code>build</code> with argument <code>
     *       (String, String)</code> and return a <code>Facility</code>.
     *   <li>Check <code>University</code> has a method <code>upgrade(Building)</code> and can
     *       throws <code>Exception</code>.
     *   <li>Check <code>University</code> has a method <code>getBudget</code> with no arguments and
     *       return an <code>float</code>.
     *   <li>Check <code>University</code> has a method <code>getReputation</code> with no arguments
     *       and return an <code>int</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's signature")
    public void testHall_Signature() {
      assertClass("");
      assertInheritedField("", "budget", float.class);
      assertInheritedField("", "estate", estateClass);
      assertInheritedField("", "reputation", int.class);
      assertConstructor("", int.class);
      assertAccessibleMethod("", facilityClass, "build", String.class, String.class);
      assertAccessibleMethod("", null, "upgrade", buildingInterface);
      assertAccessibleMethod("", float.class, "getBudget");
      assertAccessibleMethod("", int.class, "getReputation");
    }
  }

  /**
   * Test class for university.University's specification.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestUniversity
   */
  @Nested
  @DisplayName("Test university.University's specification")
  public class TestUniversitySpecification extends AbstractTestUniversity {

    private Class<?> estateClass;

    private Class<?> facilityClass;

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      estateClass = getClassOrInterface("university.Estate");
      facilityClass = getClassOrInterface("facilities.Facility");
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
    }

    /**
     * Test university.University's constructor. Create a <code>University</code> with the
     * constructor <code>University(int)</code>.
     *
     * <ul>
     *   <li>Check if the initial budget is correct (i.e., as set by the constructor's parameter)
     *   <li>Check if the reputation is correct (i.e., 0)
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's constructor")
    public void testUniversity_Constructor() {
      // PRECONDITION
      Constructor<?> universityConstructor = assertConstructor(PRECONDITION, int.class);
      Field budgetField = assertInheritedField(PRECONDITION, "budget", float.class);
      Field reputationField = assertInheritedField(PRECONDITION, "reputation", int.class);

      // Test the constructor
      Object soton =
          new TestConstructor()
              .run(
                  ": Failed to create a university using University(int) constructor",
                  universityConstructor,
                  1000);

      // Test the <code>budget</code> field (must be 1000)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(1000f, value, "Incorrect initial budget");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // Test the <code>reputation</code> field (must be 0)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(0, value, "Incorrect initial budget");
        }
      }.run(": Failed to get the budget field", reputationField, soton);
    }

    /**
     * Test university.University's build(String, String) method. The precondition is to have a
     * <code>University</code> (using constructor <code>University(1000)</code>). The test sequence
     * is as follows.
     *
     * <ul>
     *   <li>Build a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       build</code>).
     *   <li>Check if the return value is indeed a <code>Hall</code> with the name <code>"Wessex"
     *       </code>.
     *   <li>Check the <code>budget</code> field is 900.
     *   <li>Add a facility for a <code>"Lab"</code> named <code>"Zepler"</code> (using <code>
     *       build</code>).
     *   <li>Check if the return value is indeed a <code>Lab</code> with the name <code>"Zepler"
     *       </code>.
     *   <li>Check the <code>budget</code> field is 600.
     *   <li>Add a facility for a <code>"Theatre"</code> named <code>"Murray"</code> (using <code>
     *       build</code>).
     *   <li>Check if the return value is indeed a <code>Theatre</code> with the name <code>"Murray"
     *       </code>.
     *   <li>Check the <code>budget</code> field is 400.
     *   <li>Add a facility for a <code>"NoFacility"</code> named <code>"NoName"</code> (using
     *       <code>build</code>).
     *   <li>Check if the return value is <code>null</code>.
     *   <li>Check the <code>budget</code> field is 400.
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's build(String, String) method")
    public void testUniversity_build() {
      // PRECONDITION
      Constructor<?> universityConstructor = assertConstructor(PRECONDITION, int.class);
      Field budgetField = assertInheritedField(PRECONDITION, "budget", float.class);
      Method buildMethod =
          assertAccessibleMethod(PRECONDITION, facilityClass, "build", String.class, String.class);
      Method getNameMethod = assertMethod(facilityClass, PRECONDITION, String.class, "getName");
      Class<?> hallClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Hall");
      Class<?> labClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Lab");
      Class<?> theatreClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Theatre");
      Object soton =
          new TestConstructor()
              .run(
                  PRECONDITION
                      + ": Failed to create a university using University(int) constructor",
                  universityConstructor,
                  1000);

      // Test "build" method to create Wessex Hall
      Object wessex =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(hallClass),
                  "The constructed facility must be a hall");
            }
          }.run(
              ": Failed to invoke build(String, String) method",
              buildMethod,
              soton,
              "Hall",
              "Wessex");

      // Test name of the Wessex Hall.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Wessex", returnedValue, "Incorrect name for the constructed hall");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, wessex);

      // Test the budget field of the university. (900)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(900f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // Test "build" method to create Zepler Lab
      Object zepler =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(labClass),
                  "The constructed facility must be a lab");
            }
          }.run(
              "Failed to invoke build(String, String) method", buildMethod, soton, "Lab", "Zepler");

      // Test name of the Zepler Lab.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Zepler", returnedValue, "Incorrect name for the constructed lab");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, zepler);

      // Test the budget field of the university (600)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(600f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // Test "build" method to create Murray Theatre
      Object murray =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(theatreClass),
                  "The constructed facility must be a theatre");
            }
          }.run(
              "Failed to invoke build(String, String) method",
              buildMethod,
              soton,
              "Theatre",
              "Murray");

      // Test name of the Murray Theatre.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Murray", returnedValue, "Incorrect name for the constructed theatre");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, murray);

      // Test the budget field of the university (400)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(400f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // Test "build" method for an invalid facility
      new TestMethod()
          .run(
              "Failed to invoke build(String, String) method",
              buildMethod,
              soton,
              "NoFacility",
              "NoName");

      // Test the budget field of the university (400)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(400f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);
    }

    /**
     * Test university.University's upgrade(Building) method. The precondition is to have a <code>
     * University</code> (using constructor <code>University(1200)</code>), and a separate <code>
     * Hall</code> (Glen Eyre) created directly using <code>Hall("Glen Eyre")</code>. The test
     * sequence is as follows.
     *
     * <ul>
     *   <li>Build a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       build</code>).
     *   <li>Check the Hall is now at Level 1
     *   <li>Upgrade the Hall.
     *   <li>Check the Hall is now at Level 2
     *   <li>Check the <code>budget</code> field is 900.
     *   <li>Upgrade the Hall.
     *   <li>Check the Hall is now at Level 3
     *   <li>Check the <code>budget</code> field is 600.
     *   <li>Upgrade the Hall.
     *   <li>Check the Hall is now at Level 4
     *   <li>Check the <code>budget</code> field is 200.
     *   <li>Upgrade GlenEyre and check if an exception is thrown (Glen Eyre is not a part of the
     *       University).
     *   <li>Upgrade the Hall abd check if an exception is thrown (Wessex at Level 4 is the maximum
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's upgrade(Building) method")
    public void testUniversity_upgrade() {
      // PRECONDITION
      Constructor<?> universityConstructor = assertConstructor(PRECONDITION, int.class);
      Field budgetField = assertInheritedField(PRECONDITION, "budget", float.class);
      Method buildMethod =
          assertAccessibleMethod(PRECONDITION, facilityClass, "build", String.class, String.class);
      Method upgradeMethod =
          assertAccessibleMethod(PRECONDITION, null, "upgrade", buildingInterface);
      Method getLevelMethod = assertMethod(buildingInterface, PRECONDITION, int.class, "getLevel");
      Object soton =
          new TestConstructor()
              .run(
                  PRECONDITION
                      + ": Failed to create a university using University(int) constructor",
                  universityConstructor,
                  1000);
      Class<?> hallClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Hall");
      Constructor<?> hallConstructor = assertConstructor(hallClass, PRECONDITION, String.class);
      Object gleneyre =
          new TestConstructor()
              .run(
                  ": Failed to create a hall using Hall(String) constructor",
                  hallConstructor,
                  "Glen Eyre");

      // Test "build" method to create Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to invoke build(String, String) method",
                  buildMethod,
                  soton,
                  "Hall",
                  "Wessex");

      // Test level of the Wessex Hall. (Level 1)
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1, returnedValue, "Incorrect level for the constructed hall");
        }
      }.run(": Failed to invoke getLevel() method", getLevelMethod, wessex);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // Test level of the Wessex Hall. (Level 2)
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(2, returnedValue, "Incorrect level for the upgraded hall");
        }
      }.run(": Failed to invoke getLevel() method", getLevelMethod, wessex);

      // Test the budget field of the university (700)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(700f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // Test level of the Wessex Hall. (Level 3)
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(3, returnedValue, "Incorrect level for the upgraded hall");
        }
      }.run(": Failed to invoke getLevel() method", getLevelMethod, wessex);

      // Test the budget field of the university (400)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(400f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // Test level of the Wessex Hall. (Level 4)
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(4, returnedValue, "Incorrect level for the upgraded hall");
        }
      }.run(": Failed to invoke getLevel() method", getLevelMethod, wessex);

      // Test the budget field of the university (0)
      new TestField() {
        @Override
        public void assertions() {
          assertEquals(0f, value, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", budgetField, soton);

      // Test exception when upgrading non-existing building
      new TestMethodException()
          .run(
              ": upgrade(Building) method should throws some exception",
              upgradeMethod,
              soton,
              gleneyre);

      // Test exception when upgrading max-level building
      new TestMethodException()
          .run(
              ": upgrade(Building) method should throws some exception",
              upgradeMethod,
              soton,
              wessex);
    }

    /**
     * Test university.University's getBudget(String, String) method. The precondition is to have a
     * <code>University</code> (using constructor <code>University(1000)</code>). The test sequence
     * is as follows.
     *
     * <ul>
     *   <li>Build a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       build</code>).
     *   <li>Check the <code>getBudget</code> returns 900.
     *   <li>Add a facility for a <code>"Lab"</code> named <code>"Zepler"</code> (using <code>
     *       build</code>).
     *   <li>Check the <code>getBudget</code> returns 600.
     *   <li>Add a facility for a <code>"Theatre"</code> named <code>"Murray"</code> (using <code>
     *       build</code>).
     *   <li>Check the <code>getBudget</code> returns 400.
     *   <li>Add a facility for a <code>"NoFacility"</code> named <code>"NoName"</code> (using
     *       <code>build</code>).
     *   <li>Check the <code>getBudget()</code> returns 400.
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's getBudget() method")
    public void testUniversity_getBudget() {
      // PRECONDITION
      Constructor<?> universityConstructor = assertConstructor(PRECONDITION, int.class);
      Method buildMethod =
          assertAccessibleMethod(PRECONDITION, facilityClass, "build", String.class, String.class);
      Method getBudgetMethod = assertMethod(PRECONDITION, float.class, "getBudget");
      Object soton =
          new TestConstructor()
              .run(
                  PRECONDITION
                      + ": Failed to create a university using University(int) constructor",
                  universityConstructor,
                  1000);

      // Test "build" method to create Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to invoke build(String, String) method",
                  buildMethod,
                  soton,
                  "Hall",
                  "Wessex");

      // Test the getBudget returns 900
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(900f, returnedValue, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", getBudgetMethod, soton);

      // Test "build" method to create Zepler Lab
      Object zepler =
          new TestMethod()
              .run(
                  "Failed to invoke build(String, String) method",
                  buildMethod,
                  soton,
                  "Lab",
                  "Zepler");

      // Test the getBudget returns 600
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(600f, returnedValue, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", getBudgetMethod, soton);

      // Test "build" method to create Murray Theatre
      Object murray =
          new TestMethod()
              .run(
                  "Failed to invoke build(String, String) method",
                  buildMethod,
                  soton,
                  "Theatre",
                  "Murray");

      // Test the getBudget returns 400
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(400f, returnedValue, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", getBudgetMethod, soton);

      // Test "build" method for an invalid facility
      new TestMethod()
          .run(
              "Failed to invoke build(String, String) method",
              buildMethod,
              soton,
              "NoFacility",
              "NoName");

      // Test the getBudget returns 400
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(400f, returnedValue, "Incorrect remaining budget for the University");
        }
      }.run(": Failed to get the budget field", getBudgetMethod, soton);
    }

    /**
     * Test university.University's getReputation() method. The precondition is to have a <code>
     * University</code> (using constructor <code>University(1000)</code>). The test sequence is as
     * follows.
     *
     * <ul>
     *   <li>Build a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       build</code>).
     *   <li>Check the reputation of the University is 100
     *   <li>Upgrade the Hall.
     *   <li>Check the reputation of the University is 150
     *   <li>Upgrade the Hall.
     *   <li>Check the reputation of the University is 200
     *   <li>Upgrade the Hall.
     *   <li>Check the reputation of the University is 250
     * </ul>
     */
    @Test
    @DisplayName("Test university.University's getReputation() method")
    public void testUniversity_getReputation() {
      // PRECONDITION
      Constructor<?> universityConstructor = assertConstructor(PRECONDITION, int.class);
      Field budgetField = assertInheritedField(PRECONDITION, "budget", float.class);
      Method buildMethod =
          assertAccessibleMethod(PRECONDITION, facilityClass, "build", String.class, String.class);
      Method upgradeMethod =
          assertAccessibleMethod(PRECONDITION, null, "upgrade", buildingInterface);
      Method getReputationMethod = assertMethod(PRECONDITION, int.class, "getReputation");
      Object soton =
          new TestConstructor()
              .run(
                  PRECONDITION
                      + ": Failed to create a university using University(int) constructor",
                  universityConstructor,
                  1000);

      // Test "build" method to create Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to invoke build(String, String) method",
                  buildMethod,
                  soton,
                  "Hall",
                  "Wessex");

      // getReputation = 100
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(100, returnedValue, "Incorrect reputation for the university");
        }
      }.run(": Failed to invoke getLevel() method", getReputationMethod, soton);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // getReputation = 150
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(150, returnedValue, "Incorrect reputation for the university");
        }
      }.run(": Failed to invoke getLevel() method", getReputationMethod, soton);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // getReputation = 200
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(200, returnedValue, "Incorrect reputation for the university");
        }
      }.run(": Failed to invoke getLevel() method", getReputationMethod, soton);

      // "upgrade" Wessex
      new TestMethod()
          .run("Failed to invoke build(String, String) method", upgradeMethod, soton, wessex);

      // getReputation = 250
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(250, returnedValue, "Incorrect reputation for the university");
        }
      }.run(": Failed to invoke getLevel() method", getReputationMethod, soton);
    }
  }
}
