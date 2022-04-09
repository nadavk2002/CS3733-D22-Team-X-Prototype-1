package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;

public interface LabServiceRequestDAO extends DatabaseEntity {
  List<LabServiceRequest> labServiceRequests = new ArrayList<LabServiceRequest>();
  String labServiceRequestsCSV = "LabServiceRequests.csv";

  List<LabServiceRequest> getAllLabServiceRequests();

  LabServiceRequest getLabServiceRequest(String requestID);

  void deleteLabServiceRequest(LabServiceRequest labServiceRequest);

  void updateLabServiceRequest(LabServiceRequest labServiceRequest);

  void addLabServiceRequest(LabServiceRequest labServiceRequest);
}
