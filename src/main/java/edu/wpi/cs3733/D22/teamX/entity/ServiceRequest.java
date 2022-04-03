package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

/** Represents a general service request */
public abstract class ServiceRequest {

  private String requestID;
  private Location destination;
  private String status;
  private String assignee;

  // private String requestingUser

  public ServiceRequest(String requestID, Location destination, String status, String assignee) {
    this.requestID = requestID;
    this.destination = destination;
    this.status = status;
    this.assignee = assignee;
  }

  public ServiceRequest() {
    this.requestID = null;
    this.destination = null;
    this.status = null;
    this.assignee = null;
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

  public String getAssignee() {
    return assignee;
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

  public void setAssignee(String assignee) {
    this.assignee = assignee;
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

  /**
   * Creates random requestID for new requests
   *
   * @return a String with a certain number of characters constituting an available requestID
   */
  public abstract String makeRequestID();
}
