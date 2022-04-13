package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
  private String employeeID;
  private String firstName;
  private String lastName;
  private String clearanceType;
  private String jobTitle;
  private List<ServiceRequest> requests;

  public Employee() {
    this.employeeID = EmployeeDAO.getDAO().makeID();
    this.firstName = "";
    this.lastName = "";
    this.clearanceType = "staff";
    this.jobTitle = "";
    this.requests = new ArrayList<>();
  }

  public Employee(
      String employeeID, String firstName, String lastName, String clearanceType, String jobTitle) {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.clearanceType = clearanceType;
    this.jobTitle = jobTitle;
    this.requests = new ArrayList<>();
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getClearanceType() {
    return clearanceType;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  // In case of a name change
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  // In case of a name change
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // In case of a change of clearance level
  public void setClearanceType(String clearanceType) {
    this.clearanceType = clearanceType;
  }

  // In case of a job change
  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee anEmployee = (Employee) o;
    return Objects.equals(employeeID, anEmployee.employeeID);
  }

  //  public String makeEmployeeID() {
  //    EmployeeDAOImpl emplDAO = new EmployeeDAOImpl(); // gets list of all ids
  //    int nextIDFinalNum = emplDAO.getAllRecords().size() + 1;
  //
  //    return String.format("EMPL%04d", nextIDFinalNum);
  //  }
}
