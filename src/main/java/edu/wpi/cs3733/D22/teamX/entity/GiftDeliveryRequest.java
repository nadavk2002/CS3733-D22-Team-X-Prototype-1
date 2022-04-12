package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a gift delivery service request */
public class GiftDeliveryRequest extends ServiceRequest { // rename to GiftServiceRequest
  String notes;
  String giftType;

  public GiftDeliveryRequest() {
    super();
    this.notes = "";
    this.giftType = "";
  }

  public GiftDeliveryRequest(
      String requestID,
      Location destination,
      String status,
      String assignee,
      String notes,
      String giftType) {
    super(requestID, destination, status, assignee);
    this.notes = notes;
    this.giftType = giftType;
  }

  public String makeRequestID() {
    return "sample";
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String Notes) {
    this.notes = Notes;
  }

  public String getGiftType() {
    return giftType;
  }

  public void setGiftType(String giftType) {
    this.giftType = giftType;
  }
}
