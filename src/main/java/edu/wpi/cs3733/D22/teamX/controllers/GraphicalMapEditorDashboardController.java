package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAOImpl;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

// import org.w3c.dom.Text;

public class GraphicalMapEditorDashboardController implements Initializable {
  @FXML private AnchorPane anchorRoot;
  @FXML private StackPane parentPage;
  @FXML private JFXButton toMapEditor;
  @FXML private Rectangle levFiveDirty;
  @FXML private Rectangle levFiveClean;
  @FXML private Rectangle levFourDirty;
  @FXML private Rectangle levFourClean;
  @FXML private Rectangle levThreeDirty;
  @FXML private Rectangle levThreeClean;
  @FXML private Rectangle levTwoDirty;
  @FXML private Rectangle levTwoClean;
  @FXML private Rectangle levOneDirty;
  @FXML private Rectangle levOneClean;
  @FXML private Rectangle llOneDirty;
  @FXML private Rectangle llOneClean;
  @FXML private Rectangle llTwoDirty;
  @FXML private Rectangle llTwoClean;
  @FXML private Text levFiveDirtyText;
  @FXML private Text levFiveCleanText;
  @FXML private Text levFourDirtyText;
  @FXML private Text levFourCleanText;
  @FXML private Text levThreeDirtyText;
  @FXML private Text levThreeCleanText;
  @FXML private Text levTwoDirtyText;
  @FXML private Text levTwoCleanText;
  @FXML private Text levOneDirtyText;
  @FXML private Text levOneCleanText;
  @FXML private Text llOneDirtyText;
  @FXML private Text llOneCleanText;
  @FXML private Text llTwoDirtyText;
  @FXML private Text llTwoCleanText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    dynamicSizeRectangles(levFiveDirty, levFiveClean, sortEquipmentByFloor("5"));
    dirtyCleanNumber(levFiveDirtyText, levFiveCleanText, sortEquipmentByFloor("5"));

    dynamicSizeRectangles(levFourDirty, levFourClean, sortEquipmentByFloor("4"));
    dirtyCleanNumber(levFourDirtyText, levFourCleanText, sortEquipmentByFloor("4"));

    dynamicSizeRectangles(levThreeDirty, levThreeClean, sortEquipmentByFloor("3"));
    dirtyCleanNumber(levThreeDirtyText, levThreeCleanText, sortEquipmentByFloor("3"));

    dynamicSizeRectangles(levTwoDirty, levTwoClean, sortEquipmentByFloor("2"));
    dirtyCleanNumber(levTwoDirtyText, levTwoCleanText, sortEquipmentByFloor("2"));

    dynamicSizeRectangles(levOneDirty, levOneClean, sortEquipmentByFloor("1"));
    dirtyCleanNumber(levOneDirtyText, levOneCleanText, sortEquipmentByFloor("1"));

    dynamicSizeRectangles(llOneDirty, llOneClean, sortEquipmentByFloor("L1"));
    dirtyCleanNumber(llOneDirtyText, llOneCleanText, sortEquipmentByFloor("L1"));

    dynamicSizeRectangles(llTwoDirty, llTwoClean, sortEquipmentByFloor("L2"));
    dirtyCleanNumber(llTwoDirtyText, llTwoCleanText, sortEquipmentByFloor("L2"));
  }

  @FXML
  private void ToMapEditor() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    Scene scene = toMapEditor.getScene();
    root.translateYProperty().set(scene.getHeight());
    parentPage.getChildren().add(root);
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);

    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(
        event -> {
          parentPage.getChildren().remove(anchorRoot);
        });
    timeline.play();
  }

  private void dirtyCleanNumber(Text dirty, Text clean, ObservableList<EquipmentUnit> equipList) {
    int dirtyAmount = 0;
    int cleanAmount = 0;
    for (EquipmentUnit equipmentUnit : equipList) {
      if (equipmentUnit.getCurrLocation().getShortName().toLowerCase().contains("clean")) {
        cleanAmount++;
      } else {
        dirtyAmount++;
      }
    }
    //    if (cleanAmount == 0) {
    //      clean.setText("0");
    //    }
    //    if (dirtyAmount == 0) {
    //      dirty.setText("0");
    //    }
    dirty.setText(Integer.toString(dirtyAmount));
    clean.setText(Integer.toString(cleanAmount));
  }

  private void dynamicSizeRectangles(
      Rectangle dirty, Rectangle clean, ObservableList<EquipmentUnit> equipList) {
    int dirtyAmount = 0;
    int cleanAmount = 0;
    for (EquipmentUnit equipmentUnit : equipList) {
      if (equipmentUnit.getCurrLocation().getShortName().toLowerCase().contains("clean")) {
        cleanAmount++;
      } else {
        dirtyAmount++;
      }
    }
    if (cleanAmount == 0 && dirtyAmount == 0) {
      clean.setWidth(200);
      dirty.setWidth(200);
      clean.setFill(Color.LIGHTGRAY);
      dirty.setFill(Color.LIGHTGRAY);
    } else if (cleanAmount == 0 || dirtyAmount == 0) {
      if (cleanAmount == 0) {
        clean.setWidth(70);
        dirty.setWidth(330);
        clean.setFill(Color.LIGHTGRAY);
      }
      if (dirtyAmount == 0) {
        clean.setWidth(330);
        dirty.setWidth(70);
        dirty.setFill(Color.LIGHTGRAY);
      }
    } else {
      dirty.setWidth((int) ((double) (dirtyAmount) / (double) (equipList.size()) * 400.0));
      clean.setWidth((int) ((double) (cleanAmount) / (double) (equipList.size()) * 400.0));
    }
  }

  private ObservableList<EquipmentUnit> sortEquipmentByFloor(String floor) {
    ObservableList<EquipmentUnit> equipmentOnFloor = FXCollections.observableArrayList();
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllEquipmentUnits();
    for (EquipmentUnit e : equipmentUnitList) {
      if (e.getCurrLocation().getFloor().equals(floor)) {
        equipmentOnFloor.add(e);
      }
    }
    return equipmentOnFloor;
  }
}
