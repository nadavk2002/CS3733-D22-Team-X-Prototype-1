/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.*;

public class XdbTests {
  @Test
  public void testInitializeDB() {
    try {
      Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      Assert.fail();
    }

    assertNotNull(ConnectionSingleton.getConnectionSingleton().getConnection());

    try {
      Xdb.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCloseDB() {
    try {
      Xdb.initializeDB();
      assertTrue(Xdb.closeDB());
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Xdb.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateLocationTable() {
    try {
      Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Statement statement =
          ConnectionSingleton.getConnectionSingleton().getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Location");
    } catch (SQLException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Xdb.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateMedicalEquipmentServiceRequestTable() {
    try {
      Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Statement statement =
          ConnectionSingleton.getConnectionSingleton().getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM MedicalEquipmentServiceRequest");
    } catch (SQLException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Xdb.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLoadAndSaveToCSV() {
    try {
      Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      Assert.fail();
    }

    try {
      Xdb.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  public void testLoadLocationCSV() {
  //    Connection connection = Xdb.initializeDB();
  //    try {
  //      connection.close();
  //    } catch (SQLException e) {
  //      e.printStackTrace();
  //    }
  //  }

  //  @Test
  //  public void testSaveCSV() {
  //    Connection connection = Xdb.initializeDB();
  //    LocationDAOImpl locationDAO = new LocationDAOImpl();
  //    locationDAO.deleteLocation(new Location("xSTOR00503"));
  //
  //    MedicalEquipmentServiceRequestDAOImpl equipmentServiceRequestDAO =
  //        new MedicalEquipmentServiceRequestDAOImpl();
  //    equipmentServiceRequestDAO.addEquipmentServiceRequest(
  //        new MedicalEquipmentServiceRequest("MESR0016", new Location("xPATI00803"), "PROC",
  // "Tweezers", 3));
  //
  //    Xdb.closeDB(
  //        connection,
  //        "src\\main\\resources\\edu\\wpi\\cs3733\\D22\\teamX\\TowerLocations.csv",
  //        "src\\main\\resources\\edu\\wpi\\cs3733\\D22\\teamX\\MedEquipReq.csv");
  //  }
}
