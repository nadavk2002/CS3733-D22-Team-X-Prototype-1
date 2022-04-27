package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class InvisibleMusicPlayerController implements Initializable {
  private static final Media sound =
      new Media(
          App.class
              .getResource("/edu/wpi/cs3733/D22/teamX/sounds/Wii_Menu_Music.mp3")
              .toExternalForm());
  public static final MediaPlayer mediaPlayer = new MediaPlayer(sound);
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
    mediaPlayer.setVolume(.15);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setOnStopped(() -> mediaPlayer.play());
  }
}
