package edu.wpi.cs3733.D22.teamX.entity;

import java.time.LocalDateTime;

public class SharpsDisposalRequest extends ServiceRequest {
  private String type;

  // null constructor
  public SharpsDisposalRequest() {
    super();
    type = "";
  }

  // constructor
  public SharpsDisposalRequest(
      String requestID, Location destination, String status, Employee assignee, String type) {
    super(requestID, destination, status, assignee);
    this.type = type;
  }

  public SharpsDisposalRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      LocalDateTime creationTime,
      LocalDateTime PROCTime,
      LocalDateTime DONETime,
      String type) {
    super(requestID, destination, status, assignee, creationTime, PROCTime, DONETime);
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
