package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;

// https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

public class LocationDAOImpl implements LocationDAO {
  /** constructor loads data from database */
  public LocationDAOImpl() {}

  /** gets locations list */
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
    // iterate through the list of locations to find the object
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
    // iterate through the list of locations to find the location passed in and update it in
    // locations
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
    // iterate through the list of locations to find the location passed and remove it from
    // locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(location)) {
        locations.remove(locationInd);
        locationInd--; // decrement if found
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

  /** Creates the location table in the database */
  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE Location(nodeID CHAR(10) PRIMARY KEY NOT NULL, "
              + "xCoord INT, yCoord INT, "
              + "floor VARCHAR(2), "
              + "building VARCHAR(10), "
              + "nodeType CHAR(4), "
              + "longName VARCHAR(50), shortName VARCHAR(30))");
    } catch (SQLException e) {
      System.out.println("Location table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /** Drops all the tables in the database. */
  @Override
  public void dropTable() {
    try {
      Statement dropLocation = connection.createStatement();
      dropLocation.execute("DROP TABLE Location");
    } catch (SQLException e) {
      System.out.println("Location not dropped");
      e.printStackTrace();
    }
  }

  /**
   * Read the locations from CSV file. Put locations into db table "Location"
   *
   * @return whether an exception was thrown when trying o read from the file.
   */
  @Override
  public boolean loadCSV() {
    try {
      InputStream tlCSV = DatabaseCreator.class.getResourceAsStream(locationCSV);
      BufferedReader tlCSVReader = new BufferedReader(new InputStreamReader(tlCSV));
      tlCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = tlCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          Location lnode =
              new Location(
                  currLine[0],
                  Integer.parseInt(currLine[1]),
                  Integer.parseInt(currLine[2]),
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          locations.add(lnode);
        } else {
          System.out.println("TowerLocationsX CSV file formatted improperly");
          System.exit(1);
        }
      }
      tlCSV.close();
      tlCSVReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    // Insert locations from locationsFromCSV into db table
    for (int i = 0; i < locations.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder insertLocation = new StringBuilder();
        insertLocation.append("INSERT INTO Location VALUES(");
        insertLocation.append("'" + locations.get(i).getNodeID() + "'" + ", ");
        insertLocation.append(locations.get(i).getxCoord() + ", ");
        insertLocation.append(locations.get(i).getyCoord() + ", ");
        insertLocation.append("'" + locations.get(i).getFloor() + "'" + ", ");
        insertLocation.append("'" + locations.get(i).getBuilding() + "'" + ", ");
        insertLocation.append("'" + locations.get(i).getNodeType() + "'" + ", ");
        insertLocation.append("'" + locations.get(i).getLongName() + "'" + ", ");
        insertLocation.append("'" + locations.get(i).getShortName() + "'");
        insertLocation.append(")");
        initialization.execute(insertLocation.toString());
      } catch (SQLException e) {
        System.out.println("Input for Location " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /** Writes the content of the location table from the database into the TowerLocations.CSV */
  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + locationCSV, false);
      csvFile.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
      for (int i = 0; i < locations.size(); i++) {
        csvFile.write("\n" + locations.get(i).getNodeID() + ",");
        csvFile.write(locations.get(i).getxCoord() + ",");
        csvFile.write(locations.get(i).getyCoord() + ",");
        if (locations.get(i).getFloor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getFloor() + ",");
        }
        if (locations.get(i).getBuilding() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getBuilding() + ",");
        }
        if (locations.get(i).getNodeType() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getNodeType() + ",");
        }
        if (locations.get(i).getLongName() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(locations.get(i).getLongName() + ",");
        }
        if (locations.get(i).getShortName() != null) {
          csvFile.write(locations.get(i).getShortName());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occured when updating locations csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
