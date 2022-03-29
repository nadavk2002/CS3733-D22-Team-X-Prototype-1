package edu.wpi.cs3733.D22.teamX;

public abstract class ServiceRequest {
  private String requestID;
  private Location destination;

  public String getRequestID() {
    return requestID;
  }

  public Location getDestination() {
    return destination;
  }
}
