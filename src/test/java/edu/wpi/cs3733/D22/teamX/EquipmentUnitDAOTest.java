package edu.wpi.cs3733.D22.teamX;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class EquipmentUnitDAOTest {
  // CSV as of 4/4
  @Before
  public void setupDB() {
    // initialize DatabaseCreator
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateList() {
    EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
    assertEquals(15, equipmentUnitDAO.getAllRecords().size());
  }

  @Test
  public void testDeleteRecord() {
    EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
    EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
    LocationDAO locationDAO = LocationDAO.getDAO();
    EquipmentUnit record =
        new EquipmentUnit(
            "MEUN9997",
            equipmentTypeDAO.getRecord("Infusion Pump"),
            'Y',
            locationDAO.getRecord("CHALL009L2"));
    equipmentUnitDAO.addRecord(record);
    equipmentUnitDAO.deleteRecord(record);
    boolean notFound = false;
    try {
      equipmentUnitDAO.deleteRecord(record);
    } catch (NoSuchElementException e) {
      notFound = true;
    }
    assertTrue(notFound);
  }

  @Test
  public void testUpdateRecord() {
    LocationDAO locationDAO = LocationDAO.getDAO();
    EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
    EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
    // add record
    EquipmentUnit record =
        new EquipmentUnit(
            "MEUN9998",
            equipmentTypeDAO.getRecord("Bed"),
            'Y',
            locationDAO.getRecord("xSTOR001L1"));
    equipmentUnitDAO.addRecord(record);

    // update record
    EquipmentUnit recordUpdate =
        new EquipmentUnit(
            "MEUN9998",
            equipmentTypeDAO.getRecord("Bed"),
            'N',
            locationDAO.getRecord("xSTOR00303"));
    equipmentUnitDAO.updateRecord(recordUpdate);

    // check if update record is there
    recordUpdate = equipmentUnitDAO.getRecord("MEUN9998");
    assertEquals("MEUN9998", recordUpdate.getUnitID());
    assertEquals("Bed", recordUpdate.getType().getModel());
    assertEquals('N', recordUpdate.getIsAvailableChar());
    assertEquals("xSTOR00303", recordUpdate.getCurrLocation().getNodeID());
  }

  @Test
  public void testAddRecord() {
    LocationDAO locationDAO = LocationDAO.getDAO();
    EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
    EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
    EquipmentUnit record =
        new EquipmentUnit(
            "MEUN9999",
            equipmentTypeDAO.getRecord("Bed"),
            'Y',
            locationDAO.getRecord("xSTOR001L1"));
    equipmentUnitDAO.addRecord(record);
    record = equipmentUnitDAO.getRecord("MEUN9999");
    assertEquals("MEUN9999", record.getUnitID());
    assertEquals("Bed", record.getType().getModel());
    assertEquals('Y', record.getIsAvailableChar());
    assertEquals("xSTOR001L1", record.getCurrLocation().getNodeID());
  }

  @Test
  public void testGetRecord() {
    EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
    EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
    EquipmentUnit record = equipmentUnitDAO.getRecord("MEUN0001");
    assertEquals("MEUN0001", record.getUnitID());
    assertEquals("X-Ray", record.getType().getModel());
    assertEquals('Y', record.getIsAvailableChar());
    assertEquals("CREST001L1", record.getCurrLocation().getNodeID());
  }
}
