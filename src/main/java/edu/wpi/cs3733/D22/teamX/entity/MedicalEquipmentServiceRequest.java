package edu.wpi.cs3733.D22.teamX.entity;

import java.time.LocalDateTime;

public class MedicalEquipmentServiceRequest extends ServiceRequest {
  private String equipmentType;
  private int quantity;

  public MedicalEquipmentServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      //      String requestingUser,
      //      String assignee,
      String equipmentType,
      int quantity) {
    super(requestID, destination, status, assignee);
    this.equipmentType = equipmentType;
    this.quantity = quantity;
  }

  public MedicalEquipmentServiceRequest(
          String requestID,
          Location destination,
          String status,
          Employee assignee,
          LocalDateTime creationTime,
          LocalDateTime PROCTime,
          LocalDateTime DONETime,
          String equipmentType,
          int quantity) {
    super(requestID, destination, status, assignee, creationTime, PROCTime, DONETime);
    this.equipmentType = equipmentType;
    this.quantity = quantity;
  }

  @Override
  public String getLocationShortName() {
    return super.getLocationShortName();
  }

  public MedicalEquipmentServiceRequest() {
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
