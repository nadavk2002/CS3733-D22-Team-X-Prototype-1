package edu.wpi.cs3733.D22.teamX;

import java.sql.*;
import java.util.LinkedList;

// https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

public class LocationDAOImpl implements LocationDAO {
  LinkedList<Location> locations; // location storage

  // constructor
  // loads from the Database
  /**
   * creates the DAO and loads data from database
   *
   * @param connection db to connect to to get location data from
   */
  public LocationDAOImpl(Connection connection) {
    // add locations from the database connection specified
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all locations and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM Location");
      // determine the number of colunms
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      int columnsNumber = resultSetMetaData.getColumnCount();
      // place data into linked list
      while (resultSet.next()) {
        // create location varible to be appended to the link list.
        Location location = new Location();

        // go through all of the parameters of the location
        for (int i = 1; i <= columnsNumber; i++) {
          // print column of data remove after testing
          System.out.print(
              resultSetMetaData.getColumnName(i).replace("_", " ")
                  + ": "
                  + resultSet.getString(i)
                  + " ");

          // there is definetly a more efficent way of doing this
          // set params of Location based on incoming column name
          switch (resultSetMetaData.getColumnName(i)) {
            case "nodeID":
              location.setNodeID(resultSet.getString(i));
              break;
            case "xCoord":
              location.setxCoord(Integer.parseInt(resultSet.getString(i)));
              break;
            case "yCoord":
              location.setxCoord(Integer.parseInt(resultSet.getString(i)));
              break;
            case "floor":
              location.setFloor(resultSet.getString(i));
              break;
            case "building":
              location.setBuilding(resultSet.getString(i));
              break;
            case "nodeType":
              location.setNodeType(resultSet.getString(i));
              break;
            case "longName":
              location.setLongName(resultSet.getString(i));
              break;
            case "shortName":
              location.setShortName(resultSet.getString(i));
              break;
              // um how did we get here?
            default:
              System.out.println("invalid Location param from DB");
              break;
          }
        }
        // create new line
        System.out.println(" ");
        // append location on to the end of the linked list
        locations.add(location);
      }
      // creation done

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public LinkedList<Location> getAllLocations() {
    return null;
  }

  @Override
  public Location getLocation(String nodeID) {
    return null;
  }

  @Override
  public void updateLocation(Location location) {}

  @Override
  public void deleteLocation(Location location) {}
}
