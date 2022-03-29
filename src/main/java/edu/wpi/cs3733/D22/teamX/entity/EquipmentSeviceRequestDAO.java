package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface EquipmentSeviceRequestDAO {
  public List<EquipmentServiceRequest> getAllEquipmentServiceRequests();

  public EquipmentServiceRequest getEquipmentServiceRequest(); // add ids

  public void deleteEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest);

  public void updateEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest);
}
