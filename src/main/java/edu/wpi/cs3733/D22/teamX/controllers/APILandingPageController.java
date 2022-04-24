package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamD.API.EmployeeAPI;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;
import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import edu.wpi.cs3733.D22.teamX.entity.Employee;
import edu.wpi.cs3733.D22.teamX.entity.EmployeeDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
      StartAPI.appLaunch(
          100, 100, 300, 300, "/edu/wpi/cs3733/D22/teamX/stylesheets/default.css", "FDEPT00101");
      EmployeeAPI sanitationEmpAPI = new EmployeeAPI();
      List<EmployeeObj> apiEmpsList = sanitationEmpAPI.getAllEmployees();
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
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void runFloralAPI() {

  }
}
