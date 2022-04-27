package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamD.API.EmployeeAPI;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;
import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import edu.wpi.cs3733.D22.teamX.entity.Employee;
import edu.wpi.cs3733.D22.teamX.entity.EmployeeDAO;
import edu.wpi.teamW.API;
import edu.wpi.teamW.dB.enums.Languages;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class APILandingPageController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /**
   * Run the MealRequestAPI
   */
  public void runMealRequestAPI() {
    try {
      MealRequestAPI.run(
          470,
          180,
          700,
          700,
          "/edu/wpi/cs3733/D22/teamX/stylesheets/application.css",
          "FDEPT00101",
          "Hello");
    } catch (ServiceException e) {
      System.out.println("Service Exception Occured");
    }
  }

  /**
   * Run the SanitationRequestAPI
   */
  public void runSanitationAPI() {
    try {
      StartAPI sanAPI = new StartAPI();
      sanAPI.run(
          470,
          180,
          300,
          300,
          "/edu/wpi/cs3733/D22/teamX/stylesheets/application.css",
          "FDEPT00101");
      // Delete previous employees and add the employees from EmployeeDAO
      EmployeeAPI sanitationEmpAPI = new EmployeeAPI();
      while (sanitationEmpAPI.getAllEmployees().size() > 0) {
        sanitationEmpAPI.removeEmployee(sanitationEmpAPI.getAllEmployees().get(0));
      }
      List<Employee> ourEmps = EmployeeDAO.getDAO().getAllRecords();
      for (int i = 0; i < ourEmps.size(); i++) {
        Employee ourEmp = ourEmps.get(i);
        sanitationEmpAPI.addEmployee(
            new EmployeeObj(
                ourEmp.getEmployeeID(),
                ourEmp.getLastName(),
                "",
                EmployeeObj.EmployeeType.ADMINISTRATOR,
                0));
      }
    } catch (edu.wpi.cs3733.D22.teamD.API.ServiceException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Run the langRequestAPI
   */
  public void runLangAPI() {
    try {
      API.run(
          750,
          180,
          600,
          600,
          "/edu/wpi/cs3733/D22/teamX/stylesheets/application.css",
          "FDEPT00101",
          "FDEPT00102");
    } catch (edu.wpi.teamW.ServiceException e) {
      System.out.println(e.getMessage());
    }
    // Delete previous employees and add the employees from EmployeeDAO
    try {
      API.deleteAllEmployees();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Random rng = new Random();
    for (Employee e : EmployeeDAO.getDAO().getAllRecords()) {
      try {
        API.addEmployeeWithLanguage(
            new edu.wpi.teamW.dB.Employee(
                Integer.parseInt(e.getEmployeeID().substring(4)),
                e.getFirstName(),
                e.getLastName()),
            Languages.values()[rng.nextInt(Languages.values().length)]);
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}
