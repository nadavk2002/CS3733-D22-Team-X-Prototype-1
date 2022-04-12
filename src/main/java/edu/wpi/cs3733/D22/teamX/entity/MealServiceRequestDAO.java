package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MealServiceRequestDAO implements DAO<MealServiceRequest> {
  private static List<MealServiceRequest> mealServiceRequests = new ArrayList<MealServiceRequest>();
  private static String csv = "MealRequests.csv";

  /** creates new MealServiceRequestDAO */
  private MealServiceRequestDAO() {}

  /** Singleton helper Class */
  private static class SingletonHelper {
    private static final MealServiceRequestDAO mealServiceRequestDAO = new MealServiceRequestDAO();
  }

  /**
   * returns the singleton thing
   *
   * @return MealServiceRequestDAO.
   */
  public static MealServiceRequestDAO getDAO() {
    return SingletonHelper.mealServiceRequestDAO;
  }

  @Override
  public List<MealServiceRequest> getAllRecords() {
    return mealServiceRequests;
  }

  @Override
  public MealServiceRequest getRecord(String recordID) {
    // iterate through the list of requests
    for (MealServiceRequest msr : mealServiceRequests) {
      if (msr.getRequestID().equals(recordID)) {
        return msr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MealServiceRequest recordObject) {
    // remove from list
    int index = 0; // create index for while loop
    int intitialSize = mealServiceRequests.size(); // get size
    // go thru list
    while (index < mealServiceRequests.size()) {
      if (mealServiceRequests.get(index).equals(recordObject)) {
        mealServiceRequests.remove(index); // remove item at index from list
        index--;
        break; // exit loop
      }
      index++;
    }
    if (index == intitialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // remove from Database
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // remove location from DB table
      statement.executeUpdate(
          "DELETE FROM MealServiceRequest WHERE requestID = '" + recordObject.getRequestID() + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MealServiceRequest recordObject) {
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = mealServiceRequests.size(); // get size
    // go thru list
    while (index < mealServiceRequests.size()) {
      if (mealServiceRequests.get(index).equals(recordObject)) {
        mealServiceRequests.set(index, recordObject); // update lsr at index position
        break; // exit loop
      }
      index++;
    }
    if (index == intitialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // update db table.
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MealServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssignee()
              + "', mainCourse = '"
              + recordObject.getMainCourse()
              + "', side = '"
              + recordObject.getSide()
              + "', drink = '"
              + recordObject.getDrink()
              + "', patientFor = '"
              + recordObject.getPatientFor()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(MealServiceRequest recordObject) {
    // list
    mealServiceRequests.add(recordObject);
    // db
    try {
      Statement initialization = connection.createStatement();
      StringBuilder msr = new StringBuilder();
      msr.append("INSERT INTO MealServiceRequest VALUES (");
      msr.append("'" + recordObject.getRequestID() + "', ");
      msr.append("'" + recordObject.getDestination().getNodeID() + "', ");
      msr.append("'" + recordObject.getStatus() + "', ");
      msr.append("'" + recordObject.getAssignee() + "', ");
      msr.append("'" + recordObject.getMainCourse() + "', ");
      msr.append("'" + recordObject.getSide() + "', ");
      msr.append("'" + recordObject.getDrink() + "', ");
      msr.append("'" + recordObject.getPatientFor() + "'");
      msr.append(")");
      initialization.execute(msr.toString());
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Database could not be updated");
    }
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MealServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "mainCourse VARCHAR(25),"
              + "side VARCHAR(25),"
              + "drink VARCHAR(25),"
              + "patientFor VARCHAR(15),"
              + "CONSTRAINT PMSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID) "
              + "ON DELETE SET NULL)");
    } catch (SQLException e) {
      System.out.println("MealServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropMealServiceRequest = connection.createStatement();
      dropMealServiceRequest.execute("DROP TABLE MealServiceRequest");
    } catch (SQLException e) {
      System.out.println("MealServiceRequest not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      InputStream PMSRCSV = DatabaseCreator.class.getResourceAsStream(csv);
      BufferedReader PMSRCSVReader = new BufferedReader(new InputStreamReader(PMSRCSV));
      PMSRCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = PMSRCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          MealServiceRequest PMSRnode =
              new MealServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          mealServiceRequests.add(PMSRnode);
        } else {
          System.out.println("MealServiceRequests CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("MealServiceRequest.CSV not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    // Insert locations from MealServiceReqCSV into db table
    for (int i = 0; i < mealServiceRequests.size(); i++) {
      try {
        Statement initialization = connection.createStatement();
        StringBuilder MealServiceRequest = new StringBuilder();
        MealServiceRequest.append("INSERT INTO MealServiceRequest VALUES(");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getRequestID() + "'" + ", ");
        MealServiceRequest.append(
            "'" + mealServiceRequests.get(i).getDestination().getNodeID() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getStatus() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getAssignee() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getMainCourse() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getSide() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getDrink() + "'" + ", ");
        MealServiceRequest.append("'" + mealServiceRequests.get(i).getPatientFor() + "'");
        MealServiceRequest.append(")");
        initialization.execute(MealServiceRequest.toString());
      } catch (SQLException e) {
        System.out.println("Input for MealServiceReq " + i + " failed");
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
      csvFile.write("requestID,destination,status,assignee,service,patientFor");
      for (int i = 0; i < mealServiceRequests.size(); i++) {
        csvFile.write("\n" + mealServiceRequests.get(i).getRequestID() + ",");
        if (mealServiceRequests.get(i).getDestination() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getDestination().getNodeID() + ",");
        }
        if (mealServiceRequests.get(i).getStatus() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getStatus() + ",");
        }
        if (mealServiceRequests.get(i).getAssignee() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getAssignee() + ",");
        }
        if (mealServiceRequests.get(i).getMainCourse() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getMainCourse() + ",");
        }
        if (mealServiceRequests.get(i).getSide() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getSide() + ",");
        }
        if (mealServiceRequests.get(i).getDrink() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getDrink() + ",");
        }
        if (mealServiceRequests.get(i).getPatientFor() == null) {
          csvFile.write(',');
        } else {
          csvFile.write(mealServiceRequests.get(i).getPatientFor());
        }
      }
      csvFile.flush();
      csvFile.close();
    } catch (IOException e) {
      System.out.print(
          "An error occurred when trying to write to the Meal Service Request CSV file.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDNum = mealServiceRequests.size() + 1;
    return String.format("PMSR%04d", nextIDNum);
  }
}
