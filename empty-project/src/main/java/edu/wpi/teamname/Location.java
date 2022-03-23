package edu.wpi.teamname;

public class Location {
  private String nodeID;
  private int xCoord, yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName, shortName;

  public Location(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }
}
