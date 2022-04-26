package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamD.API.EmployeeAPI;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;
import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import edu.wpi.cs3733.D22.teamX.entity.Employee;
import edu.wpi.cs3733.D22.teamX.entity.EmployeeDAO;
import edu.wpi.cs3733.D22.teamX.entity.ServiceRequestDAO;
import edu.wpi.teamW.API;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import edu.wpi.teamW.dB.enums.Languages;
import javafx.fxml.Initializable;

public class APILandingPageController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

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
    try {
      API.deleteAllEmployees();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    for(Employee e : EmployeeDAO.getDAO().getAllRecords())
    {
      try {
        API.addEmployeeWithLanguage(
                new edu.wpi.teamW.dB.Employee(Integer.parseInt(
                        e.getEmployeeID().substring(4)),
                        e.getFirstName(),
                        e.getLastName()),
                Languages.values()[0]);
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}
