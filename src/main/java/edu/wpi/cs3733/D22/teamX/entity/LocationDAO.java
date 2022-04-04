package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface LocationDAO {
  List<Location> getAllLocations(); // gets all

  Location getLocation(String nodeID); // gets induviduvidual

  void updateLocation(Location location); // updates induvidual location

  void deleteLocation(Location location); // removes induvidual location

  void addLocation(Location location);
}
