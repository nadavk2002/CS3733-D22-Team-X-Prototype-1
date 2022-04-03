package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;
public interface LabServiceRequestDAO {
    List<LabServiceRequest> getAllLabServiceRequests();
    
    LabServiceRequest getLabServiceRequest(String requestID);

    void deleteLabServiceRequest (LabServiceRequest labServiceRequest);

    void updateLabServiceRequest (LabServiceRequest labServiceRequest);

    void addLabServiceRequest (LabServiceRequest labServiceRequest);
}
