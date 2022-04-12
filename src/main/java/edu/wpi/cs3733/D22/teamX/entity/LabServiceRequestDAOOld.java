package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;

public interface LabServiceRequestDAOOld extends DatabaseEntity {
  List<LabServiceRequest> labServiceRequests = new ArrayList<LabServiceRequest>();
  String labServiceRequestsCSV = "LabServiceRequests.csv";

  List<LabServiceRequest> getAllRecords();

  LabServiceRequest getRecord(String recordID);

  void deleteRecord(LabServiceRequest recordObject);

  void updateRecord(LabServiceRequest recordObject);

  void addRecord(LabServiceRequest recordObject);
}
