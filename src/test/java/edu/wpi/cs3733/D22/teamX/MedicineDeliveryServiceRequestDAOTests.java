package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamX.entity.MedicineDeliverServiceRequestDAO;
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
}
