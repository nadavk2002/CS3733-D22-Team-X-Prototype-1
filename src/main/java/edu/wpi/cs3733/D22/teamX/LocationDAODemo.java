package edu.wpi.cs3733.D22.teamX;

import java.sql.*;

public class LocationDAODemo {
  public static void demo(Connection connection) {
    LocationDAO lDAO = new LocationDAOImpl(connection);

    // print all locations
    for (Location loc : lDAO.getAllLocations()) {
      System.out.println(loc + "\n");
    }

    // update a location
    Location updLoc = lDAO.getAllLocations().get(0);
    updLoc.setLongName("hello im a location");

    // get a location
    System.out.println(lDAO.getLocation("FDEPT00101"));
  }
}
