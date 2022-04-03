package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.Location;

public class EquipmentUnit {
  private String unitID;
  private String type;
  private boolean isAvailable;
  private Location currLocation;

  public EquipmentUnit(String unitID, String type, boolean isAvailable, Location currLocation) {
    this.unitID = unitID;
    this.type = type;
    this.isAvailable = isAvailable;
    this.currLocation = currLocation;
  }

  public EquipmentUnit() {
    unitID = null;
    type = null;
    isAvailable = false;
    currLocation = null;
  }

  public String getUnitID() {
    return unitID;
  }

  public void setUnitID(String unitID) {
    this.unitID = unitID;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public Location getCurrLocation() {
    return currLocation;
  }

  public void setCurrLocation(Location currLocation) {
    this.currLocation = currLocation;
  }
}
