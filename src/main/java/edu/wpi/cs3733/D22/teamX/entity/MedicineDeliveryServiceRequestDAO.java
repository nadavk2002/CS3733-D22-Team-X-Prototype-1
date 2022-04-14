package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MedicineDeliveryServiceRequestDAO implements DAO<MedicineDeliveryServiceRequest> {
  private static List<MedicineDeliveryServiceRequest> medicineDeliveryServiceRequests =
      new ArrayList<MedicineDeliveryServiceRequest>();
  private static String csv = "MedicineDeliveryRequests.csv";

  private MedicineDeliveryServiceRequestDAO() {}

  private static class SingletonHelper {
    private static final MedicineDeliveryServiceRequestDAO medicineDeliveryServiceRequestDAO =
        new MedicineDeliveryServiceRequestDAO();
  }

  public static MedicineDeliveryServiceRequestDAO getDAO() {
    return SingletonHelper.medicineDeliveryServiceRequestDAO;
  }

  @Override
  public List<MedicineDeliveryServiceRequest> getAllRecords() {
    return medicineDeliveryServiceRequests;
  }

  @Override
  public MedicineDeliveryServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MedicineDeliveryServiceRequest esr : medicineDeliveryServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(recordID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MedicineDeliveryServiceRequest recordObject) {
    // add to destination's list of service requests
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicineDeliveryServiceRequests.size();
    while (index < medicineDeliveryServiceRequests.size()) {
      if (medicineDeliveryServiceRequests.get(index).equals(recordObject)) {
        medicineDeliveryServiceRequests.remove(index); // removes object from list
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
  public void updateRecord(MedicineDeliveryServiceRequest recordObject) {
    // remove mesr from old location and add to new one if location changes in the update
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < medicineDeliveryServiceRequests.size()) {
      if (medicineDeliveryServiceRequests.get(index).equals(recordObject)) {
        medicineDeliveryServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicineDeliveryServiceRequests.size()) {
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
  public void addRecord(MedicineDeliveryServiceRequest recordObject) {
    medicineDeliveryServiceRequests.add(recordObject);

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
      InputStream medCSV = DatabaseCreator.class.getResourceAsStream(csv);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(medCSV));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 6) {
          MedicineDeliveryServiceRequest mesrNode =
              new MedicineDeliveryServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  currLine[4],
                  currLine[5]);
          medicineDeliveryServiceRequests.add(mesrNode);
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
    for (int i = 0; i < medicineDeliveryServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder medEquipReq = new StringBuilder();
        medEquipReq.append("INSERT INTO MedicineDeliveryServiceRequest VALUES(");
        medEquipReq.append(
            "'" + medicineDeliveryServiceRequests.get(i).getRequestID() + "'" + ", ");
        medEquipReq.append(
            "'" + medicineDeliveryServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        medEquipReq.append("'" + medicineDeliveryServiceRequests.get(i).getStatus() + "'" + ", ");
        medEquipReq.append(
            "'" + medicineDeliveryServiceRequests.get(i).getAssigneeID() + "'" + ", ");
        medEquipReq.append("'" + medicineDeliveryServiceRequests.get(i).getRxNum() + "'" + ", ");
        medEquipReq.append("'" + medicineDeliveryServiceRequests.get(i).getPatientFor() + "'");
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
      for (int i = 0; i < medicineDeliveryServiceRequests.size(); i++) {
        csvFile.write("\n" + medicineDeliveryServiceRequests.get(i).getRequestID() + ",");
        if (medicineDeliveryServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineDeliveryServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (medicineDeliveryServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineDeliveryServiceRequests.get(i).getStatus() + ",");
        }
        if (medicineDeliveryServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineDeliveryServiceRequests.get(i).getAssigneeID() + ",");
        }
        if (medicineDeliveryServiceRequests.get(i).getRxNum() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineDeliveryServiceRequests.get(i).getRxNum() + ",");
        }
        if (medicineDeliveryServiceRequests.get(i).getPatientFor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicineDeliveryServiceRequests.get(i).getPatientFor() + ",");
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
    int nextIDFinalNum = getAllRecords().size() + 1;
    return String.format("MDSR%04d", nextIDFinalNum);
  }
}
