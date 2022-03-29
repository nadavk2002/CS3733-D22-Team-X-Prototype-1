package edu.wpi.cs3733.D22.teamX.entity;

public class ServiceRequest {
  private String requestingUser, assignee, serviceCompleted, serviceID;

  public ServiceRequest(String requestingUser, String assignee, String serviceID) {
    this.serviceID = serviceID;
    this.serviceCompleted = "";
    this.requestingUser = requestingUser;
    this.assignee = assignee;
  }

  public ServiceRequest() {
    this.serviceCompleted = "";
    this.requestingUser = "";
    this.assignee = "";
    this.serviceID = "";
  }

  public String serviceStatus() {
    return serviceCompleted;
  }

  public void updateStatus(String status) {
    this.serviceCompleted = status;
  }

  public String getRequestingUser() {
    return requestingUser;
  }

  public void setRequestingUser(String requestingUser) {
    this.requestingUser = requestingUser;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getServiceID() {
    return serviceID;
  }

  public void setServiceID(String serviceID) {
    this.serviceID = serviceID;
  }
}
