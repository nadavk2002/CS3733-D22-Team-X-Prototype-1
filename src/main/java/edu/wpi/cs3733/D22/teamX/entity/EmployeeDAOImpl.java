package edu.wpi.cs3733.D22.teamX.entity;

// import edu.wpi.cs3733.D22.teamX.DatabaseCreator;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeDAOImpl implements EmployeeDAO {

  public EmployeeDAOImpl() {}

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE Employee(employeeID CHAR(8) PRIMARY KEY NOT NULL, "
              + "firstName VARCHAR(20), "
              + "lastName VARCHAR(20), "
              + "clearanceType CHAR(5), "
              + "jobTitle VARCHAR(50))");
    } catch (SQLException e) {
      System.out.println("Employee table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropEmployee = connection.createStatement();
      dropEmployee.execute("DROP TABLE Employee");
    } catch (SQLException e) {
      System.out.println("Employee not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      InputStream emplCSV = DatabaseCreator.class.getResourceAsStream(employeesCSV);
      BufferedReader emplCSVReader = new BufferedReader(new InputStreamReader(emplCSV));
      emplCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = emplCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          Employee emplNode =
              new Employee(currLine[0], currLine[1], currLine[2], currLine[3], currLine[4]);
          employees.add(emplNode);
        } else {
          System.out.println("Employee CSV file formatted improperly");
          System.exit(1);
        }
      }
      emplCSV.close();
      emplCSVReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    // Insert Employee from EmployeesCSV into db table
    for (int i = 0; i < employees.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder insertEmployee = new StringBuilder();
        insertEmployee.append("INSERT INTO Employee VALUES(");
        insertEmployee.append("'" + employees.get(i).getEmployeeID() + "'" + ", ");
        insertEmployee.append("'" + employees.get(i).getFirstName() + "'" + ", ");
        insertEmployee.append("'" + employees.get(i).getLastName() + "'" + ", ");
        insertEmployee.append("'" + employees.get(i).getClearanceType() + "'" + ", ");
        insertEmployee.append("'" + employees.get(i).getJobTitle() + "'");
        insertEmployee.append(")");
        initialization.execute(insertEmployee.toString());
      } catch (SQLException e) {
        System.out.println("Input for Employee " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + employeesCSV, false);
      csvFile.write("employeeID,firstName,lastName,clearanceType,jobTitle");
      for (int i = 0; i < employees.size(); i++) {
        csvFile.write("\n" + employees.get(i).getEmployeeID() + ",");
        csvFile.write(employees.get(i).getFirstName() + ",");
        csvFile.write(employees.get(i).getLastName() + ",");
        csvFile.write(employees.get(i).getClearanceType() + ",");
        csvFile.write(employees.get(i).getJobTitle());
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occurred when updating Employee csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employees;
  }

  @Override
  public Employee getEmployee(String employeeID) {

    for (Employee element : employees) {
      // if the object has the same nodeID
      if (element.getEmployeeID().equals(employeeID)) {
        return element;
      }
    }

    throw new NoSuchElementException("Employee does not exist");
  }

  @Override
  public void deleteEmployee(Employee anEmployee) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(anEmployee)) {
        employees.remove(employeeInd);
        break;
      }
      employeeInd++;
    }
    // if the Employee is found, delete it from the Employee table
    if (employeeInd < employees.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // remove Employee from DB table
        statement.executeUpdate(
            "DELETE FROM Employee WHERE employeeID = '" + anEmployee.getEmployeeID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Employee does not exist");
      throw new NoSuchElementException("Employee does not exist (in delete)");
    }
  }

  @Override
  public void updateEmployee(Employee anEmployee) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(anEmployee)) {
        employees.set(employeeInd, anEmployee);
        break;
      }
      employeeInd++;
    }

    if (employeeInd < employees.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // update sql object
        statement.executeUpdate(
            "UPDATE Employee SET firstName = "
                + anEmployee.getFirstName()
                + ", lastName = "
                + anEmployee.getLastName()
                + ", clearanceType = '"
                + anEmployee.getClearanceType()
                + "', jobTitle = '"
                + anEmployee.getJobTitle()
                + "' "
                + "WHERE employeeID = '"
                + anEmployee.getEmployeeID()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Employee does not exist");
      throw new NoSuchElementException("Employee does not exist (in update)");
    }
  }

  @Override
  public void addEmployee(Employee anEmployee) {
    employees.add(anEmployee);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertEmployee = new StringBuilder();
      insertEmployee.append("INSERT INTO Employee VALUES(");
      insertEmployee.append("'" + anEmployee.getEmployeeID() + "'" + ", ");
      insertEmployee.append("'" + anEmployee.getFirstName() + "'" + ", ");
      insertEmployee.append("'" + anEmployee.getLastName() + "'" + ", ");
      insertEmployee.append("'" + anEmployee.getClearanceType() + "'" + ", ");
      insertEmployee.append("'" + anEmployee.getJobTitle() + "'");
      insertEmployee.append(")");
      initialization.execute(insertEmployee.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
    }
  }
}
