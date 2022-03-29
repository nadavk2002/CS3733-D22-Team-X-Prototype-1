package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import org.junit.jupiter.api.BeforeEach;

public class LocationDAOtests {

  // connection varible
  Connection connection;

  @BeforeEach
  public void setupDB() {
    // initialize Xdb
    connection = Xdb.initializeDB();
  }

  // test

}
