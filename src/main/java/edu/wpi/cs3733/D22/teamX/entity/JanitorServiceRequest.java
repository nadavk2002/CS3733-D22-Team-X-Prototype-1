package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a janitorial service request */
public class JanitorServiceRequest extends ServiceRequest {
  private String description;

  public JanitorServiceRequest() {
    super();
    this.description = "";
  }

  public JanitorServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      String serviceType) {
    super(requestID, destination, status, assignee);
    this.description = serviceType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String makeRequestID() {
    return "sample";
  }
}
