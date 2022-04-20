package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
  private SharpsDisposalRequestDAO sharpsDisposalRequestDAO = SharpsDisposalRequestDAO.getDAO();
  private MaintenanceServiceRequestDAO maintenanceServiceRequestDAO =
      MaintenanceServiceRequestDAO.getDAO();
  private InTransportServiceRequestDAO inTransportServiceRequestDAO =
      InTransportServiceRequestDAO.getDAO();

  public ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();

  private ServiceRequestDAO() {
    setServiceRequests();
  }

  private static class SingletonHelper {
    private static final ServiceRequestDAO serviceRequestDAO = new ServiceRequestDAO();
  }

  public static ServiceRequestDAO getDAO() {
    return SingletonHelper.serviceRequestDAO;
  }

  // Get all requests
  public ArrayList<ServiceRequest> getServiceRequests() {
    setServiceRequests();
    return serviceRequests;
  }

  public List<GiftDeliveryRequest> getAllGiftDeliveryRequests() {
    return giftDeliveryRequestDAO.getAllRecords();
  }

  public List<JanitorServiceRequest> getAllJanitorServiceRequests() {
    return janitorServiceRequestDAO.getAllRecords();
  }

  public List<LabServiceRequest> getAllLabServiceRequests() {
    return labServiceRequestDAO.getAllRecords();
  }

  public List<LangServiceRequest> getAllLangServiceRequests() {
    return langServiceRequestDAO.getAllRecords();
  }

  public List<LaundyServiceRequest> getAllLaundryServiceRequests() {
    return laundryServiceRequestDAO.getAllRecords();
  }

  public List<MealServiceRequest> getAllMealServiceRequests() {
    return mealServiceRequestDAO.getAllRecords();
  }

  public List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests() {
    return medicalEquipmentServiceRequestDAO.getAllRecords();
  }

  public List<MedicineServiceRequest> getAllMedicineServiceRequests() {
    return medicineDeliverServiceRequestDAO.getAllRecords();
  }

  public List<SharpsDisposalRequest> getAllSharpsDisposalServiceRequests() {
    return sharpsDisposalRequestDAO.getAllRecords();
  }

  public List<MaintenanceServiceRequest> getAllMaintenanceServiceRequests() {
    return maintenanceServiceRequestDAO.getAllRecords();
  }

  public List<InTransportServiceRequest> getAllInTransportServiceRequests() {
    return inTransportServiceRequestDAO.getAllRecords();
  }

  // Get a specific request
  public GiftDeliveryRequest getGiftDeliveryRequest(String requestID) {
    return giftDeliveryRequestDAO.getRecord(requestID);
  }

  public JanitorServiceRequest getJanitorServiceRequest(String requestID) {
    return janitorServiceRequestDAO.getRecord(requestID);
  }

  public LabServiceRequest getLabServiceRequest(String requestID) {
    return labServiceRequestDAO.getRecord(requestID);
  }

  public LangServiceRequest getLangServiceRequest(String requestID) {
    return langServiceRequestDAO.getRecord(requestID);
  }

  public LaundyServiceRequest getLaundryServiceRequest(String requestID) {
    return laundryServiceRequestDAO.getRecord(requestID);
  }

  public MealServiceRequest getMealServiceRequest(String requestID) {
    return mealServiceRequestDAO.getRecord(requestID);
  }

  public MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID) {
    return medicalEquipmentServiceRequestDAO.getRecord(requestID);
  }

  public MedicineServiceRequest getMedicineServiceRequest(String requestID) {
    return medicineDeliverServiceRequestDAO.getRecord(requestID);
  }

  public SharpsDisposalRequest getSharpsDisposalServiceRequest(String requestID) {
    return sharpsDisposalRequestDAO.getRecord(requestID);
  }

  public MaintenanceServiceRequest getMaintenanceServiceRequest(String requestID) {
    return maintenanceServiceRequestDAO.getRecord(requestID);
  }

  public InTransportServiceRequest getInTransportServiceRequest(String requestID) {
    return inTransportServiceRequestDAO.getRecord(requestID);
  }

  // Add
  public void addRecord(GiftDeliveryRequest request) {
    giftDeliveryRequestDAO.addRecord(request);
  }

  public void addRecord(JanitorServiceRequest request) {
    janitorServiceRequestDAO.addRecord(request);
  }

  public void addRecord(LabServiceRequest request) {
    labServiceRequestDAO.addRecord(request);
  }

  public void addRecord(LangServiceRequest request) {
    langServiceRequestDAO.addRecord(request);
  }

  public void addRecord(LaundyServiceRequest request) {
    laundryServiceRequestDAO.addRecord(request);
  }

  public void addRecord(MealServiceRequest request) {
    mealServiceRequestDAO.addRecord(request);
  }

  public void addRecord(MedicalEquipmentServiceRequest request) {
    medicalEquipmentServiceRequestDAO.addRecord(request);
  }

  public void addRecord(MedicineServiceRequest request) {
    medicineDeliverServiceRequestDAO.addRecord(request);
  }

  public void addRecord(SharpsDisposalRequest request) {
    sharpsDisposalRequestDAO.addRecord(request);
  }

  public void addRecord(MaintenanceServiceRequest request) {
    maintenanceServiceRequestDAO.addRecord(request);
  }

  public void addRecord(InTransportServiceRequest request) {
    inTransportServiceRequestDAO.addRecord(request);
  }

  // Upgrades people upgrades
  public void updateRecord(GiftDeliveryRequest request) {
    giftDeliveryRequestDAO.updateRecord(request);
  }

  public void updateRecord(JanitorServiceRequest request) {
    janitorServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(LabServiceRequest request) {
    labServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(LangServiceRequest request) {
    langServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(LaundyServiceRequest request) {
    laundryServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(MealServiceRequest request) {
    mealServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(MedicalEquipmentServiceRequest request) {
    medicalEquipmentServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(MedicineServiceRequest request) {
    medicineDeliverServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(SharpsDisposalRequest request) {
    sharpsDisposalRequestDAO.updateRecord(request);
  }

  public void updateRecord(MaintenanceServiceRequest request) {
    maintenanceServiceRequestDAO.updateRecord(request);
  }

  public void updateRecord(InTransportServiceRequest request) {
    inTransportServiceRequestDAO.updateRecord(request);
  }

  // Delete
  public void deleteRecord(GiftDeliveryRequest request) {
    giftDeliveryRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(JanitorServiceRequest request) {
    janitorServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(LabServiceRequest request) {
    labServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(LangServiceRequest request) {
    langServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(LaundyServiceRequest request) {
    laundryServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(MealServiceRequest request) {
    mealServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(MedicalEquipmentServiceRequest request) {
    medicalEquipmentServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRequest(MedicineServiceRequest request) {
    medicineDeliverServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRequest(SharpsDisposalRequest request) {
    sharpsDisposalRequestDAO.deleteRecord(request);
  }

  public void deleteRequest(MaintenanceServiceRequest request) {
    maintenanceServiceRequestDAO.deleteRecord(request);
  }

  public void deleteRecord(InTransportServiceRequest request) {
    inTransportServiceRequestDAO.deleteRecord(request);
  }

  // getService requests
  public ServiceRequest getServiceRequest(String requestID) {
    for (ServiceRequest request : serviceRequests) {
      if (request.getRequestID().equals(requestID)) {
        return request;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  private void setServiceRequests() {
    ArrayList<ServiceRequest> allRequests = new ArrayList<>();
    allRequests.addAll(giftDeliveryRequestDAO.getAllRecords());
    allRequests.addAll(janitorServiceRequestDAO.getAllRecords());
    allRequests.addAll(labServiceRequestDAO.getAllRecords());
    allRequests.addAll(langServiceRequestDAO.getAllRecords());
    allRequests.addAll(laundryServiceRequestDAO.getAllRecords());
    allRequests.addAll(mealServiceRequestDAO.getAllRecords());
    allRequests.addAll(medicalEquipmentServiceRequestDAO.getAllRecords());
    allRequests.addAll(medicineDeliverServiceRequestDAO.getAllRecords());
    allRequests.addAll(sharpsDisposalRequestDAO.getAllRecords());
    allRequests.addAll(maintenanceServiceRequestDAO.getAllRecords());
    allRequests.addAll(inTransportServiceRequestDAO.getAllRecords());
    this.serviceRequests = allRequests;
  }

  // Make IDs
  public String makeGiftDeliveryRequestID() {
    return giftDeliveryRequestDAO.makeID();
  }

  public String makeJanitorServiceRequestID() {
    return janitorServiceRequestDAO.makeID();
  }

  public String makeLabServiceRequestID() {
    return labServiceRequestDAO.makeID();
  }

  public String makeLangServiceRequestID() {
    return langServiceRequestDAO.makeID();
  }

  public String makeLaundryServiceRequestID() {
    return laundryServiceRequestDAO.makeID();
  }

  public String makeMealServiceRequestID() {
    return mealServiceRequestDAO.makeID();
  }

  public String makeMedicalEquipmentServiceRequestID() {
    return medicalEquipmentServiceRequestDAO.makeID();
  }

  public String makeMedicineDeliverServiceRequestID() {
    return medicineDeliverServiceRequestDAO.makeID();
  }

  public String makeSharpsDisposalServiceRequestID() {
    return sharpsDisposalRequestDAO.makeID();
  }

  public String makeMaintenanceServiceRequestID() {
    return maintenanceServiceRequestDAO.makeID();
  }

  public String makeInTransportServiceRequestID() {
    return inTransportServiceRequestDAO.makeID();
  }
}
