package edu.wpi.cs3733.D22.teamX.entity;

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
    this.service = "";
    this.patientFor = "";
  }

  public String getLocationNodeID() {
    return getDestination().getNodeID();
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

  public String makeRequestID() {
    LabServiceRequestDAO lsrDAO = LabServiceRequestDAO.getDAO(); // gets list of all ids
    int nextIDFinalNum = lsrDAO.getAllRecords().size() + 1;

    return String.format("LBSR%04d", nextIDFinalNum);
  }
}
