package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.sql.Connection;

public class Main {

  public static void main(String[] args) {
    System.setProperty(
        "prism.allowhidpi",
        "false"); // Disables windows DPI scaling so the application fits all 1920x1080 screens.
    Connection dbConn = null;
    try {
      dbConn = Xdb.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      System.exit(1);
    }

    App.launch(App.class, args);

    try {
      Xdb.closeDB(dbConn, "TowerLocations.csv", "MedEquipReq.csv");
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
