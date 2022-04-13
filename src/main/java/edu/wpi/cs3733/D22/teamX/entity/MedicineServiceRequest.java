package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a medicine delivery service request */
public class MedicineServiceRequest extends ServiceRequest {
  // needs patientFor
  private String rxNum;
  private String patientFor;

  public MedicineServiceRequest() {
    super();
    this.rxNum = "";
    this.patientFor = "";
  }

  public MedicineServiceRequest(
      String requestID, Location destination, String status, String assignee, String rxNum, String patientFor) {
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

  public String makeRequestID() {
    return "sample";
  }
}
