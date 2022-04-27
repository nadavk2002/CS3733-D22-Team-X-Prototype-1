// https://www.baeldung.com/java-nio-datagramchannel

package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.UDPServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class RoomSystemControlController implements Initializable {
  // add things
  @FXML private TextField words;
  @FXML private Label label;

  // networking things
  private final UDPServer server = UDPServer.getUDPServer();
  // change this to the actual IP adresss
  InetSocketAddress remAddress = new InetSocketAddress("192.168.137.64", 6587);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initUDPchecker();
  }

  @FXML
  public void updateTextBox(ActionEvent actionEvent) throws IOException {
    // label.setText(words.getText() + " " + server.receive());
    server.send(words.getText(), remAddress);
  }

  private void initUDPchecker() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  label.setText(server.receive());
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }
}
