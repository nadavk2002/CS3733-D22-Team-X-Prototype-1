package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.assertEquals;

import edu.wpi.cs3733.D22.teamX.controllers.ReqLangController;
import org.junit.Before;
import org.junit.Test;

public class ReqLangControllerTest {

  private ReqLangController controller;

  @Before
  public void setup() {
    controller = new ReqLangController();
  }

  @Test
  public void testLocationNameToId() {
    assertEquals(controller.locationToNodeId("CIM"), "FDEPT00101");
  }
}
