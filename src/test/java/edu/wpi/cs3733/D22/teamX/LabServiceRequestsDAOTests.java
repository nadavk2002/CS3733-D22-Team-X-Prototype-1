package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;

import edu.wpi.cs3733.D22.teamX.entity.LabServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import org.junit.Before;
import org.junit.Test;

public class LabServiceRequestsDAOTests {

  // based on CSV file as of 4/4
  @Before
  public void setupDB() {
    // initialize Xdb
    try {
      Xdb.initializeDB();
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
  }
}
