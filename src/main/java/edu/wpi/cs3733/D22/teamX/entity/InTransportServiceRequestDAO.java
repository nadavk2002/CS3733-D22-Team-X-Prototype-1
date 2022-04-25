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

public class InTransportServiceRequestDAO implements DAO<InTransportServiceRequest> {
  private static List<InTransportServiceRequest> inTransportServiceRequests =
      new ArrayList<InTransportServiceRequest>();

  private static String csv = "InTransportServiceRequest.csv";

  /** Creates a new InTransportServiceRequest object. */
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
    throw new NoSuchElementException("InTransportServiceRequest does not exist.");
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
            "DELETE FROM InTransportServiceRequest WHERE requestID = '"
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
  public void updateRecord(InTransportServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    // iterate through the list of locations to find the location passed in and update it in
    // locations
    int index = 0;
    while (index < inTransportServiceRequests.size()) {
      if (inTransportServiceRequests.get(index).equals(recordObject)) {
        inTransportServiceRequests.set(index, recordObject);
        break;
      }
      index++;
    }

    // if the location is found, update the Location table
    if (index < inTransportServiceRequests.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // update sql object
        statement.executeUpdate(
            "UPDATE InTransportServiceRequest SET requestID = '"
                + recordObject.getRequestID()
                + "', patientName = '"
                + recordObject.getPatientName()
                + "', transportFrom = '"
                + recordObject.getTransportFrom()
                + "', assignee = '"
                + recordObject.getAssigneeID()
                    + "', CreationTime = "
                    + recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC)
                    + ", PROCTime = "
                    + recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC)
                    + ", DONETime = "
                    + recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC)
                + ", status = '"
                + recordObject.getStatus()
                + "', destination = '"
                + recordObject.getDestination().getNodeID()
                + "' "
                + "WHERE requestID = '"
                + recordObject.getRequestID()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("InTransportServiceRequest does not exist");
      throw new NoSuchElementException("request does not exist (in update)");
    }
  }

  @Override
  public void addRecord(InTransportServiceRequest recordObject) {
    inTransportServiceRequests.add(recordObject);
    try {
      Statement initialization = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO InTransportServiceRequest VALUES(");
      sql.append("'" + recordObject.getRequestID() + "'" + ", ");
      sql.append("'" + recordObject.getPatientName() + "'" + ", ");
      sql.append("'" + recordObject.getTransportFrom() + "'" + ", ");
      sql.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      sql.append(recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append(recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append(recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
      sql.append("'" + recordObject.getStatus() + "'" + ", ");
      sql.append("'" + recordObject.getDestination().getNodeID() + "'");
      sql.append(")");
      initialization.execute(sql.toString());
    } catch (SQLException e) {
      System.out.println("InTransportServiceRequest database could not be updated");
      return;
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE InTransportServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "patientName VARCHAR(15),"
              + "transportFrom VARCHAR(30),"
              + "assignee CHAR(8),"
                  + "CreationTime BIGINT,"
                  + "PROCTime BIGINT,"
                  + "DONETime BIGINT,"
              + "status CHAR(4),"
              + "destination CHAR(10),"
              + "CONSTRAINT INSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT INSR_assignee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("InTransportServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropLabServiceRequest = connection.createStatement();
      dropLabServiceRequest.execute("DROP TABLE InTransportServiceRequest");
    } catch (SQLException e) {
      System.out.println("InTransportServiceRequest not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream stream = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader insrCSVReader = new BufferedReader(new InputStreamReader(stream));
      insrCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = insrCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 9) {
          InTransportServiceRequest node =
              new InTransportServiceRequest(
                  currLine[0],
                  currLine[1],
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                      LocalDateTime.ofEpochSecond(Long.parseLong(currLine[4]), 0, ZoneOffset.UTC),
                      LocalDateTime.ofEpochSecond(Long.parseLong(currLine[5]), 0, ZoneOffset.UTC),
                      LocalDateTime.ofEpochSecond(Long.parseLong(currLine[6]), 0, ZoneOffset.UTC),
                  currLine[7],
                  locDestination.getRecord(currLine[8]));
          inTransportServiceRequests.add(node);
          node.getDestination().addRequest(node);
        } else {
          System.out.println("InTransportServiceRequest CSV file formatted improperly");
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

    for (int i = 0; i < inTransportServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO InTransportServiceRequest VALUES(");
        sql.append("'" + inTransportServiceRequests.get(i).getRequestID() + "'" + ", ");
        sql.append("'" + inTransportServiceRequests.get(i).getPatientName() + "'" + ", ");
        sql.append("'" + inTransportServiceRequests.get(i).getTransportFrom() + "'" + ", ");
        sql.append("'" + inTransportServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        sql.append(
                inTransportServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                        + ",");
        sql.append(
                inTransportServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC)
                        + ",");
        sql.append(
                inTransportServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC)
                        + ",");
        sql.append("'" + inTransportServiceRequests.get(i).getStatus() + "'" + ", ");
        sql.append("'" + inTransportServiceRequests.get(i).getDestination().getNodeID() + "'");
        sql.append(")");
        initialization.execute(sql.toString());
      } catch (SQLException e) {
        System.out.println("Input for InTransportServiceRequest " + i + " failed");
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
      csvFile.write("requestID, patientName, transportFrom, assignee,CreationTime,PROCTime,DONETime, status, destination");
      for (int i = 0; i < inTransportServiceRequests.size(); i++) {
        csvFile.write("\n" + inTransportServiceRequests.get(i).getRequestID() + ",");
        if (inTransportServiceRequests.get(i).getPatientName() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(inTransportServiceRequests.get(i).getPatientName() + ",");
        }
        if (inTransportServiceRequests.get(i).getTransportFrom() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(inTransportServiceRequests.get(i).getTransportFrom() + ",");
        }
        if (inTransportServiceRequests.get(i).getAssigneeID() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(inTransportServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (inTransportServiceRequests.get(i).getCreationTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
                  inTransportServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                          + ",");
        }
        if (inTransportServiceRequests.get(i).getPROCTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
                  inTransportServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC)
                          + ",");
        }
        if (inTransportServiceRequests.get(i).getDONETime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
                  inTransportServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC)
                          + ",");
        }
        if (inTransportServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(inTransportServiceRequests.get(i).getStatus() + ",");
        }
        if (inTransportServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(inTransportServiceRequests.get(i).getDestination().getNodeID());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occurred when updating InTransportServiceRequest csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean fillFromTable() {
    try {
      inTransportServiceRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM InTransportServiceRequest");
      while (results.next()) {
        InTransportServiceRequest toAdd = new InTransportServiceRequest();
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
        toAdd.setTransportFrom(results.getString("transportFrom"));
        toAdd.setPatientName(results.getString("patientName"));
        inTransportServiceRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("InTransportServiceRequest could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("ITSR%04d", nextIDFinalNum);
  }
}
