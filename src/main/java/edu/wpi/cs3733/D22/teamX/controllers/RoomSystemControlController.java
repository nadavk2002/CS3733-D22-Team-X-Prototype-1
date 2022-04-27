// https://www.baeldung.com/java-nio-datagramchannel

package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamX.UDPServer;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class RoomSystemControlController implements Initializable {
  // fxml
  @FXML private Label tempLabel, fanText, lampText, ipText;
  @FXML private TextField tempField, ipField;
  @FXML private JFXToggleButton FanToggle, lampToggle;
  @FXML private ChoiceBox<String> roomChoice;

  // networking things
  private final UDPServer server = UDPServer.getUDPServer();
  // change this to the actual IP adresss
  InetSocketAddress remAddress = new InetSocketAddress("192.168.137.64", 6587);

  // interface lockouts
  private boolean CanUpdateRoom = true;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initUDPchecker();
    updateTempText();
    updateFanText();
    updatelampText();
    // updateIpText();

    // populate choicebox
    roomChoice.getItems().addAll("room 1"); // place holder for someone.

    // add action for the choice box
    roomChoice.setOnAction((ActionEvent event) -> updateRoom());
  }

  public void updateIpText() {}

  public void updatelampText() {
    server.getValue("LIT", remAddress);
  }

  public void updateFanText() {
    server.getValue("FAN", remAddress);
  }

  public void updateTempText() {
    server.getValue("TEM", remAddress);
  }

  public void updateRoom() {
    // set remAdress to IP of room at port 6587;
  }

  private void initUDPchecker() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  // tempLabel.setText(server.receive());
                  String serverText = server.receive();
                  if (serverText != "") {
                    CanUpdateRoom = false;
                    String typeText = serverText.substring(0, 3);
                    String infoText = serverText.substring(3);
                    switch (typeText) {
                      case "TEM":
                        tempLabel.setText(infoText);
                        tempField.setText(infoText);
                        break;
                      case "LIT":
                        lampText.setText(infoText);
                        if (infoText.equals("on")) {
                          lampToggle.setSelected(true);
                        } else if (infoText.equals("off")) {
                          lampToggle.setSelected(false);
                        }
                        break;
                      case "FAN":
                        fanText.setText(infoText);
                        if (infoText.equals("on")) {
                          FanToggle.setSelected(true);
                        } else if (infoText.equals("off")) {
                          FanToggle.setSelected(false);
                        }
                        break;
                      default:
                        tempLabel.setText(serverText);
                    }
                    CanUpdateRoom = true;
                  }
                }),
            new KeyFrame(Duration.seconds(0.1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }

  public void updateTemp(ActionEvent actionEvent) {
    if (CanUpdateRoom) {
      server.setTemp(Double.parseDouble(tempField.getText()), remAddress);
    }
  }

  public void updateFan(ActionEvent actionEvent) {
    if (CanUpdateRoom) {
      server.setFan(FanToggle.isSelected(), remAddress);
    }
  }

  public void updateLamp(ActionEvent actionEvent) {
    if (CanUpdateRoom) {
      server.setLamp(lampToggle.isSelected(), remAddress);
    }
  }

  public void updateIP(ActionEvent actionEvent) {
    if (CanUpdateRoom) {
      remAddress = new InetSocketAddress(ipField.getText(), 6587);
      ipText.setText(ipField.getText());
      updateTempText();
      updateFanText();
      updatelampText();
    }
  }
}
