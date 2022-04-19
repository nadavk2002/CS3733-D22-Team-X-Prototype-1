package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
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

  public ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();

  public ServiceRequestDAO() {
    setServiceRequests();
  }


  //Get requests
  public ArrayList<ServiceRequest> getServiceRequests() {
    return serviceRequests;
  }
  public GiftDeliveryRequest getGiftDeliveryRequest(String requestID){
    return giftDeliveryRequestDAO.getRecord(requestID);
  }
  public JanitorServiceRequest getJanitorServiceRequest(String requestID){
    return janitorServiceRequestDAO.getRecord(requestID);
  }
  public LabServiceRequest getLabServiceRequest(String requestID){
    return labServiceRequestDAO.getRecord(requestID);
  }
  public LangServiceRequest getLangServiceRequest(String requestID){
    return langServiceRequestDAO.getRecord(requestID);
  }
  public LaundyServiceRequest getLaundryServiceRequest(String requestID){
    return laundryServiceRequestDAO.getRecord(requestID);
  }
  public MealServiceRequest getMealServiceRequest(String requestID){
    return mealServiceRequestDAO.getRecord(requestID);
  }
  public MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(String requestID){
    return medicalEquipmentServiceRequestDAO.getRecord(requestID);
  }
  public MedicineServiceRequest getMedicineServiceRequest(String requestID){
    return medicineDeliverServiceRequestDAO.getRecord(requestID);
  }

  //Add
  public void addRecord(GiftDeliveryRequest request){
    giftDeliveryRequestDAO.addRecord(request);
  }
  public void addRecord(JanitorServiceRequest request){
    janitorServiceRequestDAO.addRecord(request);
  }
  public void addRecord(LabServiceRequest request){
    labServiceRequestDAO.addRecord(request);
  }
  public void addRecord(LangServiceRequest request){
    langServiceRequestDAO.addRecord(request);
  }
  public void addRecord(LaundyServiceRequest request){
    laundryServiceRequestDAO.addRecord(request);
  }
  public void addRecord(MealServiceRequest request){
    mealServiceRequestDAO.addRecord(request);
  }
  public void addRecord(MedicalEquipmentServiceRequest request){
    medicalEquipmentServiceRequestDAO.addRecord(request);
  }
  public void addRecord(MedicineServiceRequest request){
    medicineDeliverServiceRequestDAO.addRecord(request);
  }


  //Upgrades people upgrades
  public void updateRecord(GiftDeliveryRequest request){
    giftDeliveryRequestDAO.updateRecord(request);
  }
  public void updateRecord(JanitorServiceRequest request){
    janitorServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(LabServiceRequest request){
    labServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(LangServiceRequest request){
    langServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(LaundyServiceRequest request){
    laundryServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(MealServiceRequest request){
    mealServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(MedicalEquipmentServiceRequest request){
    medicalEquipmentServiceRequestDAO.updateRecord(request);
  }
  public void updateRecord(MedicineServiceRequest request){
    medicineDeliverServiceRequestDAO.updateRecord(request);
  }

  //Delete
  public void deleteRequest(GiftDeliveryRequest request){
    giftDeliveryRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(JanitorServiceRequest request){
    janitorServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(LabServiceRequest request){
    labServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(LangServiceRequest request){
    langServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(LaundyServiceRequest request){
    laundryServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(MealServiceRequest request){
    mealServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(MedicalEquipmentServiceRequest request){
    medicalEquipmentServiceRequestDAO.deleteRecord(request);
  }
  public void deleteRequest(MedicineServiceRequest request){
    medicineDeliverServiceRequestDAO.deleteRecord(request);
  }


  public ServiceRequest getServiceRequest(String requestID){
    for (ServiceRequest request:serviceRequests) {
      if(request.getRequestID().equals(requestID)){
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
    this.serviceRequests=allRequests;
  }

  //Make IDs
  public String makeGiftDeliveryRequestID(){
    return giftDeliveryRequestDAO.makeID();
  }
  public String makeJanitorServiceRequestID(){
    return janitorServiceRequestDAO.makeID();
  }
  public String makeLabServiceRequestID(){
    return labServiceRequestDAO.makeID();
  }
  public String makeLangServiceRequestID(){
    return langServiceRequestDAO.makeID();
  }
  public String makeLaundryServiceRequestID(){
    return laundryServiceRequestDAO.makeID();
  }
  public String makeMealServiceRequestID(){
    return mealServiceRequestDAO.makeID();
  }
  public String makeMedicalEquipmentServiceRequestID(){
    return medicalEquipmentServiceRequestDAO.makeID();
  }
  public String makeMedicineDeliverServiceRequestID(){
    return medicineDeliverServiceRequestDAO.makeID();
  }

}
