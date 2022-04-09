package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAOImpl;
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
    MedicalEquipmentServiceRequestDAOImpl esrDAOImpl = new MedicalEquipmentServiceRequestDAOImpl();
    assertEquals(15, esrDAOImpl.getAllMedicalEquipmentServiceRequests().size());
  }

  @Test
  public void TestDBWgetRequest() {
    MedicalEquipmentServiceRequestDAOImpl esrDAOImpl = new MedicalEquipmentServiceRequestDAOImpl();
    assertEquals(
        esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001"),
        esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001"));
  }

  @Test // test for correct values
  public void TestDBWgetRequest2() {
    MedicalEquipmentServiceRequestDAOImpl esrDAOImpl = new MedicalEquipmentServiceRequestDAOImpl();
    assertEquals(1, esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001").getQuantity());
  }

  @Test // test for updating values
  public void testDBeqRequestUpdate() {
    MedicalEquipmentServiceRequestDAOImpl esrDAOImpl = new MedicalEquipmentServiceRequestDAOImpl();
    assertEquals(1, esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001").getQuantity());
    // update
    MedicalEquipmentServiceRequest esr = esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001");
    esr.setQuantity(2);
    esrDAOImpl.updateMedicalEquipmentServiceRequest(esr); // send to db
    assertEquals(2, esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0001").getQuantity());
  }

  @Test // test for deleting values
  public void testDBeqRequestDelete() {
    MedicalEquipmentServiceRequestDAOImpl esrDAOImpl = new MedicalEquipmentServiceRequestDAOImpl();
    assertEquals(3, esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0003").getQuantity());
    // removes
    MedicalEquipmentServiceRequest esr = esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0003");
    esrDAOImpl.deleteMedicalEquipmentServiceRequest(esr);
    try {
      esrDAOImpl.getMedicalEquipmentServiceRequest("MESR0003");
    } catch (NoSuchElementException s) {
      assertTrue(true);
    }
  }
}
