package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipReqTableController implements Initializable {
  @FXML private TableView<Location> table;
  @FXML private TableColumn<Location, String> requester;
  @FXML private TableColumn<Location, String> equipType; // spike c, equipmentType
  @FXML private TableColumn<Location, String> amount; // spike c, Quantity
  @FXML private TableColumn<Location, String> equipStatus; // spike c, Status
  @FXML private TableColumn<Location, String> prevLocation;
  @FXML private TableColumn<Location, String> futureLocation; // spike c destination
  @FXML private TableColumn<Location, String> requestTime;
  @FXML private TableColumn<Location, String> fulfillRequest;
  @FXML private TableColumn<Location, String> requestID; // spike c RequestID
  @FXML private Button requestComplete;

  @FXML
  private TableColumn<Location, String> longName; // Put an entity in these columns that makes sense

  @FXML private TableColumn<Location, String> shortName;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    requester.setCellValueFactory(new PropertyValueFactory<Location, String>("Dr. Shah"));
    equipType.setCellValueFactory(new PropertyValueFactory<Location, String>("Bed"));
    amount.setCellValueFactory(new PropertyValueFactory<Location, String>("x3"));
    equipStatus.setCellValueFactory(new PropertyValueFactory<Location, String>("Fully Functional"));
    prevLocation.setCellValueFactory(new PropertyValueFactory<Location, String>("104B"));
    futureLocation.setCellValueFactory(new PropertyValueFactory<Location, String>("305F"));
    requestTime.setCellValueFactory(new PropertyValueFactory<Location, String>("1:30:22"));
    fulfillRequest.setCellValueFactory(
        new PropertyValueFactory<Location, String>("Complete Request"));
    requestID.setCellValueFactory(new PropertyValueFactory<Location, String>("104ABC"));
    table.getItems().setAll();
  }

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
