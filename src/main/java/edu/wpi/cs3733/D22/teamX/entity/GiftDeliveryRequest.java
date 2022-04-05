package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a gift delivery service request */
public class GiftDeliveryRequest extends ServiceRequest {
  String Notes;
  String giftType;

  public GiftDeliveryRequest() {
    super();
    this.Notes = "";
    this.giftType = "";
  }

  public GiftDeliveryRequest(
      String requestID,
      Location destination,
      String status,
      String assignee,
      String Notes,
      String giftType) {
    super(requestID, destination, status, assignee);
    this.Notes = Notes;
    this.giftType = giftType;
  }

  public String makeRequestID() {
    return "sample";
  }

  public String getNotes() {
    return Notes;
  }

  public void setNotes(String Notes) {
    this.Notes = Notes;
  }

  public String getGiftType() {
    return giftType;
  }

  public void setGiftType(String giftType) {
    this.giftType = giftType;
  }
}
