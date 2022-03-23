package edu.wpi.teamname;

import java.sql.*;

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

    // creates tables
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE Location(nodeID CHAR(10) PRIMARY KEY NOT NULL, "
              + "xCoord INT NOT NULL, yCoord INT NOT NULL, "
              + "floor VARCHAR(2) NOT NULL, "
              + "building VARCHAR(10) NOT NULL, "
              + "nodeType CHAR(4) NOT NULL, "
              + "longName VARCHAR(50) NOT NULL, shortName VARCHAR(30))");
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
      UserProgram.executeProgram(args[0], args[1]);
    }
  }
}
