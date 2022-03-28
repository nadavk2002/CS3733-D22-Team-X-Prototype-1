package edu.wpi.cs3733.D22.teamX.entity;

public class EquipmentServiceRequest extends ServiceRequest {
    private String equipmentType, roomNumber;
    private int numOfEquipment;

    public EquipmentServiceRequest(String requestingUser, String assignee, String equipmentType, String roomNumber, int numOfEquipment) {
        super(requestingUser, assignee);
        this.equipmentType = equipmentType;
        this.roomNumber = roomNumber;
        this.numOfEquipment = numOfEquipment;
    }

}
