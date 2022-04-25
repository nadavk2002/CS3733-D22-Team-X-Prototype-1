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

public class EquipmentUnitDAO implements DAO<EquipmentUnit> {
  private static List<EquipmentUnit> equipmentUnits = new ArrayList<EquipmentUnit>();
  private static String csv = "MedicalEquipmentUnits.csv";
  private static EquipmentTypeDAO eqtDAO = EquipmentTypeDAO.getDAO();

  private EquipmentUnitDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      equipmentUnits.clear();
    }
  }

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static EquipmentUnitDAO getDAO() {
    return SingletonHelper.equipmentUnitDAO;
  }

  @Override
  public List<EquipmentUnit> getAllRecords() {
    return equipmentUnits;
  }

  @Override
  public EquipmentUnit getRecord(String recordID) {
    // iterate through list to find object with matching ID
    for (EquipmentUnit e : equipmentUnits) {
      if (e.getUnitID().equals(recordID)) {
        return e;
      }
    }
    throw new NoSuchElementException("unit does not exist");
  }

  @Override
  public void deleteRecord(EquipmentUnit recordObject) {
    // remove unit from currLocation's list of units
    recordObject.getCurrLocation().removeUnit(recordObject);
    // iterate through the linked list of locations to find the object and update it on new list
    int index = 0;
    while (index < equipmentUnits.size()) {
      if (equipmentUnits.get(index).equals(recordObject)) {
        equipmentUnits.remove(index);
        index--;
        break;
      }
      index++;
    }

    if (index < equipmentUnits.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // remove location from DB table
        statement.executeUpdate(
            "DELETE FROM EquipmentUnit WHERE UNITID = '" + recordObject.getUnitID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("equipment unit does not exist");
      throw new NoSuchElementException("equipment unit does not exist");
    }
    // Update total units and units available for equipment type
    eqtDAO.decreaseNumTotal(recordObject.getType(), 1);
    if (recordObject.getIsAvailableChar() == 'Y') {
      eqtDAO.decreaseAvailability(recordObject.getType(), 1);
    }
  }

  @Override
  public void updateRecord(EquipmentUnit recordObject) {
    // if unit changed to be available or unavailable, adjust availability of EquipmentType
    if (recordObject.getIsAvailableChar() == 'Y'
        && getRecord(recordObject.getUnitID()).getIsAvailableChar() == 'N') {
      eqtDAO.increaseAvailability(recordObject.getType(), 1);
    }
    if (recordObject.getIsAvailableChar() == 'N'
        && getRecord(recordObject.getUnitID()).getIsAvailableChar() == 'Y') {
      eqtDAO.decreaseAvailability(recordObject.getType(), 1);
    }
    // remove unit from old location and add to new one if location changes in the update
    if (!recordObject
        .getCurrLocation()
        .equals(getRecord(recordObject.getUnitID()).getCurrLocation())) {
      getRecord(recordObject.getUnitID()).getCurrLocation().removeUnit(recordObject);
      recordObject.getCurrLocation().addUnit(recordObject);
    }
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = equipmentUnits.size(); // get size
    // go thru list
    while (index < equipmentUnits.size()) {
      if (equipmentUnits.get(index).equals(recordObject)) {
        equipmentUnits.set(index, recordObject); // update lsr at index position
        break; // exit loop
      }
      index++;
    }
    if (index == intitialSize) {
      throw new NoSuchElementException("equipment unit does not exist");
    }
    // update db table.
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE EquipmentUnit SET"
              + " type = '"
              + recordObject.getType().getModel()
              + "', ISAVAILABLE = '"
              + recordObject.getIsAvailableChar()
              + "', CURRLOCATION = '"
              + recordObject.getCurrLocation().getNodeID()
              + "' WHERE UNITID = '"
              + recordObject.getUnitID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(EquipmentUnit recordObject) {
    // list
    equipmentUnits.add(recordObject);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder record = new StringBuilder();
      record.append("INSERT INTO EquipmentUnit VALUES (");
      record.append("'" + recordObject.getUnitID() + "', ");
      record.append("'" + recordObject.getType().getModel() + "', ");
      record.append("'" + recordObject.getIsAvailableChar() + "',");
      record.append("'" + recordObject.getCurrLocation().getNodeID() + "'");
      record.append(")");
      initialization.execute(record.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("EquipmentUnit database could not be updated");
    }

    // update total units and units available for equipment type
    eqtDAO.increaseNumTotal(recordObject.getType(), 1);
    if (recordObject.getIsAvailableChar() == 'Y') {
      eqtDAO.increaseAvailability(recordObject.getType(), 1);
    }
    recordObject
        .getCurrLocation()
        .addUnit(recordObject); // add unit to currLocation's list of units
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE EquipmentUnit(UnitID CHAR(8) PRIMARY KEY NOT NULL, "
              + "type VARCHAR(20),"
              + "isAvailable CHAR(1),"
              + "currLocation CHAR(10),"
              + "CONSTRAINT MEUN_currLocation_fk "
              + "FOREIGN KEY (currLocation) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MEUN_EquipmentType_fk "
              + "FOREIGN KEY (type) REFERENCES EquipmentType(model) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("EquipmentUnit table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropEquipmentUnit = connection.createStatement();
      dropEquipmentUnit.execute("DROP TABLE EquipmentUnit");
    } catch (SQLException e) {
      System.out.println("EquipmentUnit not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    LocationDAO locationDAO = LocationDAO.getDAO();
    EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
    try {
      InputStream equipmentUnitStream =
          DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader equipmentUnitBuffer =
          new BufferedReader(new InputStreamReader(equipmentUnitStream));
      equipmentUnitBuffer.readLine();
      String nextFileLine;
      while ((nextFileLine = equipmentUnitBuffer.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 4) {
          EquipmentUnit equipmentUnitNode =
              new EquipmentUnit(
                  currLine[0],
                  equipmentTypeDAO.getRecord(currLine[1]),
                  currLine[2].charAt(0),
                  locationDAO.getRecord(currLine[3]));
          equipmentUnits.add(equipmentUnitNode);
          equipmentUnitNode
              .getCurrLocation()
              .addUnit(equipmentUnitNode); // add unit to currLocation's list of units
        } else {
          System.out.println("MedicalEquipmentUnits CSV file formatted improperly");
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

    for (int i = 0; i < equipmentUnits.size(); i++) {
      // add equipmentUnit to EquipmentUnit table
      try {
        Statement initialization = connection.createStatement();
        StringBuilder equipmentUnit = new StringBuilder();
        equipmentUnit.append("INSERT INTO EquipmentUnit VALUES(");
        equipmentUnit.append("'" + equipmentUnits.get(i).getUnitID() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnits.get(i).getType().getModel() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnits.get(i).getIsAvailableChar() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnits.get(i).getCurrLocation().getNodeID() + "'");
        equipmentUnit.append(")");
        initialization.execute(equipmentUnit.toString());
      } catch (SQLException e) {
        System.out.println("Input for EquipmentUnit " + i + " failed");
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
      csvFile.write("unitID,type,isAvailable,currLocation");
      for (int i = 0; i < equipmentUnits.size(); i++) {
        csvFile.write("\n" + equipmentUnits.get(i).getUnitID() + ",");
        if (equipmentUnits.get(i).getType().getModel() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(equipmentUnits.get(i).getType().getModel() + ",");
        }
        csvFile.write(equipmentUnits.get(i).getIsAvailableChar() + ",");
        if (equipmentUnits.get(i).getCurrLocation() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(equipmentUnits.get(i).getCurrLocation().getNodeID());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occured when updating equipment units csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Fills equipmentUnits with data from the sql table
   *
   * @return true if equipmentUnits is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      equipmentUnits.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM EquipmentUnit");
      while (results.next()) {
        EquipmentUnit toAdd = new EquipmentUnit();
        toAdd.setUnitID(results.getString("UnitID"));
        toAdd.setType(
            EquipmentTypeDAO.getDAO()
                .getRecord(results.getString("type"))); // must be an EquipmentType object!
        toAdd.setAvailable(results.getString("isAvailable").charAt(0));
        toAdd.setCurrLocation(LocationDAO.getDAO().getRecord(results.getString("currLocation")));
        equipmentUnits.add(toAdd);
        toAdd.getCurrLocation().addUnit(toAdd); // add unit to currLocation's list of units
      }
    } catch (SQLException e) {
      System.out.println("EquipmentUnitDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    EquipmentUnitDAO equDAO = EquipmentUnitDAO.getDAO(); // gets list of all ids
    int nextIDFinalNum = equDAO.getAllRecords().size() + 1;
    return String.format("MEUN%04d", nextIDFinalNum);
  }
}
