package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class LocationDAOOldTests {
  // based on the CSV file as of 3/29/22

  @Before
  public void setupDB() {
    // initialize DatabaseCreator cannot reinitiallized
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test // tests if pulls list
  public void TestLocationDAOList() {
    // create DAO object
    LocationDAO locationDAO = LocationDAO.getDAO();
    assertEquals(82, locationDAO.getAllRecords().size());
  }

  @Test // tests if pulls list
  public void TestLocationDAOelement() {
    // create DAO object
    LocationDAO locationDAO = LocationDAO.getDAO();
    assertEquals("Elevator LL2", locationDAO.getRecord("WELEV00LL2").getShortName());
  }

  @Test // tests if pulls list
  public void TestLocationDAOupdate() {
    // create DAO object
    LocationDAO locationDAO = LocationDAO.getDAO();
    assertEquals("West Plaza", locationDAO.getRecord("xSTOR00201").getShortName());
    Location loc = locationDAO.getRecord("xSTOR00201"); // store the location
    loc.setShortName("East Plaza");
    locationDAO.updateRecord(loc);
    assertEquals("East Plaza", locationDAO.getRecord("xSTOR00201").getShortName());
  }

  @Test // tests if thows exception
  public void TestLocationDAOupdate2() {
    boolean result = false;
    // create DAO object
    LocationDAO locationDAO = LocationDAO.getDAO();
    Location loc = new Location();
    loc.setNodeID("testNode");
    try {
      locationDAO.updateRecord(loc);
    } catch (NoSuchElementException s) {
      result = true;
    }
    assertTrue(result);
  }

  @Test // tests if thows exception
  public void TestLocationDAOadd() {
    boolean result = true;
    // create DAO object
    LocationDAO locationDAO = LocationDAO.getDAO();
    Location loc = new Location();
    loc.setNodeID("testNode");
    try {
      locationDAO.addRecord(loc);
    } catch (NoSuchElementException s) {
      result = false;
    }
    assertTrue(result);
  }
}
