package edu.wpi.cs3733.D22.teamX.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipmentServiceRequestDAOImpl implements EquipmentSeviceRequestDAO {
  List<EquipmentServiceRequest> EquipmentServiceRequests;
  Connection connection; // store connection info

  public EquipmentServiceRequestDAOImpl(Connection connection) {
    EquipmentServiceRequests = new ArrayList<EquipmentServiceRequest>();

    try {
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM MedicalEquipmentServiceRequest");
      // go through the results
      while (resultSet.next()) {
        EquipmentServiceRequest equipmentServiceRequest = new EquipmentServiceRequest();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<EquipmentServiceRequest> getAllEquipmentServiceRequests() {
    return null;
  }

  @Override
  public EquipmentServiceRequest getEquipmentServiceRequest(String requestID) {
    return null;
  }

  @Override
  public void deleteEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest) {}

  @Override
  public void updateEquipmentServiceRequest(EquipmentServiceRequest equipmentServiceRequest) {}
}
