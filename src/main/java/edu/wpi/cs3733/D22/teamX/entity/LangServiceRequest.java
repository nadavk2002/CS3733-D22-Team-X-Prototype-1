package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a Language Interpreter Service Request */
public class LangServiceRequest extends ServiceRequest {
  @Override
  public String makeRequestID() {
    return "sample";
  }
}
