package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Xdb {
  private static final String locationCSV = "TowerLocationsX.csv";
  private static final String medicalEquipmentServRequestCSV = "MedEquipReq.csv";
  private static final String labServiceRequestsCSV = "LabServiceRequests.csv";
  // private static final String equipmentTypesCSV = "EquipmentTypes.csv";
  private static final String equipmentUnitsCSV = "MedicalEquipmentUnits.csv";
  //  /** Initializes the db and runs the user program from Spike B. */
  //  public static void initializeAndRunDBProgram() {
  //    Connection dbConn = initializeDB();
  //    UserProgram.executeProgram(dbConn);
  //    try {
  //      dbConn.close();
  //    } catch (SQLException e) {
  //      e.printStackTrace();
  //    }
  //  }

  /** Initializes the database with tables and establishes a connection */
  public static void initializeDB() throws loadSaveFromCSVException {
    System.out.println("-----Testing Apache Derby Embedded Connection-----");
    // checks whether the driver is working
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("Apache Derby driver registered!");

    // tries to create the database and establish a connection
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    //    try {
    //
    //    } catch (SQLException e) {
    //      System.out.println("Connection failed. Check output console.");
    //      e.printStackTrace();
    //      System.exit(1);
    //    }
    System.out.println("Apache Derby connection established :D");

    dropAllTables();
    createLocationTable();
    createEqTypeTable();
    createMedicalEquipmentServiceRequestTable();
    createLabServiceRequestTable();
    createEqUnitsTable();

    if (!loadLocationCSV()
        || !loadMedEqServiceReqCSV()
        || !loadLabServiceReqCSV()
        || !loadEquipmentUnitsCSV()) {
      throw new loadSaveFromCSVException("Error when loading from CSV files.");
    }

    System.out.println("Database created successfully");
  }

  /** Saves the data from the database to the appropriate CSV files and closes the database. */
  public static boolean closeDB() throws loadSaveFromCSVException {
    if (!saveLocationDataToCSV() || !saveMedEqDataToCSV() || !saveLabServiceReqDataToCSV()) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    try {
      ConnectionSingleton.getConnectionSingleton().getConnection().close();
      System.out.println("Connection closed successfully");
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /** Creates the location table in the database */
  private static void createLocationTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE Location(nodeID CHAR(10) PRIMARY KEY NOT NULL, "
              + "xCoord INT, yCoord INT, "
              + "floor VARCHAR(2), "
              + "building VARCHAR(10), "
              + "nodeType CHAR(4), "
              + "longName VARCHAR(50), shortName VARCHAR(30))");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Creates a Equipment Types table */
  private static void createEqTypeTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE EquipmentType(model VARCHAR(15) PRIMARY KEY NOT NULL, "
              + "numUnitsTotal INT,"
              + "numUnitsAvailable INT)");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** creates the medical equipment request service table in the database */
  private static void createMedicalEquipmentServiceRequestTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicalEquipmentServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "equipmentType VARCHAR(15),"
              + "quantity INT,"
              + "CONSTRAINT MESR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL)");
      //       + "FOREIGN KEY (equipmentType) REFERENCES EquipmentType(model))");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Creates a lab service request table */
  private static void createLabServiceRequestTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
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
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Creates a Equipment Units table */
  private static void createEqUnitsTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE EquipmentUnit(UnitID CHAR(8) PRIMARY KEY NOT NULL, "
              + "EquipmentType VARCHAR(20),"
              + "isAvailable CHAR(1),"
              + "currLocation CHAR(10),"
              + "FOREIGN KEY (currLocation) REFERENCES Location(nodeID))");
      //              + "FOREIGN KEY (EquipmentType) REFERENCES EquipmentType(model))");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Drops all the tables in the database. */
  private static void dropAllTables() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();

    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE LabServiceRequest");
    } catch (SQLException e) {
      System.out.println("LabServiceRequest not dropped");
      e.printStackTrace();
    }

    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE MedicalEquipmentServiceRequest");
    } catch (SQLException e) {
      System.out.println("MedicalEquipmentServiceRequest not dropped");
      e.printStackTrace();
    }

    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE EquipmentUnit");
    } catch (SQLException e) {
      System.out.println("EquipmentUnit not dropped");
      e.printStackTrace();
    }

    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE EquipmentType");
    } catch (SQLException e) {
      System.out.println("EquipmentType not dropped");
      e.printStackTrace();
    }

    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE Location");
    } catch (SQLException e) {
      System.out.println("Location not dropped");
      e.printStackTrace();
    }
  }

  /**
   * Read the locations from CSV file. Put locations into db table "Location"
   *
   * @return whether an exception was thrown when trying o read from the file.
   */
  private static boolean loadLocationCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    // Read locations into List "locationsFromCSV"
    List<Location> locationsFromCSV = new ArrayList<Location>();
    try {
      InputStream tlCSV = Xdb.class.getResourceAsStream(locationCSV);
      BufferedReader tlCSVReader = new BufferedReader(new InputStreamReader(tlCSV));
      tlCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = tlCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          Location lnode =
              new Location(
                  currLine[0],
                  Integer.parseInt(currLine[1]),
                  Integer.parseInt(currLine[2]),
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          locationsFromCSV.add(lnode);
        } else {
          System.out.println("CSV file formatted improperly");
          System.exit(1);
        }
      }
      tlCSV.close();
      tlCSVReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    // Insert locations from locationsFromCSV into db table
    for (int i = 0; i < locationsFromCSV.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder insertLocation = new StringBuilder();
        insertLocation.append("INSERT INTO Location VALUES(");
        insertLocation.append("'" + locationsFromCSV.get(i).getNodeID() + "'" + ", ");
        insertLocation.append(locationsFromCSV.get(i).getxCoord() + ", ");
        insertLocation.append(locationsFromCSV.get(i).getyCoord() + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getFloor() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getBuilding() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getNodeType() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getLongName() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getShortName() + "'");
        insertLocation.append(")");
        initialization.execute(insertLocation.toString());
      } catch (SQLException e) {
        System.out.println("Input for Location " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * Read the MedEquipReq from CSV file. Put locations into db table
   * "MedicalEquipmentServiceRequest"
   *
   * @return a list of Medical Equipment Service Requests
   */
  private static boolean loadMedEqServiceReqCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    // Read locations into List "MedEquipReqCSV"
    List<MedicalEquipmentServiceRequest> MedEquipReqFromCSV =
        new ArrayList<MedicalEquipmentServiceRequest>();
    try {
      LocationDAO locDestination = new LocationDAOImpl();
      InputStream medCSV = Xdb.class.getResourceAsStream(medicalEquipmentServRequestCSV);
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
          MedEquipReqFromCSV.add(mesrNode);
        } else {
          System.out.println("CSV file formatted improperly");
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
    for (int i = 0; i < MedEquipReqFromCSV.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder medEquipReq = new StringBuilder();
        medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getRequestID() + "'" + ", ");
        medEquipReq.append(
            "'" + MedEquipReqFromCSV.get(i).getDestination().getNodeID() + "'" + ", ");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getStatus() + "'" + ", ");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getAssignee() + "'" + ", ");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getEquipmentType() + "'" + ", ");
        medEquipReq.append(MedEquipReqFromCSV.get(i).getQuantity());
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

  /**
   * Loads the LabServiceReq.csv to the LabServiceRequest Table
   *
   * @return true if successful
   */
  private static boolean loadLabServiceReqCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    // Read locations into List "LabServiceReqCSV"
    List<LabServiceRequest> LabServiceRequestCSV = new ArrayList<LabServiceRequest>();
    try {
      LocationDAO locDestination = new LocationDAOImpl();
      InputStream labCSV = Xdb.class.getResourceAsStream(labServiceRequestsCSV);
      BufferedReader labCSVReader = new BufferedReader(new InputStreamReader(labCSV));
      labCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = labCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 6) {
          LabServiceRequest labNode =
              new LabServiceRequest(
                  currLine[0],
                  locDestination.getLocation(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4],
                  currLine[5]);
          LabServiceRequestCSV.add(labNode);
        } else {
          System.out.println("CSV file formatted improperly");
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
    for (int i = 0; i < LabServiceRequestCSV.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder labServiceReq = new StringBuilder();
        labServiceReq.append("INSERT INTO LabServiceRequest VALUES(");
        labServiceReq.append("'" + LabServiceRequestCSV.get(i).getRequestID() + "'" + ", ");
        labServiceReq.append(
            "'" + LabServiceRequestCSV.get(i).getDestination().getNodeID() + "'" + ", ");
        labServiceReq.append("'" + LabServiceRequestCSV.get(i).getStatus() + "'" + ", ");
        labServiceReq.append("'" + LabServiceRequestCSV.get(i).getAssignee() + "'" + ", ");
        labServiceReq.append("'" + LabServiceRequestCSV.get(i).getService() + "'" + ", ");
        labServiceReq.append("'" + LabServiceRequestCSV.get(i).getPatientFor() + "'");
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

  private static boolean loadEquipmentUnitsCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    List<EquipmentUnit> equipmentUnitList = new ArrayList<EquipmentUnit>();
    try {
      LocationDAO locationDAO = new LocationDAOImpl();
      InputStream equipmentUnitStream = Xdb.class.getResourceAsStream(equipmentUnitsCSV);
      BufferedReader equipmentUnitBuffer =
          new BufferedReader(new InputStreamReader(equipmentUnitStream));
      equipmentUnitBuffer.readLine();
      String nextFileLine;
      while ((nextFileLine = equipmentUnitBuffer.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 4) {
          EquipmentUnit equipmentUnitNode =
              new EquipmentUnit(
                  currLine[0],
                  currLine[1],
                  currLine[2].charAt(0),
                  locationDAO.getLocation(currLine[3]));
          equipmentUnitList.add(equipmentUnitNode);
        } else {
          System.out.println("CSV file formatted improperly");
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

    for (int i = 0; i < equipmentUnitList.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder equipmentUnit = new StringBuilder();
        equipmentUnit.append("INSERT INTO EquipmentUnit VALUES(");
        equipmentUnit.append("'" + equipmentUnitList.get(i).getUnitID() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnitList.get(i).getType() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnitList.get(i).getIsAvailableChar() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnitList.get(i).getCurrLocation().getNodeID() + "'");
        equipmentUnit.append(")");
        initialization.execute(equipmentUnit.toString());
      } catch (SQLException e) {
        System.out.println("Input for EquipmentUnit " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /** Writes the content of the location table from the database into the TowerLocations.CSV */
  private static boolean saveLocationDataToCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    ArrayList<Location> locations = new ArrayList<Location>();
    try {
      Statement statement = connection.createStatement();
      ResultSet locationRecords = statement.executeQuery("SELECT * FROM LOCATION");
      while (locationRecords.next()) {
        locations.add(
            new Location(
                locationRecords.getString("nodeID"),
                locationRecords.getInt("xCoord"),
                locationRecords.getInt("yCoord"),
                locationRecords.getString("floor"),
                locationRecords.getString("building"),
                locationRecords.getString("nodeType"),
                locationRecords.getString("longName"),
                locationRecords.getString("shortName")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("An error occured when saving Location data to the CSV file.");
      return false;
    }

    try {
      //      File csv = new File(Xdb.class.getResource(locationCSV).getPath());
      //      FileWriter csvFile = new FileWriter(csv, false);
      FileWriter csvFile = new FileWriter(locationCSV, false);
      csvFile.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
      for (int i = 0; i < locations.size(); i++) {
        csvFile.write("\n" + locations.get(i).getNodeID() + ",");
        csvFile.write(locations.get(i).getxCoord() + ",");
        csvFile.write(locations.get(i).getyCoord() + ",");
        if (locations.get(i).getFloor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getFloor() + ",");
        }
        if (locations.get(i).getBuilding() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getBuilding() + ",");
        }
        if (locations.get(i).getNodeType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getNodeType() + ",");
        }
        if (locations.get(i).getLongName() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getLongName() + ",");
        }
        if (locations.get(i).getShortName() != null) {
          csvFile.write(locations.get(i).getShortName());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occured when updating locations csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /** Saves Medical Equipment data to CSV on close */
  private static boolean saveMedEqDataToCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    List<MedicalEquipmentServiceRequest> Equipment =
        new ArrayList<MedicalEquipmentServiceRequest>();
    LocationDAO locDestination = new LocationDAOImpl();
    try {
      Statement statement = connection.createStatement();
      ResultSet records = statement.executeQuery("SELECT * FROM MedicalEquipmentServiceRequest");
      while (records.next()) {
        Equipment.add(
            new MedicalEquipmentServiceRequest(
                records.getString("requestID"),
                locDestination.getLocation(records.getString("destination")),
                records.getString("status"),
                records.getString("assignee"),
                records.getString("equipmentType"),
                records.getInt("quantity")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(
          "An error occurred when saving MedicalEquipServRequest data to the CSV file.");
      return false;
    }

    try {
      //      URL url = Xdb.class.getResource(medicalEquipmentCSV);
      //      FileWriter csvFile = new FileWriter(url.getFile(), false);
      FileWriter csvFile = new FileWriter(medicalEquipmentServRequestCSV, false);
      csvFile.write("RequestID,Destination,Status,assignee,equipmentType,Quantity");
      for (int i = 0; i < Equipment.size(); i++) {
        csvFile.write("\n" + Equipment.get(i).getRequestID() + ",");
        if (Equipment.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(Equipment.get(i).getDestination().getNodeID() + ",");
        }
        if (Equipment.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(Equipment.get(i).getStatus() + ",");
        }
        if (Equipment.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(Equipment.get(i).getAssignee() + ",");
        }
        if (Equipment.get(i).getEquipmentType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(Equipment.get(i).getEquipmentType() + ",");
        }
        csvFile.write(Integer.toString(Equipment.get(i).getQuantity()));
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

  /** Writes the content of the location table from the database into the LabServiceReq.CSV */
  private static boolean saveLabServiceReqDataToCSV() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    ArrayList<LabServiceRequest> LabServiceReq = new ArrayList<LabServiceRequest>();
    LocationDAO locDestination = new LocationDAOImpl();
    try {
      Statement statement = connection.createStatement();
      ResultSet labServiceRecords = statement.executeQuery("SELECT * FROM LabServiceRequest");
      while (labServiceRecords.next()) {
        LabServiceReq.add(
            new LabServiceRequest(
                labServiceRecords.getString("requestID"),
                locDestination.getLocation(labServiceRecords.getString("destination")),
                labServiceRecords.getString("status"),
                labServiceRecords.getString("assignee"),
                labServiceRecords.getString("service"),
                labServiceRecords.getString("patientFor")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("An error occurred when saving Lab Service Request data to the CSV file.");
      return false;
    }

    try {
      FileWriter csvFile = new FileWriter(labServiceRequestsCSV, false);
      csvFile.write("requestID,destination,status,assignee,service,patientFor");
      for (int i = 0; i < LabServiceReq.size(); i++) {
        csvFile.write("\n" + LabServiceReq.get(i).getRequestID() + ",");
        if (LabServiceReq.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(LabServiceReq.get(i).getDestination().getNodeID() + ",");
        }
        if (LabServiceReq.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(LabServiceReq.get(i).getStatus() + ",");
        }
        if (LabServiceReq.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(LabServiceReq.get(i).getAssignee() + ",");
        }
        if (LabServiceReq.get(i).getService() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(LabServiceReq.get(i).getService() + ",");
        }
        if (LabServiceReq.get(i).getPatientFor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(LabServiceReq.get(i).getPatientFor());
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
}
