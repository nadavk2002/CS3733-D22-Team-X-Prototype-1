package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import java.io.IOException;
import java.net.URL;
import java.util.*;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
      ll2StackC,
      l5StackIU,
      l4StackIU,
      l3StackIU,
      l2StackIU,
      l1StackIU,
      ll1StackIU,
      ll2StackIU;
  @FXML public AnchorPane anchorRoot;
  @FXML public StackPane parentPage;
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
  @FXML private Rectangle levFiveIU;
  @FXML private Rectangle levFourIU;
  @FXML private Rectangle levThreeIU;
  @FXML private Rectangle levTwoIU;
  @FXML private Rectangle levOneIU;
  @FXML private Rectangle llOneIU;
  @FXML private Rectangle llTwoIU;
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
  @FXML private Text levFiveIUText;
  @FXML private Text levFourIUText;
  @FXML private Text levThreeIUText;
  @FXML private Text levTwoIUText;
  @FXML private Text levOneIUText;
  @FXML private Text llOneIUText;
  @FXML private Text llTwoIUText;
  @FXML private VBox masterBox;
  private EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  //  @FXML private HBox dirtyTableBox;
  @FXML private HBox cleanTableBox;

  // @FXML private TableView<EquipmentUnit> dirtyTable;
  @FXML private TableView<EquipmentUnit> cleanTable;
  // @FXML private TableView<EquipmentUnit> dirtyPodB;
  // @FXML private TableView<EquipmentUnit> dirtyPodC;
  @FXML private TableView<EquipmentUnit> cleanPodB;
  @FXML private TableView<EquipmentUnit> cleanPodC;
  // @FXML private TableColumn<EquipmentUnit, String> unitID;
  // @FXML private TableColumn<EquipmentUnit, String> type;
  // @FXML private TableColumn<EquipmentUnit, String> availability;
  //  @FXML private TableColumn<EquipmentUnit, String> unitIDC;
  @FXML private TableColumn<EquipmentUnit, String> typeC;
  @FXML private TableColumn<EquipmentUnit, String> availabilityC;
  @FXML private TableColumn<EquipmentUnit, String> currLocC;
  // @FXML private TableColumn<EquipmentUnit, String> unitIDPodA;
  // @FXML private TableColumn<EquipmentUnit, String> typePodA;
  // @FXML private TableColumn<EquipmentUnit, String> availabilityPodA;
  // FXML private TableColumn<EquipmentUnit, String> currLocPodA;
  @FXML private TableColumn<EquipmentUnit, String> unitIDCPodA;
  @FXML private TableColumn<EquipmentUnit, String> typeCPodA;
  @FXML private TableColumn<EquipmentUnit, String> availabilityCPodA;
  @FXML private TableColumn<EquipmentUnit, String> currLocCPodA;
  // @FXML private TableColumn<EquipmentUnit, String> unitIDPodB;
  // @FXML private TableColumn<EquipmentUnit, String> typePodB;
  // @FXML private TableColumn<EquipmentUnit, String> availabilityPodB;
  // @FXML private TableColumn<EquipmentUnit, String> currLocPodB;
  @FXML private TableColumn<EquipmentUnit, String> unitIDCPodB;
  @FXML private TableColumn<EquipmentUnit, String> typeCPodB;
  @FXML private TableColumn<EquipmentUnit, String> availabilityCPodB;
  @FXML private TableColumn<EquipmentUnit, String> currLocCPodB;
  @FXML private JFXButton showAlerts;

  // floor constants--------------------------------------
  // private final int dirtyXloc = 40;
  private final int cleanXloc = 705;
  private final int YF5 = 188 - 5;
  private final int YF4 = 290 - 6;
  private final int YF3 = 392 - 8;
  private final int YF2 = 494 - 12;
  private final int YF1 = 596 - 15;
  private final int YLL2 = 800 - 18;
  private final int YLL1 = 698 - 19;
  //  private ObservableList<String> hallsOutsideOfPatientRooms;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //    hallsOutsideOfPatientRooms.addAll(
    //        "HHALL01203",
    //        "HHALL01103",
    //        "HHALL00903",
    //        "HHALL01003",
    //        "HHALL01303",
    //        "HHALL01403",
    //        "HHALL01204",
    //        "HHALL01104",
    //        "HHALL00904",
    //        "HHALL01304",
    //        "HHALL01404",
    //        "HHALL01205",
    //        "HHALL01105",
    //        "HHALL00905",
    //        "HHALL01305",
    //        "HHALL01405");
    masterBox.setSpacing(30);
    // dirtyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    // dirtyPodB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    // dirtyPodC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanPodB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanPodC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    //
    // dirtyTableBox.setVisible(false);
    cleanTableBox.setVisible(false);
    // unitID.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    //  type.setCellValueFactory(new PropertyValueFactory<>("type"));
    //    availability.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    //    currLoc.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    //    unitIDC.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeC.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityC.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocC.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    //    unitIDPodA.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    //    typePodA.setCellValueFactory(new PropertyValueFactory<>("type"));
    //    availabilityPodA.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    //    currLocPodA.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    unitIDCPodA.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodA.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityCPodA.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocCPodA.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    //    unitIDPodB.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    //    typePodB.setCellValueFactory(new PropertyValueFactory<>("type"));
    //    availabilityPodB.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    //    currLocPodB.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    unitIDCPodB.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodB.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityCPodB.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocCPodB.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
        dynamicSizeRectangles(levFiveDirty, levFiveClean,levFiveIU, sortEquipmentByFloor("5"));
        dirtyCleanNumber(levFiveDirtyText, levFiveCleanText, sortEquipmentByFloor("5"));

        dynamicSizeRectangles(levFourDirty, levFourClean,levFiveIU, sortEquipmentByFloor("4"));
        dirtyCleanNumber(levFourDirtyText, levFourCleanText, sortEquipmentByFloor("4"));

        dynamicSizeRectangles(levThreeDirty, levThreeClean, levFiveIU,sortEquipmentByFloor("3"));
        dirtyCleanNumber(levThreeDirtyText, levThreeCleanText,sortEquipmentByFloor("3"));

        dynamicSizeRectangles(levTwoDirty, levTwoClean, levFiveIU,sortEquipmentByFloor("2"));
        dirtyCleanNumber(levTwoDirtyText, levTwoCleanText,sortEquipmentByFloor("2"));

        dynamicSizeRectangles(levOneDirty, levOneClean, levFiveIU,sortEquipmentByFloor("1"));
        dirtyCleanNumber(levOneDirtyText, levOneCleanText,sortEquipmentByFloor("1"));

        dynamicSizeRectangles(llOneDirty, llOneClean, levFiveIU,sortEquipmentByFloor("L1"));
        dirtyCleanNumber(llOneDirtyText, llOneCleanText,sortEquipmentByFloor("L1"));

        dynamicSizeRectangles(llTwoDirty, llTwoClean, levFiveIU,sortEquipmentByFloor("L2"));
        dirtyCleanNumber(llTwoDirtyText, llTwoCleanText, sortEquipmentByFloor("L2"));
    fillTable(sortByDirty(sortEquipmentByFloor("5")), cleanXloc, YF5, l5Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("4")), cleanXloc, YF4, l4Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("3")), cleanXloc, YF3, l3Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("2")), cleanXloc, YF2, l2Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("1")), cleanXloc, YF1, l1Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1Stack);
    fillTable(sortByDirty(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2Stack);

    fillTable(sortByClean(sortEquipmentByFloor("5")), cleanXloc, YF5, l5StackC);
    fillTable(sortByClean(sortEquipmentByFloor("4")), cleanXloc, YF4, l4StackC);
    fillTable(sortByClean(sortEquipmentByFloor("3")), cleanXloc, YF3, l3StackC);
    fillTable(sortByClean(sortEquipmentByFloor("2")), cleanXloc, YF2, l2StackC);
    fillTable(sortByClean(sortEquipmentByFloor("1")), cleanXloc, YF1, l1StackC);
    fillTable(sortByClean(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1StackC);
    fillTable(sortByClean(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2StackC);
    // dirtyPodA.getItems().addAll(addToPod1(sortEquipmentByFloor("3")));
    // dirtyPodB.getItems().addAll(addToPod1(sortEquipmentByFloor("4")));
    // cleanPodA.getItems().addAll(addToPod1(sortEquipmentByFloor("5")));
    // cleanPodB.getItems().addAll(addToPod1(sortEquipmentByFloor("5")));
    //    for (EquipmentUnit e : addToPod1(sortEquipmentByFloor("3"))) {
    //      System.out.println(e.getCurrLocation().getLongName());
    //    }
    //    System.out.println(addToPod1(sortEquipmentByFloor("3")).size());
  }

  @FXML
  private void ShowAlerts() throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/DashboardAlerts.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("Alerts");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  @FXML
  private void ToMapEditor() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    Scene scene = toMapEditor.getScene();
    root.translateXProperty().set(scene.getWidth());
    parentPage.getChildren().add(root);
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
    KeyFrame kf = new KeyFrame(Duration.seconds(.5), kv);

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
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("stor")
              && equipmentUnit.getIsAvailableChar() == 'Y')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'Y')) {
        cleanAmount++;
      } else if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")
              && equipmentUnit.getIsAvailableChar() == 'N')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'N')) {
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

  //  private void fillDirtyTable(
  //      ObservableList<EquipmentUnit> dirtyEquip, int XfloorVal, int YfloorVal, StackPane floor) {
  //    floor.setOnMouseEntered(
  //        event -> {
  //          if (dirtyEquip.size() > 0) {
  //            dirtyTableBox.setVisible(true);
  //            // dirtyTable.setItems(dirtyEquip);
  //            dirtyTableBox.setLayoutX(XfloorVal);
  //            dirtyTableBox.setLayoutY(YfloorVal);
  //          } else {
  //            dirtyTableBox.setVisible(false);
  //          }
  //          dirtyTableBox.setOnMouseEntered(
  //              event2 -> {
  //                if (dirtyEquip.size() > 0) {
  //                  dirtyTableBox.setVisible(true);
  //                  // dirtyTable.setItems(dirtyEquip);
  //                  dirtyTableBox.setLayoutX(XfloorVal);
  //                  dirtyTableBox.setLayoutY(YfloorVal);
  //                } else {
  //                  dirtyTableBox.setVisible(false);
  //                }
  //              });
  //        });
  //    floor.setOnMouseExited(
  //        event -> {
  //          dirtyTableBox.setVisible(false);
  //          dirtyTableBox.setOnMouseExited(
  //              event2 -> {
  //                dirtyTableBox.setVisible(false);
  //              });
  //        });
  //    // dirtyPodB.getItems().addAll(addToPod1(dirtyEquip));
  //  }

  private void fillTable(
      ObservableList<EquipmentUnit> equipment, int XfloorVal, int YfloorVal, StackPane floor) {
    floor.setOnMouseEntered(
        event -> {
          if (equipment.size() > 0) {
            cleanTableBox.setVisible(true);
            cleanTable.setItems(equipment);
            cleanTableBox.setLayoutX(XfloorVal);
            cleanTableBox.setLayoutY(YfloorVal);
          } else {
            cleanTableBox.setVisible(false);
          }
          cleanTableBox.setOnMouseEntered(
              event2 -> {
                if (equipment.size() > 0) {
                  cleanTableBox.setVisible(true);
                  cleanTable.setItems(equipment);
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
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")
              && equipmentUnit.getIsAvailableChar() == 'N')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'N')) {
        dirtyEquipment.add(equipmentUnit);
      }
    }
    return dirtyEquipment;
  }

  @FXML
  private ObservableList<EquipmentUnit> sortByClean(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> cleanEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("stor")
              && equipmentUnit.getIsAvailableChar() == 'Y')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'Y')) {
        cleanEquipment.add(equipmentUnit);
      }
    }
    return cleanEquipment;
  }

  @FXML
  private void dynamicSizeRectangles(
      Rectangle dirty, Rectangle clean, Rectangle inUse, ObservableList<EquipmentUnit> equipList) {
    int dirtyAmount = 0;
    int cleanAmount = 0;
    int inUseAmount = 0;
    for (EquipmentUnit equipmentUnit : equipList) {
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("stor")
              && equipmentUnit.getIsAvailableChar() == 'Y')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'Y')) {
        cleanAmount++;
      } else if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")
              && equipmentUnit.getIsAvailableChar() == 'N')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'N')) {
        dirtyAmount++;
      } else {
        inUseAmount++;
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
      if(inUseAmount == 0){
        clean.setWidth(330);
        inUse.setWidth(70);
        inUse.setFill(Color.LIGHTGRAY);
      }
    } else {
      dirty.setWidth(
          (int) ((double) (dirtyAmount) / (double) (equipList.size() - inUseAmount) * 600.0));
      clean.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - inUseAmount) * 600.0));
      inUse.setWidth((int)((double) (inUseAmount) / (double) (equipList.size() - inUseAmount) * 600.0));
      if (dirty.getWidth() < 70) {
        clean.setWidth(330);
        dirty.setWidth(70);
      }
      if (clean.getWidth() < 70) {
        clean.setWidth(70);
        dirty.setWidth(330);
      }
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

  private ObservableList<EquipmentUnit> addToPod2(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> allEquipInPod1 = FXCollections.observableArrayList();
    ObservableList<EquipmentUnit> allLocAllPods = FXCollections.observableArrayList();
    for (EquipmentUnit allPods : equipOnFloor) {
      if (allPods.getCurrLocation().getNodeType().toLowerCase().contains("pati")) {
        allLocAllPods.add(allPods);
      }
    }
    for (EquipmentUnit indvPod : allLocAllPods) {

      String[] patientRoom = indvPod.getCurrLocation().getLongName().split("");
      List<String> seperated = Arrays.asList(patientRoom);
      if (seperated.get(13).equals("5")
          || seperated.get(13).equals("6")
          || seperated.get(13).equals("7")
          || seperated.get(13).equals("8")) {
        allEquipInPod1.add(indvPod);
      }
    }
    return allEquipInPod1;
  }

  private ObservableList<EquipmentUnit> addToPod1(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> allEquipInPod1 = FXCollections.observableArrayList();
    ObservableList<EquipmentUnit> allLocAllPods = FXCollections.observableArrayList();
    for (EquipmentUnit allPods : equipOnFloor) {
      if (allPods.getCurrLocation().getShortName().toLowerCase().contains("pati")) {
        allLocAllPods.add(allPods);
      }
    }
    for (EquipmentUnit indvPod : allLocAllPods) {

      String[] patientRoom = indvPod.getCurrLocation().getLongName().split("");
      List<String> seperated = Arrays.asList(patientRoom);
      if (seperated.get(13).equals("1")
          || seperated.get(13).equals("2")
          || seperated.get(13).equals("3")
          || seperated.get(13).equals("4")) {
        allEquipInPod1.add(indvPod);
      }
    }
    return allEquipInPod1;
  }
}
