package edu.wpi.cs3733.D22.teamX.entity;

import java.time.LocalDateTime;

public class MaintenanceServiceRequest extends ServiceRequest {
  private String description;

  public MaintenanceServiceRequest() {
    super();
    description = "";
  }

  public MaintenanceServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      LocalDateTime creationTime,
      LocalDateTime PROCTime,
      LocalDateTime DONETime,
      String description) {
    super(requestID, destination, status, assignee, creationTime, PROCTime, DONETime);
    this.description = description;
  }

  public MaintenanceServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      String description) {
    super(requestID, destination, status, assignee);
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
