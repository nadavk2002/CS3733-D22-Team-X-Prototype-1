package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface EquipmentTypeDAO extends DatabaseEntity {
  List<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>(); // EquipmentType storage
  String equipmentTypesCSV = "EquipmentTypes.csv";

  /**
   * get all EquipmentTypes stored in the equipmentTypes list
   *
   * @return all EquipmentType entries in the database
   */
  List<EquipmentType> getAllEquipmentTypes();

  /**
   * Get the EquipmentType specified by unitID
   *
   * @param model refers to an EquipmentType
   * @return the EquipmentType from the list of EquipmentTypes
   */
  EquipmentType getEquipmentType(String model);

  /**
   * removes the EquipmentType in the database with the same model as the passed EquipmentType
   *
   * @param equipmentType removed from the EquipmentType table
   */
  void deleteEquipmentType(EquipmentType equipmentType);

  /**
   * replaces the EquipmentType in the database with the same model as the passed EquipmentType
   *
   * @param equipmentType used to update the table entry
   */
  void updateEquipmentType(EquipmentType equipmentType);

  /**
   * adds EquipmentType to the database
   *
   * @param equipmentType to be added
   */
  void addEquipmentType(EquipmentType equipmentType);
}
