package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class LabServiceRequestsDAOTests {

    //based on CSV file as of 4/4
    @Before
    public void setupDB() {
        // initialize Xdb
        try {
            Xdb.initializeDB();
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
}
