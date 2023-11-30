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

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test Part 2 of the Coursework.
 *
 * <ul>
 *   <li>{@link TestEstateSignature}: Test the signature of the university.Estate class.
 *   <li>{@link TestEstateSpecification}: Test the specification of the university.Estate class.
 * </ul>
 *
 * @author htson - v1.0 - Initial API and implementation
 * @version 1.0
 */
@DisplayName("Test Part 2 for ECS Sim Coursework")
public class TestEcsSimPart2 extends TestEcsSimCoursework {

  /**
   * Test class for university.Estate's signature.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestEstate
   */
  @Nested
  @DisplayName("Test university.Estate's signature")
  public class TestEstateSignature extends AbstractTestEstate {

    private Class<?> facilityClass;

    private Class<?> facilityArrayClass;

    private final Class<?> facilityListClass =
        ArrayList.class; // At the moment, strip the genericity

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      facilityClass = getClassOrInterface("facilities.Facility");
      assertClass(PRECONDITION, facilityClass);
      facilityArrayClass = Array.newInstance(facilityClass, 0).getClass();
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
      assertInterface(PRECONDITION, buildingInterface);
    }

    /**
     * Test the signature of the university.Estate class.
     *
     * <ul>
     *   <li>Check <code>Estate</code> is a class.
     *   <li>Check <code>Estate</code> has a field <code>facilities</code> of type <code>ArrayList
     *       </code>.
     *   <li>Check <code>Estate</code> has a constructor with no arguments.
     *   <li>Check <code>Estate</code> has a method <code>getFacilities</code> with no arguments and
     *       return an array of <code>Facility</code>.
     *   <li>Check <code>Estate</code> has a method <code>addFacility</code> with argument <code>
     *       (String, String)</code> and return a <code>Facility</code>.
     *   <li>Check <code>Estate</code> has a method <code>getMaintenanceCost</code> with no
     *       arguments and return a <code>float</code>.
     *   <li>Check <code>Estate</code> has a method <code>getNumberOfStudents</code> with no
     *       arguments and return an <code>int</code>.
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's signature")
    public void testHall_Signature() {
      assertClass("");
      assertInheritedField("", "facilities", facilityListClass);
      assertConstructor("");
      assertAccessibleMethod("", facilityArrayClass, "getFacilities");
      assertAccessibleMethod("", facilityClass, "addFacility", String.class, String.class);
      assertAccessibleMethod("", float.class, "getMaintenanceCost");
      assertAccessibleMethod("", int.class, "getNumberOfStudents");
    }
  }

  /**
   * Test class for university.Estate's specification.
   *
   * @version 1.0
   * @see AbstractTestEstate
   */
  @Nested
  @DisplayName("Test university.Estate's specification")
  public class TestEstateSpecification extends AbstractTestEstate {

    private Class<?> facilityClass;

    private Class<?> facilityArrayClass;

    private final Class<?> facilityListClass =
        ArrayList.class; // At the moment, strip the genericity

    private Class<?> buildingInterface;

    @BeforeEach
    public void setup() {
      super.setup();
      facilityClass = getClassOrInterface("facilities.Facility");
      assertClass(PRECONDITION, facilityClass);
      facilityArrayClass = Array.newInstance(facilityClass, 0).getClass();
      buildingInterface = getClassOrInterface("facilities.buildings.Building");
      assertInterface(PRECONDITION, buildingInterface);
    }

