package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LabServiceRequestDAOImpl {
  List<LabServiceRequest> labServiceRequests;
  Connection connection;

  public LabServiceRequestDAOImpl() {
    this.connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    labServiceRequests = new ArrayList<LabServiceRequest>();
    try {
      // To retrieve locations with specified destinations
      LocationDAO locDestination = new LocationDAOImpl();
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all Medical Service Requests and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM LabServiceRequests");
      // go through results
      while (resultSet.next()) {
        LabServiceRequest lsr = new LabServiceRequest();
        lsr.setRequestID(resultSet.getString("requestID"));
        lsr.setDestination(locDestination.getLocation(resultSet.getString("Destination")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
