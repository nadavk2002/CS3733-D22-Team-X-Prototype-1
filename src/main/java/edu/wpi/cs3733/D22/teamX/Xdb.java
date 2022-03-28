package edu.wpi.cs3733.D22.teamX;

import java.sql.*;

public class Xdb {
  /** Initializes the db and runs the user program from Spike B */
  public static void initializeAndRunDBProgram() {
    Connection dbConn = initializeDB();
    UserProgram.executeProgram(dbConn);
    try {
      dbConn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /** Initializes the database with tables and establishes a connection */
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

    createLocationTable(connection);

    System.out.println("Database created successfully");

    return connection;
  }

  private static void createLocationTable(Connection connection) {
    try {
      Statement dropLocation = connection.createStatement();
      try {
        dropLocation.execute("DROP TABLE Location");
      } catch (SQLException e) {
        System.out.println("Creating Location Table...");
      }
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

  private static void createMedicalEquipmentServiceRequestTable(Connection connection) {
    try {
      Statement dropLocation = connection.createStatement();
      try {
        dropLocation.execute("DROP TABLE Location");
      } catch (SQLException e) {
        System.out.println("Creating MedicalEquipmentServiceRequest Table...");
      }
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicalEquipmentServiceRequest(nodeID CHAR(10) PRIMARY KEY NOT NULL, "
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
}
