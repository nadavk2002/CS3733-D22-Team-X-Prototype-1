package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LaundryServiceRequestDAO implements DAO<LaundryServiceRequest> {
  private static List<LaundryServiceRequest> laundryServiceRequests = new ArrayList<>();
  private static String csv = "LaundryServiceRequests.csv";

  /** Creates a new LocationDAO object. */
  private LaundryServiceRequestDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final LaundryServiceRequestDAO laundryServiceRequestDAO =
        new LaundryServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static LaundryServiceRequestDAO getDAO() {
    return SingletonHelper.laundryServiceRequestDAO;
  }

  @Override
  public List<LaundryServiceRequest> getAllRecords() {
    return laundryServiceRequests;
  }

  @Override
  public LaundryServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (LaundryServiceRequest lsr : laundryServiceRequests) {
      // if matching IDs
      if (lsr.getRequestID().equals(recordID)) {
        return lsr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(LaundryServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = laundryServiceRequests.size();
    while (index < laundryServiceRequests.size()) {
      if (laundryServiceRequests.get(index).equals(recordObject)) {
        laundryServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM LaundryServiceRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(LaundryServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < laundryServiceRequests.size()) {
      if (laundryServiceRequests.get(index).equals(recordObject)) {
        laundryServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == laundryServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE LaundryServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssigneeID()
              + "', service = '"
              + recordObject.getService()
              + " WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(LaundryServiceRequest recordObject) {
    laundryServiceRequests.add(recordObject);
    try {
      Statement initialization = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO LaundryServiceRequest VALUES(");
      sql.append("'" + recordObject.getRequestID() + "'" + ", ");
      sql.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      sql.append("'" + recordObject.getStatus() + "'" + ", ");
      sql.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      sql.append("'" + recordObject.getService() + "'");
      sql.append(")");
      initialization.execute(sql.toString());
    } catch (SQLException e) {
      System.out.println("Laundry database could not be updated");
      return;
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE LaundryServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "service VARCHAR(140),"
              + "CONSTRAINT LYSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL, "
              + "CONSTRAINT LYSR_employee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID)"
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("LaundryServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE LaundryServiceRequest");
    } catch (SQLException e) {
      System.out.println("LaundryServiceRequest not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream stream = DatabaseCreator.class.getResourceAsStream(csv);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(stream));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          LaundryServiceRequest node =
              new LaundryServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  currLine[4]);
          laundryServiceRequests.add(node);
          node.getDestination().addRequest(node);
        } else {
          System.out.println("LaundryServiceRequest CSV file formatted improperly");
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

    for (int i = 0; i < laundryServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO LaundryServiceRequest VALUES(");
        sql.append("'" + laundryServiceRequests.get(i).getRequestID() + "'" + ", ");
        sql.append("'" + laundryServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        sql.append("'" + laundryServiceRequests.get(i).getStatus() + "'" + ", ");
        sql.append("'" + laundryServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        sql.append("'" + laundryServiceRequests.get(i).getService() + "'");
        sql.append(")");
        initialization.execute(sql.toString());
      } catch (SQLException e) {
        System.out.println("Input for LaundryServiceRequest " + i + " failed");
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
      csvFile.write("requestID,destination,status,assignee,service");
      for (int i = 0; i < laundryServiceRequests.size(); i++) {
        csvFile.write("\n" + laundryServiceRequests.get(i).getRequestID() + ",");
        if (laundryServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(laundryServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (laundryServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(laundryServiceRequests.get(i).getStatus() + ",");
        }
        if (laundryServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(laundryServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (laundryServiceRequests.get(i).getService() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(laundryServiceRequests.get(i).getService());
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
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("LYSR%04d", nextIDFinalNum);
  }
}
