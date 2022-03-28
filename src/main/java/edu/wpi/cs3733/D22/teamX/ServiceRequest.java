package edu.wpi.cs3733.D22.teamX;

public abstract class ServiceRequest
{
    private String requestType;
    private Location toLoc;

    public String getRequestType() {
        return requestType;
    }

    public Location getToLoc() {
        return toLoc;
    }
}
