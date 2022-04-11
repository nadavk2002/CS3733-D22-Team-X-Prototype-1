package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionSingleton {
  INSTANCE;

  private static final String embeddedURL = "jdbc:derby:embed_db;create=true";
  private static final String clientURL = "jdbc:derby://localhost:1527/client_db"; // ;create=true
  private static final String username = "admin";
  private static final String password = "admin";
  private Connection connection;

  public void setEmbedded() {
    if(connection != null)
    {
      try {
        connection.close();
        System.out.println("Embedded connection closed successfully");
      } catch (SQLException e) {
        e.printStackTrace();
        return;
      }
    }
    try {
      connection = DriverManager.getConnection(embeddedURL, username, password);
    } catch (SQLException e) {
      System.out.println("Embedded connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void setClient() {
    if(connection != null)
    {
      try {
        connection.close();
        System.out.println("Client connection closed successfully");
      } catch (SQLException e) {
        e.printStackTrace();
        return;
      }
    }
    try {
      connection = DriverManager.getConnection(clientURL, username, password);
    } catch (SQLException e) {
      System.out.println("Client connection failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static ConnectionSingleton getConnectionSingleton() {
    return INSTANCE;
  }

  public Connection getConnection() {
    return connection;
  }
}
