package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;

import java.util.ArrayList;
import java.util.List;

public class PreferencesDAO implements DAO{
    private static List<Employee> preferences = new ArrayList<Employee>();
    private static String csv = "Preferences.csv";

    private PreferencesDAO()
    {
        if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
            fillFromTable();
        }
        if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
            preferences.clear();
        }
    }

    /** Singleton Helper Class. */
    private static class SingletonHelper {
        private static final PreferencesDAO preferencesDAO = new PreferencesDAO();
    }

    /**
     * Returns the DAO Singleton.
     *
     * @return the DAO Singleton object.
     */
    public static PreferencesDAO getDAO() {
        return SingletonHelper.preferencesDAO;
    }

    /**
     * Returns a list of type T of all the records.
     *
     * @return a list of all the records in a table.
     */
    @Override
    public List getAllRecords() {
        return preferences;
    }

    /**
     * Given a recordID, returns a specific record.
     *
     * @param recordID the recordID of the record to be returned.
     * @return the record corresponding to the recordID.
     */
    @Override
    public Object getRecord(String recordID) {
        return null;
    }

    /**
     * Deletes a specified record object.
     *
     * @param recordObject the recordObject to be deleted.
     */
    @Override
    public void deleteRecord(Object recordObject) {

    }

    /**
     * Updates a specified record object. If the record doesn't exist, it is added.
     *
     * @param recordObject the recordObject to be updated or added.
     */
    @Override
    public void updateRecord(Object recordObject) {

    }

    /**
     * Adds a record object to the database.
     *
     * @param recordObject the recordObject to be added.
     */
    @Override
    public void addRecord(Object recordObject) {

    }

    /**
     * Creates the table for the entity with fields defined by csv file/entity class
     */
    @Override
    public void createTable() {

    }

    /**
     * Drops the table from the database
     */
    @Override
    public void dropTable() {

    }

    /**
     * Loads the data from the csv file into the table
     *
     * @return true if load is successful
     */
    @Override
    public boolean loadCSV() {
        return false;
    }

    /**
     * Saves the data from the database into a csv file
     *
     * @param dirPath directory path to store csv file at
     * @return true if save is successful
     */
    @Override
    public boolean saveCSV(String dirPath) {
        return false;
    }

    /**
     * Clears, then fills the DAO list with data from the sql table
     *
     * @return true if the DAO is successfully filled
     */
    @Override
    public boolean fillFromTable() {
        return false;
    }

    /**
     * Returns the next alphanumeric ID string.
     *
     * @return the next alphanumeric ID string.
     */
    @Override
    public String makeID() {
        return null;
    }
}
