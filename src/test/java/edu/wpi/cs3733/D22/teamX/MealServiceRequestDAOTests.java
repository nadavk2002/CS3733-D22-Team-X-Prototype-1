package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamX.entity.MealServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MealServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.awt.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class MealServiceRequestDAOTests {
  MealServiceRequestDAO DAO;

  @Before
  public void setupDB() {
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
    DAO = MealServiceRequestDAO.getDAO();
  }

  @Test
  public void TestGetRecord() {
    assertTrue(DAO.getRecord("PMSR0007").getStatus().equals("PROC"));
  }

  @Test
  public void TestdeleteServiceRecord() {
    Boolean testReturn = false; // must be changed to true
    assertTrue(DAO.getRecord("PMSR0008").getStatus().equals("DONE")); // make sure the thing exists
    // delete the record
    DAO.deleteRecord(DAO.getRecord("PMSR0008"));
    try {
      DAO.getRecord("PMSR0008");
    } catch (NoSuchElementException e) {
      testReturn = true;
    }
    assertTrue(testReturn);
  }

  @Test
  public void TestUpdateServiceRecord() {
    MealServiceRequest newPMSR = new MealServiceRequest();
    // test Meal Service Request
    assertTrue(DAO.getRecord("PMSR0009").getStatus().equals("PROC"));
    // set MealRequest
    newPMSR.setRequestID(DAO.getRecord("PMSR0009").getRequestID());
    newPMSR.setDestination(DAO.getRecord("PMSR0009").getDestination());
    newPMSR.setStatus("DONE");
    newPMSR.setAssignee(DAO.getRecord("PMSR0009").getAssignee());
    // update
    DAO.updateRecord(newPMSR);
    // check for change
    assertTrue(DAO.getRecord("PMSR0009").getStatus().equals("DONE"));
  }

  @Test
  public void TestAddRecord() {
    MealServiceRequest newPMSR = new MealServiceRequest();
    // test Meal Service Request
    assertTrue(DAO.getRecord("PMSR0010").getStatus().equals("DONE"));
    // set MealRequest
    newPMSR.setRequestID(DAO.makeID());
    newPMSR.setDestination(DAO.getRecord("PMSR0010").getDestination());
    newPMSR.setStatus("PROC");
    newPMSR.setAssignee(DAO.getRecord("PMSR0010").getAssignee());
    // update
    DAO.addRecord(newPMSR);
    assertTrue(DAO.getRecord("PMSR0016").getStatus().equals("PROC"));
  }

  @Test
  public void TestGetAllRequests() {
    assertTrue(DAO.getAllRecords().size() == 15);
  }

  // this test must be at the bottom of this file or else out of order code excution
  @Test
  public void TestMakeID() {
    assertEquals(DAO.makeID(), "PMSR0016");
  }
}
