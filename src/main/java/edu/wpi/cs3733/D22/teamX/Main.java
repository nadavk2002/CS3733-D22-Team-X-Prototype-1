package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    System.setProperty(
        "prism.allowhidpi",
        "false"); // Disables windows DPI scaling so the application fits all 1920x1080 screens.
    Connection dbConn = Xdb.initializeDB();
    App.launch(App.class, args);
    try {
      dbConn.close();
      System.out.println("Database successfully closed...\nApplication exiting");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
