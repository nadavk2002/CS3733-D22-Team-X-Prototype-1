package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a meal service request */
public class MealServiceRequest extends ServiceRequest {
  String mainCourse;
  String side;
  String drink;
  String patientFor;


  public MealServiceRequest() {
    super();
    this.mainCourse = "";
    this.side = "";
    this.drink = "";
    this.patientFor = "";
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
