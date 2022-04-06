package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a medicine delivery service request */
public class MedicineServiceRequest extends ServiceRequest {
  private String rxNum;

  public MedicineServiceRequest() {
    super();
    this.rxNum = "";
  }

  public MedicineServiceRequest(
      String requestID, Location destination, String status, String assignee, String rxNum) {
    super(requestID, destination, status, assignee);
    this.rxNum = rxNum;
  }

  public String getRx() {
    return rxNum;
  }

  public void setRxNum(String rxNum) {
    this.rxNum = rxNum;
  }

  @Override
  public String makeRequestID() {
    return "sample";
  }
}
