package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class LocationDAOTests {
  // based on the CSV file as of 3/29/22

  // connection varible
  Connection connection;

  @Before
  public void setupDB() {
    // initialize Xdb cannot reinitiallized
    connection = Xdb.initializeDB();
  }

  @Test // tests if pulls list
  public void TestLocationDAOList() {
    // create DAO object
    LocationDAO locationDAO = new LocationDAOImpl();
    assertEquals(139, locationDAO.getAllLocations().size());
  }

  @Test // tests if pulls list
  public void TestLocationDAOelement() {
    // create DAO object
    LocationDAO locationDAO = new LocationDAOImpl();
    assertEquals("Elevator LL2", locationDAO.getLocation("WELEV00LL2").getShortName());
  }

  @Test // tests if pulls list
  public void TestLocationDAOupdate() {
    // create DAO object
    LocationDAO locationDAO = new LocationDAOImpl();
    assertTrue(locationDAO.getLocation("xSTOR00201").getShortName().equals("West Plaza"));
    Location loc = locationDAO.getLocation("xSTOR00201"); // store the location
    loc.setShortName("East Plaza");
    locationDAO.updateLocation(loc);
    assertTrue(locationDAO.getLocation("xSTOR00201").getShortName().equals("East Plaza"));
  }

  @Test // tests if thows exception
  public void TestLocationDAOupdate2() {
    Boolean result = false;
    // create DAO object
    LocationDAO locationDAO = new LocationDAOImpl();
    Location loc = new Location();
    loc.setNodeID("testNode");
    try {
      locationDAO.updateLocation(loc);
    } catch (NoSuchElementException s) {
      result = true;
    }
    assertTrue(result);
  }
}
