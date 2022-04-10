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
    LabServiceRequestDAOImpl lsrDAOImpl = new LabServiceRequestDAOImpl();
    assertEquals(15, lsrDAOImpl.getAllLabServiceRequests().size());
  }

  @Test
  public void TestDBgetRequest() {
    LabServiceRequestDAOImpl lsrDAOImpl = new LabServiceRequestDAOImpl();
    assertEquals(
        lsrDAOImpl.getLabServiceRequest("LBSR0001"), lsrDAOImpl.getLabServiceRequest("LBSR0001"));
  }

  @Test
  public void TestDBgetRequest2() {
    LabServiceRequestDAOImpl lsrDAOImpl = new LabServiceRequestDAOImpl();
    LocationDAO locationDAO = new LocationDAOImpl();
    assertEquals(
        lsrDAOImpl.getLabServiceRequest("LBSR0001").getDestination(),
        locationDAO.getLocation("HHALL01203"));
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0001").getStatus(), "DONE");
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0001").getAssignee(), "EMPL0001");
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0001").getService(), "Blood Sample");
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0001").getPatientFor(), "PATT0001");
  }

  @Test
  public void testDBLSRequestUpdate() {
    LabServiceRequestDAOImpl lsrDAOImpl = new LabServiceRequestDAOImpl();
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0002").getStatus(), "PROC");
    LabServiceRequest lsr = lsrDAOImpl.getLabServiceRequest("LBSR0002");
    lsr.setStatus("DONE");
    lsrDAOImpl.updateLabServiceRequest(lsr);
    assertEquals("DONE", lsrDAOImpl.getLabServiceRequest("LBSR0002").getStatus());
  }

  @Test
  public void testDBLSRequestDelete() {
    boolean returnVarible = false;
    LabServiceRequestDAOImpl lsrDAOImpl = new LabServiceRequestDAOImpl();
    assertEquals(lsrDAOImpl.getLabServiceRequest("LBSR0003").getStatus(), "DONE");
    // delete
    LabServiceRequest lsr = lsrDAOImpl.getLabServiceRequest("LBSR0003");
    lsrDAOImpl.deleteLabServiceRequest(lsr);
    try {
      lsrDAOImpl.getLabServiceRequest("LBSR0003");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
  }

  @Test
  public void testDBLSRequestAdd() {
    boolean returnVarible = false;
    LabServiceRequestDAO lsrDAOImpl = new LabServiceRequestDAOImpl();
    LocationDAO locationDAO = new LocationDAOImpl();
    try {
      lsrDAOImpl.getLabServiceRequest("LBSR0016");
    } catch (NoSuchElementException s) {
      returnVarible = true;
    }
    assertTrue(returnVarible);
    // add the thing
    LabServiceRequest lsr =
        new LabServiceRequest(
            "LBSR0016",
            locationDAO.getLocation("FHALL01101"),
            "DONE",
            "EMPL0001",
            "Ultrasound",
            "PATT0125");
    // add the thing
    lsrDAOImpl.addLabServiceRequest(lsr);
    assertEquals(lsr, lsrDAOImpl.getLabServiceRequest("LBSR0016"));
  }
}
