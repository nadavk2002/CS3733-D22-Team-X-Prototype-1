package edu.wpi.teamX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserProgram {
  private static List<Location> locationsFromCSV;
  private static List<Location> locationsFromDB;

  /**
   * Starts the program that the user will interact with. This method runs until the user cannot
   * login or they enter "6" to exit the program
   *
   * @param connection used to connect to embedded database
   */
  public static void executeProgram(Connection connection) {
    loadCSV(connection);

    // This loop will end when the user selects option 6
    while (true) {
      System.out.println(
          "1 – Location Information\n"
              + "2 – Change Floor and Type\n"
              + "3 – Enter Location\n"
              + "4 – Delete Location\n"
              + "5 – Save Locations to CSV file\n"
              + "6 – Exit Program");
      Scanner input = new Scanner(System.in);
      String option = input.nextLine();
      switch (option) {
        case ("1"):
          printLocation(connection); // go to method
          break;
        case ("2"):
          System.out.println("Please input a nodeID:");
          String myNodeID = input.nextLine();
          // All apostraphes and semi-colons will be removed from the input
          myNodeID = myNodeID.replaceAll("'", "").replaceAll(";", "");
          editLocation(connection, myNodeID);
          break;
        case ("3"):
          System.out.println("Input a nodeID");
          String nodeIDThree = input.nextLine();
          nodeIDThree = nodeIDThree.replaceAll("'", "").replaceAll(";", "");
          addNewLocation(connection, nodeIDThree);
          break;
        case ("4"):
          System.out.println("Input a nodeID");
          String nodeIDFour = input.nextLine();
          nodeIDFour = nodeIDFour.replaceAll("'", "").replaceAll(";", "");
          removeLocation(connection, nodeIDFour);
          break;
        case ("5"):
          System.out.println("Enter file name (without file extension)");
          String fileName = input.nextLine();
          fileName += ".csv";
          createCSV(connection, fileName);
          break;
        case ("6"):
          System.out.println("Exiting Program");
          return;
        default:
          System.out.println("Your input should be one number between 1 and 6, try again");
          break;
      }
    }
  }

  /**
   * Read the locations from CSV file. Put locations into db table "Location"
   *
   * @param connection used to connect to embedded database
   */
  private static void loadCSV(Connection connection) {
    // Read locations into List "locationsFromCSV"
    locationsFromCSV = new ArrayList<Location>();
    try {
      Scanner sc = new Scanner(new File("src/main/resources/edu/wpi/teamX/TowerLocations.csv"));
      sc.nextLine();
      while (sc.hasNextLine()) {
        String[] currLine = sc.nextLine().replaceAll("\r\n", "").split(",");
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
          locationsFromCSV.add(lnode);
        } else {
          System.out.println("CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
    }

    // Insert locations from locationsFromCSV into db table
    for (int i = 0; i < locationsFromCSV.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder insertLocation = new StringBuilder();
        insertLocation.append("INSERT INTO Location VALUES(");
        insertLocation.append("'" + locationsFromCSV.get(i).getNodeID() + "'" + ", ");
        insertLocation.append(locationsFromCSV.get(i).getxCoord() + ", ");
        insertLocation.append(locationsFromCSV.get(i).getyCoord() + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getFloor() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getBuilding() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getNodeType() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getLongName() + "'" + ", ");
        insertLocation.append("'" + locationsFromCSV.get(i).getShortName() + "'");
        insertLocation.append(")");
        initialization.execute(insertLocation.toString());
      } catch (SQLException e) {
        System.out.println("Input for Location " + i + " failed");
        e.printStackTrace();
        return;
      }
    }
  }

  /**
   * prints locations within the database to the monitor
   *
   * @param connection SQL database connection to use.
   */
  private static void printLocation(Connection connection) {
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // execute query to see all locations and store it to a result set
      ResultSet resultSet = statement.executeQuery("Select * FROM Location");
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      int columnsNumber = resultSetMetaData.getColumnCount();
      while (resultSet.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
          // print column of data
          System.out.print(
              resultSetMetaData.getColumnName(i).replace("_", " ")
                  + ": "
                  + resultSet.getString(i)
                  + " ");
        }
        // create new line
        System.out.println(" ");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Changes the floor and nodeType for a location entry in the location table
   *
   * @param connection used to connect to embedded database
   * @param nodeID user input for which location to change the floor/type
   */
  private static void editLocation(Connection connection, String nodeID) {
    try {
      Scanner floor = new Scanner(System.in);
      Statement statement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      ResultSet locationNodeID =
          statement.executeQuery("SELECT * FROM Location WHERE nodeID = '" + nodeID + "'");
      if (locationNodeID.next()) {
        System.out.println("Location with nodeID " + nodeID + " successfully found.");
        System.out.println(
            "On a new line please enter the new floor then the new location type...");
        System.out.println("Enter the new floor:");
        String newFloor = floor.nextLine();
        System.out.println("\n" + "Enter the new location type:");
        String newType = floor.nextLine();
        if (newFloor.length() > 2 || newType.length() > 4) {
          System.out.println("Invalid floor or type input");
          return;
        }
        statement.executeUpdate(
            "UPDATE Location SET floor = '"
                + newFloor
                + "', nodeType = '"
                + newType
                + "' WHERE nodeID = '"
                + nodeID
                + "'");
        ResultSet confirmedLocationNodeID =
            statement.executeQuery("SELECT * FROM Location WHERE nodeID = '" + nodeID + "'");
        confirmedLocationNodeID.next();
        System.out.println("Location updated.");
        System.out.println(
            "NodeID:"
                + confirmedLocationNodeID.getString("nodeId")
                + "X-Coordinate:"
                + confirmedLocationNodeID.getInt("xCoord")
                + "Y-Coordinate:"
                + confirmedLocationNodeID.getInt("yCoord")
                + "Floor:"
                + confirmedLocationNodeID.getString("floor")
                + "Building:"
                + confirmedLocationNodeID.getString("building")
                + "Node Type:"
                + confirmedLocationNodeID.getString("nodeType")
                + "Long Name:"
                + confirmedLocationNodeID.getString("longName")
                + "Short Name:"
                + confirmedLocationNodeID.getString("shortName"));
        // }

      } else {
        System.out.println("Location with nodeID " + nodeID + " not found.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
  }

  /**
   * Adds a new location to the database
   *
   * @param connection used to connect to embedded database
   * @param nodeID user input to create new location in database
   */
  private static void addNewLocation(Connection connection, String nodeID) {
    try {
      Statement statement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      if (statement.executeUpdate("INSERT INTO Location (NODEID) VALUES ('" + nodeID + "')") > 0) {
        System.out.println("Location with nodeID " + nodeID + " added successfully.");
      } else {
        System.out.println(
            "Location with nodeID "
                + nodeID
                + " could not be added. Perhaps this is because it already exists.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
  }

  /**
   * Adds a new location to the database
   *
   * @param connection used to connect to embedded database
   * @param nodeID user input to remove location from database
   */
  private static void removeLocation(Connection connection, String nodeID) {
    try {
      Statement statement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      if (statement.executeUpdate("DELETE FROM Location WHERE nodeID = '" + nodeID + "'") > 0) {
        System.out.println("Location with nodeID " + nodeID + " successfully deleted.");
      } else {
        System.out.println("Location with nodeID " + nodeID + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
  }

  /**
   * Creates a CSV file out of the entries in the location table
   *
   * @param connection used to connect to embedded database
   * @param csvFileName the name of the csv file to be created (input from the menu)
   */
  private static void createCSV(Connection connection, String csvFileName) {
    locationsFromDB = new ArrayList<Location>();
    try {
      Statement statement = connection.createStatement();
      ResultSet rSet = statement.executeQuery("SELECT * FROM Location");
      while (rSet.next()) {
        locationsFromDB.add(
            new Location(
                rSet.getString("nodeID"),
                rSet.getInt("xCoord"),
                rSet.getInt("yCoord"),
                rSet.getString("floor"),
                rSet.getString("building"),
                rSet.getString("nodeType"),
                rSet.getString("longName"),
                rSet.getString("shortName")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      FileWriter csvFile = new FileWriter(csvFileName);
      csvFile.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
      for (int i = 0; i < locationsFromDB.size(); i++) {
        csvFile.write("\n" + locationsFromDB.get(i).getNodeID() + ",");
        csvFile.write(locationsFromDB.get(i).getxCoord() + ",");
        csvFile.write(locationsFromDB.get(i).getyCoord() + ",");
        csvFile.write(locationsFromDB.get(i).getFloor() + ",");
        csvFile.write(locationsFromDB.get(i).getBuilding() + ",");
        csvFile.write(locationsFromDB.get(i).getNodeType() + ",");
        csvFile.write(locationsFromDB.get(i).getLongName() + ",");
        csvFile.write(locationsFromDB.get(i).getShortName());
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
