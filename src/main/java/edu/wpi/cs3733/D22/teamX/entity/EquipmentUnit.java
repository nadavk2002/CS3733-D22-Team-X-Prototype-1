package edu.wpi.cs3733.D22.teamX.entity;

import java.util.Objects;

public class EquipmentUnit {
  private String unitID;
  private EquipmentType type;
  private boolean isAvailable;
  private Location currLocation;

  public EquipmentUnit(
      String unitID, EquipmentType type, boolean isAvailable, Location currLocation) {
    this.unitID = unitID;
    this.type = type;
    this.isAvailable = isAvailable;
    this.currLocation = currLocation;
  }

  public EquipmentUnit(String unitID, EquipmentType type, char isAvailable, Location currLocation) {
    this.unitID = unitID;
    this.type = type;
    // Converts CHAR(1) to boolean
    if (isAvailable == 'Y') {
      this.isAvailable = true;
    } else {
      this.isAvailable = false;
    }
    this.currLocation = currLocation;
  }

  public EquipmentUnit() {
    unitID = "";
    type = null;
    isAvailable = false;
    currLocation = new Location();
  }

  public String getUnitID() {
    return unitID;
  }

  public void setUnitID(String unitID) {
    this.unitID = unitID;
  }

  public EquipmentType getType() {
    return type;
  }

  public void setType(EquipmentType type) {
    this.type = type;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public char getIsAvailableChar() {
    if (isAvailable) {
      return 'Y';
    } else {
      return 'N';
    }
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public void setAvailable(char available) {
    if (available == 'Y') {
      this.isAvailable = true;
    } else if (available == 'N') {
      this.isAvailable = false;
    }

    // should probably throw an exception here
  }

  public Location getCurrLocation() {
    return currLocation;
  }

  public String getCurrLocationShortName() {
    return currLocation.getShortName();
  }

  public void setCurrLocation(Location currLocation) {
    this.currLocation = currLocation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EquipmentUnit equipmentUnit = (EquipmentUnit) o;
    return Objects.equals(unitID, equipmentUnit.unitID);
  }

  public String makeEquipmentID() {
    EquipmentUnitDAO equipDAO = EquipmentUnitDAO.getDAO(); // gets list of all ids
    int nextIDFinalNum = equipDAO.getAllRecords().size() + 1;

    return String.format("MEUN%04d", nextIDFinalNum);
  }
}
