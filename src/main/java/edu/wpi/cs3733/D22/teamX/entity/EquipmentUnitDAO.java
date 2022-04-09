package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface EquipmentUnitDAO extends DatabaseEntity {
  List<EquipmentUnit> equipmentUnits = new ArrayList<EquipmentUnit>();
  Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
  String equipmentUnitsCSV = "MedicalEquipmentUnits.csv";

  /**
   * get all EquipmentUnits stored in the equipmentUnits list
   *
   * @return all EquipmentUnit entries in the database
   */
  List<EquipmentUnit> getAllEquipmentUnits();

  /**
   * Get the EquipmentUnit specified by unitID
   *
   * @param unitID refers to an EquipmentUnit
   * @return the EquipmentUnit from the list of EquipmentUnits
   */
  EquipmentUnit getEquipmentUnit(String unitID);

  /**
   * removes the EquipmentUnit in the database with the same unitID as the passed EquipmentUnit
   *
   * @param equipmentUnit removed from the EquipmentUnit table
   */
  void deleteEquipmentUnit(EquipmentUnit equipmentUnit);

  /**
   * replaces the EquipmentUnit in the database with the same unitID as the passed EquipmentUnit
   *
   * @param equipmentUnit used to update the table entry
   */
  void updateEquipmentUnit(EquipmentUnit equipmentUnit);

  /**
   * adds equipmentUnit to the database
   *
   * @param equipmentUnit to be added
   */
  void addEquipmentUnit(EquipmentUnit equipmentUnit);
}
