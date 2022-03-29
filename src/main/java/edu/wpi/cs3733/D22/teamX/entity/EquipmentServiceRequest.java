package edu.wpi.cs3733.D22.teamX.entity;

public class EquipmentServiceRequest extends ServiceRequest {
  private String equipmentType, roomNumber;
  private int numOfEquipment;

  public EquipmentServiceRequest(
      String requestingUser,
      String assignee,
      String equipmentType,
      String roomNumber,
      String serviceID,
      int numOfEquipment) {
    super(requestingUser, assignee, serviceID);
    this.equipmentType = equipmentType;
    this.roomNumber = roomNumber;
    this.numOfEquipment = numOfEquipment;
  }

  public EquipmentServiceRequest() {
    super();
    this.equipmentType = "";
    this.roomNumber = "";
    this.numOfEquipment = -1;
  }

  public String getEquipmentType() {
    return equipmentType;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setEquipmentType(String equipmentType) {
    this.equipmentType = equipmentType;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public void setNumOfEquipment(int numOfEquipment) {
    this.numOfEquipment = numOfEquipment;
  }

  public int getNumOfEquipment() {
    return numOfEquipment;
  }
}
