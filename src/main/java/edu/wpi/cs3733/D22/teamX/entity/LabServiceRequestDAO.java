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

public class LabServiceRequestDAO implements DAO<LabServiceRequest> {
  private static List<LabServiceRequest> labServiceRequests = new ArrayList<LabServiceRequest>();
  private static String csv = "LabServiceRequests.csv";

  /** Creates a new LabServiceRequestDAO object. */
  private LabServiceRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      labServiceRequests.clear();
    }
  }

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final LabServiceRequestDAO labServiceRequestDAO = new LabServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static LabServiceRequestDAO getDAO() {
    return SingletonHelper.labServiceRequestDAO;
  }

  @Override
  public List<LabServiceRequest> getAllRecords() {
    return labServiceRequests;
  }

  @Override
  public LabServiceRequest getRecord(String recordID) {
    // iterate through list to find object with matching ID
    for (LabServiceRequest lsr : labServiceRequests) {
      if (lsr.getRequestID().equals(recordID)) {
        return lsr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(LabServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index for while loop
    int intitialSize = labServiceRequests.size(); // get size
    // go thru list
    while (index < labServiceRequests.size()) {
      if (labServiceRequests.get(index).equals(recordObject)) {
        labServiceRequests.remove(index); // remove item at index from list
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
          "DELETE FROM LabServiceRequest WHERE requestID = '" + recordObject.getRequestID() + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(LabServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = labServiceRequests.size(); // get size
    // go thru list
    while (index < labServiceRequests.size()) {
      if (labServiceRequests.get(index).equals(recordObject)) {
        labServiceRequests.set(index, recordObject); // update lsr at index position
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
              + "', service = '"
              + recordObject.getService()
              + "', patientFor = '"
              + recordObject.getPatientFor()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(LabServiceRequest recordObject) {
    // list
    labServiceRequests.add(recordObject);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder lsr = new StringBuilder();
      lsr.append("INSERT INTO LabServiceRequest VALUES (");
      lsr.append("'" + recordObject.getRequestID() + "'" + ", ");
      lsr.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      lsr.append("'" + recordObject.getStatus() + "'" + ", ");
      lsr.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      lsr.append(recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
      lsr.append(recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
      lsr.append(recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
      lsr.append("'" + recordObject.getService() + "'" + ", ");
      lsr.append("'" + recordObject.getPatientFor() + "'");
      lsr.append(")");
      initialization.execute(lsr.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("LabServiceRequest database could not be updated");
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE LabServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "CreationTime BIGINT,"
              + "PROCTime BIGINT,"
              + "DONETime BIGINT,"
              + "service VARCHAR(15),"
              + "patientFor VARCHAR(15),"
              + "CONSTRAINT LBSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT LBSR_assignee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("LabServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropLabServiceRequest = connection.createStatement();
      dropLabServiceRequest.execute("DROP TABLE LabServiceRequest");
    } catch (SQLException e) {
      System.out.println("LabServiceRequest not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplAssignee = EmployeeDAO.getDAO();
      InputStream labCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader labCSVReader = new BufferedReader(new InputStreamReader(labCSV));
      labCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = labCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 9) {
          LabServiceRequest labNode =
              new LabServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplAssignee.getRecord(currLine[3]),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[4]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[5]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[6]), 0, ZoneOffset.UTC),
                  currLine[7],
                  currLine[8]);
          labServiceRequests.add(labNode);
          labNode.getDestination().addRequest(labNode);

        } else {
          System.out.println("LabServiceRequests CSV file formatted improperly");
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
    // Insert locations from LabServiceReqCSV into db table
    for (int i = 0; i < labServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder labServiceReq = new StringBuilder();
        labServiceReq.append("INSERT INTO LabServiceRequest VALUES(");
        labServiceReq.append("'" + labServiceRequests.get(i).getRequestID() + "'" + ", ");
        labServiceReq.append(
            "'" + labServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        labServiceReq.append("'" + labServiceRequests.get(i).getStatus() + "'" + ", ");
        labServiceReq.append("'" + labServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        labServiceReq.append(
            labServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
        labServiceReq.append(
            labServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
        labServiceReq.append(
            labServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
        labServiceReq.append("'" + labServiceRequests.get(i).getService() + "'" + ", ");
        labServiceReq.append("'" + labServiceRequests.get(i).getPatientFor() + "'");
        labServiceReq.append(")");
        initialization.execute(labServiceReq.toString());
      } catch (SQLException e) {
        System.out.println("Input for LabServiceReq " + i + " failed");
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
      csvFile.write("requestID,destination,status,assignee,service,patientFor");
      for (int i = 0; i < labServiceRequests.size(); i++) {
        csvFile.write("\n" + labServiceRequests.get(i).getRequestID() + ",");
        if (labServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (labServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getStatus() + ",");
        }
        if (labServiceRequests.get(i).getAssigneeID() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (labServiceRequests.get(i).getCreationTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              labServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
        }
        if (labServiceRequests.get(i).getPROCTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              labServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
        }
        if (labServiceRequests.get(i).getDONETime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              labServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
        }
        if (labServiceRequests.get(i).getService() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getService() + ",");
        }
        if (labServiceRequests.get(i).getPatientFor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getPatientFor());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.print(
          "An error occurred when trying to write to the Lab Service Request CSV file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Fills labServiceRequests with data from the sql table
   *
   * @return true if labServiceRequests is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      labServiceRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM LabServiceRequest");
      while (results.next()) {
        LabServiceRequest toAdd = new LabServiceRequest();
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
        toAdd.setService(results.getString("service"));
        toAdd.setPatientFor(results.getString("patientFor"));
        labServiceRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("LabServiceRequestDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    LabServiceRequestDAO lsrDAO = LabServiceRequestDAO.getDAO(); // gets list of all ids
    int nextIDFinalNum = lsrDAO.getAllRecords().size() + 1;

    return String.format("LBSR%04d", nextIDFinalNum);
  }
}
