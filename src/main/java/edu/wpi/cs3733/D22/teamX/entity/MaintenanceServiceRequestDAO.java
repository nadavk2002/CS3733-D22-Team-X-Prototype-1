package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MaintenanceServiceRequestDAO implements DAO<MaintenanceServiceRequest> {
  private static List<MaintenanceServiceRequest> maintenanceServiceRequests = new ArrayList<>();
  private static String csv = "MaintenanceServiceRequests.csv";

  /** Creates a new MaintenanceServiceRequestDAO object. */
  private MaintenanceServiceRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      maintenanceServiceRequests.clear();
    }
  }

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final MaintenanceServiceRequestDAO maintenanceServiceRequestDAO =
        new MaintenanceServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static MaintenanceServiceRequestDAO getDAO() {
    return SingletonHelper.maintenanceServiceRequestDAO;
  }

  @Override
  public List<MaintenanceServiceRequest> getAllRecords() {
    return maintenanceServiceRequests;
  }

  @Override
  public MaintenanceServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MaintenanceServiceRequest msr : maintenanceServiceRequests) {
      // if matching IDs
      if (msr.getRequestID().equals(recordID)) {
        return msr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MaintenanceServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = maintenanceServiceRequests.size();
    while (index < maintenanceServiceRequests.size()) {
      if (maintenanceServiceRequests.get(index).equals(recordObject)) {
        maintenanceServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM MaintenanceServiceRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MaintenanceServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < maintenanceServiceRequests.size()) {
      if (maintenanceServiceRequests.get(index).equals(recordObject)) {
        maintenanceServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == maintenanceServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MaintenanceServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssigneeID()
              + "', CreationTime = "
              + recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC)
              + ", PROCTime = "
              + recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC)
              + ", DONETime = "
              + recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC)
              + ", description = '"
              + recordObject.getDescription()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(MaintenanceServiceRequest recordObject) {
    maintenanceServiceRequests.add(recordObject);

    try {
      Statement statement = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO MaintenanceServiceRequest VALUES(");
      sql.append("'" + recordObject.getRequestID() + "'" + ", ");
      sql.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      sql.append("'" + recordObject.getStatus() + "'" + ", ");
      sql.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      sql.append(recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append(recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append(recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append("'" + recordObject.getDescription() + "'");
      sql.append(")");
      statement.execute(sql.toString());
    } catch (SQLException e) {
      System.out.println("MaintenanceServiceRequest database could not be updated");
      return;
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute(
          "CREATE TABLE MaintenanceServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "CreationTime BIGINT,"
              + "PROCTime BIGINT,"
              + "DONETime BIGINT,"
              + "description VARCHAR(140),"
              + "CONSTRAINT MSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MSR_assignee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID)"
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("MaintenanceServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE MaintenanceServiceRequest");
    } catch (SQLException e) {
      System.out.println("MaintenanceServiceRequest not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream stream = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(stream));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          MaintenanceServiceRequest node =
              new MaintenanceServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[4]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[5]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[6]), 0, ZoneOffset.UTC),
                  currLine[7]);
          maintenanceServiceRequests.add(node);
          node.getDestination().addRequest(node);
        } else {
          System.out.println("MaintenanceServiceRequest CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    for (int i = 0; i < maintenanceServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO MaintenanceServiceRequest VALUES(");
        sql.append("'" + maintenanceServiceRequests.get(i).getRequestID() + "'" + ", ");
        sql.append(
            "'" + maintenanceServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        sql.append("'" + maintenanceServiceRequests.get(i).getStatus() + "'" + ", ");
        sql.append("'" + maintenanceServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        sql.append(
            maintenanceServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                + ",");
        sql.append(
            maintenanceServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
        sql.append(
            maintenanceServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
        sql.append("'" + maintenanceServiceRequests.get(i).getDescription() + "'");
        sql.append(")");
        initialization.execute(sql.toString());
      } catch (SQLException e) {
        System.out.println("Input for MaintenanceServiceRequest " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
      csvFile.write("requestID,destination,status,assignee,description");
      for (int i = 0; i < maintenanceServiceRequests.size(); i++) {
        csvFile.write("\n" + maintenanceServiceRequests.get(i).getRequestID() + ",");
        if (maintenanceServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(maintenanceServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (maintenanceServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(maintenanceServiceRequests.get(i).getStatus() + ",");
        }
        if (maintenanceServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(maintenanceServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (maintenanceServiceRequests.get(i).getCreationTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              maintenanceServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                  + ",");
        }
        if (maintenanceServiceRequests.get(i).getPROCTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              maintenanceServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
        }
        if (maintenanceServiceRequests.get(i).getDONETime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              maintenanceServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
        }
        if (maintenanceServiceRequests.get(i).getDescription() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(maintenanceServiceRequests.get(i).getDescription());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.print("An error occurred when trying to write to the CSV file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean fillFromTable() {
    try {
      maintenanceServiceRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM MaintenanceServiceRequest");
      while (results.next()) {
        MaintenanceServiceRequest toAdd = new MaintenanceServiceRequest();
        toAdd.setRequestID(results.getString("requestID"));
        toAdd.setDestination(LocationDAO.getDAO().getRecord(results.getString("destination")));
        toAdd.setStatus(results.getString("status"));
        toAdd.setAssignee(EmployeeDAO.getDAO().getRecord(results.getString("assignee")));
        toAdd.setCreationTime(
            LocalDateTime.ofEpochSecond(
                Long.parseLong(results.getString("CreationTime")), 0, ZoneOffset.UTC));
        toAdd.setPROCTime(
            LocalDateTime.ofEpochSecond(
                Long.parseLong(results.getString("PROCTime")), 0, ZoneOffset.UTC));
        toAdd.setDONETime(
            LocalDateTime.ofEpochSecond(
                Long.parseLong(results.getString("DONETime")), 0, ZoneOffset.UTC));
        toAdd.setDescription(results.getString("description"));
        maintenanceServiceRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("MaintenanceServiceRequestDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("MASR%04d", nextIDFinalNum);
  }
}
