package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LabServiceRequestDAOImpl implements LabServiceRequestDAO {
  List<LabServiceRequest> labServiceRequests;
  Connection connection;

  public LabServiceRequestDAOImpl() {
    this.connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    labServiceRequests = new ArrayList<LabServiceRequest>();
    try {
      // To retrieve locations with specified destinations
      LocationDAO locDestination = new LocationDAOImpl();
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM LabServiceRequest");
      // go through results
      while (resultSet.next()) {
        LabServiceRequest lsr = new LabServiceRequest();
        lsr.setRequestID(resultSet.getString("requestID"));
        lsr.setDestination(locDestination.getLocation(resultSet.getString("Destination")));
        lsr.setStatus(resultSet.getString("status"));
        lsr.setAssignee(resultSet.getString("assignee"));
        lsr.setService(resultSet.getString("service"));
        lsr.setPatientFor("patientFor");
        // add lsr to the list
        labServiceRequests.add(lsr);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * gets the list of all the Lab Exam Service Requests
   *
   * @return a list of all the Lab Exam Service Requests
   */
  @Override
  public List<LabServiceRequest> getAllLabServiceRequests() {
    return labServiceRequests;
  }

  /**
   * gets individual Lab Exam Service Request
   *
   * @param requestID requestID of the individual Lab Exam Service Request
   * @return a Lab Exam Service Request with a matching requestID
   */
  @Override
  public LabServiceRequest getLabServiceRequest(String requestID) {
    // iterate through list to find object with matching ID
    for (LabServiceRequest lsr : labServiceRequests) {
      if (lsr.getRequestID().equals(requestID)) {
        return lsr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  /**
   * deletes object from DAO and database.
   *
   * @param labServiceRequest Lab Exam Service Request to be removed
   */
  @Override
  public void deleteLabServiceRequest(LabServiceRequest labServiceRequest) {
    // remove from list
    int index = 0; // create index for while loop
    int intitialSize = labServiceRequests.size(); // get size
    // go thru list
    while (index < labServiceRequests.size()) {
      if (labServiceRequests.get(index).equals(labServiceRequest)) {
        labServiceRequests.remove(index); // remove item at index from list
        break; // exit loop
      }
      index++;
    }
    if (index == intitialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // remove from Database
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // remove location from DB table
      statement.executeUpdate(
          "DELETE FROM LabServiceRequest WHERE requestID = '"
              + labServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * updates object from DAO and database.
   *
   * @param labServiceRequest Lab Exam Service Request to be updated
   */
  @Override
  public void updateLabServiceRequest(LabServiceRequest labServiceRequest) {
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = labServiceRequests.size(); // get size
    // go thru list
    while (index < labServiceRequests.size()) {
      if (labServiceRequests.get(index).equals(labServiceRequest)) {
        labServiceRequests.set(index, labServiceRequest); // update lsr at index position
        break; // exit loop
      }
      index++;
    }
    if (index == intitialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // update db table.
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE LabServiceRequest SET"
              + " destination = '"
              + labServiceRequest.getDestination().getNodeID()
              + "', status = '"
              + labServiceRequest.getStatus()
              + "', assignee = '"
              + labServiceRequest.getAssignee()
              + "', service = '"
              + labServiceRequest.getService()
              + "', patientFor = '"
              + labServiceRequest.getPatientFor()
              + "' WHERE requestID = '"
              + labServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * adds object from DAO and database.
   *
   * @param labServiceRequest Lab Exam Service Request to be added
   */
  @Override
  public void addLabServiceRequest(LabServiceRequest labServiceRequest) {
    // list
    labServiceRequests.add(labServiceRequest);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder lsr = new StringBuilder();
      lsr.append("INSERT INTO LabServiceRequest VALUES (");
      lsr.append("'" + labServiceRequest.getRequestID() + "', ");
      lsr.append("'" + labServiceRequest.getDestination().getNodeID() + "', ");
      lsr.append("'" + labServiceRequest.getStatus() + "', ");
      lsr.append("'" + labServiceRequest.getAssignee() + "', ");
      lsr.append("'" + labServiceRequest.getService() + "', ");
      lsr.append("'" + labServiceRequest.getPatientFor() + "'");
      lsr.append(")");
      initialization.execute(lsr.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Database could not be updated");
    }
  }
}
