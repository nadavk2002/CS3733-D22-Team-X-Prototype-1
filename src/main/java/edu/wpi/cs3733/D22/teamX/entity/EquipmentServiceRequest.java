package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

public class EquipmentServiceRequest extends ServiceRequest {
  private String equipmentType;
  private int quantity;

  public EquipmentServiceRequest(
      String requestID,
      Location destination,
      String status,
      //      String requestingUser,
      //      String assignee,
      String equipmentType,
      int quantity) {
    super(requestID, destination, status);
    this.equipmentType = equipmentType;
    this.quantity = quantity;
  }

  public EquipmentServiceRequest() {
    super();
    this.equipmentType = "";
    this.quantity = -1;
  }

  public String getLocationNodeID() {
    return getDestination().getNodeID();
  }

  public String getEquipmentType() {
    return equipmentType;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setEquipmentType(String equipmentType) {
    this.equipmentType = equipmentType;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
