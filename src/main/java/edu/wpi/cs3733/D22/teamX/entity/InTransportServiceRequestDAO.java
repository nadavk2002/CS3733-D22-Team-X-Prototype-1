package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class InTransportServiceRequestDAO implements DAO<InTransportServiceRequest> {
  private static List<InTransportServiceRequest> inTransportServiceRequests =
      new ArrayList<InTransportServiceRequest>();

  /** Creates a new GiftDeliveryRequestDAO object. */
  private InTransportServiceRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      inTransportServiceRequests.clear();
    }
  }

  /** Singleton helper class. */
  private static class SingletonHelper {
    private static final InTransportServiceRequestDAO inTransportServiceRequestDAO =
        new InTransportServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static InTransportServiceRequestDAO getDAO() {
    return SingletonHelper.inTransportServiceRequestDAO;
  }

  @Override
  public List<InTransportServiceRequest> getAllRecords() {
    return inTransportServiceRequests;
  }

  @Override
  public InTransportServiceRequest getRecord(String recordID) {
    for (InTransportServiceRequest request : inTransportServiceRequests) {
      if (request.getRequestID().equals(recordID)) return request;
    }
    throw new NoSuchElementException("GiftDeliveryRequest does not exist.");
  }

  @Override
  public void deleteRecord(InTransportServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    int index = 0;
    // find index
    while (index < inTransportServiceRequests.size()) {
      if (inTransportServiceRequests.get(index).equals(recordObject)) {
        inTransportServiceRequests.remove(index);
        index--;
        break;
      }
      index++;
    }

    // if object exists, delete it from the table
    if (index < inTransportServiceRequests.size()) {
      try {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
            "DELETE FROM InTransportDeliveryRequest WHERE requestID = '"
                + recordObject.getRequestID()
                + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      throw new NoSuchElementException("Request does not exist. Deletion cannot be performed.");
    }
  }

  @Override
  public void updateRecord(InTransportServiceRequest recordObject) {}

  @Override
  public void addRecord(InTransportServiceRequest recordObject) {}

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
  public boolean fillFromTable() {
    return false;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("ITSR%04d", nextIDFinalNum);
  }
}
