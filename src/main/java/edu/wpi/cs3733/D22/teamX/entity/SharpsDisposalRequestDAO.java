package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.ResultSet;
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
    // iterate through list to find object with matching ID
    for (SharpsDisposalRequest SDSR : sharpsDisposalRequests) {
      if (SDSR.getRequestID().equals(recordID)) {
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
          "DELETE FROM SharpsDisposalRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
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
  public void updateRecord(SharpsDisposalRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = sharpsDisposalRequests.size(); // get size
    // go thru list
    while (index < sharpsDisposalRequests.size()) {
      if (sharpsDisposalRequests.get(index).equals(recordObject)) {
        sharpsDisposalRequests.set(index, recordObject); // update lsr at index position
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
          "UPDATE SharpsDisposalRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssigneeID()
              + "', type = '"
              + recordObject.getType()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a record object to the database.
   *
   * @param recordObject the recordObject to be added.
   */
  @Override
  public void addRecord(SharpsDisposalRequest recordObject) {
    // list
    sharpsDisposalRequests.add(recordObject);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder SDSR = new StringBuilder();
      SDSR.append("INSERT INTO SharpsDisposalRequest VALUES (");
      SDSR.append("'" + recordObject.getRequestID() + "', ");
      SDSR.append("'" + recordObject.getDestination().getNodeID() + "', ");
      SDSR.append("'" + recordObject.getStatus() + "', ");
      SDSR.append("'" + recordObject.getAssigneeID() + "', ");
      SDSR.append("'" + recordObject.getType() + "'");
      SDSR.append(")");
      initialization.execute(SDSR.toString());
    } catch (SQLException e) {
      System.out.println("SDSR could not be added");
      e.printStackTrace();
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  /** Creates the table for the entity with fields defined by csv file/entity class */
  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE SharpsDisposalRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "type VARCHAR(25),"
              + "CONSTRAINT PMSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT PMSR_assignee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("SharpsDisposalRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Drops the table from the database */
  @Override
  public void dropTable() {
    try {
      Statement dropMealServiceRequest = connection.createStatement();
      dropMealServiceRequest.execute("DROP TABLE SharpsDisposalRequest");
    } catch (SQLException e) {
      System.out.println("SharpsDisposalRequest not dropped");
      e.printStackTrace();
    }
  }

  /**
   * Loads the data from the csv file into the table
   *
   * @return true if load is successful
   */
  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream SDSRCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader SDSRCSVReader = new BufferedReader(new InputStreamReader(SDSRCSV));
      SDSRCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = SDSRCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          SharpsDisposalRequest SDSRnode =
              new SharpsDisposalRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  currLine[4]);
          sharpsDisposalRequests.add(SDSRnode);
          SDSRnode.getDestination().addRequest(SDSRnode);
        } else {
          System.out.println("SharpsDisposalRequest CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("SharpsDisposalRequests.CSV not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    // Insert locations from MealServiceReqCSV into db table
    for (int i = 0; i < sharpsDisposalRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder MealServiceRequest = new StringBuilder();
        MealServiceRequest.append("INSERT INTO SharpsDisposalRequest VALUES(");
        MealServiceRequest.append("'" + sharpsDisposalRequests.get(i).getRequestID() + "'" + ", ");
        MealServiceRequest.append(
            "'" + sharpsDisposalRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        MealServiceRequest.append("'" + sharpsDisposalRequests.get(i).getStatus() + "'" + ", ");
        MealServiceRequest.append("'" + sharpsDisposalRequests.get(i).getAssigneeID() + "'" + ", ");
        MealServiceRequest.append("'" + sharpsDisposalRequests.get(i).getType() + "'");
        MealServiceRequest.append(")");
        initialization.execute(MealServiceRequest.toString());
      } catch (SQLException e) {
        System.out.println("Input for SharpsDisposalRequest " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * Saves the data from the database into a csv file
   *
   * @param dirPath directory path to store csv file at
   * @return true if save is successful
   */
  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
      csvFile.write("requestID,destination,status,assignee,type");
      for (int i = 0; i < sharpsDisposalRequests.size(); i++) {
        csvFile.write("\n" + sharpsDisposalRequests.get(i).getRequestID() + ",");
        if (sharpsDisposalRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(sharpsDisposalRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (sharpsDisposalRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(sharpsDisposalRequests.get(i).getStatus() + ",");
        }
        if (sharpsDisposalRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(sharpsDisposalRequests.get(i).getAssigneeID() + ",");
        }
        if (sharpsDisposalRequests.get(i).getType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(sharpsDisposalRequests.get(i).getType());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.print(
          "An error occurred when trying to write to the SharpsDisposalRequest CSV file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Clears, then fills the DAO list with data from the sql table
   *
   * @return true if the DAO is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      sharpsDisposalRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM SharpsDisposalRequest");
      while (results.next()) {
        SharpsDisposalRequest toAdd = new SharpsDisposalRequest();
        toAdd.setRequestID(results.getString("requestID"));
        toAdd.setDestination(LocationDAO.getDAO().getRecord(results.getString("destination")));
        toAdd.setStatus(results.getString("status"));
        toAdd.setAssignee(EmployeeDAO.getDAO().getRecord(results.getString("assignee")));
        toAdd.setType(results.getString("type"));
        sharpsDisposalRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("SharpsDisposalRequest could not be filled from the sql table");
      return false;
    }
    return true;
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
