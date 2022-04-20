package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAO;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
  @FXML private HBox l5Hbox, l4Hbox, l3Hbox, l2Hbox, l1Hbox, ll1Hbox, ll2Hbox;
  private EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  @FXML private HBox cleanTableBox;

  @FXML private TableView<EquipmentUnit> cleanTable;

  @FXML private TableView<EquipmentUnit> cleanPodB;
  @FXML private TableView<EquipmentUnit> cleanPodC;

  @FXML private TableColumn<EquipmentUnit, String> unitIDC;
  @FXML private TableColumn<EquipmentUnit, String> typeC;
  @FXML private TableColumn<EquipmentUnit, String> availabilityC;
  @FXML private TableColumn<EquipmentUnit, String> currLocC;

  @FXML private TableColumn<EquipmentUnit, String> unitIDCPodA;
  @FXML private TableColumn<EquipmentUnit, String> typeCPodA;
  @FXML private TableColumn<EquipmentUnit, String> availabilityCPodA;
  @FXML private TableColumn<EquipmentUnit, String> currLocCPodA;
  @FXML private TableColumn<EquipmentUnit, String> unitIDCPodB;
  @FXML private TableColumn<EquipmentUnit, String> typeCPodB;
  @FXML private TableColumn<EquipmentUnit, String> availabilityCPodB;
  @FXML private TableColumn<EquipmentUnit, String> currLocCPodB;
  @FXML private JFXButton showAlerts;
  @FXML private Label alertLabel;
  @FXML private VBox alertBox;
  MedicalEquipmentServiceRequestDAO MESRDAO = MedicalEquipmentServiceRequestDAO.getDAO();
  // floor constants--------------------------------------
  private final int cleanXloc = 705;
  private final int YF5 = 188 - 5;
  private final int YF4 = 290 - 6;
  private final int YF3 = 392 - 8;
  private final int YF2 = 494 - 12;
  private final int YF1 = 596 - 15;
  private final int YLL2 = 800 - 18;
  private final int YLL1 = 698 - 19;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    alertBox.setVisible(false);
    masterBox.setSpacing(30);

    cleanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    cleanPodB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanPodC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    cleanTableBox.setVisible(false);

    unitIDC.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeC.setCellValueFactory(new PropertyValueFactory<>("typeName"));
    availabilityC.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocC.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

    unitIDCPodA.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodA.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityCPodA.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocCPodA.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

    unitIDCPodB.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodB.setCellValueFactory(new PropertyValueFactory<>("type"));
    availabilityCPodB.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocCPodB.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    dynamicSizeRectangles(levFiveDirty, levFiveClean, levFiveIU, sortEquipmentByFloor("5"));
    rectangleNumber(levFiveDirtyText, levFiveCleanText, levFiveIUText, sortEquipmentByFloor("5"));

    dynamicSizeRectangles(levFourDirty, levFourClean, levFourIU, sortEquipmentByFloor("4"));
    rectangleNumber(levFourDirtyText, levFourCleanText, levFourIUText, sortEquipmentByFloor("4"));

    dynamicSizeRectangles(levThreeDirty, levThreeClean, levThreeIU, sortEquipmentByFloor("3"));
    rectangleNumber(
        levThreeDirtyText, levThreeCleanText, levThreeIUText, sortEquipmentByFloor("3"));

    dynamicSizeRectangles(levTwoDirty, levTwoClean, levTwoIU, sortEquipmentByFloor("2"));
    rectangleNumber(levTwoDirtyText, levTwoCleanText, levTwoIUText, sortEquipmentByFloor("2"));

    dynamicSizeRectangles(levOneDirty, levOneClean, levOneIU, sortEquipmentByFloor("1"));
    rectangleNumber(levOneDirtyText, levOneCleanText, levOneIUText, sortEquipmentByFloor("1"));

    dynamicSizeRectangles(llOneDirty, llOneClean, llOneIU, sortEquipmentByFloor("L1"));
    rectangleNumber(llOneDirtyText, llOneCleanText, llOneIUText, sortEquipmentByFloor("L1"));

    dynamicSizeRectangles(llTwoDirty, llTwoClean, llTwoIU, sortEquipmentByFloor("L2"));
    rectangleNumber(llTwoDirtyText, llTwoCleanText, llTwoIUText, sortEquipmentByFloor("L2"));
    fillTable(sortByDirty(sortEquipmentByFloor("5")), cleanXloc, YF5, l5Stack, l5Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("4")), cleanXloc, YF4, l4Stack, l4Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("3")), cleanXloc, YF3, l3Stack, l3Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("2")), cleanXloc, YF2, l2Stack, l2Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("1")), cleanXloc, YF1, l1Stack, l1Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1Stack, ll1Hbox);
    fillTable(sortByDirty(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2Stack, ll2Hbox);

    fillTable(sortByClean(sortEquipmentByFloor("5")), cleanXloc, YF5, l5StackC, l5Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("4")), cleanXloc, YF4, l4StackC, l4Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("3")), cleanXloc, YF3, l3StackC, l3Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("2")), cleanXloc, YF2, l2StackC, l2Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("1")), cleanXloc, YF1, l1StackC, l1Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1StackC, ll1Hbox);
    fillTable(sortByClean(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2StackC, ll2Hbox);

    fillTable(sortByInUse(sortEquipmentByFloor("5")), cleanXloc, YF5, l5StackIU, l5Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("4")), cleanXloc, YF4, l4StackIU, l4Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("3")), cleanXloc, YF3, l3StackIU, l3Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("2")), cleanXloc, YF2, l2StackIU, l2Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("1")), cleanXloc, YF1, l1StackIU, l1Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("L1")), cleanXloc, YLL1, ll1StackIU, ll1Hbox);
    fillTable(sortByInUse(sortEquipmentByFloor("L2")), cleanXloc, YLL2, ll2StackIU, ll2Hbox);
    // dirtyPodA.getItems().addAll(addToPod1(sortEquipmentByFloor("3")));
    // dirtyPodB.getItems().addAll(addToPod1(sortEquipmentByFloor("4")));
    // cleanPodA.getItems().addAll(addToPod1(sortEquipmentByFloor("5")));
    // cleanPodB.getItems().addAll(addToPod1(sortEquipmentByFloor("5")));
    //    for (EquipmentUnit e : addToPod1(sortEquipmentByFloor("3"))) {
    //      System.out.println(e.getCurrLocation().getLongName());
    //    }
    //    System.out.println(addToPod1(sortEquipmentByFloor("3")).size());
    displayAlert();
  }

  private void displayAlert() {
    ObservableList<MedicalEquipmentServiceRequest> alertList = MESRDAO.getAlerts();
    alertList.addListener(
        new ListChangeListener<MedicalEquipmentServiceRequest>() {
          @Override
          public void onChanged(
              javafx.collections.ListChangeListener.Change<? extends MedicalEquipmentServiceRequest>
                  c) {
            if (MESRDAO
                .getAllRecords()
                .get(MESRDAO.getAllRecords().size() - 1)
                .getEquipmentType()
                .equals("Bed")) {
              alertBox.setVisible(true);
              alertLabel.setText("Service Request for Beds have been sent to the OR Bed Park");
            }
            if (MESRDAO
                .getAllRecords()
                .get(MESRDAO.getAllRecords().size() - 1)
                .getEquipmentType()
                .equals("Infusion Pump")) {
              alertLabel.setVisible(true);
              alertLabel.setText(
                  "Service Request for Infusion Pumps have been sent to the West Plaza");
            }
          }
        });
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

  private void rectangleNumber(
      Text dirty, Text clean, Text inUse, ObservableList<EquipmentUnit> equipList) {
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
    dirty.setText(Integer.toString(dirtyAmount));
    clean.setText(Integer.toString(cleanAmount));
    inUse.setText(Integer.toString(inUseAmount));
  }

  private void fillTable(
      ObservableList<EquipmentUnit> equipment,
      int XfloorVal,
      int YfloorVal,
      StackPane floor,
      HBox floorBox) {
    floor.setOnMouseClicked(
        event -> {
          floor.toFront();
        });
    floor.setOnMouseEntered(
        event6 -> {
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
          floorBox.setOnMouseEntered(
              event3 -> {
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
  private ObservableList<EquipmentUnit> sortByInUse(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> inUseEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if (!((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("stor")
                  && equipmentUnit.getIsAvailableChar() == 'Y')
              || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
                  && equipmentUnit.getIsAvailableChar() == 'Y'))
          && !((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")
                  && equipmentUnit.getIsAvailableChar() == 'N')
              || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
                  && equipmentUnit.getIsAvailableChar() == 'N'))) {
        inUseEquipment.add(equipmentUnit);
      }
    }
    return inUseEquipment;
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
    int dirtyWidth = (int) ((double) (dirtyAmount) / (double) (equipList.size()) * 600.0);
    int cleanWidth = (int) ((double) (cleanAmount) / (double) (equipList.size()) * 600.0);
    int inUseWidth = (int) ((double) (inUseAmount) / (double) (equipList.size()) * 600.0);
    if (cleanAmount == 0 && dirtyAmount == 0 && inUseAmount == 0) {
      clean.setWidth(200);
      dirty.setWidth(200);
      inUse.setWidth(200);
      clean.setFill(Color.LIGHTGRAY);
      dirty.setFill(Color.LIGHTGRAY);
      inUse.setFill(Color.LIGHTGRAY);
    } else if (dirtyAmount == 0 && cleanAmount == 0) {
      clean.setWidth(70);
      dirty.setWidth(70);
      inUse.setWidth(460.0);
      clean.setFill(Color.LIGHTGRAY);
      dirty.setFill(Color.LIGHTGRAY);

    } else if (cleanAmount == 0 && inUseAmount == 0) {
      clean.setWidth(70);
      inUse.setWidth(70);
      dirty.setWidth(460.0);
      clean.setFill(Color.LIGHTGRAY);
      inUse.setFill(Color.LIGHTGRAY);

    } else if (dirtyAmount == 0 && inUseAmount == 0) {
      inUse.setWidth(70);
      dirty.setWidth(70);
      clean.setWidth(460);
      inUse.setFill(Color.LIGHTGRAY);
      dirty.setFill(Color.LIGHTGRAY);

    } else if (cleanAmount == 0) {
      clean.setWidth(70);
      dirty.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - cleanAmount) * 530.0));
      inUse.setWidth(
          (int) ((double) (inUseAmount) / (double) (equipList.size() - cleanAmount) * 530.0));
      clean.setFill(Color.LIGHTGRAY);
    } else if (dirtyAmount == 0) {
      dirty.setWidth(70);
      clean.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - dirtyAmount) * 530.0));
      inUse.setWidth(
          (int) ((double) (inUseAmount) / (double) (equipList.size() - dirtyAmount) * 530.0));
      dirty.setFill(Color.LIGHTGRAY);
    } else if (inUseAmount == 0) {
      inUse.setWidth(70);
      dirty.setWidth(
          (int) ((double) (dirtyAmount) / (double) (equipList.size() - inUseAmount) * 530.0));
      clean.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - inUseAmount) * 530.0));
      inUse.setFill(Color.LIGHTGRAY);
    } else if (dirtyWidth <= 70 && cleanWidth <= 70) {
      clean.setWidth(70);
      dirty.setWidth(70);
      inUse.setWidth(
          (int)
              ((double) (inUseAmount)
                  / (double) (equipList.size() - cleanAmount - dirtyAmount)
                  * 460.0));
    } else if (cleanWidth <= 70 && inUseWidth <= 70) {
      clean.setWidth(70);
      inUse.setWidth(70);
      dirty.setWidth((int) ((double) (inUseAmount) / (double) (equipList.size()) * 460.0));
    } else if (dirtyWidth <= 70 && inUseWidth <= 70) {
      inUse.setWidth(70);
      dirty.setWidth(70);
      clean.setWidth(
          (int)
              ((double) (inUseAmount)
                  / (double) (equipList.size() - inUseAmount - dirtyAmount)
                  * 460.0));
    } else if (dirtyWidth <= 70) {
      dirty.setWidth(70);
      clean.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - dirtyAmount) * 530.0));
      inUse.setWidth(
          (int) ((double) (inUseAmount) / (double) (equipList.size() - dirtyAmount) * 530.0));
    } else if (cleanWidth <= 70) {
      clean.setWidth(70);
      dirty.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - cleanAmount) * 530.0));
      inUse.setWidth(
          (int) ((double) (inUseAmount) / (double) (equipList.size() - cleanAmount) * 530.0));
    } else if (inUseWidth <= 70) {
      dirty.setWidth(
          (int) ((double) (dirtyAmount) / (double) (equipList.size() - inUseAmount) * 530.0));
      clean.setWidth(
          (int) ((double) (cleanAmount) / (double) (equipList.size() - inUseAmount) * 530.0));
      inUse.setWidth(70);
    } else {
      dirty.setWidth((int) ((double) (dirtyAmount) / (double) (equipList.size()) * 600.0));
      clean.setWidth((int) ((double) (cleanAmount) / (double) (equipList.size()) * 600.0));
      inUse.setWidth((int) ((double) (inUseAmount) / (double) (equipList.size()) * 600.0));
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
