package edu.wpi.cs3733.D22.teamX;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAOImpl;
import java.sql.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentServiceRequestTests {

  // based on the CSV file as of 3/29/22

  // connection variable
  Connection connection;
  EquipmentServiceRequest esr;

  @BeforeEach
  public void setupDB() {
    // initialize Xdb
    connection = Xdb.initializeDB();
  }

  // these test will fail if the csv file changes
  @Test
  public void TestDBListRequests() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl(connection);
    assertTrue(esrDAOImpl.getAllEquipmentServiceRequests().size() == 15);
  }

  @Test
  public void TestDBWgetRequest() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl(connection);
    assertTrue(
        esrDAOImpl
            .getEquipmentServiceRequest("MER_0001")
            .equals(esrDAOImpl.getEquipmentServiceRequest("MER_0001")));
  }

  @Test //test for correct values
  public void TestDBWgetRequest2() {
    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl(connection);
    assertTrue(esrDAOImpl.getEquipmentServiceRequest("MER_0001").getQuantity() == 1);
  }

}
