package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Xdb {
  /** Initializes the db and runs the user program from Spike B. */
  public static void initializeAndRunDBProgram() {
    Connection dbConn = initializeDB();
    UserProgram.executeProgram(dbConn);
    try {
      dbConn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

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
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:derby:embed_db;create=true", "admin", "admin");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("Apache Derby connection established :D");

    dropAllTables(connection);
    createLocationTable(connection);
    createMedicalEquipmentServiceRequestTable(connection);
    loadLocationCSV(connection);
    loadMedEqServiceReqCSV(connection);

    System.out.println("Database created successfully");

    return connection;
  }

  /**
   * Creates the location table in the database
   *
   * @param connection a connection to the database
   */
  private static void createLocationTable(Connection connection) {
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

  /**
   * creates the medical equipment request service table in the database
   *
   * @param connection a connection to the database
   */
  private static void createMedicalEquipmentServiceRequestTable(Connection connection) {
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

  /**
   * Drops all the tables in the database.
   *
   * @param connection a connection to the database
   */
  private static void dropAllTables(Connection connection) {
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
   * @param connection used to connect to embedded database
   * @return a list of location objects from the "TowerLocations.CSV" file.
   */
  private static List<Location> loadLocationCSV(Connection connection) {
    // Read locations into List "locationsFromCSV"
    List<Location> locationsFromCSV = new ArrayList<Location>();
    try {
      InputStream tlCSV = UserProgram.class.getResourceAsStream("TowerLocations.csv");
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
   * @param connection used to connect to embedded database
   * @return
   */
  private static List<EquipmentServiceRequest> loadMedEqServiceReqCSV(Connection connection) {
    // Read locations into List "MedEquipReqCSV"
    List<EquipmentServiceRequest> MedEquipReqFromCSV = new ArrayList<EquipmentServiceRequest>();
    try {
      LocationDAO destination = new LocationDAOImpl(connection);
      InputStream tlCSV = UserProgram.class.getResourceAsStream("MedEquipReq.csv");
      BufferedReader tlCSVReader = new BufferedReader(new InputStreamReader(tlCSV));
      tlCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = tlCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          EquipmentServiceRequest lnode =
              new EquipmentServiceRequest(
                  currLine[0],
                  destination.getLocation(currLine[1]),
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

  /**
   * Writes the content of the location table from the database into the TowerLocations.CSV
   * @param connection a connection to the database
   */
  private static void saveLocationDataToCSV(Connection connection) {
    try {
      Statement statement = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
