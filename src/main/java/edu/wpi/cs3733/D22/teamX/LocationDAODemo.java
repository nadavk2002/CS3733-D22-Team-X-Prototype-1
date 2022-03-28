package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;

public class LocationDAODemo
{
    public static void demo(Connection connection)
    {
        LocationDAO lDAO = new LocationDAOImpl(connection);
    }
}
