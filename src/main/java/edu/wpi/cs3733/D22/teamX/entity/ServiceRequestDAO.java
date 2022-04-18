package edu.wpi.cs3733.D22.teamX.entity;

public class ServiceRequestDAO {
  LocationDAO locationDAO = LocationDAO.getDAO();
  EmployeeDAO employeeDAO = EmployeeDAO.getDAO();
  EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
  EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  GiftDeliveryRequestDAO giftDeliveryRequestDAO = GiftDeliveryRequestDAO.getDAO();
  JanitorServiceRequestDAO janitorServiceRequestDAO = JanitorServiceRequestDAO.getDAO();
  LabServiceRequestDAO labServiceRequestDAO = LabServiceRequestDAO.getDAO();
  LangServiceRequestDAO langServiceRequestDAO = LangServiceRequestDAO.getDAO();
  LaundryServiceRequestDAO laundryServiceRequestDAO = LaundryServiceRequestDAO.getDAO();
  MealServiceRequestDAO mealServiceRequestDAO = MealServiceRequestDAO.getDAO();
  MedicalEquipmentServiceRequestDAO medicalEquipmentServiceRequestDAO =
      MedicalEquipmentServiceRequestDAO.getDAO();
  MedicineDeliverServiceRequestDAO medicineDeliverServiceRequestDAO =
      MedicineDeliverServiceRequestDAO.getDAO();

  public ServiceRequestDAO() {}

  public LocationDAO getLocationDAO() {
    return locationDAO;
  }

  public EmployeeDAO getEmployeeDAO() {
    return employeeDAO;
  }

  public EquipmentTypeDAO getEquipmentTypeDAO() {
    return equipmentTypeDAO;
  }

  public EquipmentUnitDAO getEquipmentUnitDAO() {
    return equipmentUnitDAO;
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
}
