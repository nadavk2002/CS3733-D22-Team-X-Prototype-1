package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.LoginManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class LoginScreenController {
  @FXML private PasswordField password;
  @FXML private TextField username;
  @FXML private JFXButton loginButton;
  @FXML private Label message;

  @FXML
  public void validLogin() throws IOException {
    if (LoginManager.getInstance()
        .isValidLogin(username.getText(), password.getText().hashCode())) {
      App.switchScene(
          FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    } else {
      message.setText("        Your username or password is incorrect");
      message.setTextFill(Color.rgb(210, 39, 30));
      message.setTextAlignment(TextAlignment.CENTER);
    }
  }

  //  @FXML
  //  public void Login() {
  //    LoginScreen Login = new LoginScreen();
  //    LoginManager.getInstance().isValidLogin(Login.getUsername(),
  // Login.getPassword().hashCode());
  //  }

  public void disableLoginButton() {
    loginButton.setDisable(username.getText().isEmpty() || password.getText().isEmpty());
  }
}
