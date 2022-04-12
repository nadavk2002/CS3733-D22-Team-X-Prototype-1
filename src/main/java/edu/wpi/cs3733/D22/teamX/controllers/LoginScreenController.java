package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.util.Map;

import edu.wpi.cs3733.D22.teamX.LoginManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginScreenController{
  @FXML private PasswordField Password;
  @FXML private TextField Username;
  @FXML private JFXButton LoginButton;
  @FXML private Label message;

  /*@FXML
  public void initialize(){

  }*/
  @FXML
  public void ValidLogin() throws IOException {
    if (LoginManager.getInstance().isValidLogin("admin", "admin".hashCode())
            || LoginManager.getInstance().isValidLogin("staff", "staff".hashCode())) {
      App.switchScene(
              FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    }
    else{
      message.setText("Your username or password are incorrect");
      message.setTextFill(Color.rgb(210, 39, 30));
    }
  }

  public void disableLoginButton() {
    LoginButton.setDisable(Username.getText().equals("") || Password.getText().equals(""));
  }
}
