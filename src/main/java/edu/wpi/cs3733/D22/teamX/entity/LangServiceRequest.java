package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a Language Interpreter Service Request */
public class LangServiceRequest extends ServiceRequest {
  String language;

  @Override
  public String makeRequestID() {
    return "sample";
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
