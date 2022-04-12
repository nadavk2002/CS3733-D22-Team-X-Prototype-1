package edu.wpi.cs3733.D22.teamX.entity;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MealServiceRequestDAO implements DAO<MealServiceRequest> {
    private static List<MealServiceRequest> mealServiceRequests = new ArrayList<MealServiceRequest>();
    private static String csv = "MealRequests.csv";
    @Override
    public List<MealServiceRequest> getAllRecords() {
        return null;
    }

    @Override
    public MealServiceRequest getRecord(String recordID) {
        return null;
    }

    @Override
    public void deleteRecord(MealServiceRequest recordObject) {

    }

    @Override
    public void updateRecord(MealServiceRequest recordObject) {

    }

    @Override
    public void addRecord(MealServiceRequest recordObject) {

    }

    @Override
    public void createTable() {

    }

    @Override
    public void dropTable() {

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
