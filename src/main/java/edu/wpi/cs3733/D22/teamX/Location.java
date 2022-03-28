package edu.wpi.cs3733.D22.teamX;

import java.util.Objects;

public class Location {
  private String nodeID;
  private int xCoord, yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName, shortName;

  public Location() {
    nodeID = null;
    xCoord = -1;
    yCoord = -1;
    floor = null;
    building = null;
    nodeType = null;
    longName = null;
    shortName = null;
  }

  public Location(String nodeID) {
    this.nodeID = nodeID;
    this.xCoord = -1;
    this.yCoord = -1;
    this.floor = null;
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.shortName = null;
  }

  public Location(
      String nodeID,
      Integer xCoord,
      Integer yCoord,
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

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setxCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public void setyCoord(int yCoord) {
    this.yCoord = yCoord;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getNodeID() {
    return nodeID;
  }

  public int getxCoord() {
    return xCoord;
  }

  public int getyCoord() {
    return yCoord;
  }

  public String getFloor() {
    return floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return Objects.equals(nodeID, location.nodeID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeID);
  }
}
