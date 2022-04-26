package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAO;
import eu.hansolo.fx.charts.Axis;
import eu.hansolo.fx.charts.ChartType;
import eu.hansolo.fx.charts.NestedBarChart;
import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.series.ChartItemSeries;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GraphicalMapEditorDashboardController implements Initializable {
  @FXML
  private StackPane l5StackD,
      l4StackD,
      l3StackD,
      l2StackD,
      l1StackD,
      ll1StackD,
      ll2StackD,
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
      ll2StackIU,
      l5Stack,
      l4Stack,
      l3Stack,
      l2Stack,
      l1Stack,
      ll1Stack,
      ll2Stack;
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
  @FXML private VBox towerBox;
  @FXML private HBox buttonBox;
  @FXML
  private VBox l5Vbox,
      l4Vbox,
      l3Vbox,
      l2Vbox,
      l1Vbox,
      ll1Vbox,
      ll2Vbox,
      l5Master,
      l4Master,
      l3Master,
      l2Master,
      l1Master,
      ll1Master,
      ll2Master,
      infoBox;
  @FXML private NestedBarChart nestedBarChart;

  @FXML private Axis yAxis;
  ChartItem chartItem = new ChartItem("Clean", 43, Color.RED, Instant.ofEpochSecond(1));
  ChartItem chartItem2 = new ChartItem("Clean", 433, Color.GREEN, Instant.ofEpochSecond(1));
  MedicalEquipmentServiceRequestDAO MESRDAO = MedicalEquipmentServiceRequestDAO.getDAO();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nestedBarChart.addSeries(fillChartData(sortEquipmentByFloor("5")));
    nestedBarChart.addSeries(fillChartData(sortEquipmentByFloor("4")));
    nestedBarChart.addSeries(fillChartData(sortEquipmentByFloor("3")));
    fillChartData(sortEquipmentByFloor("5")).getSumOfAllItems();
    yAxis.setMaxValue(fillChartData(sortEquipmentByFloor("5")).getSumOfAllItems());
    //    coxcombChart.addItem(chartItem);
    //    coxcombChart.addItem(chartItem2);
    alertBox.setVisible(false);
    infoBox.setSpacing(30);
    masterBox.setSpacing(5);
    towerBox.setSpacing(3);
    buttonBox.setSpacing(6);
    l5Vbox.setSpacing(3);
    l4Vbox.setSpacing(3);
    l3Vbox.setSpacing(3);
    l2Vbox.setSpacing(3);
    l1Vbox.setSpacing(3);
    ll1Vbox.setSpacing(3);
    ll2Vbox.setSpacing(3);
    l5Master.setSpacing(3);
    l4Master.setSpacing(3);
    l3Master.setSpacing(3);
    l2Master.setSpacing(3);
    l1Master.setSpacing(3);
    ll1Master.setSpacing(3);
    ll2Master.setSpacing(3);
    yAxis.setAutoFontSize(false);
    yAxis.setTickLabelFontSize(20);
    cleanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    cleanPodB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    cleanPodC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    unitIDC.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeC.setCellValueFactory(new PropertyValueFactory<>("typeName"));
    availabilityC.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocC.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

    unitIDCPodA.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodA.setCellValueFactory(new PropertyValueFactory<>("typeName"));
    availabilityCPodA.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLocCPodA.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

    unitIDCPodB.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    typeCPodB.setCellValueFactory(new PropertyValueFactory<>("typeName"));
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
    fillTable(
        sortByDirty(sortEquipmentByFloor("5")),
        addToPodB(sortEquipmentByFloor("5")),
        addToPodC(sortEquipmentByFloor("5")),
        l5StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("4")),
        addToPodB(sortEquipmentByFloor("4")),
        addToPodC(sortEquipmentByFloor("4")),
        l4StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("3")),
        addToPodB(sortEquipmentByFloor("3")),
        addToPodC(sortEquipmentByFloor("3")),
        l3StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("2")),
        addToPodB(sortEquipmentByFloor("2")),
        addToPodC(sortEquipmentByFloor("2")),
        l2StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("1")),
        addToPodB(sortEquipmentByFloor("1")),
        addToPodC(sortEquipmentByFloor("1")),
        l1StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("L1")),
        addToPodB(sortEquipmentByFloor("L1")),
        addToPodC(sortEquipmentByFloor("L1")),
        ll1StackD);
    fillTable(
        sortByDirty(sortEquipmentByFloor("L2")),
        addToPodB(sortEquipmentByFloor("L2")),
        addToPodC(sortEquipmentByFloor("L2")),
        ll2StackD);

    fillTable(
        sortByClean(sortEquipmentByFloor("5")),
        addToPodB(sortEquipmentByFloor("5")),
        addToPodC(sortEquipmentByFloor("5")),
        l5StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("4")),
        addToPodB(sortEquipmentByFloor("4")),
        addToPodC(sortEquipmentByFloor("4")),
        l4StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("3")),
        addToPodB(sortEquipmentByFloor("3")),
        addToPodC(sortEquipmentByFloor("3")),
        l3StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("2")),
        addToPodB(sortEquipmentByFloor("2")),
        addToPodC(sortEquipmentByFloor("2")),
        l2StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("1")),
        addToPodB(sortEquipmentByFloor("1")),
        addToPodC(sortEquipmentByFloor("1")),
        l1StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("L1")),
        addToPodB(sortEquipmentByFloor("L1")),
        addToPodC(sortEquipmentByFloor("L1")),
        ll1StackC);
    fillTable(
        sortByClean(sortEquipmentByFloor("L2")),
        addToPodB(sortEquipmentByFloor("L2")),
        addToPodC(sortEquipmentByFloor("L2")),
        ll2StackC);

    fillTable(
        sortByInUse(sortEquipmentByFloor("5")),
        addToPodB(sortEquipmentByFloor("5")),
        addToPodC(sortEquipmentByFloor("5")),
        l5StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("4")),
        addToPodB(sortEquipmentByFloor("4")),
        addToPodC(sortEquipmentByFloor("4")),
        l4StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("3")),
        addToPodB(sortEquipmentByFloor("3")),
        addToPodC(sortEquipmentByFloor("3")),
        l3StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("2")),
        addToPodB(sortEquipmentByFloor("2")),
        addToPodC(sortEquipmentByFloor("2")),
        l2StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("1")),
        addToPodB(sortEquipmentByFloor("1")),
        addToPodC(sortEquipmentByFloor("1")),
        l1StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("L1")),
        addToPodB(sortEquipmentByFloor("L1")),
        addToPodC(sortEquipmentByFloor("L1")),
        ll1StackIU);
    fillTable(
        sortByInUse(sortEquipmentByFloor("L2")),
        addToPodB(sortEquipmentByFloor("L2")),
        addToPodC(sortEquipmentByFloor("L2")),
        ll2StackIU);
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

  private ChartItemSeries<ChartItem> fillChartData(ObservableList<EquipmentUnit> floorEquip) {
    ChartItemSeries<ChartItem> nestedBarList =
        new ChartItemSeries<>(
            ChartType.NESTED_BAR, "Beds", Paint.valueOf("#446fdc"), Paint.valueOf("#446fdc"));
    List<ChartItem> itemList = new ArrayList<>();
    int dirtyAmount = 0;
    int cleanAmount = 0;
    int inUseAmount = 0;
    for (EquipmentUnit equipmentUnit : floorEquip) {
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
    ChartItem clean =
        new ChartItem(
            "Clean", cleanAmount, Color.valueOf("#78ff78"), Instant.ofEpochSecond(1), true, 5000);
    ChartItem dirty =
        new ChartItem(
            "Dirty", dirtyAmount, Color.valueOf("#ff7474"), Instant.ofEpochSecond(1), true, 5000);
    ChartItem inuse =
        new ChartItem(
            "In Use", inUseAmount, Color.valueOf("#feff75"), Instant.ofEpochSecond(1), true, 5000);
    itemList.add(clean);
    itemList.add(dirty);
    itemList.add(inuse);

    nestedBarList.setItems(itemList);

    return nestedBarList;
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

  private void fillChart() {}

  private void fillTable(
      ObservableList<EquipmentUnit> equipment,
      ObservableList<EquipmentUnit> podB,
      ObservableList<EquipmentUnit> podC,
      StackPane floor) {

    floor.setOnMouseClicked(
        event -> {
          if (equipment.size() > 0) {
            cleanTable.setItems(equipment);
            cleanPodB.setItems(podB);
            cleanPodC.setItems(podC);
          }
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
    dirty.setWidth((int) ((double) (dirtyAmount) / (double) (equipList.size()) * 600.0));
    clean.setWidth((int) ((double) (cleanAmount) / (double) (equipList.size()) * 600.0));
    inUse.setWidth((int) ((double) (inUseAmount) / (double) (equipList.size()) * 600.0));
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

  private ObservableList<EquipmentUnit> addToPodC(ObservableList<EquipmentUnit> equipOnFloor) {
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

  private ObservableList<EquipmentUnit> addToPodB(ObservableList<EquipmentUnit> equipOnFloor) {
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

  public void ToEquipmentTable(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass()
                .getResource(
                    "/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorEquipmentTableOverlay.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("Equipment Table");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  public void ToLocationTable(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass()
                .getResource(
                    "/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorLocationTableOverlay.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("Location Table");
    stage.setScene(new Scene(root1));
    stage.show();
  }
}
