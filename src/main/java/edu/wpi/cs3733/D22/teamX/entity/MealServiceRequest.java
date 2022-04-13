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
      Employee assignee,
      String mainCourse,
      String side,
      String drink,
      String patientFor) {
    super(requestID, destination, status, assignee);
    this.mainCourse = mainCourse;
    this.side = side;
    this.drink = drink;
    this.patientFor = patientFor;
  }

  public String getMainCourse() {
    return mainCourse;
  }

  public void setMainCourse(String mainCourse) {
    this.mainCourse = mainCourse;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getDrink() {
    return drink;
  }

  public void setDrink(String drink) {
    this.drink = drink;
  }

  public String getPatientFor() {
    return patientFor;
  }

  public void setPatientFor(String patientFor) {
    this.patientFor = patientFor;
  }

  // backwards compatability stuff please don't kill me
  public String makeRequestID() {
    return "sample";
  }

  public void setMealType(String mainCourse) {
    this.mainCourse = mainCourse;
  }

  public void setPatientID(String patientFor) {
    this.patientFor = patientFor;
  }
}
