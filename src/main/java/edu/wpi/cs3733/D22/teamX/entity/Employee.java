package edu.wpi.cs3733.D22.teamX.entity;

public abstract class Employee {
    private String employeeID;
    private String firstName;
    private String lastName;
    private String clearanceType;
    private String jobTitle;

    public Employee(String employeeID, String firstName, String lastName, String clearanceType, String jobTitle) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clearanceType = clearanceType;
        this.jobTitle = jobTitle;
    }

    private String getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String getClearanceType() {
        return clearanceType;
    }

    private String getJobTitle() {
        return jobTitle;
    }

    //In case of a change of clearance level
    public void setClearanceType(String clearanceType) {
        this.clearanceType = clearanceType;
    }

    //In case of a name change
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //In case of a job change
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
