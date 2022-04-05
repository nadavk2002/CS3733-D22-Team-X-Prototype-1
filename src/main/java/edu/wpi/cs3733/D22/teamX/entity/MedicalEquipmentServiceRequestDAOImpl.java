package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MedicalEquipmentServiceRequestDAOImpl implements MedicalEquipmentServiceRequestDAO {
  List<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequests;
  Connection connection; // store connection info

  public MedicalEquipmentServiceRequestDAOImpl() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    medicalEquipmentServiceRequests = new ArrayList<MedicalEquipmentServiceRequest>();
    try {
      // To retrieve locations with specified destinations
      LocationDAO locDestination = new LocationDAOImpl();
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM MedicalEquipmentServiceRequest");
      // go through the results
      while (resultSet.next()) {
        MedicalEquipmentServiceRequest esr = new MedicalEquipmentServiceRequest();
        esr.setRequestID(resultSet.getString("requestID"));
        esr.setDestination(locDestination.getLocation(resultSet.getString("destination")));
        esr.setStatus(resultSet.getString("status"));
        esr.setAssignee(resultSet.getString("assignee"));
        esr.setEquipmentType(resultSet.getString("equipmentType"));
        esr.setQuantity(Integer.parseInt(resultSet.getString("quantity")));

        medicalEquipmentServiceRequests.add(esr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.connection = connection;
  }

  /**
   * gets the list of all the medical equipment service requests
   *
   * @return a list of all the medical equipment service requests
   */
  @Override
  public List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests() {
    return medicalEquipmentServiceRequests;
  }

  /**
   * gets induviduial equipment service requests
   *
   * @param requestID requestID of the induvidual service request
   * @return a medical equipment service request with a matching requestID
   */
  @Override
  public MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID)
      throws NoSuchElementException {
    // iterate through list to find element with matching requestID
    for (MedicalEquipmentServiceRequest esr : medicalEquipmentServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(requestID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  /**
   * deletes object from DAO and database.
   *
   * @param medicalEquipmentServiceRequest medical equipment service request to be updated
   */
  @Override
  public void deleteMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) throws NoSuchElementException {
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicalEquipmentServiceRequests.size();
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(medicalEquipmentServiceRequest)) {
        medicalEquipmentServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM MedicalEquipmentServiceRequest WHERE requestID = '"
              + medicalEquipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * updates DAO and database element
   *
   * @param medicalEquipmentServiceRequest equipment service request to be updated
   */
  @Override
  public void updateMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) throws NoSuchElementException {
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(medicalEquipmentServiceRequest)) {
        medicalEquipmentServiceRequests.set(index, medicalEquipmentServiceRequest);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicalEquipmentServiceRequests.size()) {
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
              + medicalEquipmentServiceRequest.getDestination().getNodeID()
              + "', status = '"
              + medicalEquipmentServiceRequest.getStatus()
              + "', assignee = '"
              + medicalEquipmentServiceRequest.getAssignee()
              + "', equipmentType = '"
              + medicalEquipmentServiceRequest.getEquipmentType()
              + "', quantity = "
              + medicalEquipmentServiceRequest.getQuantity()
              + " WHERE requestID = '"
              + medicalEquipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) {
    medicalEquipmentServiceRequests.add(medicalEquipmentServiceRequest);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medEquipReq = new StringBuilder();
      medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getRequestID() + "'" + ", ");
      medEquipReq.append(
          "'" + medicalEquipmentServiceRequest.getDestination().getNodeID() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getStatus() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getAssignee() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getEquipmentType() + "'" + ", ");
      medEquipReq.append(medicalEquipmentServiceRequest.getQuantity());
      medEquipReq.append(")");
      initialization.execute(medEquipReq.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
  }
}