    /**
     * Test university.Estate's constructor. Create an <code>Estate</code> with the constructor
     * <code>Estate()</code>.
     *
     * <ul>
     *   <li>Check if there are no facilities in the estate (the <code>facilities</code> is empty).
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's constructor")
    public void testEstate_Constructor() {
      // PRECONDITION
      Constructor<?> estateConstructor = assertConstructor(PRECONDITION);
      Field facilitiesField = assertInheritedField(PRECONDITION, "facilities", facilityListClass);

      // Test the constructor
      Object sotonEstate =
          new TestConstructor()
              .run(": Failed to create an estate using Estate() constructor", estateConstructor);

      // Test the <code>facilities</code> field
      new TestField() {
        @Override
        public void assertions() {
          List<?> facilities = (List<?>) value;
          assertNotNull(facilities, "Facilities should be none null");
          assertTrue(facilities.isEmpty(), "There should be no facilities");
        }
      }.run(": Failed to get the facilities field", facilitiesField, sotonEstate);
    }

    /**
     * Test university.Estate's addFacility(String, String) method. The precondition is to have an
     * <code>Estate</code> (using constructor <code>Estate()</code>). The test sequence is as
     * follows.
     *
     * <ul>
     *   <li>Add a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the return value is indeed a <code>Hall</code> with the name <code>"Wessex"
     *       </code>.
     *   <li>Check that <code>facilities</code> is a list containing exactly 1 element which is the
     *       newly constructed hall.
     *   <li>Add a facility for a <code>"Lab"</code> named <code>"Zepler"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the return value is indeed a <code>Lab</code> with the name <code>"Zepler"
     *       </code>.
     *   <li>Check that <code>facilities</code> is a list containing exactly 2 elements which are
     *       the previously constructed hall and the newly constructed lab.
     *   <li>Add a facility for a <code>"Theatre"</code> named <code>"Murray"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the return value is indeed a <code>Theatre</code> with the name <code>"Murray"
     *       </code>.
     *   <li>Check that <code>facilities</code> is a list containing exactly 3 elements which are
     *       the previously constructed hall, lab, and the newly constructed theatre.
     *   <li>Add a facility for a <code>"NoFacility"</code> named <code>"NoName"</code> (using
     *       <code>addFacility</code>).
     *   <li>Check if the return value is <code>null</code>.
     *   <li>Check that <code>facilities</code> is a list containing exactly 3 elements which are
     *       the previously constructed hall, lab, and theatre.
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's addFacility(String, String) method")
    public void testEstate_addFacility() {
      // PRECONDITION
      Constructor<?> estateConstructor = assertConstructor(PRECONDITION);
      Field facilitiesField = assertInheritedField(PRECONDITION, "facilities", facilityListClass);
      Method addFacilityMethod =
          assertAccessibleMethod(
              PRECONDITION, facilityClass, "addFacility", String.class, String.class);
      Method getNameMethod = assertMethod(facilityClass, PRECONDITION, String.class, "getName");
      Class<?> hallClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Hall");
      Class<?> labClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Lab");
      Class<?> theatreClass = assertClassOrInterface(PRECONDITION, "facilities.buildings.Theatre");
      Object sotonEstate =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create an estate using Estate() constructor",
                  estateConstructor);

      // Test "addFacility" method to create Wessex Hall
      Object wessex =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(hallClass),
                  "The constructed facility must be a hall");
            }
          }.run(
              ": Failed to invoke addFacility(String, String) method",
              addFacilityMethod,
              sotonEstate,
              "Hall",
              "Wessex");

      // Test name of the Wessex Hall.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Wessex", returnedValue, "Incorrect name for the constructed hall");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, wessex);

      // Test the facilities field of the estate.
      new TestField() {
        @Override
        public void assertions() {
          List<?> facilities = (List<?>) value;
          assertNotNull(facilities, "Facilities should be none null");
          assertEquals(1, facilities.size(), "There should be 1 facility");
          assertEquals(wessex, facilities.get(0), "Wessex is the first facility");
        }
      }.run(": Failed to get the facilities field", facilitiesField, sotonEstate);

      // Test "addFacility" method to create Zepler Lab
      Object zepler =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(labClass),
                  "The constructed facility must be a lab");
            }
          }.run(
              "Failed to invoke addFacility(String, String) method",
              addFacilityMethod,
              sotonEstate,
              "Lab",
              "Zepler");

      // Test name of the Zepler Lab.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Zepler", returnedValue, "Incorrect name for the constructed lab");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, zepler);

      // Test the facilities field of the estate.
      new TestField() {
        @Override
        public void assertions() {
          List<?> facilities = (List<?>) value;
          assertNotNull(facilities, "Facilities should be none null");
          assertEquals(2, facilities.size(), "There should be 1 facility");
          assertEquals(wessex, facilities.get(0), "Wessex is the first facility");
          assertEquals(zepler, facilities.get(1), "Zepler is the second facility");
        }
      }.run(": Failed to get the facilities field", facilitiesField, sotonEstate);

      // Test "addFacility" method to create Murray Theatre
      Object murray =
          new TestMethod() {
            @Override
            public void assertions() {
              assertTrue(
                  returnedValue.getClass().isAssignableFrom(theatreClass),
                  "The constructed facility must be a theatre");
            }
          }.run(
              "Failed to invoke addFacility(String, String) method",
              addFacilityMethod,
              sotonEstate,
              "Theatre",
              "Murray");

      // Test name of the Murray Theatre.
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals("Murray", returnedValue, "Incorrect name for the constructed theatre");
        }
      }.run(": Failed to invoke getName() method", getNameMethod, murray);

      // Test the facilities field of the estate.
      new TestField() {
        @Override
        public void assertions() {
          List<?> facilities = (List<?>) value;
          assertNotNull(facilities, "Facilities should be none null");
          assertEquals(3, facilities.size(), "There should be 1 facility");
          assertEquals(wessex, facilities.get(0), "Wessex is the first facility");
          assertEquals(zepler, facilities.get(1), "Zepler is the second facility");
          assertEquals(murray, facilities.get(2), "Murray is the third facility");
        }
      }.run(": Failed to get the facilities field", facilitiesField, sotonEstate);

      // Test "addFacility" method for an invalid facility
      new TestMethod()
          .run(
              "Failed to invoke addFacility(String, String) method",
              addFacilityMethod,
              sotonEstate,
              "NoFacility",
              "NoName");

      // Test the facilities field of the estate.
      new TestField() {
        @Override
        public void assertions() {
          List<?> facilities = (List<?>) value;
          assertNotNull(facilities, "Facilities should be none null");
          assertEquals(3, facilities.size(), "There should be 1 facility");
          assertEquals(wessex, facilities.get(0), "Wessex is the first facility");
          assertEquals(zepler, facilities.get(1), "Zepler is the second facility");
          assertEquals(murray, facilities.get(2), "Murray is the third facility");
        }
      }.run(": Failed to get the facilities field", facilitiesField, sotonEstate);
    }

    /**
     * Test university.Estate's getFacilities() method. The precondition is to have an <code>Estate
     * </code>. The test sequence is as follows.
     *
     * <ul>
     *   <li>Add a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check that <code>getFacilities</code> returns an array containing exactly 1 element
     *       which is the newly constructed hall.
     *   <li>Add a facility for a <code>"Lab"</code> named <code>"Zepler"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check that <code>getFacilities</code> returns an array containing exactly 2 elements
     *       which are the previously constructed hall and the newly constructed lab.
     *   <li>Add a facility for a <code>"Theatre"</code> named <code>"Murray"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check that <code>getFacilities</code> returns an array containing exactly 3 elements
     *       which are the previously constructed hall, lab, and the newly constructed theatre.
     *   <li>Add a facility for a <code>"NoFacility"</code> named <code>"NoName"</code> (using
     *       <code>addFacility</code>).
     *   <li>Check that <code>getFacilities</code> returns an array containing exactly 3 elements
     *       which are the previously constructed hall, lab, and theatre.
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's getFacilities() method")
    public void testEstate_getFacilities() {
      // PRECONDITION
      Constructor<?> estateConstructor = assertConstructor(PRECONDITION);
      Method addFacilityMethod =
          assertAccessibleMethod(
              PRECONDITION, facilityClass, "addFacility", String.class, String.class);
      Method getFacilityMethod =
          assertAccessibleMethod(PRECONDITION, facilityArrayClass, "getFacilities");
      Object sotonEstate =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create a estate using Estate() constructor",
                  estateConstructor);

      // addFacility Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Hall",
                  "Wessex");

      // getFacility return {wessex}
      new TestMethod() {
        @Override
        public void assertions() {
          assertNotNull(returnedValue, "Facilities should be none null");
          assertArrayEquals(
              new Object[] {wessex}, (Object[]) returnedValue, "Wessex is the first facility");
        }
      }.run(": Failed to run getFacility() method", getFacilityMethod, sotonEstate);

      // addFacility Zepler Lab
      Object zepler =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Lab",
                  "Zepler");

      // getFacility return {wessex, zepler}
      new TestMethod() {
        @Override
        public void assertions() {
          assertNotNull(returnedValue, "Facilities should be none null");
          assertArrayEquals(
              new Object[] {wessex, zepler},
              (Object[]) returnedValue,
              "Wessex is the first facility, Zepler is the second facility");
        }
      }.run(": Failed to run getFacility() method", getFacilityMethod, sotonEstate);

      // addFacility Murray Theatre
      Object murray =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Theatre",
                  "Murray");

      // getFacility return {wessex, zepler, murray}
      new TestMethod() {
        @Override
        public void assertions() {
          assertNotNull(returnedValue, "Facilities should be none null");
          assertArrayEquals(
              new Object[] {wessex, zepler, murray},
              (Object[]) returnedValue,
              "Wessex is the first facility, Zepler is the second facility, Murray is the third facility");
        }
      }.run(": Failed to run getFacility() method", getFacilityMethod, sotonEstate);

      // addFacility NoName NoFacility (should fail)
      Object nullObject =
          new TestMethod() {
            @Override
            public void assertions() {
              assertNull(returnedValue, "No facility should be created for incorrect type");
            }
          }.run(
              ": Failed to run addFacility(String, String) method",
              addFacilityMethod,
              sotonEstate,
              "NoFacility",
              "NoName");

      // getFacility return {wessex, zepler, murray}
      new TestMethod() {
        @Override
        public void assertions() {
          assertNotNull(returnedValue, "Facilities should be none null");
          assertArrayEquals(
              new Object[] {wessex, zepler, murray},
              (Object[]) returnedValue,
              "Wessex is the first facility, Zepler is the second facility, Murray is the third facility");
        }
      }.run(": Failed to run getFacility() method", getFacilityMethod, sotonEstate);
    }

    /**
     * Test university.Estate's <code>getNumberOfStudents</code> method. The precondition is to have
     * an <code>Estate</code>. The test sequence is as follows.
     *
     * <ul>
     *   <li>Check if the number of students (using <code>getNumberOfStudents</code>) is 0.
     *   <li>Add a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the number of students (using <code>getNumberOfStudents</code>) is 0.
     *   <li>Add a facility for a <code>"Lab"</code> named <code>"Zepler"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the number of students (using <code>getNumberOfStudents</code>) is 0.
     *   <li>Add a facility for a <code>"Theatre"</code> named <code>"Murray"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check if the number of students (using <code>getNumberOfStudents</code>) is 5.
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's getNumberOfStudents() method")
    public void testEstate_getNumberOfStudents() {
      Constructor<?> estateConstructor = assertConstructor(PRECONDITION);
      Method addFacilityMethod =
          assertAccessibleMethod(
              PRECONDITION, facilityClass, "addFacility", String.class, String.class);
      Method getNumberOfStudentsMethod =
          assertAccessibleMethod(PRECONDITION, int.class, "getNumberOfStudents");
      Method increaseLevelMethod =
          assertAccessibleMethod(buildingInterface, PRECONDITION, null, "increaseLevel");
      Object sotonEstate =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create an estate using Estate() constructor",
                  estateConstructor);

      // getNumberOfStudents = 0
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(0, returnedValue, "Incorrect number of students");
        }
      }.run(": Failed to run getNumberOfStudents() method", getNumberOfStudentsMethod, sotonEstate);

      // addFacility Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Hall",
                  "Wessex");

      // getNumberOfStudents = 0
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(0, returnedValue, "Incorrect number of students");
        }
      }.run(": Failed to run getNumberOfStudents() method", getNumberOfStudentsMethod, sotonEstate);

      // addFacility Zepler Lab
      Object zepler =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Lab",
                  "Zepler");

      // getNumberOfStudents = 0
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(0, returnedValue, "Incorrect number of students");
        }
      }.run(": Failed to run getNumberOfStudents() method", getNumberOfStudentsMethod, sotonEstate);

      // addFacility Murray Theatre
      Object murray =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Theatre",
                  "Murray");

      // getNumberOfStudents = 5
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(5, returnedValue, "Incorrect number of students");
        }
      }.run(": Failed to run getNumberOfStudents() method", getNumberOfStudentsMethod, sotonEstate);
    }

    /**
     * Test university.Estate's getMaintenance() method. The precondition is to have an <code>Estate
     * </code>. The test sequence is as follows.
     *
     * <ul>
     *   <li>Check the maintenance cost (using <code>getMaintenanceCost()</code>) is 0.
     *   <li>Add a facility for a <code>"Hall"</code> named <code>"Wessex"</code> (using <code>
     *       addFacility</code>).
     *   <li>Check the maintenance cost (using <code>getMaintenanceCost()</code>) is 0.6.
     *   <li>Upgrading the hall.
     *   <li>Check the budget for the university (using <code>getMaintenanceCost()</code>) is 1.2.
     * </ul>
     */
    @Test
    @DisplayName("Test university.Estate's getMaintenanceCost() method")
    public void testEstate_getMaintenanceCost() {
      // PRECONDITION
      Constructor<?> estateConstructor = assertConstructor(PRECONDITION);
      Method addFacilityMethod =
          assertAccessibleMethod(
              PRECONDITION, facilityClass, "addFacility", String.class, String.class);
      Method increaseLevelMethod =
          assertAccessibleMethod(buildingInterface, PRECONDITION, null, "increaseLevel");
      Method getMaintenanceCostMethod =
          assertAccessibleMethod(PRECONDITION, float.class, "getMaintenanceCost");
      Object sotonEstate =
          new TestConstructor()
              .run(
                  PRECONDITION + ": Failed to create an estate using Estate() constructor",
                  estateConstructor);

      // getMaintenanceCost = 0
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(0f, returnedValue, ": Incorrect maintenance cost for no facilities");
        }
      }.run(": Failed to run getMaintenanceCost() method", getMaintenanceCostMethod, sotonEstate);

      // addFacility Wessex Hall
      Object wessex =
          new TestMethod()
              .run(
                  ": Failed to run addFacility(String, String) method",
                  addFacilityMethod,
                  sotonEstate,
                  "Hall",
                  "Wessex");

      // getMaintenanceCost = 0.6
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(0.6f, returnedValue, ": Incorrect maintenance cost for Wessex (Level 1)");
        }
      }.run(": Failed to run getMaintenanceCost() method", getMaintenanceCostMethod, sotonEstate);

      // increaseLevel Wessex
      new TestMethod().run(": Failed to run increaseLevel()", increaseLevelMethod, wessex);

      // getMaintenanceCost = 1.2
      new TestMethod() {
        @Override
        public void assertions() {
          assertEquals(1.2f, returnedValue, ": Incorrect maintenance cost for Wessex (Level 2)");
        }
      }.run(": Failed to run getMaintenanceCost() method", getMaintenanceCostMethod, sotonEstate);
    }
  }
}
