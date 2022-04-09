package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;

public interface DatabaseEntity {
  Connection connection =
      ConnectionSingleton.getConnectionSingleton().getConnection(); // store connection info

  /** Creates the table for the entity with fields defined by csv file/entity class */
  void createTable();

  /** Drops the table from the database */
  void dropTable();

  /**
   * Loads the data from the csv file into the table
   *
   * @return true if load is successful
   */
  boolean loadCSV();

  /**
   * Saves the data from the database into a csv file
   *
   * @param dirPath directory path to store csv file at
   * @return true if save is successful
   */
  boolean saveCSV(String dirPath);
}
