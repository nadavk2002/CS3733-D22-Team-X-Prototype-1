package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface EquipmentUnitDAO {
  List<EquipmentUnit> getAllEquipmentUnits();

  EquipmentUnit getEquipmentUnit(String nodeID);

  void deleteEquipmentUnit(EquipmentUnit equipmentUnit);

  void updateEquipmentUnit(EquipmentUnit equipmentUnit);

  void addUpdateEquipmentUnit(EquipmentUnit equipmentUnit);
}
