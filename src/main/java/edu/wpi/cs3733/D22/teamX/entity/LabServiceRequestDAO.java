package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LabServiceRequestDAO implements DAO<LabServiceRequest> {
  private static List<LabServiceRequest> labServiceRequests = new ArrayList<LabServiceRequest>();
  private static String csv = "LabServiceRequests.csv";

  /** Creates a new LabServiceRequestDAO object. */
  private LabServiceRequestDAO() {}

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
              + recordObject.getAssignee()
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
      lsr.append("'" + recordObject.getRequestID() + "', ");
      lsr.append("'" + recordObject.getDestination().getNodeID() + "', ");
      lsr.append("'" + recordObject.getStatus() + "', ");
      lsr.append("'" + recordObject.getAssignee() + "', ");
      lsr.append("'" + recordObject.getService() + "', ");
      lsr.append("'" + recordObject.getPatientFor() + "'");
      lsr.append(")");
      initialization.execute(lsr.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Database could not be updated");
    }
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
              + "service VARCHAR(15),"
              + "patientFor VARCHAR(15),"
              + "CONSTRAINT LBSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
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
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      InputStream labCSV = DatabaseCreator.class.getResourceAsStream(csv);
      BufferedReader labCSVReader = new BufferedReader(new InputStreamReader(labCSV));
      labCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = labCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 6) {
          LabServiceRequest labNode =
              new LabServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4],
                  currLine[5]);
          labServiceRequests.add(labNode);
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
        labServiceReq.append("'" + labServiceRequests.get(i).getAssignee() + "'" + ", ");
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
        if (labServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(labServiceRequests.get(i).getAssignee() + ",");
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

  @Override
  public String makeID() {
    LabServiceRequestDAO lsrDAO = LabServiceRequestDAO.getDAO(); // gets list of all ids
    int nextIDFinalNum = lsrDAO.getAllRecords().size() + 1;

    return String.format("LBSR%04d", nextIDFinalNum);
  }
}
