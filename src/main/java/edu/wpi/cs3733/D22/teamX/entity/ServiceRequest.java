package edu.wpi.cs3733.D22.teamX.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/** Represents a general service request */
public abstract class ServiceRequest {
  private LocalDateTime nullTime =
      LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC); // 1 - 1 - 1970

  private String requestID;
  private Location destination;
  private String status;
  private Employee assignee;
  private LocalDateTime CreationTime;
  private LocalDateTime PROCTime;
  private LocalDateTime DONETime;

  // did this do anything

  // private String requestingUser

  public ServiceRequest(String requestID, Location destination, String status, Employee assignee) {
    this.requestID = requestID;
    this.destination = destination;
    this.status = status;
    this.assignee = assignee;
    this.CreationTime = LocalDateTime.now();
    if (status == "PROC") {
      this.PROCTime = CreationTime;
      this.DONETime = nullTime;
    } else if (status == "DONE") {
      this.PROCTime = CreationTime;
      this.DONETime = CreationTime;
    }
  }

  public ServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      LocalDateTime creationTime,
      LocalDateTime PROCTime,
      LocalDateTime DONETime) {
    this.requestID = requestID;
    this.destination = destination;
    this.status = status;
    this.assignee = assignee;
    this.CreationTime = creationTime;
    this.PROCTime = PROCTime;
    this.DONETime = DONETime;
  }

  public ServiceRequest() {
    this.requestID = "";
    this.destination = new Location();
    this.status = "";
    this.assignee = new Employee();
    this.CreationTime = LocalDateTime.now();
    this.PROCTime = nullTime;
    this.DONETime = nullTime;
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
    // this may violate ECB but allows for this to be applied to all SRs without having to update
    // controllers but DAOs still have to be updated.
    if (this.status != status) {
      if (status == "PROC") {
        this.PROCTime = LocalDateTime.now();
        this.DONETime = nullTime;
      } else if (status == "DONE") {
        DONETime = LocalDateTime.now();
        if (PROCTime.equals(nullTime)) {
          PROCTime = DONETime;
        }
      }
    }
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

  public LocalDateTime getCreationTime() {
    return CreationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    CreationTime = creationTime;
  }

  public LocalDateTime getPROCTime() {
    return PROCTime;
  }

  public void setPROCTime(LocalDateTime PROCTime) {
    this.PROCTime = PROCTime;
  }

  public LocalDateTime getDONETime() {
    return DONETime;
  }

  public void setDONETime(LocalDateTime DONETime) {
    this.DONETime = DONETime;
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
