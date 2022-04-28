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
        try {
            InputStream userPrefCSV = DatabaseCreator.class.getResourceAsStream(csvFolderPath + csv);
            BufferedReader userPrefCSVReader = new BufferedReader(new InputStreamReader(userPrefCSV));
            userPrefCSVReader.readLine();
            String nextFileLine;
            while ((nextFileLine = userPrefCSVReader.readLine()) != null) {
                String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
                if (currLine.length == 4) {
                    UserPreference userPrefNode =
                            new UserPreference(currLine[0], currLine[1].charAt(0), currLine[2].charAt(0), Integer.parseInt(currLine[3]));
                    userPreferences.add(userPrefNode);
                } else {
                    System.out.println("UserPreference CSV file formatted improperly");
                    System.exit(1);
                }
            }
            userPrefCSV.close();
            userPrefCSVReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Insert Employee from EmployeesCSV into db table
        for (int i = 0; i < userPreferences.size(); i++) {
            try {
                Statement initialization = connection.createStatement();
                StringBuilder userPreference = new StringBuilder();
                userPreference.append("INSERT INTO UserPreference VALUES(");
                userPreference.append("'" + userPreferences.get(i).getUsername() + "'" + ", ");
                userPreference.append("'" + userPreferences.get(i).getMuteSoundsChar() + "'" + ", ");
                userPreference.append("'" + userPreferences.get(i).getMuteMusicChar() + "'" + ", ");
                userPreference.append(userPreferences.get(i).getVolume());
                userPreference.append(")");
                initialization.execute(userPreference.toString());
            } catch (SQLException e) {
                System.out.println("Input for UserPreference " + i + " failed");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Saves the data from the database into a csv file
     *
     * @param dirPath directory path to store csv file at
     * @return true if save is successful
     */
    @Override
    public boolean saveCSV(String dirPath) {
        try {
            FileWriter csvFile = new FileWriter(dirPath + csv, false);
            csvFile.write("username,muteSounds,muteMusic,volume");
            for (int i = 0; i < userPreferences.size(); i++) {
                csvFile.write("\n" + userPreferences.get(i).getUsername() + ",");
                csvFile.write(userPreferences.get(i).getMuteSoundsChar() + ",");
                csvFile.write(userPreferences.get(i).getMuteMusicChar() + ",");
                csvFile.write(userPreferences.get(i).getVolume());
            }
            csvFile.flush();
            csvFile.close();
        } catch (IOException e) {
            System.out.println("Error occurred when updating UserPreferences csv file.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Clears, then fills the DAO list with data from the sql table
     *
     * @return true if the DAO is successfully filled
     */
    @Override
    public boolean fillFromTable() {
        try {
            userPreferences.clear();
            Statement fromTable = connection.createStatement();
            ResultSet results = fromTable.executeQuery("SELECT * FROM UserPreference");
            while (results.next()) {
                UserPreference toAdd = new UserPreference();
                toAdd.setUsername(results.getString("username"));
                toAdd.setMuteSounds(results.getString("muteSounds").charAt(0));
                toAdd.setMuteMusic(results.getString("muteMusic").charAt(0));
                toAdd.setVolume(results.getInt("volume"));
                userPreferences.add(toAdd);
            }
        } catch (SQLException e) {
            System.out.println("UserPreferenceDAO could not be filled from the sql table");
            return false;
        }
        return true;
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
