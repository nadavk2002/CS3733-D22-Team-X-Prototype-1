package edu.wpi.cs3733.D22.teamX.entity;

import java.util.Objects;

/** Represents a general service request */
public abstract class ServiceRequest {

  private String requestID;
  private Location destination;
  private String status;
  private Employee assignee;

  // did this do anything

  // private String requestingUser

  public ServiceRequest(String requestID, Location destination, String status, Employee assignee) {
    this.requestID = requestID;
    this.destination = destination;
    this.status = status;
    this.assignee = assignee;
  }

  public ServiceRequest() {
    this.requestID = "";
    this.destination = new Location();
    this.status = "";
    this.assignee = new Employee();
  }

  public String getRequestID() {
    return requestID;
  }

  public String getStatus() {
    return status;
  }

  public Location getDestination() {
    return destination;
  }

  public Employee getAssignee() {
    return assignee;
  }

  public String getAssigneeID() {
    return assignee.getEmployeeID();
  }

  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDestination(Location destination) {
    this.destination = destination;
  }

  public void setAssignee(Employee assignee) {
    this.assignee = assignee;
  }

  public String getLocationShortName() {
    return destination.getShortName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ServiceRequest serviceRequest = (ServiceRequest) o;
    return Objects.equals(requestID, serviceRequest.requestID);
  }

  public String getSimpleName() {
    // there's definitely a way to simplify this, maybe a list of all possible requests?
    // whatever tho
    return this.getClass().getSimpleName();
    //    if (this instanceof GiftDeliveryRequest) return "GiftDeliveryRequest";
    //    if (this instanceof JanitorServiceRequest) return "JanitorServiceRequest";
    //    if (this instanceof LabServiceRequest) return "LabServiceRequest";
    //    if (this instanceof LangServiceRequest) return "LangServiceRequest";
    //    if (this instanceof LaundyServiceRequest) return "LaundyServiceRequest";
    //    if (this instanceof MealServiceRequest) return "MealServiceRequest";
    //    if (this instanceof MedicalEquipmentServiceRequest) return
    // "MedicalEquipmentServiceRequest";
    //    if (this instanceof MedicineServiceRequest) return "MedicineServiceRequest";
    //    if (this instanceof SharpsDisposalRequest) return "SharpsDisposalRequest";
    //    if (this instanceof InTransportServiceRequest) return "InTransportServiceRequest";

    //    System.out.println("ADD NEW SERVICE REQUEST HERE OTHERWISE MAP EDITOR WONT WORK :[");
    //    return null;
  }

  //  public String getRequestingUser() {
  //    return requestingUser;
  //  }

  //  public void setRequestingUser(String requestingUser) {
  //    this.requestingUser = requestingUser;
  //  }

  //  public String getAssignee() {
  //    return assignee;
  //  }

  //  public void setAssignee(String assignee) {
  //    this.assignee = assignee;
  //  }
}
