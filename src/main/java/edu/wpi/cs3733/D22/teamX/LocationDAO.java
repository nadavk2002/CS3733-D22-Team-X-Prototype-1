package edu.wpi.cs3733.D22.teamX;

import java.util.LinkedList;

public interface LocationDAO {
  public LinkedList<Location> getAllLocations(); // gets all

  public Location getLocation(String nodeID); // gets induviduvidual

  public void updateLocation(Location location); // updates induvidual location

  public void deleteLocation(Location location); // removes induvidual location
}
