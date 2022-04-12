package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.sql.*;

public class DatabaseCreator {
  // Create impls in order to avoid FK errors
  private static final LocationDAO locDAO = new LocationDAOImpl();
  private static final EquipmentTypeDAO eqtDAO = new EquipmentTypeDAOImpl();
  private static final MedicalEquipmentServiceRequestDAO mesrDAO =
      new MedicalEquipmentServiceRequestDAOImpl();
  private static final LabServiceRequestDAO labDAO = new LabServiceRequestDAOImpl();
  private static final EquipmentUnitDAO equDAO = new EquipmentUnitDAOImpl();

  /** Initializes the database with tables and establishes a connection */
  public static void initializeDB()
      throws loadSaveFromCSVException { // method must be called after app startup, so
    // user can choose server type
    System.out.println("-----Testing Apache Derby Embedded Connection-----");
    // checks whether the driver is working
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("Apache Derby driver registered!");
    // tries to create the database and establish a connection
    // currently, for client, the impls are not filled with table data
    ConnectionSingleton.getConnectionSingleton().setEmbedded();
    System.out.println("Apache Derby connection established :D");

    System.out.println("Database created successfully");
  }

  /** Saves the data from the database to the appropriate CSV files and closes the database. */
  public static boolean closeDB() throws loadSaveFromCSVException {
    try {
      ConnectionSingleton.getConnectionSingleton().getConnection().close();
      System.out.println("Connection closed successfully");
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /** Drops all database tables */
  public static void dropAllTables() {
    equDAO.dropTable();
    labDAO.dropTable();
    mesrDAO.dropTable();
    eqtDAO.dropTable();
    locDAO.dropTable();
  }

  /** Creates all database tables */
  public static void createAllTables() {
    locDAO.createTable();
    eqtDAO.createTable();
    mesrDAO.createTable();
    labDAO.createTable();
    equDAO.createTable();
  }

  /**
   * Reads data from all resource csv files and loads them into the database tables and DAO
   * Implementations
   */
  public static boolean loadAllCSV() throws loadSaveFromCSVException {
    if (!locDAO.loadCSV()
        || !eqtDAO.loadCSV()
        || !mesrDAO.loadCSV()
        || !labDAO.loadCSV()
        || !equDAO.loadCSV()) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }

  /**
   * Saves all CSV files to the specified directory
   *
   * @param dirPath path to folder on local machine
   * @return true if all CSV's are successfully saved
   * @throws loadSaveFromCSVException if save does not execute
   */
  public static boolean saveAllCSV(String dirPath) throws loadSaveFromCSVException {
    if (!locDAO.saveCSV(dirPath)
        || !eqtDAO.saveCSV(dirPath)
        || !mesrDAO.saveCSV(dirPath)
        || !labDAO.saveCSV(dirPath)
        || !equDAO.saveCSV(dirPath)) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }
}
