package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;

/** Represents a lab work service request */
public class LabServiceRequest extends ServiceRequest {
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
    super(requestID, destination, status, assignee);
    this.service = service;
    this.patientFor = patientFor;
  }

  // blank
  public LabServiceRequest() {
    super();
    this.service = null;
    this.patientFor = null;
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
    LabServiceRequestDAO lsrDAO = new LabServiceRequestDAOImpl(); //gets list of all ids
    int nextIDFinalNum = lsrDAO.getAllLabServiceRequests().size() + 1;
    return String.format("LBSR%04d", nextIDFinalNum);
  }
}
