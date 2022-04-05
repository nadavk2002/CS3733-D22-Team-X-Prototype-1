package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a medicine delivery service request */
public class GiftDeliveryRequest extends ServiceRequest {
  String Notes;
  private String patient;

  @Override
  public String makeRequestID() {
    return "sample";
  }

  public String getNotes() {
    return Notes;
  }

  public void setNotes(String Notes) {
    this.Notes = Notes;
  }

  public String getPatient() {
    return patient;
  }

  public void setPatient(String patient) {
    this.patient = patient;
  }
}
