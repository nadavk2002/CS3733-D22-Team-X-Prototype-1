package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentTypeDAO implements DAO<EquipmentType> {
  private static List<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>();
  private static String csv = "EquipmentTypes.csv";

  /** Creates a new EquipmentTypeDAO object. */
  private EquipmentTypeDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      equipmentTypes.clear();
    }
  }

  /** Singleton helper class. */
  private static class SingletonHelper {
    private static final EquipmentTypeDAO equipmentTypeDAO = new EquipmentTypeDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO singleton object.
   */
  public static EquipmentTypeDAO getDAO() {
    return SingletonHelper.equipmentTypeDAO;
  }

  @Override
  public List<EquipmentType> getAllRecords() {
    return equipmentTypes;
  }

  @Override
  public EquipmentType getRecord(String recordID) {
    // iterate through the list of locations to find the object
    for (EquipmentType element : equipmentTypes) {
      // if the object has the same nodeID
      if (element.getModel().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("EquipmentType does not exist");
  }

  @Override
  public void deleteRecord(EquipmentType recordObject) {
    // iterate through the list of EquipmentTypes to find the EquipmentType passed and remove it
    // from equipmentTypes
    int equipmentTypeInd = 0;
    while (equipmentTypeInd < equipmentTypes.size()) {
      if (equipmentTypes.get(equipmentTypeInd).equals(recordObject)) {
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
            "DELETE FROM EquipmentType WHERE model = '" + recordObject.getModel() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("EquipmentType does not exist");
      throw new NoSuchElementException("request does not exist (in delete)");
    }
  }

  @Override
  public void updateRecord(EquipmentType recordObject) {
    // iterate through the list of EquipmentTypes to find the EquipmentType passed in and update it
    // in equipmentTypes
    int equipmentTypeInd = 0;
    while (equipmentTypeInd < equipmentTypes.size()) {
      if (equipmentTypes.get(equipmentTypeInd).equals(recordObject)) {
        equipmentTypes.set(equipmentTypeInd, recordObject);
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
                + recordObject.getNumUnitsTotal()
                + ", numUnitsAvailable = "
                + recordObject.getNumUnitsAvailable()
                + " WHERE model = '"
                + recordObject.getModel()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("EquipmentType does not exist");
      throw new NoSuchElementException("request does not exist (in update)");
    }
  }

  @Override
  public void addRecord(EquipmentType recordObject) {
    equipmentTypes.add(recordObject);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertEquipmentType = new StringBuilder();
      insertEquipmentType.append("INSERT INTO EquipmentType VALUES(");
      insertEquipmentType.append("'" + recordObject.getModel() + "'" + ", ");
      insertEquipmentType.append(recordObject.getNumUnitsTotal() + ", ");
      insertEquipmentType.append(recordObject.getNumUnitsAvailable());
      insertEquipmentType.append(")");
      initialization.execute(insertEquipmentType.toString());
    } catch (SQLException e) {
      System.out.println("EquipmentType database could not be updated");
      return;
    }
  }

  @Override
  public void createTable() {
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
          DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
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
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
      csvFile.write("model,numUnitsTotal,numUnitsAvailable");
      for (int i = 0; i < equipmentTypes.size(); i++) {
        csvFile.write("\n" + equipmentTypes.get(i).getModel() + ",");
        if (equipmentTypes.get(i).getNumUnitsTotal() == -1) {
          csvFile.write(',');
        } else {
          csvFile.write(equipmentTypes.get(i).getNumUnitsTotal() + ",");
        }
        csvFile.write(Integer.toString(equipmentTypes.get(i).getNumUnitsAvailable()));
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

  /**
   * Fills equipmentTypes with data from the sql table
   *
   * @return true if equipmentTypes is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      equipmentTypes.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM EquipmentType");
      while (results.next()) {
        EquipmentType toAdd = new EquipmentType();
        toAdd.setModel(results.getString("model"));
        toAdd.setNumUnitsTotal(results.getInt("numUnitsTotal"));
        toAdd.setNumUnitsAvailable(results.getInt("numUnitsAvailable"));
        equipmentTypes.add(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("EquipmentTypeDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    return null;
  }

  public void decreaseAvailability(String model, int quantityMadeUnavailable) {
    EquipmentType et = new EquipmentType();
    et.setModel(model);
    et.setNumUnitsTotal(getRecord(model).getNumUnitsTotal());
    et.setNumUnitsAvailable(getRecord(model).getNumUnitsAvailable() - quantityMadeUnavailable);
    updateRecord(et);
  }

  public void increaseAvailability(String model, int quantityMadeAvailable) {
    EquipmentType et = new EquipmentType();
    et.setModel(model);
    et.setNumUnitsTotal(getRecord(model).getNumUnitsTotal());
    et.setNumUnitsAvailable(getRecord(model).getNumUnitsAvailable() + quantityMadeAvailable);
    updateRecord(et);
  }

  public void decreaseNumTotal(String model, int quantityRemoved) {
    EquipmentType et = new EquipmentType();
    et.setModel(model);
    et.setNumUnitsTotal(getRecord(model).getNumUnitsTotal() - quantityRemoved);
    et.setNumUnitsAvailable(getRecord(model).getNumUnitsAvailable());
    updateRecord(et);
  }

  public void increaseNumTotal(String model, int quantityAdded) {
    EquipmentType et = new EquipmentType();
    et.setModel(model);
    et.setNumUnitsTotal(getRecord(model).getNumUnitsTotal() + quantityAdded);
    et.setNumUnitsAvailable(getRecord(model).getNumUnitsAvailable());
    updateRecord(et);
  }
}
