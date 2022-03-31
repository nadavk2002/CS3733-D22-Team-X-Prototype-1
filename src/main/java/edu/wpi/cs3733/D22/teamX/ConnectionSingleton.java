package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
  private static final String url = "jdbc:derby:embed_db;create=true";
  private static final String username = "admin";
  private static final String password = "admin";

  private static ConnectionSingleton connSing;

  private ConnectionSingleton() {}

  private static class ConnectionSingletonHelper {
    private static final ConnectionSingleton conn = new ConnectionSingleton();
  }

  public static ConnectionSingleton getConnectionSingleton() {
    return ConnectionSingletonHelper.conn;
  }

  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
    return connection;
  }
}