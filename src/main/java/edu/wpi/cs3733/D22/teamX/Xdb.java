package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class Xdb {
  private static final String locationCSV = "TowerLocations.csv";
  private static final String medicalEquipmentCSV = "MedEquipReq.csv";
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

  /**
   * Initializes the database with tables and establishes a connection
   *
   * @return a connection to the database
   */
  public static Connection initializeDB() {
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
    Connection connection = ConnectionMaker.getConnection();
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
    createMedicalEquipmentServiceRequestTable();
    loadLocationCSV();
    loadMedEqServiceReqCSV();

    System.out.println("Database created successfully");

    return connection;
  }

  /**
   * Saves the data from the database to the appropriate CSV files and closes the database.
   *
   * @param connection connection to the database to be closed
   */
  public static void closeDB(Connection connection) {
    saveLocationDataToCSV();
    saveMedEqDataToCSV();
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /** Creates the location table in the database */
  private static void createLocationTable() {
    Connection connection = ConnectionMaker.getConnection();
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

  /** creates the medical equipment request service table in the database */
  private static void createMedicalEquipmentServiceRequestTable() {
    Connection connection = ConnectionMaker.getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicalEquipmentServiceRequest(requestID CHAR(8), "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "equipmentType VARCHAR(15),"
              + "quantity INT,"
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID))");
    } catch (SQLException e) {
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Drops all the tables in the database. */
  private static void dropAllTables() {
    Connection connection = ConnectionMaker.getConnection();
    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE MedicalEquipmentServiceRequest");
      dropLocation.execute("DROP TABLE Location");
    } catch (SQLException e) {
      System.out.println("Tables not dropped");
    }
  }

  /**
   * Read the locations from CSV file. Put locations into db table "Location"
   *
   * @return a list of location objects from the "TowerLocations.CSV" file.
   */
  private static List<Location> loadLocationCSV() {
    Connection connection = ConnectionMaker.getConnection();
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
    } catch (IOException e) {
      e.printStackTrace();
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
        return null;
      }
    }
    return locationsFromCSV;
  }

  /**
   * Read the MedEquipReq from CSV file. Put locations into db table
   * "MedicalEquipmentServiceRequest"
   *
   * @return a list of Medical Equipment Service Requests
   */
  private static List<EquipmentServiceRequest> loadMedEqServiceReqCSV() {
    Connection connection = ConnectionMaker.getConnection();
    // Read locations into List "MedEquipReqCSV"
    List<EquipmentServiceRequest> MedEquipReqFromCSV = new ArrayList<EquipmentServiceRequest>();
    try {
      LocationDAO locDestination = new LocationDAOImpl();
      InputStream tlCSV = UserProgram.class.getResourceAsStream(medicalEquipmentCSV);
      BufferedReader tlCSVReader = new BufferedReader(new InputStreamReader(tlCSV));
      tlCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = tlCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          EquipmentServiceRequest lnode =
              new EquipmentServiceRequest(
                  currLine[0],
                  locDestination.getLocation(currLine[1]),
                  currLine[2],
                  currLine[3],
                  Integer.parseInt(currLine[4]));
          MedEquipReqFromCSV.add(lnode);
        } else {
          System.out.println("CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Insert locations from locationsFromCSV into db table
    for (int i = 0; i < MedEquipReqFromCSV.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder medEquipReq = new StringBuilder();
        medEquipReq.append("INSERT INTO MedicalEquipmentServiceRequest VALUES(");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getRequestID() + "'" + ", ");
        medEquipReq.append(
            "'" + MedEquipReqFromCSV.get(i).getDestination().getNodeID() + "'" + ", ");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getStatus() + "'" + ", ");
        medEquipReq.append("'" + MedEquipReqFromCSV.get(i).getEquipmentType() + "'" + ", ");
        medEquipReq.append(MedEquipReqFromCSV.get(i).getQuantity());
        medEquipReq.append(")");
        initialization.execute(medEquipReq.toString());
      } catch (SQLException e) {
        System.out.println("Input for MedEquipReq " + i + " failed");
        e.printStackTrace();
        return null;
      }
    }
    return MedEquipReqFromCSV;
  }

  /** Writes the content of the location table from the database into the TowerLocations.CSV */
  private static void saveLocationDataToCSV() {
    Connection connection = ConnectionMaker.getConnection();
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
    }

    try {
      URL url = Xdb.class.getResource(locationCSV);
      FileWriter csvFile = new FileWriter(url.getFile(), false);
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
    }
  }

  /** Saves Medical Equipment data to CSV on close */
  private static void saveMedEqDataToCSV() {
    Connection connection = ConnectionMaker.getConnection();
    List<EquipmentServiceRequest> Equipment = new ArrayList<EquipmentServiceRequest>();
    LocationDAO locDestination = new LocationDAOImpl();
    try {
      Statement statement = connection.createStatement();
      ResultSet records = statement.executeQuery("SELECT * FROM MedicalEquipmentServiceRequest");
      while (records.next()) {
        Equipment.add(
            new EquipmentServiceRequest(
                records.getString("requestID"),
                locDestination.getLocation(records.getString("destination")),
                records.getString("status"),
                records.getString("equipmentType"),
                records.getInt("quantity")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(
          "An error occurred when saving MedicalEquipServRequest data to the CSV file.");
    }

    try {
      URL url = Xdb.class.getResource(medicalEquipmentCSV);
      FileWriter csvFile = new FileWriter(url.getFile(), false);
      csvFile.write("RequestID,Destination,Status,equipmentType,Quantity");
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
        if (Equipment.get(i).getEquipmentType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(Equipment.get(i).getEquipmentType() + ",");
        }
        csvFile.write(Equipment.get(i).getQuantity());
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.print("An error occurred when trying to write to the CSV file.");
      e.printStackTrace();
    }
  }
}
