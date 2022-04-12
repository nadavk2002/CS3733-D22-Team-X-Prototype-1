package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO extends DatabaseEntity{
    ArrayList<Employee> employees = new ArrayList<>();
    String employeesCSV ="Employees.csv";

    /**
     * Gets all Employees in the database
     *
     * @return a list of all Employees in the database
     */
    List<Employee> getAllEmployees();

    /**
     * Get the Employee specified by their employeeID
     *
     * @param employeeID refers to an Employee
     * @return the Employee from the list of Employees
     */
    Employee getEmployee(String employeeID);

    /**
     * removes the Employee from the database with the same employeeID as the passed Employee
     *
     * @param anEmployee the employee you want to remove from the database
     */
    void deleteEmployee(Employee anEmployee);

    /**
     * replaces the Employee in the database with the same employeeID as the passed Employee
     *
     * @param anEmployee the employee you want to update in the database
     */
    void updateEmployee(Employee anEmployee);

    /**
     * adds an Employee to the database
     *
     * @param anEmployee to be added
     */
    void addEmployee(Employee anEmployee);

}
