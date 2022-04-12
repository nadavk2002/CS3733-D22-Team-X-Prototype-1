package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LangServiceRequestDAO implements DAO<LangServiceRequest> {
  private static List<LangServiceRequest> langServiceRequests = new ArrayList<>();
  private static String csv = "LanguageInterpreterRequests.csv";

  /** Creates a new LocationDAO object. */
  private LangServiceRequestDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final LangServiceRequestDAO langServiceRequestDAO = new LangServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static LangServiceRequestDAO getDAO() {
    return SingletonHelper.langServiceRequestDAO;
  }

  @Override
  public List<LangServiceRequest> getAllRecords() {
    return langServiceRequests;
  }

  @Override
  public LangServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (LangServiceRequest lsr : langServiceRequests) {
      // if matching IDs
      if (lsr.getRequestID().equals(recordID)) {
        return lsr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(LangServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = langServiceRequests.size();
    while (index < langServiceRequests.size()) {
      if (langServiceRequests.get(index).equals(recordObject)) {
        langServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM LangServiceRequest WHERE requestID = '" + recordObject.getRequestID() + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(LangServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < langServiceRequests.size()) {
      if (langServiceRequests.get(index).equals(recordObject)) {
        langServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == langServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MedicalEquipmentServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssignee()
              + "', language = '"
              + recordObject.getLanguage()
              + " WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(LangServiceRequest recordObject) {
    langServiceRequests.add(recordObject);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO LangServiceRequest VALUES(");
      sql.append("'" + recordObject.getRequestID() + "'" + ", ");
      sql.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      sql.append("'" + recordObject.getStatus() + "'" + ", ");
      sql.append("'" + recordObject.getAssignee() + "'" + ", ");
      sql.append("'" + recordObject.getLanguage() + "'");
      sql.append(")");
      initialization.execute(sql.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE LangServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "language VARCHAR(20),"
              + "CONSTRAINT LASR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("LangServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE LangServiceRequest");
    } catch (SQLException e) {
      System.out.println("LangServiceRequest not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      InputStream medCSV = DatabaseCreator.class.getResourceAsStream(csv);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(medCSV));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          LangServiceRequest node =
              new LangServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4]);
          langServiceRequests.add(node);
          node.getDestination().addRequest(node);
        } else {
          System.out.println("LangServiceRequest CSV file formatted improperly");
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

    // Insert medical equipment service requests from MedEquipReqFromCSV into db table
    for (int i = 0; i < langServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO LangServiceRequest VALUES(");
        sql.append("'" + langServiceRequests.get(i).getRequestID() + "'" + ", ");
        sql.append("'" + langServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        sql.append("'" + langServiceRequests.get(i).getStatus() + "'" + ", ");
        sql.append("'" + langServiceRequests.get(i).getAssignee() + "'" + ", ");
        sql.append("'" + langServiceRequests.get(i).getLanguage() + "'");
        sql.append(")");
        initialization.execute(sql.toString());
      } catch (SQLException e) {
        System.out.println("Input for LangServiceRequest " + i + " failed");
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
      csvFile.write("requestID,destination,status,assignee,language");
      for (int i = 0; i < langServiceRequests.size(); i++) {
        csvFile.write("\n" + langServiceRequests.get(i).getRequestID() + ",");
        if (langServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(langServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (langServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(langServiceRequests.get(i).getStatus() + ",");
        }
        if (langServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(langServiceRequests.get(i).getAssignee() + ",");
        }
        if (langServiceRequests.get(i).getLanguage() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(langServiceRequests.get(i).getLanguage());
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
    return String.format("MESR%04d", nextIDFinalNum);
  }
}