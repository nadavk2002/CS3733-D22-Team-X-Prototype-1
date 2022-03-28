package edu.wpi.cs3733.D22.teamX.entity;

public class ServiceRequest {
  private boolean serviceCompleted; // false if incomplete
  private String requestingUser, assignee;

  public ServiceRequest(String requestingUser, String assignee) {
    this.serviceCompleted = false;
    this.requestingUser = requestingUser;
    this.assignee = assignee;
  }

  public boolean isServiceCompleted() {
    return serviceCompleted;
  }

  public void completeService() {
    this.serviceCompleted = true;
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
}
