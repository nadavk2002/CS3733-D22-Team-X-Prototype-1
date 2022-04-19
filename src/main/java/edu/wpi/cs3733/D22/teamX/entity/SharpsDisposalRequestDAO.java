package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SharpsDisposalRequestDAO implements DAO<SharpsDisposalRequest> {
  private static List<SharpsDisposalRequest> sharpsDisposalRequests = new ArrayList<>();
  private static String csv = "SharpsDisposalRequest.csv";

  private SharpsDisposalRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      sharpsDisposalRequests.clear();
    }
  }

  private static class SingletonHelper {
    public static final SharpsDisposalRequestDAO sharpsDisposalRequestDAO =
        new SharpsDisposalRequestDAO();
  }

  public SharpsDisposalRequestDAO getDAO() {
    return SingletonHelper.sharpsDisposalRequestDAO;
  }

  /**
   * Returns a list of type T of all the records.
   *
   * @return a list of all the records in a table.
   */
  @Override
  public List<SharpsDisposalRequest> getAllRecords() {
    return sharpsDisposalRequests;
  }

  /**
   * Given a recordID, returns a specific record.
   *
   * @param recordID the recordID of the record to be returned.
   * @return the record corresponding to the recordID.
   */
  @Override
  public SharpsDisposalRequest getRecord(String recordID) {
    //iterate through list to find object with matching ID
      for(SharpsDisposalRequest SDSR : sharpsDisposalRequests){
          if(SDSR.getRequestID().equals(recordID)){
              return SDSR;
          }
      }
      throw new NoSuchElementException("Request with " + recordID + " requestID does not exist");
  }

  /**
   * Deletes a specified record object.
   *
   * @param recordObject the recordObject to be deleted.
   */
  @Override
  public void deleteRecord(SharpsDisposalRequest recordObject) {
      recordObject.getDestination().removeRequest(recordObject);
      // remove from list
      int index = 0; // create index for while loop
      int intitialSize = sharpsDisposalRequests.size(); // get size
      // go thru list
      while (index < sharpsDisposalRequests.size()) {
          if (sharpsDisposalRequests.get(index).equals(recordObject)) {
              sharpsDisposalRequests.remove(index); // remove item at index from list
              index--;
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
                  "DELETE FROM SharpsDisposalRequest WHERE requestID = '" + recordObject.getRequestID() + "'");
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  /**
   * Updates a specified record object. If the record doesn't exist, it is added.
   *
   * @param recordObject the recordObject to be updated or added.
   */
  @Override
  public void updateRecord(SharpsDisposalRequest recordObject) {}

  /**
   * Adds a record object to the database.
   *
   * @param recordObject the recordObject to be added.
   */
  @Override
  public void addRecord(SharpsDisposalRequest recordObject) {}

  /** Creates the table for the entity with fields defined by csv file/entity class */
  @Override
  public void createTable() {}

  /** Drops the table from the database */
  @Override
  public void dropTable() {}

  /**
   * Loads the data from the csv file into the table
   *
   * @return true if load is successful
   */
  @Override
  public boolean loadCSV() {
    return false;
  }

  /**
   * Saves the data from the database into a csv file
   *
   * @param dirPath directory path to store csv file at
   * @return true if save is successful
   */
  @Override
  public boolean saveCSV(String dirPath) {
    return false;
  }

  /**
   * Clears, then fills the DAO list with data from the sql table
   *
   * @return true if the DAO is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    return false;
  }

  /**
   * Returns the next alphanumeric ID string.
   *
   * @return the next alphanumeric ID string.
   */
  @Override
  public String makeID() {
    return null;
  }
}
