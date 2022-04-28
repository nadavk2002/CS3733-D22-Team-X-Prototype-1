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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicalEquipmentServiceRequestDAO implements DAO<MedicalEquipmentServiceRequest> {
  private static List<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequests =
      new ArrayList<MedicalEquipmentServiceRequest>();
  private static String csv = "MedEquipReq.csv";
  public static ObservableList<MedicalEquipmentServiceRequest> alerts =
      FXCollections.observableArrayList();
  /** Creates a new LocationDAO object. */
  private MedicalEquipmentServiceRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      medicalEquipmentServiceRequests.clear();
    }
  }

  public ObservableList<MedicalEquipmentServiceRequest> getAlerts() {
    return alerts;
  }

  public void addAlert(MedicalEquipmentServiceRequest MESR) {
    alerts.add(MESR);
  }
  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final MedicalEquipmentServiceRequestDAO medicalEquipmentServiceRequestDAO =
        new MedicalEquipmentServiceRequestDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static MedicalEquipmentServiceRequestDAO getDAO() {
    return SingletonHelper.medicalEquipmentServiceRequestDAO;
  }

  @Override
  public List<MedicalEquipmentServiceRequest> getAllRecords() {
    return medicalEquipmentServiceRequests;
  }

  @Override
  public MedicalEquipmentServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MedicalEquipmentServiceRequest esr : medicalEquipmentServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(recordID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MedicalEquipmentServiceRequest recordObject) {
    // add mesr to destination's list of service requests
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicalEquipmentServiceRequests.size();
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(recordObject)) {
        medicalEquipmentServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM MedicalEquipmentServiceRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MedicalEquipmentServiceRequest recordObject) {
    // remove mesr from old location and add to new one if location changes in the update
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(recordObject)) {
        medicalEquipmentServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicalEquipmentServiceRequests.size()) {
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
              + recordObject.getAssigneeID()
              + "', CreationTime = "
              + recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC)
              + ", PROCTime = "
              + recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC)
              + ", DONETime = "
              + recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC)
              + ", equipmentType = '"
              + recordObject.getEquipmentType()
              + "', quantity = "
              + recordObject.getQuantity()
              + " WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(MedicalEquipmentServiceRequest recordObject) {
    medicalEquipmentServiceRequests.add(recordObject);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medEquipReq = new StringBuilder();
      medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
      medEquipReq.append("'" + recordObject.getRequestID() + "'" + ", ");
      medEquipReq.append("'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      medEquipReq.append("'" + recordObject.getStatus() + "'" + ", ");
      medEquipReq.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      medEquipReq.append(recordObject.getCreationTime().toEpochSecond(ZoneOffset.UTC) + ",");
      medEquipReq.append(recordObject.getPROCTime().toEpochSecond(ZoneOffset.UTC) + ",");
      medEquipReq.append(recordObject.getDONETime().toEpochSecond(ZoneOffset.UTC) + ",");
      medEquipReq.append("'" + recordObject.getEquipmentType() + "'" + ", ");
      medEquipReq.append(recordObject.getQuantity());
      medEquipReq.append(")");
      initialization.execute(medEquipReq.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("MedicalEquipmentSR database could not be updated");
      return;
    }
    recordObject
        .getDestination()
        .addRequest(recordObject); // add mesr to destination's list of service requests

    //    // If service request completed, update the availability of the equipment type
    //    // SHOULD BE BASED ON LOCATION ADDED TO
    //    if (medicalEquipmentServiceRequest.getStatus().equals("DONE")) {
    //      EquipmentTypeDAOImpl eqtDAOImpl = new EquipmentTypeDAOImpl();
    //      eqtDAOImpl.decreaseAvailability(
    //          medicalEquipmentServiceRequest.getEquipmentType(),
    //          medicalEquipmentServiceRequest.getQuantity());
    //    }
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicalEquipmentServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "CreationTime BIGINT,"
              + "PROCTime BIGINT,"
              + "DONETime BIGINT,"
              + "equipmentType VARCHAR(20),"
              + "quantity INT,"
              + "CONSTRAINT MESR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MESR_equipmentType_fk "
              + "FOREIGN KEY (equipmentType) REFERENCES EquipmentType(model) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MESR_assignee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println(
          "MedicalEquipmentServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropMedicalEquipmentServiceRequest = connection.createStatement();
      dropMedicalEquipmentServiceRequest.execute("DROP TABLE MedicalEquipmentServiceRequest");
    } catch (SQLException e) {
      System.out.println("MedicalEquipmentServiceRequest not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream medCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(medCSV));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 9) {
          MedicalEquipmentServiceRequest mesrNode =
              new MedicalEquipmentServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[4]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[5]), 0, ZoneOffset.UTC),
                  LocalDateTime.ofEpochSecond(Long.parseLong(currLine[6]), 0, ZoneOffset.UTC),
                  currLine[7],
                  Integer.parseInt(currLine[8]));
          medicalEquipmentServiceRequests.add(mesrNode);
          mesrNode
              .getDestination()
              .addRequest(mesrNode); // add mesr to destination's list of service requests
        } else {
          System.out.println("MedEquipReq CSV file formatted improperly");
          System.out.println(currLine);
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
    for (int i = 0; i < medicalEquipmentServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder medEquipReq = new StringBuilder();
        medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
        medEquipReq.append(
            "'" + medicalEquipmentServiceRequests.get(i).getRequestID() + "'" + ", ");
        medEquipReq.append(
            "'" + medicalEquipmentServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        medEquipReq.append("'" + medicalEquipmentServiceRequests.get(i).getStatus() + "'" + ", ");
        medEquipReq.append(
            "'" + medicalEquipmentServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        medEquipReq.append(
            medicalEquipmentServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                + ",");
        medEquipReq.append(
            medicalEquipmentServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC)
                + ",");
        medEquipReq.append(
            medicalEquipmentServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC)
                + ",");
        medEquipReq.append(
            "'" + medicalEquipmentServiceRequests.get(i).getEquipmentType() + "'" + ", ");
        medEquipReq.append(medicalEquipmentServiceRequests.get(i).getQuantity());
        medEquipReq.append(")");
        initialization.execute(medEquipReq.toString());
      } catch (SQLException e) {
        System.out.println("Input for MedEquipReq " + i + " failed");
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
      csvFile.write(
          "RequestID,Destination,Status,assignee,CreationTime,PROCTime,DONETime,equipmentType,Quantity");
      for (int i = 0; i < medicalEquipmentServiceRequests.size(); i++) {
        csvFile.write("\n" + medicalEquipmentServiceRequests.get(i).getRequestID() + ",");
        if (medicalEquipmentServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicalEquipmentServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicalEquipmentServiceRequests.get(i).getStatus() + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getAssigneeID() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicalEquipmentServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getCreationTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              medicalEquipmentServiceRequests.get(i).getCreationTime().toEpochSecond(ZoneOffset.UTC)
                  + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getPROCTime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              medicalEquipmentServiceRequests.get(i).getPROCTime().toEpochSecond(ZoneOffset.UTC)
                  + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getDONETime() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(
              medicalEquipmentServiceRequests.get(i).getDONETime().toEpochSecond(ZoneOffset.UTC)
                  + ",");
        }
        if (medicalEquipmentServiceRequests.get(i).getEquipmentType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicalEquipmentServiceRequests.get(i).getEquipmentType() + ",");
        }
        csvFile.write(Integer.toString(medicalEquipmentServiceRequests.get(i).getQuantity()));
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

  /**
   * Fills medicalEquipmentServiceRequests with data from the sql table
   *
   * @return true if medicalEquipmentServiceRequests is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      medicalEquipmentServiceRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM MedicalEquipmentServiceRequest");
      while (results.next()) {
        MedicalEquipmentServiceRequest toAdd = new MedicalEquipmentServiceRequest();
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
        toAdd.setEquipmentType(results.getString("equipmentType"));
        toAdd.setQuantity(results.getInt("quantity"));
        medicalEquipmentServiceRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println(
          "MedicalEquipmentServiceRequestDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    MedicalEquipmentServiceRequestDAO esrDAO = MedicalEquipmentServiceRequestDAO.getDAO();
    int nextIDFinalNum = esrDAO.getAllRecords().size() + 1;
    return String.format("MESR%04d", nextIDFinalNum);
  }
}
