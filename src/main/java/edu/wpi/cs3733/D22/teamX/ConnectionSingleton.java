package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionSingleton {
  INSTANCE;

  private static final String embeddedURL = "jdbc:derby:embed_db;create=true";
  private static final String clientURLAlreadyCreated = "jdbc:derby://localhost:1527/client_db";
  private static final String clientURLCreatingDB =
      "jdbc:derby://localhost:1527/client_db;create=true";
  private static final String username = "admin";
  private static final String password = "admin";

  private Connection connection;
  //  private String connectionType;

  /**
   * Establish a connection to an Apache Derby embedded database and load csv data into its tables
   */
  public void setEmbedded() {
    // If connection already established, close it
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Embedded connection closed successfully");
      } catch (SQLException e) {
        e.printStackTrace();
        return;
      }
    }
    // Establish connection to embedded database
    try {
      connection = DriverManager.getConnection(embeddedURL);
      DatabaseCreator.setAllDAOVars();
      DatabaseCreator.clearAllDAO();
      DatabaseCreator.dropAllTables();
      DatabaseCreator.createAllTables();
      DatabaseCreator.loadAllCSV();
    } catch (SQLException | loadSaveFromCSVException e) {
      System.out.println("Embedded connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
    //    connectionType = "embedded";
  }

  /** Establish a connection to an Apache Derby client database and load csv data into its tables */
  public void setClient() {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Client connection closed successfully");
      } catch (SQLException e) {
        e.printStackTrace();
        return;
      }
    }
    // Try connecting to client database. If unable, create the database and connect to it.
    try {
      connection = DriverManager.getConnection(clientURLAlreadyCreated);
      DatabaseCreator.setAllDAOVars();
      // DatabaseCreator.fillAllDAO();
      System.out.println("Client database already created");
    } catch (SQLException dbNotYetCreated) {
      System.out.println("Creating client database");
      try {
        connection = DriverManager.getConnection(clientURLCreatingDB);
        DatabaseCreator.setAllDAOVars();
        DatabaseCreator.createAllTables();
        DatabaseCreator.loadAllCSV();
      } catch (SQLException e) {
        System.out.println("CONNECTION NOT ESTABLISHED");
      } catch (loadSaveFromCSVException e) {
        System.out.println("CSV FILES NOT LOADED");
      }
    }
    //    connectionType = "client";
  }

  public static ConnectionSingleton getConnectionSingleton() {
    return INSTANCE;
  }

  public Connection getConnection() {
    return connection;
  }

  //  public String getConnectionType() {
  //    return connectionType;
  //  }
}
