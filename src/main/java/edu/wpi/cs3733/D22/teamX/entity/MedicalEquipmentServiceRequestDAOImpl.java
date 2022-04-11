package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public class MedicalEquipmentServiceRequestDAOImpl implements MedicalEquipmentServiceRequestDAO {
  public MedicalEquipmentServiceRequestDAOImpl() {}

  /**
   * gets the list of all the medical equipment service requests
   *
   * @return a list of all the medical equipment service requests
   */
  @Override
  public List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests() {
    return medicalEquipmentServiceRequests;
  }

  /**
   * gets individual equipment service requests
   *
   * @param requestID requestID of the induvidual service request
   * @return a medical equipment service request with a matching requestID
   */
  @Override
  public MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID)
      throws NoSuchElementException {
    // iterate through list to find element with matching requestID
    for (MedicalEquipmentServiceRequest esr : medicalEquipmentServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(requestID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  /**
   * deletes object from DAO and database.
   *
   * @param medicalEquipmentServiceRequest medical equipment service request to be updated
   */
  @Override
  public void deleteMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) throws NoSuchElementException {
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicalEquipmentServiceRequests.size();
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(medicalEquipmentServiceRequest)) {
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
              + medicalEquipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * updates DAO and database element
   *
   * @param medicalEquipmentServiceRequest equipment service request to be updated
   */
  @Override
  public void updateMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) throws NoSuchElementException {
    int index = 0; // create indexer varible for while loop
    while (index < medicalEquipmentServiceRequests.size()) {
      if (medicalEquipmentServiceRequests.get(index).equals(medicalEquipmentServiceRequest)) {
        medicalEquipmentServiceRequests.set(index, medicalEquipmentServiceRequest);
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
              + medicalEquipmentServiceRequest.getDestination().getNodeID()
              + "', status = '"
              + medicalEquipmentServiceRequest.getStatus()
              + "', assignee = '"
              + medicalEquipmentServiceRequest.getAssignee()
              + "', equipmentType = '"
              + medicalEquipmentServiceRequest.getEquipmentType()
              + "', quantity = "
              + medicalEquipmentServiceRequest.getQuantity()
              + " WHERE requestID = '"
              + medicalEquipmentServiceRequest.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest) {
    medicalEquipmentServiceRequests.add(medicalEquipmentServiceRequest);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medEquipReq = new StringBuilder();
      medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getRequestID() + "'" + ", ");
      medEquipReq.append(
          "'" + medicalEquipmentServiceRequest.getDestination().getNodeID() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getStatus() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getAssignee() + "'" + ", ");
      medEquipReq.append("'" + medicalEquipmentServiceRequest.getEquipmentType() + "'" + ", ");
      medEquipReq.append(medicalEquipmentServiceRequest.getQuantity());
      medEquipReq.append(")");
      initialization.execute(medEquipReq.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
    //    // If service request completed, update the availability of the equipment type
    //    // SHOULD BE BASED ON LOCATION ADDED TO
    //    if (medicalEquipmentServiceRequest.getStatus().equals("DONE")) {
    //      EquipmentTypeDAOImpl eqtDAOImpl = new EquipmentTypeDAOImpl();
    //      eqtDAOImpl.decreaseAvailability(
    //          medicalEquipmentServiceRequest.getEquipmentType(),
    //          medicalEquipmentServiceRequest.getQuantity());
    //    }
  }

  /** creates the medical equipment request service table in the database */
  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicalEquipmentServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "equipmentType VARCHAR(20),"
              + "quantity INT,"
              + "CONSTRAINT MESR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MESR_equipmentType_fk "
              + "FOREIGN KEY (equipmentType) REFERENCES EquipmentType(model) "
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
      e.printStackTrace();
    }
  }

  /**
   * Read the MedEquipReq from CSV file. Put locations into db table
   * "MedicalEquipmentServiceRequest"
   *
   * @return a list of Medical Equipment Service Requests
   */
  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = new LocationDAOImpl();
      InputStream medCSV =
          DatabaseCreator.class.getResourceAsStream(medicalEquipmentServRequestCSV);
      BufferedReader medCSVReader = new BufferedReader(new InputStreamReader(medCSV));
      medCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = medCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 6) {
          MedicalEquipmentServiceRequest mesrNode =
              new MedicalEquipmentServiceRequest(
                  currLine[0],
                  locDestination.getLocation(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4],
                  Integer.parseInt(currLine[5]));
          medicalEquipmentServiceRequests.add(mesrNode);
        } else {
          System.out.println("MedEquipReq CSV file formatted improperly");
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
        medEquipReq.append("'" + medicalEquipmentServiceRequests.get(i).getAssignee() + "'" + ", ");
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

  /** Saves Medical Equipment data to CSV on close */
  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + medicalEquipmentServRequestCSV, false);
      csvFile.write("RequestID,Destination,Status,assignee,equipmentType,Quantity");
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
        if (medicalEquipmentServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(medicalEquipmentServiceRequests.get(i).getAssignee() + ",");
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
}
