package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class JanitorServiceRequestDAO implements DAO<JanitorServiceRequest> {
  private static List<JanitorServiceRequest> janitorServiceRequests = new ArrayList<>();
  private static String csv = "JanitorServiceRequests.csv";

  /** Creates a new LocationDAO object. */
  private JanitorServiceRequestDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final JanitorServiceRequestDAO janitorServiceRequestDAO =
            new JanitorServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static JanitorServiceRequestDAO getDAO() {
    return SingletonHelper.janitorServiceRequestDAO;
  }

  @Override
  public List<JanitorServiceRequest> getAllRecords() {
    return janitorServiceRequests;
  }

  @Override
  public JanitorServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (JanitorServiceRequest jsr : janitorServiceRequests) {
      // if matching IDs
      if (jsr.getRequestID().equals(recordID)) {
        return jsr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(JanitorServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = janitorServiceRequests.size();
    while (index < janitorServiceRequests.size()) {
      if (janitorServiceRequests.get(index).equals(recordObject)) {
        janitorServiceRequests.remove(index); // removes object from list
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
              "DELETE FROM JanitorServiceRequest WHERE requestID = '"
                      + recordObject.getRequestID()
                      + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(JanitorServiceRequest recordObject) {
    if (!recordObject
            .getDestination()
            .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < janitorServiceRequests.size()) {
      if (janitorServiceRequests.get(index).equals(recordObject)) {
        janitorServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == janitorServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
              "UPDATE JanitorServiceRequest SET"
                      + " destination = '"
                      + recordObject.getDestination().getNodeID()
                      + "', status = '"
                      + recordObject.getStatus()
                      + "', assignee = '"
                      + recordObject.getAssigneeID()
                      + "', description = '"
                      + recordObject.getDescription()
                      + " WHERE requestID = '"
                      + recordObject.getRequestID()
                      + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(JanitorServiceRequest recordObject) {
    janitorServiceRequests.add(recordObject);

    try {
      Statement statement = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO LangServiceRequest VALUES(");
      sql.append("'" + recordObject.getRequestID() + "'" + ", ");
      sql.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      sql.append("'" + recordObject.getStatus() + "'" + ", ");
      sql.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      sql.append("'" + recordObject.getDescription() + "'");
      sql.append(")");
      statement.execute(sql.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute(
              "CREATE TABLE JanitorServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
                      + "destination CHAR(10),"
                      + "status CHAR(4),"
                      + "assignee CHAR(8),"
                      + "description VARCHAR(140),"
                      + "CONSTRAINT JSR_dest_fk "
                      + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
                      + "ON DELETE SET NULL, "
                      + "CONSTRAINT JSR_assignee_fk "
                      + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID)"
                      + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("JanitorServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE JanitorServiceRequest");
    } catch (SQLException e) {
      System.out.println("JanitorServiceRequest not dropped");
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
          JanitorServiceRequest node =
                  new JanitorServiceRequest(
                          currLine[0],
                          locDestination.getRecord(currLine[1]),
                          currLine[2],
                          emplDAO.getRecord(currLine[3]),
                          currLine[4]);
          janitorServiceRequests.add(node);
          node.getDestination().addRequest(node);
        } else {
          System.out.println("JanitorServiceRequest CSV file formatted improperly");
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

    for (int i = 0; i < janitorServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO JanitorServiceRequest VALUES(");
        sql.append("'" + janitorServiceRequests.get(i).getRequestID() + "'" + ", ");
        sql.append("'" + janitorServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        sql.append("'" + janitorServiceRequests.get(i).getStatus() + "'" + ", ");
        sql.append("'" + janitorServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        sql.append("'" + janitorServiceRequests.get(i).getDescription() + "'");
        sql.append(")");
        initialization.execute(sql.toString());
      } catch (SQLException e) {
        System.out.println("Input for JanitorServiceRequest " + i + " failed");
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
      for (int i = 0; i < janitorServiceRequests.size(); i++) {
        csvFile.write("\n" + janitorServiceRequests.get(i).getRequestID() + ",");
        if (janitorServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(janitorServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (janitorServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(janitorServiceRequests.get(i).getStatus() + ",");
        }
        if (janitorServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(janitorServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (janitorServiceRequests.get(i).getDescription() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(janitorServiceRequests.get(i).getDescription());
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
    return String.format("JASR%04d", nextIDFinalNum);
  }
}