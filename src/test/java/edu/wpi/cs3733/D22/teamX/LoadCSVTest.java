/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.cs3733.D22.teamX;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LoadCSVTest {

  @Test
  public void testLoadLocationCSV() {
    Connection connection = Xdb.initializeDB();
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
