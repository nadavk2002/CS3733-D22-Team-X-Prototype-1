package edu.wpi.cs3733.D22.teamX.entity;

public class LoginScreen {
  String Login;
  String Username;
  String Password;

  public String getPassword() {
    return Password;
  }

  public String getUsername() {
    return Username;
  }

  public void setPassword(String password) {
    Password = password;
  }

  public void setUsername(String username) {
    Username = username;
  }

  public String getLogin() {
    return Login;
  }

  public void setLogin(String login) {
    Login = login;
  }
}
