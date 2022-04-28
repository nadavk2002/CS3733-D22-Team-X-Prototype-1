package edu.wpi.cs3733.D22.teamX.entity;

import java.util.Objects;

public class UserPreference {
  private String username;
  private boolean muteSounds;
  private boolean muteMusic;
  private double volume;

  public UserPreference() {
    this.username = "";
    this.muteSounds = false;
    this.muteMusic = false;
    this.volume = 0;
  }

  public UserPreference(String username, boolean muteSounds, boolean muteMusic, double volume) {
    this.username = username;
    this.muteSounds = muteSounds;
    this.muteMusic = muteMusic;
    this.volume = volume;
  }

  public UserPreference(String username, char muteSounds, char muteMusic, double volume) {
    this.username = username;
    if (muteSounds == 'Y') {
      this.muteSounds = true;
    } else {
      this.muteSounds = false;
    }
    if (muteMusic == 'Y') {
      this.muteMusic = true;
    } else {
      this.muteMusic = false;
    }
    this.volume = volume;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public boolean getMuteSounds() {
    return muteSounds;
  }

  public char getMuteSoundsChar() {
    if (muteSounds) {
      return 'Y';
    } else {
      return 'N';
    }
  }

  public void setMuteSounds(boolean muteSounds) {
    this.muteSounds = muteSounds;
  }

  public void setMuteSounds(char muteSounds) {
    if (muteSounds == 'Y') {
      this.muteSounds = true;
    } else if (muteSounds == 'N') {
      this.muteSounds = false;
    }
  }

  public boolean getMuteMusic() {
    return muteMusic;
  }

  public char getMuteMusicChar() {
    if (muteMusic) {
      return 'Y';
    } else {
      return 'N';
    }
  }

  public void setMuteMusic(boolean muteMusic) {
    this.muteMusic = muteMusic;
  }

  public void setMuteMusic(char muteMusic) {
    if (muteMusic == 'Y') {
      this.muteMusic = true;
    } else if (muteMusic == 'N') {
      this.muteMusic = false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPreference userPreference = (UserPreference) o;
    return Objects.equals(username, userPreference.username);
  }
}
