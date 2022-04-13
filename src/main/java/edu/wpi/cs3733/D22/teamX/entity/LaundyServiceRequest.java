package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a Laundry Service Request */
public class LaundyServiceRequest extends ServiceRequest {
  private String service;

  public LaundyServiceRequest() {
    super();
    this.service = "";
  }

  public LaundyServiceRequest(
      String requestID, Location destination, String status, Employee assignee, String service) {
    super(requestID, destination, status, assignee);
    this.service = service;
  }

  public String makeRequestID() {
    return "sample";
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }
}
