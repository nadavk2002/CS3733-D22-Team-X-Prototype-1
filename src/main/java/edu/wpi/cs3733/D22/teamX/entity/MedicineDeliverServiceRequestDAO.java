package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;

public class MedicineDeliverServiceRequestDAO implements DAO<MedicineServiceRequest> {
  private static List<MedicineServiceRequest> medicineServiceRequests =
      new ArrayList<MedicineServiceRequest>();
  private static String csv = "MedicineDeliveryRequests.csv";

  private MedicineDeliverServiceRequestDAO() {}

  private static class SingletonHelper(){
      private static final MedicineDeliverServiceRequestDAO medicineDeliveryServiceRequestDAO = new MedicineDeliverServiceRequestDAO();
  }

  public static MedicineDeliverServiceRequestDAO getDAO() {
      return SingletonHelper.medicineDeliveryServiceRequestDAO;
  }


  @Override
  public List<MedicineServiceRequest> getAllRecords() {
    return medicineServiceRequests
  }

  @Override
  public MedicineServiceRequest getRecord(String recordID) {
    return null;
  }

  @Override
  public void deleteRecord(MedicineServiceRequest recordObject) {}

  @Override
  public void updateRecord(MedicineServiceRequest recordObject) {}

  @Override
  public void addRecord(MedicineServiceRequest recordObject) {}

  @Override
  public void createTable() {}

  @Override
  public void dropTable() {}

  @Override
  public boolean loadCSV() {
    return false;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    return false;
  }

  @Override
  public String makeID() {
    return null;
  }
}
