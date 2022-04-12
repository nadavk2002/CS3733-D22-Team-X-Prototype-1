package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a Laundry Service Request */
public class LaundyServiceRequest extends ServiceRequest {
  private String service;

  public LaundyServiceRequest() {
    super();
    this.service = "";
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
