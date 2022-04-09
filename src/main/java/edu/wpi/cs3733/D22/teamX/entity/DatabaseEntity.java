package edu.wpi.cs3733.D22.teamX.entity;

public interface DatabaseEntity {
  void createTable();

  void dropTable();

  boolean loadCSV();

  boolean saveCSV(String dirPath);
}
