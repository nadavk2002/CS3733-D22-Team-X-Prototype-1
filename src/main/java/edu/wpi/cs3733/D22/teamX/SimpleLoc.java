package edu.wpi.cs3733.D22.teamX;

public class SimpleLoc {
  private Location loc;
  private String id;
  private int x;
  private int y;
  private String floor;
  private String building;
  private String type;
  private String longName;
  private String shortName;

  public SimpleLoc(Location loc) {
    this.loc = loc;
    id = loc.getNodeID();
    x = loc.getxCoord();
    y = loc.getyCoord();
    floor = loc.getFloor();
    building = loc.getBuilding();
    type = loc.getNodeType();
    longName = loc.getLongName();
    shortName = loc.getShortName();
  }

  public String getId() {
    return loc.getNodeID();
  }

  public int getX() {
    return loc.getxCoord();
  }

  public int getY() {
    return loc.getyCoord();
  }

  public String getFloor() {
    return loc.getFloor();
  }

  public String getBuilding() {
    return loc.getBuilding();
  }

  public String getType() {
    return loc.getNodeType();
  }

  public String getLongName() {
    return loc.getLongName();
  }

  public String getShortName() {
    return loc.getShortName();
  }

  public void setId(String id) {
    loc.setNodeID(id);
  }

  public void setX(int x) {
    loc.setxCoord(x);
  }

  public void setY(int y) {
    loc.setyCoord(y);
  }

  public void setFloor(String floor) {
    loc.setFloor(floor);
  }

  public void setBuilding(String building) {
    loc.setBuilding(building);
  }

  public void setType(String type) {
    loc.setNodeType(type);
  }

  public void setLongName(String longName) {
    loc.setLongName(longName);
  }

  public void setShortName(String shortName) {
    loc.setShortName(shortName);
  }
}
