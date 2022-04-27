package edu.wpi.cs3733.D22.teamX.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient {
  private String patientID;
  private Location location;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String bloodType;
  private String allergy;
  private String medication;
  private List<String> allergies;
  private List<String> medications;
  private String homeAddress;
  private double height;
  private double weight;
  private String nativeLanguage;

  public Patient() {
    this.patientID = "";
    this.location = new Location();
    this.firstName = "";
    this.lastName = "";
    this.dateOfBirth = "";
    this.bloodType = "";
    this.allergies = new ArrayList<>();
    this.medications = new ArrayList<>();
    this.homeAddress = "";
    this.height = 0;
    this.weight = 0;
    this.nativeLanguage = "";
  }

  public Patient(
      String patientID,
      Location location,
      String firstName,
      String lastName,
      String dateOfBirth,
      String bloodType,
      String allergy,
      String medication,
      String homeAddress,
      double height,
      double weight,
      String nativeLanguage) {
    this.patientID = patientID;
    this.location = location;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.bloodType = bloodType;
    this.allergy = allergy;
    this.allergies = new ArrayList<>();
    addAllergies(allergy);
    this.medication = medication;
    this.medications = new ArrayList<>();
    addMedications(medication);
    this.homeAddress = homeAddress;
    this.height = height;
    this.weight = weight;
    this.nativeLanguage = nativeLanguage;
  }

  public void setPatientID(String patientID) {
    this.patientID = patientID;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setBloodType(String bloodType) {
    this.bloodType = bloodType;
  }

  public void setAllergy(String allergy) {
    this.allergy = allergy;
  }

  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }

  public void addAllergies(String allergy) {
    allergies.add(allergy);
  }

  public void removeAllergies(String allergy) {
    allergies.remove(allergy);
  }

  public void setMedication(String medication) {
    this.medication = medication;
  }

  public void setMedications(List<String> medications) {
    this.medications = medications;
  }

  public void addMedications(String medication) {
    medications.add(medication);
  }

  public void removeMedications(String medication) {
    medications.remove(medication);
  }

  public void setHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public void setNativeLanguage(String nativeLanguage) {
    this.nativeLanguage = nativeLanguage;
  }

  public String getPatientID() {
    return patientID;
  }

  public Location getLocation() {
    return location;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getBloodType() {
    return bloodType;
  }

  public String getAllergy() {
    return allergy;
  }

  public String getMedication() {
    return medication;
  }

  public List<String> getAllergies() {
    return allergies;
  }

  public List<String> getMedications() {
    return medications;
  }

  public String getHomeAddress() {
    return homeAddress;
  }

  public double getHeight() {
    return height;
  }

  public double getWeight() {
    return weight;
  }

  public String getNativeLanguage() {
    return nativeLanguage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Patient anPatient = (Patient) o;
    return Objects.equals(patientID, anPatient.patientID);
  }
}
