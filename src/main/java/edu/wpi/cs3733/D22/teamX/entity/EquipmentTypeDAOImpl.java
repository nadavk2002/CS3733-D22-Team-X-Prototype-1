package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentTypeDAOImpl implements EquipmentTypeDAO {

  public EquipmentTypeDAOImpl() {}

  /**
   * Gets all stored EquipmentTypes
   *
   * @return list of all stored EquipmentTypes
   */
  @Override
  public List<EquipmentType> getAllEquipmentTypes() {
    return equipmentTypes;
  }

  /**
   * Gets individual EquipmentType
   *
   * @param model refers to an EquipmentType
   * @return a stored EquipmentType
   */
  @Override
  public EquipmentType getEquipmentType(String model) {
    // iterate through the list of locations to find the object
    for (EquipmentType element : equipmentTypes) {
      // if the object has the same nodeID
      if (element.getModel().equals(model)) {
        return element;
      }
    }
    throw new NoSuchElementException("EquipmentType does not exist");
  }

  /**
   * removes a stored EquipmentType
   *
   * @param equipmentType removed from the EquipmentType table and equipmentTypes
   */
  @Override
  public void deleteEquipmentType(EquipmentType equipmentType) {
    // iterate through the list of EquipmentTypes to find the EquipmentType passed and remove it
    // from equipmentTypes
    int equipmentTypeInd = 0;
    while (equipmentTypeInd < equipmentTypes.size()) {
      if (equipmentTypes.get(equipmentTypeInd).equals(equipmentType)) {
        equipmentTypes.remove(equipmentTypeInd);
        equipmentTypeInd--; // decrement if found
        break;
      }
      equipmentTypeInd++;
    }

    // if the EquipmentType is found, delete it from the EquipmentType table
    if (equipmentTypeInd < equipmentTypes.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // remove location from DB table
        statement.executeUpdate(
            "DELETE FROM EquipmentType WHERE model = '" + equipmentType.getModel() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("EquipmentType does not exist");
      throw new NoSuchElementException("request does not exist (in delete)");
    }
  }

  /**
   * updates EquipmentType based on matching model
   *
   * @param equipmentType used to update the table entry
   */
  @Override
  public void updateEquipmentType(EquipmentType equipmentType) {
    // iterate through the list of EquipmentTypes to find the EquipmentType passed in and update it
    // in equipmentTypes
    int equipmentTypeInd = 0;
    while (equipmentTypeInd < equipmentTypes.size()) {
      if (equipmentTypes.get(equipmentTypeInd).equals(equipmentType)) {
        equipmentTypes.set(equipmentTypeInd, equipmentType);
        break;
      }
      equipmentTypeInd++;
    }

    // if the EquipmentType is found, update the EquipmentType table
    if (equipmentTypeInd < equipmentTypes.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // update sql object
        statement.executeUpdate(
            "UPDATE EquipmentType SET numUnitsTotal = "
                + equipmentType.getNumUnitsTotal()
                + ", numUnitsAvailable = "
                + equipmentType.getNumUnitsAvailable()
                + " WHERE model = '"
                + equipmentType.getModel()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("EquipmentType does not exist");
      throw new NoSuchElementException("request does not exist (in update)");
    }
  }

  /**
   * add EquipmentType to the database and equipmentTypes
   *
   * @param equipmentType to be added
   */
  @Override
  public void addEquipmentType(EquipmentType equipmentType) {
    equipmentTypes.add(equipmentType);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertEquipmentType = new StringBuilder();
      insertEquipmentType.append("INSERT INTO EquipmentType VALUES(");
      insertEquipmentType.append("'" + equipmentType.getModel() + "'" + ", ");
      insertEquipmentType.append(equipmentType.getNumUnitsTotal() + ", ");
      insertEquipmentType.append(equipmentType.getNumUnitsAvailable());
      insertEquipmentType.append(")");
      initialization.execute(insertEquipmentType.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
  }

  /** Creates a Equipment Types table */
  @Override
  public void createTable() {
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE EquipmentType(model VARCHAR(20) PRIMARY KEY NOT NULL, "
              + "numUnitsTotal INT,"
              + "numUnitsAvailable INT)");
    } catch (SQLException e) {
      System.out.println("EquipmentType table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropEquipmentType = connection.createStatement();
      dropEquipmentType.execute("DROP TABLE EquipmentType");
    } catch (SQLException e) {
      System.out.println("EquipmentType not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      InputStream equipmentTypeStream =
          DatabaseCreator.class.getResourceAsStream(equipmentTypesCSV);
      BufferedReader equipmentTypeBuffer =
          new BufferedReader(new InputStreamReader(equipmentTypeStream));
      equipmentTypeBuffer.readLine();
      String nextFileLine;
      while ((nextFileLine = equipmentTypeBuffer.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 3) {
          EquipmentType equipmentTypeNode =
              new EquipmentType(
                  currLine[0], Integer.parseInt(currLine[1]), Integer.parseInt(currLine[2]));
          equipmentTypes.add(equipmentTypeNode);
        } else {
          System.out.println("EquipmentTypes CSV file formatted improperly");
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

    for (int i = 0; i < equipmentTypes.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder equipmentType = new StringBuilder();
        equipmentType.append("INSERT INTO EquipmentType VALUES(");
        equipmentType.append("'" + equipmentTypes.get(i).getModel() + "'" + ", ");
        equipmentType.append(equipmentTypes.get(i).getNumUnitsTotal() + ", ");
        equipmentType.append(equipmentTypes.get(i).getNumUnitsAvailable());
        equipmentType.append(")");
        initialization.execute(equipmentType.toString());
      } catch (SQLException e) {
        System.out.println("Input for EquipmentType " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + equipmentTypesCSV, false);
      csvFile.write("model,numUnitsTotal,numUnitsAvailable");
      for (int i = 0; i < equipmentTypes.size(); i++) {
        csvFile.write("\n" + equipmentTypes.get(i).getModel() + ",");
        if (equipmentTypes.get(i).getNumUnitsTotal() == -1) {
          csvFile.write(',');
        } else {
          csvFile.write(equipmentTypes.get(i).getNumUnitsTotal() + ",");
        }
        csvFile.write(equipmentTypes.get(i).getNumUnitsAvailable() + ",");
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occured when updating equipment types csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
