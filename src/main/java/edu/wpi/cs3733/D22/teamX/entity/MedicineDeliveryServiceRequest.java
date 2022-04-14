package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a medicine delivery service request */
public class MedicineDeliveryServiceRequest extends ServiceRequest {
  // needs patientFor
  private String rxNum;
  private String patientFor;

  public MedicineDeliveryServiceRequest() {
    super();
    this.rxNum = "";
    this.patientFor = "";
  }

  public MedicineDeliveryServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      String rxNum,
      String patientFor) {
    super(requestID, destination, status, assignee);
    this.rxNum = rxNum;
    this.patientFor = patientFor;
  }

  public String getRxNum() {
    return rxNum;
  }

  public String getPatientFor() {
    return patientFor;
  }

  public void setPatientFor(String patientFor) {
    this.patientFor = patientFor;
  }

  public void setRxNum(String rxNum) {
    this.rxNum = rxNum;
  }
}
