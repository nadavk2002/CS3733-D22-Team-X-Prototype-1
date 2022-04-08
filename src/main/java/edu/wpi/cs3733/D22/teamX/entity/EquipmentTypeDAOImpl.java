package edu.wpi.cs3733.D22.teamX.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public class EquipmentTypeDAOImpl implements EquipmentTypeDAO {

  public EquipmentTypeDAOImpl() {
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all locations and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM EquipmentType");
      while (resultSet.next()) {
        // create EquipmentType variable to be appended to the list.
        EquipmentType equipmentType = new EquipmentType();

        // go through all the fields of the EquipmentType
        equipmentType.setModel(resultSet.getString("model"));
        equipmentType.setNumUnitsTotal(Integer.parseInt(resultSet.getString("numUnitsTotal")));
        equipmentType.setNumUnitsAvailable(
            Integer.parseInt(resultSet.getString("numUnitsAvailable")));

        // append equipmentType on to the end of the equipmentTypes list
        equipmentTypes.add(equipmentType);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

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
}
