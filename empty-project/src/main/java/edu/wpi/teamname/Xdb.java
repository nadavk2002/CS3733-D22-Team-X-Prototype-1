package edu.wpi.teamname;

import java.sql.*;

/** Created by Darren Kwee */
public class Xdb {


  public static void main(String args[]) {
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
      Statement testStatement = connection.createStatement();
//      testStatement.execute("CREATE TABLE TEST(ID INT PRIMARY KEY)");
//      testStatement.execute("INSERT INTO TEST VALUES(3)");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established :D");
  }
}
