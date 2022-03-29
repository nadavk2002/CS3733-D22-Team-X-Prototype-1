package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

public abstract class ServiceRequest {

  private String requestID;
  private Location destination;
  private String status;

  // private String requestingUser, assignee, serviceCompleted, serviceID;

  public ServiceRequest(String requestID, Location destination, String status) {
    this.requestID = requestID;
    this.destination = destination;
    this.status = status;
  }

  public ServiceRequest() {
    this.requestID = "";
    this.destination = new Location();
    this.status = "";
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

  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDestination(Location destination) {
    this.destination = destination;
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
