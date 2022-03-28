package edu.wpi.cs3733.D22.teamX;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
//https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

public class LocationDAOImpl implements LocationDAO{
    List<Location> locations; //location storage
    Connection connection; //store connection info

    /**
     * constructor loads data from database
     * @param connection db to connect to to get location data from
     */
    public LocationDAOImpl(Connection connection){
        //add locations from the database connection specified
        locations = new ArrayList<Location>();
        try {
            // create the statement
            Statement statement = connection.createStatement();
            // execute query to see all locations and store it to a result set
            ResultSet resultSet = statement.executeQuery("Select * FROM Location");
            while (resultSet.next()) {
                //create location varible to be appended to the link list.
                Location location = new Location();

                //go through all the parameters of the location
                location.setNodeID(resultSet.getString("nodeID"));
                location.setxCoord(Integer.parseInt(resultSet.getString("xCoord")));
                location.setyCoord(Integer.parseInt(resultSet.getString("yCoord")));
                location.setFloor(resultSet.getString("floor"));
                location.setBuilding(resultSet.getString("building"));
                location.setNodeType(resultSet.getString("nodeType"));
                location.setLongName(resultSet.getString("longName"));
                location.setShortName(resultSet.getString("shortName"));

                //append location on to the end of the locations list
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = connection; //store connection information
    }


    @Override
    /**
     * gets locations linkedList
     */
    public List<Location> getAllLocations() {
        return locations; //returns locations
    }

    @Override
    /**
     * gets induvidual location
     * @param nodeID node id of location being looked up
     */
    public Location getLocation(String nodeID) {
        //iterate through the linked list of locations to find the object
        for(Location element : locations){
            //if the object has the same nodeID
            if (element.getNodeID().equals(nodeID)){
                return element;
            }
        }
        throw new NoSuchElementException("Location does not exist");
    }

    @Override
    /**
     * updates location based off matching nodeIDs.
     * @param location location being updated
     */
    public void updateLocation(Location location) {
        //iterate through the list of locations to find the object and update it on new list
        int locationInd = 0;
        while(locationInd < locations.size())
        {
            if(locations.get(locationInd).getNodeID().equals(location.getNodeID()))
            {
                locations.set(locationInd, location);
                break;
            }
            locationInd++;
        }

        // update Location table
        if(locationInd == locations.size()) {
            try {
                // create the statement
                Statement statement = connection.createStatement();
                //update sql object
                statement.executeUpdate(
                        "UPDATE Location SET xCoord = " + location.getxCoord() +
                                ", yCoord = " + location.getyCoord() +
                                ", floor = '" + location.getFloor() +
                                "', building = '" + location.getBuilding() +
                                "', nodeType = '" + location.getNodeType() +
                                "', longName = '" + location.getLongName() +
                                "', shortName = '" + location.getShortName() + "' " +
                                "WHERE nodeID = '" + location.getNodeID() + "'");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("location does not exist");
        }

    }

    @Override
    /**
     * removes location from database.
     * @param location location to be removed.
     */
    public void deleteLocation(Location location) {
        //iterate through the linked list of locations to find the object and update it on new list
        int locationInd = 0;
        while(locationInd < locations.size())
        {
            if(locations.get(locationInd).getNodeID().equals(location.getNodeID()))
            {
                locations.remove(locationInd);
                break;
            }
            locationInd++;
        }

        if(locationInd == locations.size()) {
            try {
                // create the statement
                Statement statement = connection.createStatement();
                //remove location from DB table
                statement.executeUpdate("DELETE FROM Location WHERE nodeID = '" + location.getNodeID() + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Location does not exist");
        }

    }

}