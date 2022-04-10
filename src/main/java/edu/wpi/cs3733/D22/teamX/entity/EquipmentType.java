package edu.wpi.cs3733.D22.teamX.entity;

import java.util.Objects;

// model,numUnitsTotal,numUnitsAvailable
public class EquipmentType {
  private String model;
  private int numUnitsTotal;
  private int numUnitsAvailable;

  public EquipmentType(String model, int numUnitsTotal, int numUnitsAvailable) {
    this.model = model;
    this.numUnitsTotal = numUnitsTotal;
    this.numUnitsAvailable = numUnitsAvailable;
  }

  public EquipmentType() {
    this("", -1, -1);
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getNumUnitsTotal() {
    return numUnitsTotal;
  }

  public void setNumUnitsTotal(int numUnitsTotal) {
    this.numUnitsTotal = numUnitsTotal;
  }

  public int getNumUnitsAvailable() {
    return numUnitsAvailable;
  }

  public void setNumUnitsAvailable(int numUnitsAvailable) {
    this.numUnitsAvailable = numUnitsAvailable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EquipmentType equipmentType = (EquipmentType) o;
    return Objects.equals(model, equipmentType.model);
  }
}
