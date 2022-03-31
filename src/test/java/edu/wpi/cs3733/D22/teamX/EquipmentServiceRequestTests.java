// package edu.wpi.cs3733.D22.teamX;
//
// import static org.junit.jupiter.api.Assertions.assertTrue;
//
// import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
// import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAOImpl;
// import java.sql.Connection;
// import java.util.NoSuchElementException;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// public class EquipmentServiceRequestTests {
//
//  // based on the CSV file as of 3/29/22
//
//  // connection variable
//  Connection connection;
//  EquipmentServiceRequest esr;
//
//  @BeforeEach
//  public void setupDB() {
//    // initialize Xdb
//    connection = Xdb.initializeDB();
//  }
//
//  // these test will fail if the csv file changes
//  @Test
//  public void TestDBListRequests() {
//    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
//    assertTrue(esrDAOImpl.getAllEquipmentServiceRequests().size() == 15);
//  }
//
//  @Test
//  public void TestDBWgetRequest() {
//    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
//    assertTrue(
//        esrDAOImpl
//            .getEquipmentServiceRequest("MER_0001")
//            .equals(esrDAOImpl.getEquipmentServiceRequest("MER_0001")));
//  }
//
//  @Test // test for correct values
//  public void TestDBWgetRequest2() {
//    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
//    assertTrue(esrDAOImpl.getEquipmentServiceRequest("MER_0001").getQuantity() == 1);
//  }
//
//  @Test // test for updating values
//  public void testDBeqRequestUpdate() {
//    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
//    assertTrue(esrDAOImpl.getEquipmentServiceRequest("MER_0001").getQuantity() == 1);
//    // update
//    EquipmentServiceRequest esr = esrDAOImpl.getEquipmentServiceRequest("MER_0001");
//    esr.setQuantity(2);
//    esrDAOImpl.updateEquipmentServiceRequest(esr); // send to db
//    assertTrue(esrDAOImpl.getEquipmentServiceRequest("MER_0001").getQuantity() == 2);
//  }
//
//  @Test // test for deleting values
//  public void testDBeqRequestDelete() {
//    EquipmentServiceRequestDAOImpl esrDAOImpl = new EquipmentServiceRequestDAOImpl();
//    assertTrue(esrDAOImpl.getEquipmentServiceRequest("MER_0003").getQuantity() == 3);
//    // removes
//    EquipmentServiceRequest esr = esrDAOImpl.getEquipmentServiceRequest("MER_0003");
//    esrDAOImpl.deleteEquipmentServiceRequest(esr);
//    try {
//      esrDAOImpl.getEquipmentServiceRequest("MER_0003");
//    } catch (NoSuchElementException s) {
//      assertTrue(true);
//    }
//  }
// }
