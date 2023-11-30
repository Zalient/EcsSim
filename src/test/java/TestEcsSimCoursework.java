/***************************************************************************************************
 * Copyright (c) 2023 University of Southampton.
 *
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
 **************************************************************************************************/

/*
 * Abstract class for testing the ECS Sim coursework.
 *
 * @author Son Hoang - v1.0.0 - Initial API and implementation.
 * @version 1.0.0
 */
public abstract class TestEcsSimCoursework {

  /**
   * Abstract test class for facilities.Facility.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestFacility extends AbstractTestClass {

    /**
     * We are testing facilities.Facility.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "facilities.Facility");
    }
  }

  /**
   * Abstract test class for facilities.buildings.Building.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestBuilding extends AbstractTestClass {

    /**
     * We are testing facilities.buildings.Building.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "facilities.buildings.Building");
    }
  }

  /**
   * Abstract test class for facilities.buildings.Hall.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestHall extends AbstractTestClass {

    /**
     * We are testing facilities.buildings.Hall.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "facilities.buildings.Hall");
    }
  }

  /**
   * Abstract test class for facilities.buildings.Theatre.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestTheatre extends AbstractTestClass {

    /**
     * We are testing facilities.buildings.Theatre.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "facilities.buildings.Theatre");
    }
  }

  /**
   * Abstract test class for facilities.buildings.Lab.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestLab extends AbstractTestClass {

    /**
     * We are testing facilities.buildings.Lab.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "facilities.buildings.Lab");
    }
  }

  /**
   * Abstract test class for university.Estate.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestEstate extends AbstractTestClass {

    /**
     * We are testing university.Estate.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "university.Estate");
    }
  }

  /**
   * Abstract test class for university.University.
   *
   * @author htson
   * @version 1.0
   * @see AbstractTestClass
   */
  protected abstract static class AbstractTestUniversity extends AbstractTestClass {

    /**
     * We are testing university.Estate.
     */
    protected Class<?> getTestClass() {
      return assertClassOrInterface("", "university.University");
    }
  }

}
