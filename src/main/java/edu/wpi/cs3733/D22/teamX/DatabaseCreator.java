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
    System.out.println("Apache Derby connection established :D");

    dropAllTables();
    createAllTables();
    loadAllCSV();

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
  private static void dropAllTables() {
    labDAO.dropTable();
    mesrDAO.dropTable();
    equDAO.dropTable();
    eqtDAO.dropTable();
    locDAO.dropTable();
  }

  /** Creates all database tables */
  private static void createAllTables() {
    locDAO.createTable();
    eqtDAO.createTable();
    equDAO.createTable();
    mesrDAO.createTable();
    labDAO.createTable();
  }

  /**
   * Reads data from all resource csv files and loads them into the database tables and DAO
   * Implementations
   */
  private static void loadAllCSV() {
    locDAO.loadCSV();
    eqtDAO.loadCSV();
    equDAO.loadCSV();
    mesrDAO.loadCSV();
    labDAO.loadCSV();
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
        || !equDAO.saveCSV(dirPath)
        || !mesrDAO.saveCSV(dirPath)
        || !labDAO.saveCSV(dirPath)) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }
}
