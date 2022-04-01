package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests();

  MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID);

  void deleteMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);

  void updateMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);

  void addMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);
}
