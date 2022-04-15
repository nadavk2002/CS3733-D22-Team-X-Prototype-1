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

public class MedicineDeliverServiceRequestDAO implements DAO<MedicineServiceRequest> {
  private static List<MedicineServiceRequest> medicineServiceRequests =
      new ArrayList<MedicineServiceRequest>();
  private static String csv = "MedicineDeliveryRequests.csv";

  private MedicineDeliverServiceRequestDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      medicineServiceRequests.clear();
    }
  }

  private static class SingletonHelper {
    private static final MedicineDeliverServiceRequestDAO medicineDeliveryServiceRequestDAO =
        new MedicineDeliverServiceRequestDAO();
  }

  public static MedicineDeliverServiceRequestDAO getDAO() {
    return SingletonHelper.medicineDeliveryServiceRequestDAO;
  }

  @Override
  public List<MedicineServiceRequest> getAllRecords() {
    return medicineServiceRequests;
  }

  @Override
  public MedicineServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MedicineServiceRequest esr : medicineServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(recordID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MedicineServiceRequest recordObject) {
    // add to destination's list of service requests
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicineServiceRequests.size();
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.remove(index); // removes object from list
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
          "DELETE FROM MedicineDeliveryServiceRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MedicineServiceRequest recordObject) {
    // remove mesr from old location and add to new one if location changes in the update
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicineServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MedicineDeliveryServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssigneeID()
              + "', rxNum = '"
              + recordObject.getRxNum()
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
  public void addRecord(MedicineServiceRequest recordObject) {
    medicineServiceRequests.add(recordObject);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medicineDeliveryServiceRequest = new StringBuilder();
      medicineDeliveryServiceRequest.append("INSERT INTO MedicineDeliveryServiceRequest VALUES(");
      medicineDeliveryServiceRequest.append("'" + recordObject.getRequestID() + "'" + ", ");
      medicineDeliveryServiceRequest.append(
          "'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getStatus() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getAssigneeID() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getRxNum() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getPatientFor() + "'");
      medicineDeliveryServiceRequest.append(")");
      initialization.execute(medicineDeliveryServiceRequest.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
    recordObject
        .getDestination()
        .addRequest(recordObject); // add mesr to destination's list of service requests
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicineDeliveryServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "rxNum CHAR(8),"
              + "patientFor CHAR(8),"
              + "CONSTRAINT MDSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MDSR_employee_fk "
              + "FOREIGN KEY (assignee) REFERENCES Employee(employeeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println(
          "MedicineDeliveryServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropMedicalEquipmentServiceRequest = connection.createStatement();
      dropMedicalEquipmentServiceRequest.execute("DROP TABLE MedicineDeliveryServiceRequest");
    } catch (SQLException e) {
      System.out.println("MedicineDeliveryServiceRequest not dropped");
      e.printStackTrace();
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
        if (currLine.length == 6) {
          MedicineServiceRequest mesrNode =
              new MedicineServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  currLine[4],
                  currLine[5]);
          medicineServiceRequests.add(mesrNode);
          mesrNode
              .getDestination()
              .addRequest(mesrNode); // add mesr to destination's list of service requests
        } else {
          System.out.println("MedicineDeliveryRequests CSV file formatted improperly");
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
    for (int i = 0; i < medicineServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder medEquipReq = new StringBuilder();
        medEquipReq.append("INSERT INTO MedicineDeliveryServiceRequest VALUES(");
        medEquipReq.append("'" + medicineServiceRequests.get(i).getRequestID() + "'" + ", ");
        medEquipReq.append(
            "'" + medicineServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        medEquipReq.append("'" + medicineServiceRequests.get(i).getStatus() + "'" + ", ");
        medEquipReq.append("'" + medicineServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        medEquipReq.append("'" + medicineServiceRequests.get(i).getRxNum() + "'" + ", ");
        medEquipReq.append("'" + medicineServiceRequests.get(i).getPatientFor() + "'");
        medEquipReq.append(")");
        initialization.execute(medEquipReq.toString());
      } catch (SQLException e) {
        System.out.println("Input for MedicineDeliveryRequests " + i + " failed");
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
      csvFile.write("requestID,destination,status,assignee,rxNumber,patientFor");
      for (int i = 0; i < medicineServiceRequests.size(); i++) {
        csvFile.write("\n" + medicineServiceRequests.get(i).getRequestID() + ",");
        if (medicineServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (medicineServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineServiceRequests.get(i).getStatus() + ",");
        }
        if (medicineServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (medicineServiceRequests.get(i).getRxNum() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineServiceRequests.get(i).getRxNum() + ",");
        }
        if (medicineServiceRequests.get(i).getPatientFor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineServiceRequests.get(i).getPatientFor() + ",");
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

  /**
   * Fills medicineServiceRequests with data from the sql table
   *
   * @return true if medicineServiceRequests is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      medicineServiceRequests.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM MedicineDeliveryServiceRequest");
      while (results.next()) {
        MedicineServiceRequest toAdd = new MedicineServiceRequest();
        toAdd.setRequestID(results.getString("requestID"));
        toAdd.setDestination(LocationDAO.getDAO().getRecord(results.getString("destination")));
        toAdd.setStatus(results.getString("status"));
        toAdd.setAssignee(EmployeeDAO.getDAO().getRecord(results.getString("assignee")));
        toAdd.setRxNum(results.getString("rxNum"));
        toAdd.setPatientFor(results.getString("patientFor"));
        medicineServiceRequests.add(toAdd);
        toAdd.getDestination().addRequest(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("MedicineDeliverServiceRequestDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = getAllRecords().size() + 1;
    return String.format("MDSR%04d", nextIDFinalNum);
  }
}
