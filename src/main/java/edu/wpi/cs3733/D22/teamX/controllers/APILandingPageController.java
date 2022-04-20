package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;
import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class APILandingPageController implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //
  }

  public void runMealRequestAPI() {
    try {
      MealRequestAPI.run(
          470,
          180,
          0,
          0,
          "/edu/wpi/cs3733/D22/teamX/stylesheets/application.css",
          "FDEPT00101",
          "Hello");
    } catch (ServiceException e) {
      System.out.println("Service Exception Occured");
    }
  }
}
