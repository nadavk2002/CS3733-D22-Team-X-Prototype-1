package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationDAOtests {
  // based on the CSV file as of 3/29/22

  // connection varible
  Connection connection;

  @BeforeEach
  public void setupDB() {
    // initialize Xdb
    connection = Xdb.initializeDB();
  }

  @Test
  public void TestLocationDAO() {}

  // test

}
