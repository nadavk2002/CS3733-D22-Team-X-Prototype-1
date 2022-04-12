package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class MedicalEquipmentServiceRequestTests {

  // based on the CSV file as of 3/31/22

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
    MedicalEquipmentServiceRequestDAO esrDAOImpl = MedicalEquipmentServiceRequestDAO.getDAO();
    assertEquals(15, esrDAOImpl.getAllRecords().size());
  }

  @Test
  public void TestDBWgetRequest() {
    MedicalEquipmentServiceRequestDAO esrDAOImpl = MedicalEquipmentServiceRequestDAO.getDAO();
    assertEquals(esrDAOImpl.getRecord("MESR0001"), esrDAOImpl.getRecord("MESR0001"));
  }

  @Test // test for correct values
  public void TestDBWgetRequest2() {
    MedicalEquipmentServiceRequestDAO esrDAOImpl = MedicalEquipmentServiceRequestDAO.getDAO();
    assertEquals(1, esrDAOImpl.getRecord("MESR0001").getQuantity());
  }

  @Test // test for updating values
  public void testDBeqRequestUpdate() {
    MedicalEquipmentServiceRequestDAO esrDAOImpl = MedicalEquipmentServiceRequestDAO.getDAO();
    assertEquals(1, esrDAOImpl.getRecord("MESR0001").getQuantity());
    // update
    MedicalEquipmentServiceRequest esr = esrDAOImpl.getRecord("MESR0001");
    esr.setQuantity(2);
    esrDAOImpl.updateRecord(esr); // send to db
    assertEquals(2, esrDAOImpl.getRecord("MESR0001").getQuantity());
  }

  @Test // test for deleting values
  public void testDBeqRequestDelete() {
    MedicalEquipmentServiceRequestDAO esrDAOImpl = MedicalEquipmentServiceRequestDAO.getDAO();
    assertEquals(3, esrDAOImpl.getRecord("MESR0003").getQuantity());
    // removes
    MedicalEquipmentServiceRequest esr = esrDAOImpl.getRecord("MESR0003");
    esrDAOImpl.deleteRecord(esr);
    try {
      esrDAOImpl.getRecord("MESR0003");
    } catch (NoSuchElementException s) {
      assertTrue(true);
    }
  }
}
