package edu.wpi.cs3733.D22.teamX.entity;

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

  /**
   * Creates an eight character string constituting a requestID for a new MESR object
   *
   * @return a requestID that does not already exist in the MedicalEquipmentServiceRequest table
   */
  public String makeRequestID() {
    MedicalEquipmentServiceRequestDAO esrDAO = MedicalEquipmentServiceRequestDAO.getDAO();
    int nextIDFinalNum = esrDAO.getAllRecords().size() + 1;
    return String.format("MESR%04d", nextIDFinalNum);
  }
}
