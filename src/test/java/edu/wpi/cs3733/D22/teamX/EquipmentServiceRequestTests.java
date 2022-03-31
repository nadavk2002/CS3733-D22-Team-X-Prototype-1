package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class EquipmentServiceRequestTests {

  // based on the CSV file as of 3/31/22

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
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
    assertEquals(15, esrDAOImpl.getAllEquipmentServiceRequests().size());
  }

  @Test
  public void TestDBWgetRequest() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
    assertEquals(esrDAOImpl
            .getEquipmentServiceRequest("MESR0001"), esrDAOImpl.getEquipmentServiceRequest("MESR0001"));
  }

  @Test // test for correct values
  public void TestDBWgetRequest2() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
    assertEquals(1, esrDAOImpl.getEquipmentServiceRequest("MESR0001").getQuantity());
  }

  @Test // test for updating values
  public void testDBeqRequestUpdate() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
    assertEquals(1, esrDAOImpl.getEquipmentServiceRequest("MESR0001").getQuantity());
    // update
    EquipmentServiceRequest esr = esrDAOImpl.getEquipmentServiceRequest("MESR0001");
    esr.setQuantity(2);
    esrDAOImpl.updateEquipmentServiceRequest(esr); // send to db
    assertEquals(2, esrDAOImpl.getEquipmentServiceRequest("MESR0001").getQuantity());
  }

  @Test // test for deleting values
  public void testDBeqRequestDelete() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
    assertEquals(3, esrDAOImpl.getEquipmentServiceRequest("MESR0003").getQuantity());
    // removes
    EquipmentServiceRequest esr = esrDAOImpl.getEquipmentServiceRequest("MESR0003");
    esrDAOImpl.deleteEquipmentServiceRequest(esr);
    try {
      esrDAOImpl.getEquipmentServiceRequest("MESR0003");
    } catch (NoSuchElementException s) {
      assertTrue(true);
    }
  }
}
