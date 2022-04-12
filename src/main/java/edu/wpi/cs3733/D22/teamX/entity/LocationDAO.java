package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LocationDAO implements DAO<Location> {
  private static List<Location> locations = new ArrayList<Location>();
  private static String csv = "TowerLocationsX.csv";

  /** Creates a new LocationDAO object. */
  private LocationDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final LocationDAO locationDAO = new LocationDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static LocationDAO getDAO() {
    return SingletonHelper.locationDAO;
  }

  @Override
  public List<Location> getAllRecords() {
    return locations;
  }

  @Override
  public Location getRecord(String recordID) {
    // iterate through the list of locations to find the object
    for (Location element : locations) {
      // if the object has the same nodeID
      if (element.getNodeID().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Location does not exist");
  }

  @Override
  public void deleteRecord(Location recordObject) {
    // iterate through the list of locations to find the location passed and remove it from
    // locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(recordObject)) {
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
            "DELETE FROM Location WHERE nodeID = '" + recordObject.getNodeID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Location does not exist");
      throw new NoSuchElementException("request does not exist (in delete)");
    }
  }

  @Override
  public void updateRecord(Location recordObject) {
    // iterate through the list of locations to find the location passed in and update it in
    // locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(recordObject)) {
        locations.set(locationInd, recordObject);
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
                + recordObject.getxCoord()
                + ", yCoord = "
                + recordObject.getyCoord()
                + ", floor = '"
                + recordObject.getFloor()
                + "', building = '"
                + recordObject.getBuilding()
                + "', nodeType = '"
                + recordObject.getNodeType()
                + "', longName = '"
                + recordObject.getLongName()
                + "', shortName = '"
                + recordObject.getShortName()
                + "' "
                + "WHERE nodeID = '"
                + recordObject.getNodeID()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("location does not exist");
      throw new NoSuchElementException("request does not exist (in update)");
    }
  }

  @Override
  public void addRecord(Location recordObject) {
    locations.add(recordObject);
    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertLocation = new StringBuilder();
      insertLocation.append("INSERT INTO Location VALUES(");
      insertLocation.append("'" + recordObject.getNodeID() + "'" + ", ");
      insertLocation.append(recordObject.getxCoord() + ", ");
      insertLocation.append(recordObject.getyCoord() + ", ");
      insertLocation.append("'" + recordObject.getFloor() + "'" + ", ");
      insertLocation.append("'" + recordObject.getBuilding() + "'" + ", ");
      insertLocation.append("'" + recordObject.getNodeType() + "'" + ", ");
      insertLocation.append("'" + recordObject.getLongName() + "'" + ", ");
      insertLocation.append("'" + recordObject.getShortName() + "'");
      insertLocation.append(")");
      initialization.execute(insertLocation.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
  }

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

  @Override
  public boolean loadCSV() {
    try {
      InputStream tlCSV = DatabaseCreator.class.getResourceAsStream(csv);
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

  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
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
      System.out.println("Error occurred when updating locations csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    return null;
  }
}
