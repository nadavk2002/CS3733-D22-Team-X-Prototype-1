package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

public class LocationDAOImpl implements LocationDAO {
  private List<Location> locations; // location storage
  private Connection connection; // store connection info

  /** constructor loads data from database */
  public LocationDAOImpl() {
    // add locations from the database connection specified
    Connection connection = ConnectionSingleton.getConnectionSingleton().getConnection();
    locations = new ArrayList<Location>();
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all locations and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM Location");
      while (resultSet.next()) {
        // create location variable to be appended to the list.
        Location location = new Location();

        // go through all the parameters of the location
        location.setNodeID(resultSet.getString("nodeID"));
        location.setxCoord(Integer.parseInt(resultSet.getString("xCoord")));
        location.setyCoord(Integer.parseInt(resultSet.getString("yCoord")));
        location.setFloor(resultSet.getString("floor"));
        location.setBuilding(resultSet.getString("building"));
        location.setNodeType(resultSet.getString("nodeType"));
        location.setLongName(resultSet.getString("longName"));
        location.setShortName(resultSet.getString("shortName"));

        // append location on to the end of the locations list
        locations.add(location);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.connection = connection; // store connection information
  }

  /** gets locations linkedList */
  @Override
  public List<Location> getAllLocations() {
    return locations; // returns locations
  }

  /**
   * gets individual location
   *
   * @param nodeID node id of location being looked up
   */
  @Override
  public Location getLocation(String nodeID) {
    // iterate through the linked list of locations to find the object
    for (Location element : locations) {
      // if the object has the same nodeID
      if (element.getNodeID().equals(nodeID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Location does not exist");
  }

  /**
   * updates location based off matching nodeIDs.
   *
   * @param location location being updated
   */
  @Override
  public void updateLocation(Location location) {
    // iterate through the list of locations to find the location passed in and update it in locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(location)) {
        locations.set(locationInd, location);
        break;
      }
      locationInd++;
    }

    // if the location is found, update the Location table
    if (locationInd < locations.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // update sql object
        statement.executeUpdate(
            "UPDATE Location SET xCoord = "
                + location.getxCoord()
                + ", yCoord = "
                + location.getyCoord()
                + ", floor = '"
                + location.getFloor()
                + "', building = '"
                + location.getBuilding()
                + "', nodeType = '"
                + location.getNodeType()
                + "', longName = '"
                + location.getLongName()
                + "', shortName = '"
                + location.getShortName()
                + "' "
                + "WHERE nodeID = '"
                + location.getNodeID()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("location does not exist");
      throw new NoSuchElementException("request does not exist (in update)");
    }
  }

  /**
   * removes location from database.
   *
   * @param location location to be removed.
   */
  @Override
  public void deleteLocation(Location location) {
    // iterate through the linked list of locations to find the location passed and remove it from locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(location)) {
        locations.remove(locationInd);
        locationInd--;  // decrement if found
        break;
      }
      locationInd++;
    }

    // if the location is found, delete it from the Location table
    if (locationInd < locations.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // remove location from DB table
        statement.executeUpdate(
            "DELETE FROM Location WHERE nodeID = '" + location.getNodeID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Location does not exist");
      throw new NoSuchElementException("request does not exist (in delete)");
    }
  }

  @Override
  public void addLocation(Location location) {
    locations.add(location);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertLocation = new StringBuilder();
      insertLocation.append("INSERT INTO Location VALUES(");
      insertLocation.append("'" + location.getNodeID() + "'" + ", ");
      insertLocation.append(location.getxCoord() + ", ");
      insertLocation.append(location.getyCoord() + ", ");
      insertLocation.append("'" + location.getFloor() + "'" + ", ");
      insertLocation.append("'" + location.getBuilding() + "'" + ", ");
      insertLocation.append("'" + location.getNodeType() + "'" + ", ");
      insertLocation.append("'" + location.getLongName() + "'" + ", ");
      insertLocation.append("'" + location.getShortName() + "'");
      insertLocation.append(")");
      initialization.execute(insertLocation.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
  }
}
