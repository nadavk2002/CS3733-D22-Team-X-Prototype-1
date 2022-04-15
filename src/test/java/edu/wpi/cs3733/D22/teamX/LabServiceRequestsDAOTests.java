package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class LabServiceRequestsDAOTests {

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

  // these test will fail if the csv file changes
  @Test
  public void TestDBListRequests() {
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    assertEquals(15, lsrDAOImpl.getAllRecords().size());
  }

  @Test
  public void TestDBgetRequest() {
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    assertEquals(lsrDAOImpl.getRecord("LBSR0001"), lsrDAOImpl.getRecord("LBSR0001"));
  }

  @Test
  public void TestDBgetRequest2() {
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    LocationDAO locationDAO = LocationDAO.getDAO();
    assertEquals(
        lsrDAOImpl.getRecord("LBSR0001").getDestination(), locationDAO.getRecord("HHALL01203"));
    assertEquals(lsrDAOImpl.getRecord("LBSR0001").getStatus(), "DONE");
    assertEquals(lsrDAOImpl.getRecord("LBSR0001").getAssignee(), "EMPL0001");
    assertEquals(lsrDAOImpl.getRecord("LBSR0001").getService(), "Blood Sample");
    assertEquals(lsrDAOImpl.getRecord("LBSR0001").getPatientFor(), "PATT0001");
  }

  @Test
  public void testDBLSRequestUpdate() {
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    assertEquals(lsrDAOImpl.getRecord("LBSR0002").getStatus(), "PROC");
    LabServiceRequest lsr = lsrDAOImpl.getRecord("LBSR0002");
    lsr.setStatus("DONE");
    lsrDAOImpl.updateRecord(lsr);
    assertEquals("DONE", lsrDAOImpl.getRecord("LBSR0002").getStatus());
  }

  @Test
  public void testDBLSRequestDelete() {
    boolean returnVarible = false;
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    assertEquals(lsrDAOImpl.getRecord("LBSR0003").getStatus(), "DONE");
    // delete
    LabServiceRequest lsr = lsrDAOImpl.getRecord("LBSR0003");
    lsrDAOImpl.deleteRecord(lsr);
    try {
      lsrDAOImpl.getRecord("LBSR0003");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
  }

  @Test
  public void testDBLSRequestAdd() {
    boolean returnVarible = false;
    LabServiceRequestDAO lsrDAOImpl = LabServiceRequestDAO.getDAO();
    LocationDAO locationDAO = LocationDAO.getDAO();
    EmployeeDAO empDAO = EmployeeDAO.getDAO();
    try {
      lsrDAOImpl.getRecord("LBSR0016");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
    // add the thing
    LabServiceRequest lsr =
        new LabServiceRequest(
            "LBSR0016",
            locationDAO.getRecord("FHALL01101"),
            "DONE",
            empDAO.getRecord("EMPL0001"),
            "Ultrasound",
            "PATT0125");
    // add the thing
    lsrDAOImpl.addRecord(lsr);
    assertEquals(lsr, lsrDAOImpl.getRecord("LBSR0016"));
  }
}
