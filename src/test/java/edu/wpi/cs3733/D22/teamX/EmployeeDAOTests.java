package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.*;

public class EmployeeDAOTests {
  // based on CSV file as of 4/4
  @Before
  public void setupDB() {
    // initialize DatabaseCreator
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void TestDBListEmployees() {
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    assertEquals(28, emplDAOImpl.getAllEmployees().size());
  }

  @Test
  public void TestDBgetEmployee() {
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    assertNotEquals(emplDAOImpl.getEmployee("EMPL0002"), emplDAOImpl.getEmployee("EMPL0001"));
  }

  @Test
  public void TestDBgetEmployee2() {
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    assertEquals(emplDAOImpl.getEmployee("EMPL0001").getEmployeeID(), "EMPL0001");
    assertEquals(emplDAOImpl.getEmployee("EMPL0001").getFirstName(), "Richard");
    assertEquals(emplDAOImpl.getEmployee("EMPL0001").getLastName(), "Webber");
    assertEquals(emplDAOImpl.getEmployee("EMPL0001").getClearanceType(), "admin");
    assertEquals(emplDAOImpl.getEmployee("EMPL0001").getJobTitle(), "Chief of Surgery");
  }

  @Test
  public void testEmployeeUpdate() {
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    assertEquals(emplDAOImpl.getEmployee("EMPL0008").getJobTitle(), "Custodian");
    Employee empl = emplDAOImpl.getEmployee("EMPL0008");
    empl.setJobTitle("BOSS");
    emplDAOImpl.updateEmployee(empl);
    assertEquals("BOSS", emplDAOImpl.getEmployee("EMPL0008").getJobTitle());
  }

  @Test
  public void testEmployeeDelete() {
    boolean returnVarible = false;
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    //    assertEquals(emplDAOImpl.getEmployee("EMPL0003").getStatus(), "DONE");
    //    // delete
    Employee empl = emplDAOImpl.getEmployee("EMPL0003");
    emplDAOImpl.deleteEmployee(empl);
    try {
      emplDAOImpl.getEmployee("EMPL0003");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
  }

  @Test
  public void testDBLSRequestAdd() {
    boolean returnVarible = false;
    EmployeeDAO emplDAOImpl = new EmployeeDAOImpl();
    try {
      emplDAOImpl.getEmployee("EMPL0029");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
    // add the thing
    Employee empl = new Employee("EMPL0029", "Sam", "Smith", "staff", "Ultrasound");
    // add the thing
    emplDAOImpl.addEmployee(empl);
    assertEquals(empl, emplDAOImpl.getEmployee("EMPL0029"));
  }
}
