package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentUnitDAOImpl implements EquipmentUnitDAO {
  private static final EquipmentTypeDAOImpl eqtDAOImpl = new EquipmentTypeDAOImpl();

  public EquipmentUnitDAOImpl() {}

  @Override
  public List<EquipmentUnit> getAllEquipmentUnits() {
    return equipmentUnits;
  }

  @Override
  public EquipmentUnit getEquipmentUnit(String unitID) {
    // iterate through list to find object with matching ID
    for (EquipmentUnit e : equipmentUnits) {
      if (e.getUnitID().equals(unitID)) {
        return e;
      }
    }
    throw new NoSuchElementException("unit does not exist");
  }

  @Override
  public void deleteEquipmentUnit(EquipmentUnit equipmentUnit) {
    // iterate through the linked list of locations to find the object and update it on new list
    int index = 0;
    while (index < equipmentUnits.size()) {
      if (equipmentUnits.get(index).equals(equipmentUnit)) {
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
            "DELETE FROM EquipmentUnit WHERE UNITID = '" + equipmentUnit.getUnitID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("equipment unit does not exist");
      throw new NoSuchElementException("equipment unit does not exist");
    }
    // Update total units and units available for equipment type
    eqtDAOImpl.decreaseNumTotal(equipmentUnit.getType(), 1);
    if (equipmentUnit.getIsAvailableChar() == 'Y') {
      eqtDAOImpl.decreaseAvailability(equipmentUnit.getType(), 1);
    }
  }

  @Override
  public void updateEquipmentUnit(EquipmentUnit equipmentUnit) {
    if (equipmentUnit.getIsAvailableChar() == 'Y'
        && getEquipmentUnit(equipmentUnit.getUnitID()).getIsAvailableChar() == 'N') {
      eqtDAOImpl.increaseAvailability(equipmentUnit.getType(), 1);
    }
    if (equipmentUnit.getIsAvailableChar() == 'N'
        && getEquipmentUnit(equipmentUnit.getUnitID()).getIsAvailableChar() == 'Y') {
      eqtDAOImpl.decreaseAvailability(equipmentUnit.getType(), 1);
    }
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = equipmentUnits.size(); // get size
    // go thru list
    while (index < equipmentUnits.size()) {
      if (equipmentUnits.get(index).equals(equipmentUnit)) {
        equipmentUnits.set(index, equipmentUnit); // update lsr at index position
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
              + equipmentUnit.getType()
              + "', ISAVAILABLE = '"
              + equipmentUnit.getIsAvailableChar()
              + "', CURRLOCATION = '"
              + equipmentUnit.getCurrLocation().getNodeID()
              + "' WHERE UNITID = '"
              + equipmentUnit.getUnitID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * adds object to DAO and database
   *
   * @param equipmentUnit equipment unit to be added
   */
  @Override
  public void addEquipmentUnit(EquipmentUnit equipmentUnit) {
    // list
    equipmentUnits.add(equipmentUnit);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder record = new StringBuilder();
      record.append("INSERT INTO EquipmentUnit VALUES (");
      record.append("'" + equipmentUnit.getUnitID() + "', ");
      record.append("'" + equipmentUnit.getType() + "', ");
      record.append("'" + equipmentUnit.getIsAvailableChar() + "',");
      record.append("'" + equipmentUnit.getCurrLocation().getNodeID() + "'");
      record.append(")");
      initialization.execute(record.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Database could not be updated");
    }

    // update total units and units available for equipment type
    eqtDAOImpl.increaseNumTotal(equipmentUnit.getType(), 1);
    if (equipmentUnit.getIsAvailableChar() == 'Y') {
      eqtDAOImpl.increaseAvailability(equipmentUnit.getType(), 1);
    }
  }

  /** Creates a Equipment Units table */
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
      e.printStackTrace();
    }
  }

  /**
   * Loads the data from the equipmentUnitsCSV file into the DAO
   *
   * @return whether the load was successful.
   */
  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locationDAO = new LocationDAOImpl();
      InputStream equipmentUnitStream =
          DatabaseCreator.class.getResourceAsStream(equipmentUnitsCSV);
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
                  currLine[1],
                  currLine[2].charAt(0),
                  locationDAO.getLocation(currLine[3]));
          equipmentUnits.add(equipmentUnitNode);
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
      try {
        Statement initialization = connection.createStatement();
        StringBuilder equipmentUnit = new StringBuilder();
        equipmentUnit.append("INSERT INTO EquipmentUnit VALUES(");
        equipmentUnit.append("'" + equipmentUnits.get(i).getUnitID() + "'" + ", ");
        equipmentUnit.append("'" + equipmentUnits.get(i).getType() + "'" + ", ");
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

  /**
   * saves data from DAO to equipmentUnitCSV
   *
   * @return whether the save was successful
   */
  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + equipmentUnitsCSV, false);
      csvFile.write("unitID,type,isAvailable,currLocation");
      for (int i = 0; i < equipmentUnits.size(); i++) {
        csvFile.write("\n" + equipmentUnits.get(i).getUnitID() + ",");
        if (equipmentUnits.get(i).getType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(equipmentUnits.get(i).getType() + ",");
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
}
