package edu.wpi.cs3733.D22.teamX;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

//in theory alot of the functionallity of this thing is handled in userProgram but cannot be used because of the private flag (3/27/22)
public class LocationDAOImpl implements LocationDAO{
    List<Location> locations; //location storage
    Connection connection; //store connection info

    /**
     * constructor loads data from database
     * @param connection db to connect to to get location data from
     */
    public LocationDAOImpl(Connection connection){
        //add locations from the database connection specified
        try {
            // create the statement
            Statement statement = connection.createStatement();
            // execute query to see all locations and store it to a result set
            ResultSet resultSet = statement.executeQuery("Select * FROM Location");
            //determine the number of colunms
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            //place data into linked list
            while (resultSet.next()) {
                //create location varible to be appended to the link list.
                Location location = new Location();

                //go through all of the parameters of the location
                for (int i = 1; i <= columnsNumber; i++) {
                    // print column of data remove after testing
                    System.out.print(
                            resultSetMetaData.getColumnName(i).replace("_", " ")
                                    + ": "
                                    + resultSet.getString(i)
                                    + " ");

                    //there is definetly a more efficent way of doing this
                    //set params of Location based on incoming column name
                    switch (resultSetMetaData.getColumnName(i)){
                        case "nodeID": location.setNodeID(resultSet.getString(i));
                        break;
                        case "xCoord": location.setxCoord(Integer.parseInt(resultSet.getString(i)));
                        break;
                        case "yCoord": location.setxCoord(Integer.parseInt(resultSet.getString(i)));
                        break;
                        case "floor": location.setFloor(resultSet.getString(i));
                        break;
                        case "building": location.setBuilding(resultSet.getString(i));
                        break;
                        case "nodeType": location.setNodeType(resultSet.getString(i));
                        break;
                        case "longName": location.setLongName(resultSet.getString(i));
                        break;
                        case "shortName": location.setShortName(resultSet.getString(i));
                        break;
                        //um how did we get here?
                        default: System.out.println("invalid Location param from DB");
                        break;
                    }
                }
                // create new line
                System.out.println(" ");
                //append location on to the end of the linked list
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
        Location location = new Location(); //location being returned
        //is there a contains for parameter of object in linklist?
        //iterate through the linked list of locations to find the object
        for(Location element: locations){
            //if the object has the same nodeID
            if (element.getNodeID().equals(nodeID)){
                location = element; //record element that had matching ID
                break; //exit for loop;
            }
        }
        return location; //return location
        //what happens when there is an invalid location ID? guess we return a blank location.
    }

    @Override
    /**
     * updates location based off matching nodeIDs.
     * @param location location being updated
     */
    public void updateLocation(Location location) {
        List<Location> newLocationList = new ArrayList<Location>(); //location list being updated.
        //update location in linked list

        //this is horrible probably
        //iterate through the linked list of locations to find the object and update it on new list
        for(int i = 0; i < locations.size(); i++){ //check if exits correctly [0.1,2,3].size = 4 exit when i = 3
            //if location node id matches
            if(locations.get(i).getNodeID().equals(location.getNodeID())){
                //append updated location data
                newLocationList.add(location);
            }
            else{
                //append old location data
                newLocationList.add(locations.get(i));
            }
        }
        //update running locations linkedlist
        locations = newLocationList;

        //update DB table from darren kwee ctrl+c ctrl+v + some stuff
        try{
            // create the statement
            Statement statement = connection.createStatement();
            // execute query to see if location even exists? something else needs to be done maybe? (throw exception?)
            statement.executeQuery("SELECT * FROM Location WHERE nodeID = '" + location.getNodeID() + "'");

            //update sql object
            statement.executeUpdate(
                    "UPDATE Location SET floor = '"
                            + location.getFloor()
                            + "', nodeType = '"
                            + location.getNodeType()
                            + "', xCoord = '"
                            + location.getxCoord()
                            + "', yCoord = '"
                            + location.getyCoord()
                            + "', building = '"
                            + location.getBuilding()
                            + "', longName = '"
                            + location.getLongName()
                            + "', shortName = '"
                            + location.getShortName()
                            + "' WHERE nodeID = '"
                            + location.getNodeID()
                            + "'");

        } catch (SQLException e) {
        e.printStackTrace();
         }

    }

    @Override
    /**
     * removes location from database.
     * @param location location to be removed.
     */
    public void deleteLocation(Location location) {
        ArrayList<Location> newLocationList = new ArrayList<Location>(); //location list being updated.
        //update location in linked list

        //this is horrible probably
        //iterate through the linked list of locations to find the object and update it on new list
        for(int i = 0; i < locations.size(); i++){ //check if exits correctly [0.1,2,3].size = 4 exit when i = 3
            //if location node id matches
            if(locations.get(i).getNodeID().equals(location.getNodeID())){
                //do nothing.
            }
            else{
                //append old location data
                newLocationList.add(locations.get(i));
            }
        }
        //update running locations linkedlist
        locations = newLocationList;

        //update DB table from darren kwee ctrl+c ctrl+v + some stuff
        try{
            // create the statement
            Statement statement = connection.createStatement();
            // execute query to see if location even exists? something else needs to be done maybe? (throw exception?)
            statement.executeQuery("SELECT * FROM Location WHERE nodeID = '" + location.getNodeID() + "'");
            //remove location from DB table
            statement.executeUpdate("DELETE FROM Location WHERE nodeID = '" + location.getNodeID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //done

    }

}