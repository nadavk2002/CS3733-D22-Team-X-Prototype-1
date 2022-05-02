package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import edu.wpi.cs3733.D22.teamX.LoginManager;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nu.pattern.OpenCV;

public class LoginScreenController implements Initializable {
  @FXML private VBox serverVBox;
  @FXML private JFXRadioButton optionEmbedded;
  @FXML private JFXRadioButton optionClient;
  @FXML private PasswordField password;
  @FXML private TextField username;
  @FXML private JFXButton loginButton, exitButton;
  @FXML private Label message;
  @FXML private ImageView loginImage;
  @FXML private VBox loginFields;
  @FXML private BorderPane loginPane;
  @FXML private JFXCheckBox faceCheckBox;
  public static String currentUsername;
  private SimpleBooleanProperty property = new SimpleBooleanProperty(false);

  @FXML
  public void validLogin() throws IOException {
    if (LoginManager.getInstance()
        .isValidLogin(username.getText(), password.getText().hashCode())) {
      currentUsername = username.getText();
      if (!ConnectionSingleton.getConnectionSingleton().isConnectionEstablished()) {
        if (optionEmbedded.isSelected()) {
          try {
            DatabaseCreator.initializeDB();
            ConnectionSingleton.getConnectionSingleton().setEmbedded();
            System.out.println("Apache Derby connection established :D");
          } catch (loadSaveFromCSVException e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
        if (optionClient.isSelected()) {
          try {
            DatabaseCreator.initializeDB();
            ConnectionSingleton.getConnectionSingleton().setClient();
            System.out.println("Apache Derby connection established :D");
          } catch (loadSaveFromCSVException e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
      }
      App.switchRoot();
      password.setOnKeyPressed(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              try {
                App.switchRoot();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          });
    } else {
      message.setText("Your username or password is incorrect");
    }
  }

  public void disableLoginButton() {
    loginButton.setDisable(
        username.getText().isEmpty() || password.getText().isEmpty() || !property.get());
  }

  @FXML
  void ExitApplication() {
    Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (ConnectionSingleton.getConnectionSingleton().isConnectionEstablished()) {
      serverVBox.setDisable(true);
      serverVBox.setVisible(false);
    }
    faceCheckBox.setDisable(false);
    loginImage.fitWidthProperty().bind(loginFields.widthProperty());
    loginImage.fitHeightProperty().bind(loginFields.heightProperty());

    loginFields
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              loginImage.setFitWidth(loginFields.getWidth() - 150);
            });
    this.property.addListener(
        event -> {
          disableLoginButton();
        });
  }

  @FXML
  private void goToFaceDetection() throws IOException {
    OpenCV.loadLocally();
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/FaceDetection.fxml"));
    Stage stage = new Stage();
    fxmlLoader.setController(new FaceDetectionController(property, stage));
    Parent root1 = fxmlLoader.load();
    stage.setOnCloseRequest(
        event -> {
          disableLoginButton();
        });
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("ARE YOU A ROBOT?");
    stage.setScene(new Scene(root1));
    stage.show();
  }
}
