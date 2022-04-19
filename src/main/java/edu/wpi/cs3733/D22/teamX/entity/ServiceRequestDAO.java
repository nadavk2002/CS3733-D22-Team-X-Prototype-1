package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;

public class ServiceRequestDAO {
  private GiftDeliveryRequestDAO giftDeliveryRequestDAO = GiftDeliveryRequestDAO.getDAO();
  private JanitorServiceRequestDAO janitorServiceRequestDAO = JanitorServiceRequestDAO.getDAO();
  private LabServiceRequestDAO labServiceRequestDAO = LabServiceRequestDAO.getDAO();
  private LangServiceRequestDAO langServiceRequestDAO = LangServiceRequestDAO.getDAO();
  private LaundryServiceRequestDAO laundryServiceRequestDAO = LaundryServiceRequestDAO.getDAO();
  private MealServiceRequestDAO mealServiceRequestDAO = MealServiceRequestDAO.getDAO();
  private MedicalEquipmentServiceRequestDAO medicalEquipmentServiceRequestDAO =
      MedicalEquipmentServiceRequestDAO.getDAO();
  private MedicineDeliverServiceRequestDAO medicineDeliverServiceRequestDAO =
      MedicineDeliverServiceRequestDAO.getDAO();

  public ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();

  public ServiceRequestDAO() {
    setServiceRequests();
  }

  public GiftDeliveryRequestDAO getGiftDeliveryRequestDAO() {
    return giftDeliveryRequestDAO;
  }

  public JanitorServiceRequestDAO getJanitorServiceRequestDAO() {
    return janitorServiceRequestDAO;
  }

  public LabServiceRequestDAO getLabServiceRequestDAO() {
    return labServiceRequestDAO;
  }

  public LangServiceRequestDAO getLangServiceRequestDAO() {
    return langServiceRequestDAO;
  }

  public LaundryServiceRequestDAO getLaundryServiceRequestDAO() {
    return laundryServiceRequestDAO;
  }

  public MealServiceRequestDAO getMealServiceRequestDAO() {
    return mealServiceRequestDAO;
  }

  public MedicalEquipmentServiceRequestDAO getMedicalEquipmentServiceRequestDAO() {
    return medicalEquipmentServiceRequestDAO;
  }

  public MedicineDeliverServiceRequestDAO getMedicineDeliverServiceRequestDAO() {
    return medicineDeliverServiceRequestDAO;
  }

  public void setServiceRequests() {
    ArrayList<ServiceRequest> allRequests = new ArrayList<>();
    for(ServiceRequest request:giftDeliveryRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:janitorServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:labServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:langServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:laundryServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:mealServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:medicalEquipmentServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    for(ServiceRequest request:medicineDeliverServiceRequestDAO.getAllRecords()) {
      allRequests.add(request);
    }
    this.serviceRequests=allRequests;
  }
}
