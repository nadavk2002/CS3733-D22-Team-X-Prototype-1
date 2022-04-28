package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PatientDAO implements DAO<Patient> {

  private List<Patient> patients = new ArrayList<>();
  private static String csv = "Patients.csv";

  /** Creates a new PatientDAO object. */
  private PatientDAO() {
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
      fillFromTable();
    }
    if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
      patients.clear();
    }
  }

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final PatientDAO patientDAO = new PatientDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static PatientDAO getDAO() {
    return SingletonHelper.patientDAO;
  }

  @Override
  public List<Patient> getAllRecords() {
    return patients;
  }

  @Override
  public Patient getRecord(String recordID) {
    for (Patient element : patients) {
      // if the object has the same nodeID
      if (element.getPatientID().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Patient: " + recordID + " does not exist");
  }

  @Override
  public void deleteRecord(Patient recordObject) {
    int patientInd = 0;
    while (patientInd < patients.size()) {
      if (patients.get(patientInd).equals(recordObject)) {
        patients.remove(patientInd);
        break;
      }
      patientInd++;
    }
    // if the Patient is found, delete it from the Patient table
    if (patientInd < patients.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // remove Patient from DB table
        statement.executeUpdate(
            "DELETE FROM Patient WHERE patientID = '" + recordObject.getPatientID() + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Patient does not exist (in delete)");
      throw new NoSuchElementException("Patient does not exist (in delete)");
    }
  }

  @Override
  public void updateRecord(Patient recordObject) {
    int patientInd = 0;
    while (patientInd < patients.size()) {
      if (patients.get(patientInd).equals(recordObject)) {
        patients.set(patientInd, recordObject);
        break;
      }
      patientInd++;
    }

    if (patientInd < patients.size()) {
      try {
        // create the statement
        Statement statement = connection.createStatement();
        // update sql object
        statement.executeUpdate(
            "UPDATE Patient SET location = '"
                + recordObject.getLocation().getNodeID()
                + "', firstName = '"
                + recordObject.getFirstName()
                + "', lastName = '"
                + recordObject.getLastName()
                + "', dateOfBirth = '"
                + recordObject.getDateOfBirth()
                + "', bloodType = '"
                + recordObject.getBloodType()
                + "', allergy = '"
                + recordObject.getAllergies().get(0)
                + "', medication = '"
                + recordObject.getMedications().get(0)
                + "', homeAddress = '"
                + recordObject.getHomeAddress()
                + "', height = "
                + recordObject.getHeight()
                + ", weight = "
                + recordObject.getWeight()
                + ", nativeLanguage = '"
                + recordObject.getNativeLanguage()
                + "' "
                + "WHERE patientID = '"
                + recordObject.getPatientID()
                + "'");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Patient does not exist (in update)");
      throw new NoSuchElementException("Patient does not exist (in update)");
    }
  }

  @Override
  public void addRecord(Patient recordObject) {
    patients.add(recordObject);
    try {
      Statement initialization = connection.createStatement();
      StringBuilder insertPatient = new StringBuilder();
      insertPatient.append("INSERT INTO Patient VALUES(");
      insertPatient.append("'" + recordObject.getPatientID() + "'" + ", ");
      insertPatient.append("'" + recordObject.getLocation().getNodeID() + "'" + ", ");
      insertPatient.append("'" + recordObject.getFirstName() + "'" + ", ");
      insertPatient.append("'" + recordObject.getLastName() + "'" + ", ");
      insertPatient.append("'" + recordObject.getDateOfBirth() + "'" + ", ");
      insertPatient.append("'" + recordObject.getBloodType() + "'" + ", ");
      insertPatient.append("'" + recordObject.getAllergy() + "'" + ", ");
      insertPatient.append("'" + recordObject.getMedication() + "'" + ", ");
      insertPatient.append("'" + recordObject.getHomeAddress() + "'" + ", ");
      insertPatient.append(recordObject.getHeight() + ", ");
      insertPatient.append(recordObject.getWeight() + ", ");
      insertPatient.append("'" + recordObject.getNativeLanguage() + "'");
      insertPatient.append(")");
      initialization.execute(insertPatient.toString());
    } catch (SQLException e) {
      System.out.println("Patient database could not be updated");
    }
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE Patient(patientID CHAR(8) PRIMARY KEY NOT NULL, "
              + "location CHAR(10), "
              + "firstName VARCHAR(20), "
              + "lastName VARCHAR(20), "
              + "dateOfBirth VARCHAR(50), "
              + "bloodType VARCHAR(3), "
              + "allergy VARCHAR(30), "
              + "medication VARCHAR(30), "
              + "homeAddress VARCHAR(50), "
              + "height INT, "
              + "weight INT, "
              + "nativeLanguage VARCHAR(15), "
              + "CONSTRAINT PATI_loca_fk "
              + "FOREIGN KEY (location) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("Patient table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropPatient = connection.createStatement();
      dropPatient.execute("DROP TABLE Patient");
    } catch (SQLException e) {
      System.out.println("Patient not dropped");
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      InputStream patiCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader patiCSVReader = new BufferedReader(new InputStreamReader(patiCSV));
      patiCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = patiCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 12) {
          Patient patient =
              new Patient(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7],
                  currLine[8],
                  Integer.parseInt(currLine[9]),
                  Integer.parseInt(currLine[10]),
                  currLine[11]);
          patients.add(patient);
        } else {
          System.out.println("Patient CSV file formatted improperly");
          System.exit(1);
        }
      }
      patiCSV.close();
      patiCSVReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    // Insert Patient from PatientsCSV into db table
    for (int i = 0; i < patients.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder insertPatient = new StringBuilder();
        insertPatient.append("INSERT INTO Patient VALUES(");
        insertPatient.append("'" + patients.get(i).getPatientID() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getLocation().getNodeID() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getFirstName() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getLastName() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getDateOfBirth() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getBloodType() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getAllergy() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getMedication() + "'" + ", ");
        insertPatient.append("'" + patients.get(i).getHomeAddress() + "'" + ", ");
        insertPatient.append(patients.get(i).getHeight() + ", ");
        insertPatient.append(patients.get(i).getWeight() + ", ");
        insertPatient.append("'" + patients.get(i).getNativeLanguage() + "'");
        insertPatient.append(")");
        initialization.execute(insertPatient.toString());
      } catch (SQLException e) {
        System.out.println("Input for Patient " + i + " failed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    try {
      FileWriter csvFile = new FileWriter(dirPath + csv, false);
      csvFile.write(
          "patientID,location,firstName,lastName,dateOfBirth,bloodType,allergy,medication,homeAddress,height,weight,"
              + "nativeLanguage");
      for (int i = 0; i < patients.size(); i++) {
        csvFile.write("\n" + patients.get(i).getPatientID() + ",");
        csvFile.write(patients.get(i).getLocation().getNodeID() + ",");
        csvFile.write(patients.get(i).getFirstName() + ",");
        csvFile.write(patients.get(i).getLastName() + ",");
        csvFile.write(patients.get(i).getDateOfBirth() + ",");
        csvFile.write(patients.get(i).getBloodType() + ",");
        csvFile.write(patients.get(i).getAllergy() + ",");
        csvFile.write(patients.get(i).getMedication() + ",");
        csvFile.write(patients.get(i).getHomeAddress() + ",");
        csvFile.write(patients.get(i).getHeight() + ",");
        csvFile.write(patients.get(i).getWeight() + ",");
        csvFile.write(patients.get(i).getNativeLanguage());
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.println("Error occurred when writing the Patient csv file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean fillFromTable() {
    LocationDAO locDestination = LocationDAO.getDAO();
    try {
      patients.clear();
      Statement fromTable = connection.createStatement();
      ResultSet results = fromTable.executeQuery("SELECT * FROM Patient");
      while (results.next()) {
        Patient toAdd = new Patient();
        toAdd.setPatientID(results.getString("patientID"));
        toAdd.setLocation(locDestination.getRecord(results.getString("location")));
        toAdd.setFirstName(results.getString("firstName"));
        toAdd.setLastName(results.getString("lastName"));
        toAdd.setDateOfBirth(results.getString("dateOfBirth"));
        toAdd.setBloodType(results.getString("bloodType"));
        toAdd.setAllergy(results.getString("allergy"));
        toAdd.setMedication(results.getString("medication"));
        toAdd.setHomeAddress(results.getString("homeAddress"));
        toAdd.setHeight(Integer.parseInt(results.getString("clearanceType")));
        toAdd.setWeight(Integer.parseInt(results.getString("lastName")));
        toAdd.setNativeLanguage(results.getString("jobTitle"));
        patients.add(toAdd);
      }
    } catch (SQLException e) {
      System.out.println("PatientDAO could not be filled from the sql table");
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("PATT%04d", nextIDFinalNum);
  }
}
