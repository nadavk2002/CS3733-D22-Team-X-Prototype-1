package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PreferencePageController implements Initializable {
  @FXML private JFXComboBox<String> colorSchemeCombo, accessOptionsCombo;
  @FXML private JFXToggleButton darkModeToggle, muteSoundsToggle, muteMusicToggle;
  @FXML private JFXSlider volumeSlider;
  private static final Media menuButtonPressSound =
      new Media(
          App.class
              .getResource("/edu/wpi/cs3733/D22/teamX/sounds/Wii_sound_menu_button_press.mp3")
              .toExternalForm());
  public static final MediaPlayer menuButtonPressSoundPlayer =
      new MediaPlayer(menuButtonPressSound);
  private static boolean muteSoundsToggleOn = false;
  private static boolean muteMusicToggleOn = false;
  private static boolean darkModeToggleOn = false;
  private static String colorSchemeLast = "Blue";
  private static String accessOptionsLast = "None";
  private static String colorSchemeCurrent = colorSchemeLast;
  private static String accessOptionsCurrent = accessOptionsLast;
  private HashMap<String, String[]> palettes;
  public static boolean muteSoundsToggleOn = false;
  public static boolean muteMusicToggleOn = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initPalettes();
    muteSoundsToggle.setSelected(muteSoundsToggleOn);
    muteMusicToggle.setSelected(muteMusicToggleOn);
    darkModeToggle.setSelected(darkModeToggleOn);
    colorSchemeCombo.getSelectionModel().select(colorSchemeLast);
    accessOptionsCombo.getSelectionModel().select(accessOptionsLast);
    menuButtonPressSoundPlayer.setVolume(.50);
    volumeSlider.setValue(InvisibleMusicPlayerController.mediaPlayer.getVolume() * 100);
    volumeSlider
        .valueProperty()
        .addListener(
            observable ->
                InvisibleMusicPlayerController.mediaPlayer.setVolume(
                    volumeSlider.getValue() / 100));

    colorSchemeCombo.setOnAction(
        (event) -> {
          String currentScheme = "";

          if (accessOptionsCurrent != "None") {
            currentScheme += " " + accessOptionsCurrent;
          }
          if (darkModeToggle.isSelected()) {
            currentScheme += " " + "Dark";
          }
          currentScheme = colorSchemeCombo.getSelectionModel().getSelectedItem() + currentScheme;
          System.out.println(currentScheme);

          String[] selectedScheme = palettes.get(currentScheme);
          App.changeColorStyle(selectedScheme);
        });

    accessOptionsCombo.setOnAction(
        (event) -> {
          String currentScheme = colorSchemeCurrent;

          if (accessOptionsCombo.getSelectionModel().getSelectedItem() != "None") {
            currentScheme += " " + accessOptionsCombo.getSelectionModel().getSelectedItem();
          }
          if (darkModeToggle.isSelected()) {
            currentScheme += " " + "Dark";
          }

          System.out.println(currentScheme);

          String[] selectedScheme = palettes.get(currentScheme);
          App.changeColorStyle(selectedScheme);
        });

    darkModeToggle.setOnAction(
        (event) -> {
          String currentScheme = colorSchemeCurrent;

          if (accessOptionsCurrent != "None") {
            currentScheme += " " + accessOptionsCurrent;
          }
          if (darkModeToggle.isSelected()) {
            currentScheme += " " + "Dark";
          }

          System.out.println(currentScheme);

          String[] selectedScheme = palettes.get(currentScheme);
          App.changeColorStyle(selectedScheme);
        });
  }

  private void initPalettes() {
    palettes = new HashMap<>();

    String[] blueLightPalette = new String[] {"F7FDFF", "9ED5FF", "378BCC", "16629C", "899091"};
    String[] greenLightPalette = new String[] {"FAFFF7", "B4F598", "7DE069", "6BBD5B", "919089"};
    String[] blueDarkPalette = new String[] {"061B2C", "0F273A", "004274", "00698C", "9FA7AB"};
    String[] blueProtanopiaPalette =
        new String[] {"F9F9FE", "B5B6F4", "5B5CBC", "36378D", "8C8C90"};
    String[] blueDarkProtanopiaPalette =
        new String[] {"0F0F27", "191935", "1C1D67", "2D2E83", "A2A2AA"};

    palettes.put("Blue", blueLightPalette);
    palettes.put("Green", greenLightPalette);

    palettes.put("Blue Dark", blueDarkPalette);

    palettes.put("Blue Protanopia", blueProtanopiaPalette);

    palettes.put("Blue Protanopia Dark", blueDarkProtanopiaPalette);

    String[] schemeNames = {"Blue", "Green"};
    String[] accessNames = {"Protanopia"};
    colorSchemeCombo.getItems().addAll(schemeNames);
    accessOptionsCombo.getItems().addAll(accessNames);
    accessOptionsCombo.getItems().add("None");
  }

  /** Mutes the background music if the mute toggle is selected, unmutes if unselected */
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

  /** Plays the menu button sound */
  private void playMenuButtonPressSound() {
    menuButtonPressSoundPlayer.stop();
    menuButtonPressSoundPlayer.play();
  }

  /** Mutes all button press sounds if mute toggle is selected, unmutes if unselected */
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

  @FXML
  private void applyChanges() {
    App.applyColorChanges();
  }

  // WONT DO SOUND YET

  @FXML
  private void setDefault() {
    colorSchemeLast = colorSchemeCombo.getSelectionModel().getSelectedItem();
    accessOptionsLast = accessOptionsCombo.getSelectionModel().getSelectedItem();
    darkModeToggleOn = darkModeToggle.isSelected();
  }

  @FXML
  private void revertDefault() {
    darkModeToggle.setSelected(darkModeToggleOn);
    colorSchemeCombo.getSelectionModel().select(colorSchemeLast);
    accessOptionsCombo.getSelectionModel().select(accessOptionsLast);
    // App.changeColorStyle();
  }
}
