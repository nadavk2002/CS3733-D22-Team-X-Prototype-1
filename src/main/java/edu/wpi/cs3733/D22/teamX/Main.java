package edu.wpi.cs3733.D22.teamX;
// import edu.wpi.cs3733.c

import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;

public class Main {

  public static void main(String[] args) {
    System.setProperty(
        "prism.allowhidpi",
        "false"); // Disables windows DPI scaling so the application fits all 1920x1080 screens.
    //    try {
    //      DatabaseCreator.initializeDB();
    //    } catch (loadSaveFromCSVException e) {
    //      e.printStackTrace();
    //      System.exit(1);
    //    }

    App.launch(App.class, args);
    // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    try {
      DatabaseCreator.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
