package edu.wpi.cs3733.D22.teamX.entity;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MedicineDeliverServiceRequestDAO implements DAO<MedicineServiceRequest> {
  private static List<MedicineServiceRequest> medicineServiceRequests =
      new ArrayList<MedicineServiceRequest>();
  private static String csv = "MedicineDeliveryRequests.csv";

  private MedicineDeliverServiceRequestDAO() {}

  private static class SingletonHelper {
    private static final MedicineDeliverServiceRequestDAO medicineDeliveryServiceRequestDAO =
        new MedicineDeliverServiceRequestDAO();
  }

  public static MedicineDeliverServiceRequestDAO getDAO() {
    return SingletonHelper.medicineDeliveryServiceRequestDAO;
  }

  @Override
  public List<MedicineServiceRequest> getAllRecords() {
    return medicineServiceRequests;
  }

  @Override
  public MedicineServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MedicineServiceRequest esr : medicineServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(recordID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MedicineServiceRequest recordObject) {
    // add to destination's list of service requests
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicineServiceRequests.size();
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.remove(index); // removes object from list
        index--;
        break; // exit
      }
      index++; // increment if not found yet
    }
    if (index == initialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // remove from database
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // remove location from DB table
      statement.executeUpdate(
          "DELETE FROM MedicineDeliveryServiceRequests WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MedicineServiceRequest recordObject) {
    // remove from old location and add to new one if location changes in the update
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicineServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MedicalEquipmentServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssignee()
              + "', rxNum = '"
              + recordObject.getRxNum()
              + "', patientFor = '"
              + recordObject.getPatientFor()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(MedicineServiceRequest recordObject) {}

  @Override
  public void createTable() {}

  @Override
  public void dropTable() {}

  @Override
  public boolean loadCSV() {
    return false;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    return false;
  }

  @Override
  public String makeID() {
    return null;
  }
}
