package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeDAO implements DAO<Employee> {
  private static List<Employee> employees = new ArrayList<Employee>();
  private static String csv = "Employees.csv";

  /** Creates a new EmployeeDAO object. */
  private EmployeeDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      employees.clear();
    }
  }

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static EmployeeDAO getDAO() {
    return SingletonHelper.employeeDAO;
  }

  @Override
  public List<Employee> getAllRecords() {
    return employees;
  }

  @Override
  public Employee getRecord(String recordID) {
    for (Employee element : employees) {
      // if the object has the same nodeID
      if (element.getEmployeeID().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Employee" + recordID + "does not exist");
  }

  @Override
  public void deleteRecord(Employee recordObject) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(recordObject)) {
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
            "DELETE FROM Employee WHERE employeeID = '" + recordObject.getEmployeeID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Employee does not exist");
      throw new NoSuchElementException("Employee does not exist (in delete)");
    }
  }

  @Override
  public void updateRecord(Employee recordObject) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(recordObject)) {
        employees.set(employeeInd, recordObject);
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
            "UPDATE Employee SET firstName = '"
                + recordObject.getFirstName()
                + "', lastName = '"
                + recordObject.getLastName()
                + "', clearanceType = '"
                + recordObject.getClearanceType()
                + "', jobTitle = '"
                + recordObject.getJobTitle()
                + "' "
                + "WHERE employeeID = '"
                + recordObject.getEmployeeID()
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
  public void addRecord(Employee recordObject) {
    employees.add(recordObject);
    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertEmployee = new StringBuilder();
      insertEmployee.append("INSERT INTO Employee VALUES(");
      insertEmployee.append("'" + recordObject.getEmployeeID() + "'" + ", ");
      insertEmployee.append("'" + recordObject.getFirstName() + "'" + ", ");
      insertEmployee.append("'" + recordObject.getLastName() + "'" + ", ");
      insertEmployee.append("'" + recordObject.getClearanceType() + "'" + ", ");
      insertEmployee.append("'" + recordObject.getJobTitle() + "'");
      insertEmployee.append(")");
      initialization.execute(insertEmployee.toString());
    } catch (SQLException e) {
      System.out.println("Employee database could not be updated");
    }
  }

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
      InputStream emplCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
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
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
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

  /**
   * Fills employees with data from the sql table
   *
   * @return true if employees is successfully filled
   */
  @Override
  public boolean fillFromTable() {
    try {
      employees.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM Employee");
      while (results.next()) {
        Employee toAdd = new Employee();
        toAdd.setEmployeeID(results.getString("employeeID"));
        toAdd.setFirstName(results.getString("firstName"));
        toAdd.setLastName(results.getString("lastName"));
        toAdd.setClearanceType(results.getString("clearanceType"));
        toAdd.setJobTitle(results.getString("jobTitle"));
        employees.add(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("EmployeeDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("EMPL%04d", nextIDFinalNum);
  }
}
