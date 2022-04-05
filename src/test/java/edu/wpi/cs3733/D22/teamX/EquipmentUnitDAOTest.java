package edu.wpi.cs3733.D22.teamX;

import static junit.framework.TestCase.assertEquals;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import org.junit.Before;
import org.junit.Test;

public class EquipmentUnitDAOTest {
  // CSV as of 4/4
  @Before
  public void setupDB() {
    // initialize Xdb
    try {
      Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateList() {
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    assertEquals(15, equipmentUnitDAO.getAllEquipmentUnits().size());
  }

  @Test
  public void testUpdateRecord() {
    LocationDAO locationDAO = new LocationDAOImpl();
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    // add record
    EquipmentUnit record =
        new EquipmentUnit("MEUN9998", "Bed", 'Y', locationDAO.getLocation("xSTOR001L1"));
    equipmentUnitDAO.addEquipmentUnit(record);

    // update record
    EquipmentUnit recordUpdate =
        new EquipmentUnit("MEUN9998", "Bed", 'N', locationDAO.getLocation("xSTOR00303"));
    equipmentUnitDAO.updateEquipmentUnit(recordUpdate);

    // check if update record is there
    recordUpdate = equipmentUnitDAO.getEquipmentUnit("MEUN9998");
    assertEquals("MEUN9998", recordUpdate.getUnitID());
    assertEquals("Bed", recordUpdate.getType());
    assertEquals('N', recordUpdate.getIsAvailableChar());
    assertEquals("xSTOR00303", recordUpdate.getCurrLocation().getNodeID());
  }

  @Test
  public void testAddRecord() {
    LocationDAO locationDAO = new LocationDAOImpl();
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    EquipmentUnit record =
        new EquipmentUnit("MEUN9999", "Bed", 'Y', locationDAO.getLocation("xSTOR001L1"));
    equipmentUnitDAO.addEquipmentUnit(record);
    record = equipmentUnitDAO.getEquipmentUnit("MEUN9999");
    assertEquals("MEUN9999", record.getUnitID());
    assertEquals("Bed", record.getType());
    assertEquals('Y', record.getIsAvailableChar());
    assertEquals("xSTOR001L1", record.getCurrLocation().getNodeID());
  }

  @Test
  public void testGetRecord() {
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    EquipmentUnit record = equipmentUnitDAO.getEquipmentUnit("MEUN0001");
    assertEquals("MEUN0001", record.getUnitID());
    assertEquals("X-Ray", record.getType());
    assertEquals('Y', record.getIsAvailableChar());
    assertEquals("CREST001L1", record.getCurrLocation().getNodeID());
  }
}
