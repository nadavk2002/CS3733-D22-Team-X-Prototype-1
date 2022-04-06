package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface LocationDAO {
  /**
   * get all locations stored in the locations list
   *
   * @return all location entries in the database
   */
  List<Location> getAllLocations(); // gets all

  /**
   * Get the location specified by nodeID
   *
   * @param nodeID refers to a location
   * @return the location from the list of locations
   */
  Location getLocation(String nodeID); // gets induviduvidual

  /**
   * replaces the location in the database with the same nodeID as the passed Location
   *
   * @param location used to update the table entry
   */
  void updateLocation(Location location); // updates induvidual location

  /**
   * removes the location in the database with the same nodeID as the passed Location
   *
   * @param location removed from the Location table
   */
  void deleteLocation(Location location); // removes induvidual location

  /**
   * adds location to the database
   *
   * @param location to be added
   */
  void addLocation(Location location);
}
