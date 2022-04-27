package edu.wpi.cs3733.D22.teamX.entity;

import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserPreferenceDAO implements DAO<UserPreference> {
    private static List<UserPreference> userPreferences = new ArrayList<UserPreference>();
    private static String csv = "UserPreferences.csv";

    private UserPreferenceDAO()
    {
        if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("client")) {
            fillFromTable();
        }
        if (ConnectionSingleton.getConnectionSingleton().getConnectionType().equals("embedded")) {
            userPreferences.clear();
        }
    }

    /** Singleton Helper Class. */
    private static class SingletonHelper {
        private static final UserPreferenceDAO USER_PREFERENCE_DAO = new UserPreferenceDAO();
    }

    /**
     * Returns the DAO Singleton.
     *
     * @return the DAO Singleton object.
     */
    public static UserPreferenceDAO getDAO() {
        return SingletonHelper.USER_PREFERENCE_DAO;
    }

    /**
     * Returns a list of type T of all the records.
     *
     * @return a list of all the records in a table.
     */
    @Override
    public List getAllRecords() {
        return userPreferences;
    }

    /**
     * Given a recordID, returns a specific record.
     *
     * @param username the username of the record to be returned.
     * @return the record corresponding to the username.
     */
    @Override
    public UserPreference getRecord(String username) {
        for (UserPreference element : userPreferences) {
            // if the object has the same nodeID
            if (element.getUsername().equals(username)) {
                return element;
            }
        }
        throw new NoSuchElementException("UserPreference" + username + "does not exist");
    }

    /**
     * Deletes a specified record object.
     *
     * @param recordObject the recordObject to be deleted.
     */
    @Override
    public void deleteRecord(UserPreference recordObject) {
        int userPreferenceInd = 0;
        while (userPreferenceInd < userPreferences.size()) {
            if (userPreferences.get(userPreferenceInd).equals(recordObject)) {
                userPreferences.remove(userPreferenceInd);
                break;
            }
            userPreferenceInd++;
        }
        // if the Employee is found, delete it from the Employee table
        if (userPreferenceInd < userPreferences.size()) {
            try {
                // create the statement
                Statement statement = connection.createStatement();
                // remove Employee from DB table
                statement.executeUpdate(
                        "DELETE FROM UserPreference WHERE username = '" + recordObject.getUsername() + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchElementException("UserPreference does not exist (in delete)");
        }
    }

    /**
     * Updates a specified record object. If the record doesn't exist, it is added.
     *
     * @param recordObject the recordObject to be updated or added.
     */
    @Override
    public void updateRecord(UserPreference recordObject) {
        int userPreferenceInd = 0;
        while (userPreferenceInd < userPreferences.size()) {
            if (userPreferences.get(userPreferenceInd).equals(recordObject)) {
                userPreferences.set(userPreferenceInd, recordObject);
                break;
            }
            userPreferenceInd++;
        }

        if (userPreferenceInd < userPreferences.size()) {
            try {
                // create the statement
                Statement statement = connection.createStatement();
                // update sql object
                statement.executeUpdate(
                        "UPDATE UserPreference SET muteSounds = '" + recordObject.getMuteSounds()
                        + "', muteMusic = '" + recordObject.getMuteMusic()
                        + "', volume = " + recordObject.getVolume()
                        + " WHERE username = '" + recordObject.getUsername() + "'");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchElementException("UserPreference does not exist (in update)");
        }
    }

    /**
     * Adds a record object to the database.
     *
     * @param recordObject the recordObject to be added.
     */
    @Override
    public void addRecord(UserPreference recordObject) {
        userPreferences.add(recordObject);
        try {
            Statement initialization = connection.createStatement();
            StringBuilder userPreference = new StringBuilder();
            userPreference.append("INSERT INTO UserPreference VALUES(");
            userPreference.append("'" + recordObject.getUsername() + "'" + ", ");
            userPreference.append("'" + recordObject.getMuteSoundsChar() + "'" + ", ");
            userPreference.append("'" + recordObject.getMuteMusicChar() + "'" + ", ");
            userPreference.append(recordObject.getVolume());
            userPreference.append(")");
            initialization.execute(userPreference.toString());
        } catch (SQLException e) {
            System.out.println("UserPreference database could not be updated");
        }
    }

    /**
     * Creates the table for the entity with fields defined by csv file/entity class
     */
    @Override
    public void createTable() {
        try {
            Statement initialization = connection.createStatement();
            initialization.execute(
                    "CREATE TABLE UserPreference(username VARCHAR(20) PRIMARY KEY NOT NULL, "
                            + "muteSounds CHAR(1), "
                            + "muteMusic CHAR(1), "
                            + "volume INT)");
        } catch (SQLException e) {
            System.out.println("UserPreference table creation failed. Check output console.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Drops the table from the database
     */
    @Override
    public void dropTable() {
        try {
            Statement dropUserPreference = connection.createStatement();
            dropUserPreference.execute("DROP TABLE UserPreference");
        } catch (SQLException e) {
            System.out.println("UserPreference not dropped");
        }
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
