package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

/** Represents a lab work service request */
public class LabServiceRequest extends ServiceRequest {
  private String assignee; // employee id
  private String service;
  private String patientFor; // patient id

  // constructor
  public LabServiceRequest(
      String requestID,
      Location destination,
      String status,
      String assignee,
      String service,
      String patientFor) {
    super(requestID, destination, status);
    this.assignee = assignee;
    this.service = service;
    this.patientFor = patientFor;
  }

  // blank
  public LabServiceRequest() {
    super();
    this.assignee = null;
    this.service = null;
    this.patientFor = null;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getPatientFor() {
    return patientFor;
  }

  public void setPatientFor(String patientFor) {
    this.patientFor = patientFor;
  }

  @Override
  public String makeRequestID() {
    return "sample";
  }
}
