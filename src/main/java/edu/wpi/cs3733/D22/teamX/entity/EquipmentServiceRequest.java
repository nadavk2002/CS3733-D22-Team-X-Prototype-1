package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

public class EquipmentServiceRequest extends ServiceRequest {
  private String equipmentType;
  private int quantity;

  public EquipmentServiceRequest(
      String requestID,
      Location destination,
      String status,
      //      String requestingUser,
      //      String assignee,
      String equipmentType,
      int quantity) {
    super(requestID, destination, status);
    this.equipmentType = equipmentType;
    this.quantity = quantity;
  }

  public EquipmentServiceRequest() {
    super();
    this.equipmentType = null;
    this.quantity = -1;
  }

  public String getLocationNodeID() {
    return getDestination().getNodeID();
  }

  public String getEquipmentType() {
    return equipmentType;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setEquipmentType(String equipmentType) {
    this.equipmentType = equipmentType;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Creates an eight character string constituting a requestID for a new MESR object
   *
   * @return a requestID that does not already exist in the MedicalEquipmentServiceRequest table
   */
  @Override
  public String makeRequestID() {
    //    Random rand = new Random();
    //    StringBuilder strID = new StringBuilder();
    //    List<Character> alphanumerics = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd',
    // 'e', 'f', 'g', 'h', 'i',
    //            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
    // 'z', 'A', 'B', 'C', 'D',
    //            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    // 'U', 'V', 'W', 'X', 'Y',
    //            'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    ////    while(true)
    ////    {
    //      for(int i = 0; i < 8; i++)
    //      {
    //        int range = rand.nextInt(alphanumerics.size());
    //        strID.append(alphanumerics.get(range));
    //      }
    //      return strID.toString();
    ////    }
    EquipmentServiceRequestDAO esrDAO = new EquipmentServiceRequestDAOImpl();
    int nextIDFinalNum = esrDAO.getAllEquipmentServiceRequests().size() + 1;
    return String.format("MER_%04d", nextIDFinalNum);
  }
}
