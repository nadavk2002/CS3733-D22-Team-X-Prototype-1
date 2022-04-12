package edu.wpi.cs3733.D22.teamX.entity;

import java.security.PublicKey;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MealServiceRequestDAO implements DAO<MealServiceRequest> {
    private static List<MealServiceRequest> mealServiceRequests = new ArrayList<MealServiceRequest>();
    private static String csv = "MealRequests.csv";

    /**
     * creates new MealServiceRequestDAO
     */
    private MealServiceRequestDAO(){}

    /**
     * Singleton helper Class
     */
    private static class SingletonHelper{
        private static final MealServiceRequestDAO mealServiceRequestDAO = new MealServiceRequestDAO();
    }

    /**
     * returns the singleton thing
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
        for(MealServiceRequest msr: mealServiceRequests){
            if(msr.equals(recordID)){
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
        return false;
    }

    @Override
    public boolean saveCSV(String dirPath) {
        return false;
    }

    @Override
    public String makeID() {
        return null;
    }
}
