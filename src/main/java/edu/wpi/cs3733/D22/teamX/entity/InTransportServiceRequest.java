package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a transport service request */
public class InTransportServiceRequest extends ServiceRequest {
  private String patientName;
  private Location transportTo; // this is destination, this is unecessary
  private String addAccommodation;

  //  public InTransportServiceRequest(String patientName, Location transportFrom, String
  // addAccommodation) {
  //    this.patientName = patientName;
  //    this.transportTo = transportFrom;
  //    this.addAccommodation = addAccommodation;
  //  }

  public String makeRequestID() {
    return "sample";
  }

  public String getPatientName() {
    return patientName;
  }

  public Location getTransportTo() {
    return transportTo;
  }

  public String getTransportToShortName() {
    return transportTo.getShortName();
  }

  public String getAddAccommodation() {
    return addAccommodation;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public void setTransportTo(Location transportTo) {
    this.transportTo = transportTo;
  }

  public void setAddAccommodation(String addAccommodation) {
    this.addAccommodation = addAccommodation;
  }
}
