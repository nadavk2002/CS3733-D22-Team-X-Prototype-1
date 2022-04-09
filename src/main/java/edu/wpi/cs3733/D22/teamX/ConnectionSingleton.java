package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
  private static final String embeddedURL = "jdbc:derby:embed_db;create=true";
  private static final String clientURL = "jdbc:derby:client_db;create=true";
  private static final String username = "admin";
  private static final String password = "admin";

  private Connection connection;

  private ConnectionSingleton() {
    connection = null;
    try {
      connection = DriverManager.getConnection(embeddedURL, username, password);
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static class ConnectionSingletonHelper {
    private static final ConnectionSingleton conn = new ConnectionSingleton();
  }

  public static ConnectionSingleton getConnectionSingleton() {
    return ConnectionSingletonHelper.conn;
  }

  public Connection getConnection() {
    return connection;
  }
}
