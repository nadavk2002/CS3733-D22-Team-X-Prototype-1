package edu.wpi.teamname;

import java.sql.*;

// change Xdb to something else
// change csv file path to something else, if it changes
/** Created by Darren Kwee */
public class Xdb {
  public static void main(String[] args) {
    System.out.println("-----Testing Apache Derby Embedded Connection-----");
    // checks whether the driver is working
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby driver registered!");

    // tries to create the database and establish a connection
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:derby:Xdb;create=true");
      // Statement testStatement = connection.createStatement();
      //      testStatement.execute("CREATE TABLE TEST(ID INT PRIMARY KEY)");
      //      testStatement.execute("INSERT INTO TEST VALUES(3)");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established :D");

    // creates table
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
      System.out.println("Table creation failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Database created successfully");

    // Ensures username and password are passed as arguments
    if (args.length < 2 || args[0] == null || args[1] == null) {
      System.out.println("Enter username and password as arguments I.E. \"Xdb username password\"");
      return;
    } else {
      UserProgram.executeProgram(args[0], args[1], connection);
    }
  }

  public static void addNewLocation(Connection connection, String nodeID) {
    try {
      Statement statement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      if (statement.executeUpdate("INSERT INTO Location (NODEID) VALUES ('" + nodeID + "')") > 0) {
        System.out.println("Location with nodeID " + nodeID + " added successfully.");
      } else {
        System.out.println(
            "Location with nodeID "
                + nodeID
                + " could not be added. Perhaps this is because it already exsits.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
  }

  public static void removeLocation(Connection connection, String nodeID) {
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
}
