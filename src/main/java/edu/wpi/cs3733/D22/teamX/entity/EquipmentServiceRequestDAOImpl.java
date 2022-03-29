package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentServiceRequestDAOImpl implements EquipmentSeviceRequestDAO {
  List<EquipmentServiceRequest> medicalEquipmentServiceRequests;
  Connection connection; // store connection info

  public EquipmentServiceRequestDAOImpl(Connection connection) {
    medicalEquipmentServiceRequests = new ArrayList<>();

    try {
      LocationDAO locDestination = new LocationDAOImpl(connection);
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM MedicalEquipmentServiceRequest");
      // go through the results
      while (resultSet.next()) {
        EquipmentServiceRequest esr = new EquipmentServiceRequest();
        esr.setRequestID(resultSet.getString("requestID"));
        esr.setDestination(locDestination.getLocation(resultSet.getString("destination")));
        esr.setStatus(resultSet.getString("status"));
        esr.setEquipmentType(resultSet.getString("equipmentType"));
        esr.setQuantity(Integer.parseInt(resultSet.getString("quantity")));

        medicalEquipmentServiceRequests.add(esr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.connection = connection;
  }

  @Override
  public List<EquipmentServiceRequest> getAllEquipmentServiceRequests() {
    return medicalEquipmentServiceRequests;
  }

  @Override
  public EquipmentServiceRequest getEquipmentServiceRequest(String requestID) {
    // iterate through list to find element with matching requestID
    for (EquipmentServiceRequest esr : medicalEquipmentServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(requestID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest) {
    // remove from list
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests
          .get(index)
          .getRequestID()
          .equals(equipmentServiceRequest.getRequestID())) {
        medicalEquipmentServiceRequests.remove(index); // removes object from list
        break; // exit
      }
      index++; // increment if not found
    }

    // remove from database
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // remove location from DB table
      statement.executeUpdate(
          "DELETE FROM MedicalEquipmentServiceRequest WHERE requestID = '"
              + equipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest) {
    // update in local list
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests
          .get(index)
          .getRequestID()
          .equals(equipmentServiceRequest.getRequestID())) {
        medicalEquipmentServiceRequests.set(
            index, equipmentServiceRequest); // removes object from list
        break; // exit
      }
      index++; // increment if not found yet
    }

    // update DB table

    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MedicalEquipmentServiceRequest SET"
              + " destination = '"
              + equipmentServiceRequest.getDestination()
              + "', status = '"
              + equipmentServiceRequest.getStatus()
              + "', equipmentType = '"
              + equipmentServiceRequest.getEquipmentType()
              + "', quantity = "
              + equipmentServiceRequest.getQuantity()
              + " WHERE requestID = '"
              + equipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
