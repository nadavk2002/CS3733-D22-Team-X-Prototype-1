package edu.wpi.cs3733.D22.teamX.entity;

public class ServiceRequest {
    private boolean serviceCompleted; //false if incomplete
    private String requestingUser, assignee;

    public ServiceRequest(String requestingUser, String assignee) {
        serviceCompleted=false;
        this.requestingUser=requestingUser;
        this.assignee=assignee;
    }
}
