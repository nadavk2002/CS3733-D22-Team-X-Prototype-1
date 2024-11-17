package edu.wpi.cs3733.D22.teamX;
// import edu.wpi.cs3733.c
// Nadav 11/17/2024 -- booted the app up for old times sake, noticed this version is not the final edition after running the app (I remember this from the live demo that I don't have the correct version)
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
    try {
      DatabaseCreator.closeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
