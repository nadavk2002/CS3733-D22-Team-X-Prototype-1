package edu.wpi.cs3733.D22.teamX;

public enum ServerTypeEnum {
  INSTANCE;

  // example of how attributes are added to the Enum
  int binType;

  public void setBinType(int binType) {
    this.binType = binType;
  }
}
