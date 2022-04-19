package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.SharpsDisposalRequest;
import edu.wpi.cs3733.D22.teamX.entity.SharpsDisposalRequestDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.awt.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class SharpsDisposalServiceRequestDAOTests {
  SharpsDisposalRequestDAO DAO;

  @Before
  public void setupDB() {
    ConnectionSingleton.getConnectionSingleton().setEmbedded();
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
    DAO = SharpsDisposalRequestDAO.getDAO();
  }

  @Test
  public void TestGetRecord() {
    assertTrue(DAO.getRecord("SDSR0007").getStatus().equals("PROC"));
  }

  @Test
  public void TestdeleteServiceRecord() {
    Boolean testReturn = false; // must be changed to true
    assertTrue(DAO.getRecord("SDSR0008").getStatus().equals("DONE")); // make sure the thing exists
    // delete the record
    DAO.deleteRecord(DAO.getRecord("SDSR0008"));
    try {
      DAO.getRecord("SDSR0008");
    } catch (NoSuchElementException e) {
      testReturn = true;
    }
    assertTrue(testReturn);
  }

  @Test
  public void TestUpdateServiceRecord() {
    SharpsDisposalRequest newSDSR = new SharpsDisposalRequest();
    // test Meal Service Request
    assertTrue(DAO.getRecord("SDSR0009").getStatus().equals("PROC"));
    // set MealRequest
    newSDSR.setRequestID(DAO.getRecord("SDSR0009").getRequestID());
    newSDSR.setDestination(DAO.getRecord("SDSR0009").getDestination());
    newSDSR.setStatus("DONE");
    newSDSR.setAssignee(DAO.getRecord("SDSR0009").getAssignee());
    // update
    DAO.updateRecord(newSDSR);
    // check for change
    assertTrue(DAO.getRecord("SDSR0009").getStatus().equals("DONE"));
  }

  @Test
  public void TestAddRecord() {
    SharpsDisposalRequest newSDSR = new SharpsDisposalRequest();
    // test Meal Service Request
    // set MealRequest
    newSDSR.setRequestID(DAO.makeID());
    newSDSR.setDestination(DAO.getRecord("SDSR0010").getDestination());
    newSDSR.setStatus("PROC");
    newSDSR.setAssignee(DAO.getRecord("SDSR0010").getAssignee());
    // update
    DAO.addRecord(newSDSR);
    // check for change
    assertTrue(DAO.getRecord("SDSR0015").getStatus().equals("PROC"));
  }

  @Test
  public void TestGetAllRequests() {
    assertTrue(DAO.getAllRecords().size() == 14);
  }

  @Test
  public void TestMakeID() {
    assertEquals(DAO.makeID(), "SDSR0015");
  }
}
