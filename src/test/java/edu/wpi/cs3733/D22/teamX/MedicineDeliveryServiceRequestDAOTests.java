package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamX.entity.MedicineDeliverServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.entity.MedicineServiceRequest;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.awt.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class MedicineDeliveryServiceRequestDAOTests {
  MedicineDeliverServiceRequestDAO DAO;

  @Before
  public void setupDB() {
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
    DAO = MedicineDeliverServiceRequestDAO.getDAO();
  }

  @Test
  public void TestGetRecord() {
    assertTrue(DAO.getRecord("MDSR0001").getStatus().equals("PROC"));
  }

  @Test
  public void TestdeleteServiceRecord() {
    Boolean testReturn = false; // must be changed to true
    assertTrue(DAO.getRecord("MDSR0008").getStatus().equals("DONE")); // make sure the thing exists
    // delete the record
    DAO.deleteRecord(DAO.getRecord("MDSR0008"));
    try {
      DAO.getRecord("MDSR0008");
    } catch (NoSuchElementException e) {
      testReturn = true;
    }
    assertTrue(testReturn);
  }

  @Test
  public void TestUpdateServiceRecord() {
    MedicineServiceRequest newMDSR = new MedicineServiceRequest();
    // test Meal Service Request
    assertTrue(DAO.getRecord("MDSR0009").getStatus().equals("PROC"));
    // set MealRequest
    newMDSR.setRequestID(DAO.getRecord("MDSR0009").getRequestID());
    newMDSR.setDestination(DAO.getRecord("MDSR0009").getDestination());
    newMDSR.setStatus("DONE");
    newMDSR.setAssignee(DAO.getRecord("MDSR0009").getAssignee());
    // update
    DAO.updateRecord(newMDSR);
    // check for change
    assertTrue(DAO.getRecord("MDSR0009").getStatus().equals("DONE"));
  }

  @Test
  public void TestAddRecord() {
    MedicineServiceRequest newMDSR = new MedicineServiceRequest();
    // test Meal Service Request
    assertTrue(DAO.getRecord("MDSR0011").getStatus().equals("PROC"));
    // set MealRequest
    newMDSR.setRequestID(DAO.makeID());
    newMDSR.setDestination(DAO.getRecord("MDSR0011").getDestination());
    newMDSR.setStatus("PROC");
    newMDSR.setAssignee(DAO.getRecord("MDSR0011").getAssignee());
    // update
    DAO.addRecord(newMDSR);
    assertTrue(DAO.getRecord("MDSR0016").getStatus().equals("PROC"));
  }

  @Test
  public void TestGetAllRequests() {
    assertTrue(DAO.getAllRecords().size() == 15);
  }

  // this test must be at the bottom of this file or else out of order code excution
  @Test
  public void TestMakeID() {
    assertEquals(DAO.makeID(), "MDSR0016");
  }
}
