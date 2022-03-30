package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMaker {
  private static String url = "jdbc:derby:embed_db;create=true";
  private static String username = "admin";
  private static String password = "admin";

  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      System.out.println("Connection not established");
    }
    return connection;
  }
}
