package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class SecretPageController implements Initializable {

  @FXML private WebView secretVideo;

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if the root
   *     object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    secretVideo
        .getEngine()
        .load(App.class.getResource("/edu/wpi/cs3733/D22/teamX/secretPage.html").toExternalForm());
  }

  @FXML
  private void stopSecretPage() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    secretVideo.getEngine().load("");
  }
}
