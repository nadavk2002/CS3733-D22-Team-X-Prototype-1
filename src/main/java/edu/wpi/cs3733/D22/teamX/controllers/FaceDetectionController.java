package edu.wpi.cs3733.D22.teamX.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class FaceDetectionController {
  @FXML private ImageView currentFrame;
  @FXML private Button button;

  // a timer for acquiring the video stream
  private Timer timer;
  // the OpenCV object that realizes the video capture
  private VideoCapture capture = new VideoCapture();
  // a flag to change the button behavior
  private boolean cameraActive = false;
  private Image i,histo;

  @FXML
  protected void startCamera()
  {
    if (!this.cameraActive)
    {
      // start the video capture
      this.capture.open(0);

      // is the video stream available?
      if (this.capture.isOpened())
      {
        this.cameraActive = true;

        // grab a frame every 33 ms (30 frames/sec)
        TimerTask frameGrabber = new TimerTask() {
          @Override
          public void run()
          {
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                currentFrame.setImage(i);
              }
            });
          }
        };
        this.timer = new Timer();
        this.timer.schedule(frameGrabber, 0, 33);

        // update the button content
        this.button.setText("Stop Camera");
      }
      else
      {
        // log the error
        System.err.println("Impossible to open the camera connection...");
      }
    }
    else
    {
      // the camera is not active at this point
      this.cameraActive = false;
      // update again the button content
      this.button.setText("Start Camera");
      // stop the timer
      if (this.timer != null)
      {
        this.timer.cancel();
        this.timer = null;
      }
      // release the camera
      this.capture.release();
      // clean the image area
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          currentFrame.setImage(null);
        }
      });
    }
  }
}
