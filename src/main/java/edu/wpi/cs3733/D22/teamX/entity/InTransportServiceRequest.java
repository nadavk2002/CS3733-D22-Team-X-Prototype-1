package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a transport service request */
public class InTransportServiceRequest extends ServiceRequest {
  @Override
  public String makeRequestID() {
    return "sample";
  }
}
