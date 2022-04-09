package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface MedicalEquipmentServiceRequestDAO extends DatabaseEntity {
  List<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequests =
      new ArrayList<MedicalEquipmentServiceRequest>();
  Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
  String medicalEquipmentServRequestCSV = "MedEquipReq.csv";

  /**
   * get all Medical Equipment Service Requests (MESR) stored in the MESR list
   *
   * @return all MESR entries in the database
   */
  List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests();

  /**
   * Get the MESR specified by requestID
   *
   * @param requestID refers to a MESR
   * @return the MESR from the list of MESRs
   */
  MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID);

  /**
   * removes the MESR in the database with the same requestID as the passed MESR
   *
   * @param medicalEquipmentServiceRequest removed from the MESR table
   */
  void deleteMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);

  /**
   * * replaces the MESR in the database with the same requestID as the passed MESR
   *
   * @param medicalEquipmentServiceRequest MESR used to update the table entry
   */
  void updateMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);

  /**
   * adds medicalEquipmentServiceRequest to the database
   *
   * @param medicalEquipmentServiceRequest to be added
   */
  void addMedicalEquipmentServiceRequest(
      MedicalEquipmentServiceRequest medicalEquipmentServiceRequest);
}
