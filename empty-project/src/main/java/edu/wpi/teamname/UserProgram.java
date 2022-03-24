package edu.wpi.teamname;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserProgram {
  private static List<Location> locationsFromCSV;

  /**
   * Starts the program that the user will interact with. This method runs until the user cannot
   * login or they enter "6" to exit the program
   *
   * @param username used to login
   * @param password used to login
   */
  public static void executeProgram(String username, String password, Connection connection) {
    if (username.equals("admin") && password.equals("admin")) {
      System.out.println("Successfully logged in!");
    } else {
      System.out.println("Incorrect username or password");
      return;
    }

    loadCSV(connection);

    System.out.println(
        "1 – Location Information\n"
            + "2 – Change Floor and Type\n"
            + "3 – Enter Location\n"
            + "4 – Delete Location\n"
            + "5 – Save Locations to CSV file\n"
            + "6 – Exit Program");

    // This loop will end when the user selects option 6
    while (true) {
      Scanner input = new Scanner(System.in);
      String option = input.nextLine();
      switch (option) {
        case ("1"):
          System.out.println("option 1 placeholder");
          break;
        case ("2"):
          System.out.println("option 2 placeholder");
          break;
        case ("3"):
          System.out.println("option 3 placeholder");
          break;
        case ("4"):
          System.out.println("option 4 placeholder");
          break;
        case ("5"):
          System.out.println("option 5 placeholder");
          break;
        case ("6"):
          return;
        default:
          System.out.println("Your input should be one number between 1 and 6, try again");
          break;
      }
    }
  }

  private static void loadCSV(Connection connection) {
    locationsFromCSV = new ArrayList<Location>();
    try {
      Scanner sc =
          new Scanner(
              new File("empty-project/src/main/resources/edu/wpi/teamname/TowerLocations.csv"));
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
}
