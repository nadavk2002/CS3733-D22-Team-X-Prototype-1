package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a meal service request */
public class MealServiceRequest extends ServiceRequest {
  String mealType;
  String patientID;

  public MealServiceRequest() {
    super();
    this.mealType = "";
    this.patientID = "";
  }

  public MealServiceRequest(
      String requestID,
      Location destination,
      String status,
      String assignee,
      String patientID,
      String mealType) {
    super(requestID, destination, status, assignee);
    this.mealType = mealType;
    this.patientID = patientID;
  }

  public void setMealType(String mealType) {
    this.mealType = mealType;
  }

  public void setPatientID(String patientID) {
    this.patientID = patientID;
  }

  public String getMealType() {
    return mealType;
  }

  public String getPatientID() {
    return patientID;
  }

  public String makeRequestID() {
    return "sample";
  }
}
