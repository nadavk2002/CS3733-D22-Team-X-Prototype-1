package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a Laundry Service Request */
public class LaundyServiceRequest extends ServiceRequest {
  String Laundry;

  public String makeRequestID() {
    return "sample";
  }

  public String getLaundry() {
    return Laundry;
  }

  public void setLaundry(String Laundry) {
    this.Laundry = Laundry;
  }
}
