package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GraphicalMapEditorDashboardController implements Initializable {
  @FXML
  private StackPane l5Stack,
      l4Stack,
      l3Stack,
      l2Stack,
      l1Stack,
      ll1Stack,
      ll2Stack,
      l5StackC,
      l4StackC,
      l3StackC,
      l2StackC,
      l1StackC,
      ll1StackC,
      ll2StackC;
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
  private EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  @FXML private HBox dirtyTableBox;
  @FXML private HBox cleanTableBox;

  @FXML private TableView<EquipmentUnit> dirtyTable;
  @FXML private TableView<EquipmentUnit> cleanTable;
  @FXML private TableColumn<EquipmentUnit, String> unitID;
  @FXML private TableColumn<EquipmentUnit, String> type;
  @FXML private TableColumn<EquipmentUnit, String> availability;
  @FXML private TableColumn<EquipmentUnit, String> currLoc;
  @FXML private TableColumn<EquipmentUnit, String> unitIDC;
  @FXML private TableColumn<EquipmentUnit, String> typeC;
  @FXML private TableColumn<EquipmentUnit, String> availabilityC;
  @FXML private TableColumn<EquipmentUnit, String> currLocC;

  // floor contants--------------------------------------
  private final int dirtyXloc = 70;
  private final int cleanXloc = 1005;
  private final int YF5 = 188;
  private final int YF4 = 290;
  private final int YF3 = 392;
  private final int YF2 = 494;
  private final int YF1 = 596;
  private final int YLL2 = 800;
  private final int YLL1 = 698;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    dirtyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    dirtyTableBox.setVisible(false);
    cleanTableBox.setVisible(false);
    unitID.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    availability.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLoc.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    unitIDC.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeC.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityC.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocC.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

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
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("5")), dirtyXloc, YF5, l5Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("4")), dirtyXloc, YF4, l4Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("3")), dirtyXloc, YF3, l3Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("2")), dirtyXloc, YF2, l2Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("1")), dirtyXloc, YF1, l1Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("L1")), dirtyXloc, YLL1, ll1Stack);
    fillDirtyTable(sortByDirty(sortEquipmentByFloor("L2")), dirtyXloc, YLL2, ll2Stack);

    fillCleanTable(sortByClean(sortEquipmentByFloor("5")), cleanXloc, YF5, l5StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("4")), cleanXloc, YF4, l4StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("3")), cleanXloc, YF3, l3StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("2")), cleanXloc, YF2, l2StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("1")), cleanXloc, YF1, l1StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1StackC);
    fillCleanTable(sortByClean(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2StackC);
  }

  @FXML
  private void ToMapEditor() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    Scene scene = toMapEditor.getScene();
    root.translateXProperty().set(scene.getHeight());
    parentPage.getChildren().add(root);
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
    KeyFrame kf = new KeyFrame(Duration.seconds(.75), kv);

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

  private void fillDirtyTable(
      ObservableList<EquipmentUnit> dirtyEquip, int XfloorVal, int YfloorVal, StackPane floor) {
    floor.setOnMouseEntered(
        event -> {
          if (dirtyEquip.size() > 0) {
            dirtyTableBox.setVisible(true);
            dirtyTable.setItems(dirtyEquip);
            dirtyTableBox.setLayoutX(XfloorVal);
            dirtyTableBox.setLayoutY(YfloorVal);
          } else {
            dirtyTableBox.setVisible(false);
          }
          dirtyTableBox.setOnMouseEntered(
              event2 -> {
                if (dirtyEquip.size() > 0) {
                  dirtyTableBox.setVisible(true);
                  dirtyTable.setItems(dirtyEquip);
                  dirtyTableBox.setLayoutX(XfloorVal);
                  dirtyTableBox.setLayoutY(YfloorVal);
                } else {
                  dirtyTableBox.setVisible(false);
                }
              });
        });
    floor.setOnMouseExited(
        event -> {
          dirtyTableBox.setVisible(false);
          dirtyTableBox.setOnMouseExited(
              event2 -> {
                dirtyTableBox.setVisible(false);
              });
        });
  }

  private void fillCleanTable(
      ObservableList<EquipmentUnit> cleanEquip, int XfloorVal, int YfloorVal, StackPane floor) {
    floor.setOnMouseEntered(
        event -> {
          if (cleanEquip.size() > 0) {
            cleanTableBox.setVisible(true);
            cleanTable.setItems(cleanEquip);
            cleanTableBox.setLayoutX(XfloorVal);
            cleanTableBox.setLayoutY(YfloorVal);
          } else {
            cleanTableBox.setVisible(false);
          }
          cleanTableBox.setOnMouseEntered(
              event2 -> {
                if (cleanEquip.size() > 0) {
                  cleanTableBox.setVisible(true);
                  cleanTable.setItems(cleanEquip);
                  cleanTableBox.setLayoutX(XfloorVal);
                  cleanTableBox.setLayoutY(YfloorVal);
                } else {
                  cleanTableBox.setVisible(false);
                }
              });
        });
    floor.setOnMouseExited(
        event -> {
          cleanTableBox.setVisible(false);
          cleanTableBox.setOnMouseExited(
              event2 -> {
                cleanTableBox.setVisible(false);
              });
        });
  }

  @FXML
  private ObservableList<EquipmentUnit> sortByDirty(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> dirtyEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if (!equipmentUnit.getCurrLocation().getShortName().toLowerCase().contains("clean")) {
        dirtyEquipment.add(equipmentUnit);
      }
    }
    return dirtyEquipment;
  }

  private ObservableList<EquipmentUnit> sortByClean(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> cleanEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if (equipmentUnit.getCurrLocation().getShortName().toLowerCase().contains("clean")) {
        cleanEquipment.add(equipmentUnit);
      }
    }
    return cleanEquipment;
  }

  @FXML
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
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllRecords();
    for (EquipmentUnit e : equipmentUnitList) {
      if (e.getCurrLocation().getFloor().equals(floor)) {
        equipmentOnFloor.add(e);
      }
    }
    return equipmentOnFloor;
  }
}
