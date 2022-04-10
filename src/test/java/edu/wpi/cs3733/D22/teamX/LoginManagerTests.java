package edu.wpi.cs3733.D22.teamX;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginManagerTests {
  @Test
  public void testValidLogin() {
    String usr = "empl0000";
    int pwd = "helloworld".hashCode();
    int falsePwd = "whatsupworld".hashCode();
    assertTrue(LoginManager.getInstance().isValidLogin(usr, pwd));
    assertFalse(LoginManager.getInstance().isValidLogin(usr, falsePwd));
  }

  @Test
  public void testAddLogin() {
    String usr = "testusr";
    int pwd = "testpwd".hashCode();
    LoginManager.getInstance().addLogin(usr, pwd);
    assertTrue(LoginManager.getInstance().isValidLogin(usr, pwd));
  }
}
