package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentUnitDAOImpl implements EquipmentUnitDAO {
  List<EquipmentUnit> equipmentUnits;
  Connection connection;

  public EquipmentUnitDAOImpl() {
    this.connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    equipmentUnits = new ArrayList<EquipmentUnit>();
    try {
      // To retrieve locations with specified destinations
      LocationDAO locDestination = new LocationDAOImpl();
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM EquipmentUnit");
      // go through results
      while (resultSet.next()) {
        EquipmentUnit eqUnit = new EquipmentUnit();
        eqUnit.setUnitID(resultSet.getString("unitID"));
        eqUnit.setType(resultSet.getString("EQUIPMENTTYPE"));
        eqUnit.setAvailable(resultSet.getString("isAvailable").charAt(0));
        eqUnit.setCurrLocation(locDestination.getLocation(resultSet.getString("currLocation")));
        // add lsr to the list
        equipmentUnits.add(eqUnit);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

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
    throw new NoSuchElementException("request does not exist");
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
  }

  @Override
  public void updateEquipmentUnit(EquipmentUnit equipmentUnit) {
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
              + " EQUIPMENTTYPE = '"
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
  }
}
