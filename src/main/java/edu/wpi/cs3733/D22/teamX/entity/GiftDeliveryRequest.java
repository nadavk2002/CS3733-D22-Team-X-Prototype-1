package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a medicine delivery service request */
public class GiftDeliveryRequest extends ServiceRequest {
  @Override
  public String makeRequestID() {
    return "sample";
  }
}
