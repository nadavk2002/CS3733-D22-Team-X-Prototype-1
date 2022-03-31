package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentServiceRequestDAOImpl implements EquipmentServiceRequestDAO {
  List<EquipmentServiceRequest> medicalEquipmentServiceRequests;
  Connection connection; // store connection info

  /** constructor */
  public EquipmentServiceRequestDAOImpl() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    medicalEquipmentServiceRequests = new ArrayList<EquipmentServiceRequest>();
    try {
      // To retrieve locations with specified destinations
      LocationDAO locDestination = new LocationDAOImpl();
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

  /**
   * gets the list of all the medical equipment service requests
   *
   * @return a list of all the medical equipment service requests
   */
  @Override
  public List<EquipmentServiceRequest> getAllEquipmentServiceRequests() {
    return medicalEquipmentServiceRequests;
  }

  /**
   * gets induviduial equipment service requests
   *
   * @param requestID requestID of the induvidual service request
   * @return a medical equipment service request with a matching requestID
   */
  @Override
  public EquipmentServiceRequest getEquipmentServiceRequest(String requestID)
      throws NoSuchElementException {
    // iterate through list to find element with matching requestID
    for (EquipmentServiceRequest esr : medicalEquipmentServiceRequests) {
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
   * @param equipmentServiceRequest medical equipment service request to be updated
   */
  @Override
  public void deleteEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest)
      throws NoSuchElementException {
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicalEquipmentServiceRequests.size();
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(equipmentServiceRequest)) {
        medicalEquipmentServiceRequests.remove(index); // removes object from list
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
              + equipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * updates DAO and database element
   *
   * @param equipmentServiceRequest equipment service request to be updated
   */
  @Override
  public void updateEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest)
      throws NoSuchElementException {
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(equipmentServiceRequest)) {
        medicalEquipmentServiceRequests.set(index, equipmentServiceRequest);
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
              + equipmentServiceRequest.getDestination().getNodeID()
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

  @Override
  public void addEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest) {
    medicalEquipmentServiceRequests.add(equipmentServiceRequest);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medEquipReq = new StringBuilder();
      medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
      medEquipReq.append("'" + equipmentServiceRequest.getRequestID() + "'" + ", ");
      medEquipReq.append("'" + equipmentServiceRequest.getDestination().getNodeID() + "'" + ", ");
      medEquipReq.append("'" + equipmentServiceRequest.getStatus() + "'" + ", ");
      medEquipReq.append("'" + equipmentServiceRequest.getEquipmentType() + "'" + ", ");
      medEquipReq.append(equipmentServiceRequest.getQuantity());
      medEquipReq.append(")");
      initialization.execute(medEquipReq.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
  }
}
