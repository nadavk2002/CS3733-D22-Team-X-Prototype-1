package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertTrue;

import edu.wpi.cs3733.D22.teamX.entity.SharpsDisposalRequestDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.awt.*;
import org.junit.Before;
import org.junit.Test;

public class SharpsDisposalServiceRequestDAOTests {
  SharpsDisposalRequestDAO DAO;

  @Before
  public void setupDB() {
    ConnectionSingleton.getConnectionSingleton().setEmbedded();
    try {
      DatabaseCreator.initializeDB();
    } catch (loadSaveFromCSVException e) {
      e.printStackTrace();
    }
    DAO = SharpsDisposalRequestDAO.getDAO();
  }

  @Test
  public void TestGetRecord() {
    assertTrue(DAO.getRecord("SDSR0007").getStatus().equals("PROC"));
  }
}
