package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PreferencePageController implements Initializable {
  @FXML private JFXToggleButton muteSoundsToggle;
  @FXML private JFXToggleButton muteMusicToggle;
  @FXML private JFXSlider volumeSlider;
  private static final Media menuButtonPressSound =
      new Media(
          App.class
              .getResource("/edu/wpi/cs3733/D22/teamX/sounds/Wii_sound_menu_button_press.mp3")
              .toExternalForm());
  private static final MediaPlayer menuButtonPressSoundPlayer =
      new MediaPlayer(menuButtonPressSound);
  private static boolean muteSoundsToggleOn = false;
  private static boolean muteMusicToggleOn = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    muteSoundsToggle.setSelected(muteSoundsToggleOn);
    muteMusicToggle.setSelected(muteMusicToggleOn);
    menuButtonPressSoundPlayer.setVolume(.50);
    menuButtonPressSoundPlayer.setVolume(0); // remove this line eventually
    volumeSlider.setValue(InvisibleMusicPlayerController.mediaPlayer.getVolume() * 100);
    volumeSlider
        .valueProperty()
        .addListener(
            observable ->
                InvisibleMusicPlayerController.mediaPlayer.setVolume(
                    volumeSlider.getValue() / 100));
  }

  @FXML
  private void muteBackgroundMusic() {
    playMenuButtonPressSound();
    if (muteMusicToggle.isSelected()) {
      muteMusicToggleOn = true;
    } else {
      muteMusicToggleOn = false;
    }
    InvisibleMusicPlayerController.mediaPlayer.setMute(muteMusicToggleOn);
  }

  private void playMenuButtonPressSound() {
    menuButtonPressSoundPlayer.stop();
    menuButtonPressSoundPlayer.play();
  }

  @FXML
  private void muteSounds() {
    if (muteSoundsToggle.isSelected()) {
      muteSoundsToggleOn = true;
    } else {
      playMenuButtonPressSound();
      muteSoundsToggleOn = false;
    }
    BasicLayoutController.buttonPressSoundPlayer.setMute(muteSoundsToggleOn);
    menuButtonPressSoundPlayer.setMute(muteSoundsToggleOn);
  }
}
