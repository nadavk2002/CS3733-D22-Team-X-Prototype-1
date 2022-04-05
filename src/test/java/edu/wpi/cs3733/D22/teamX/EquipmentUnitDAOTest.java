package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAOImpl;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EquipmentUnitDAOTest {
    //CSV as of 4/4
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
    public void testEquipmentUnitDAOList() {
        EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
        assertEquals(15, equipmentUnitDAO.getAllEquipmentUnits().size());
    }

    @Test
    public void testEquipmentUnitDAOElement() {
//        EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
//        assertEquals();
    }

}
