package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GiftDeliveryController implements Initializable {
  @FXML private Button ReturnToMain;
  @FXML private TextField giftNoteField;
  @FXML
  private ChoiceBox<String> selectGiftDestination, selectAssignStaff, selectStatus, selectGiftType;
  @FXML private Button submitButton;
  @FXML private TableView<GiftDeliveryRequest> tbView;

  private LocationDAO locationDAO;
  private List<Location> locations;
  private TableColumn<GiftDeliveryRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<GiftDeliveryRequest, String> assigneeColumn = new TableColumn("Assignee");
  private TableColumn<GiftDeliveryRequest, String> locationColumn = new TableColumn("Location");
  private TableColumn<GiftDeliveryRequest, String> statusColumn = new TableColumn("Request Status");
  private TableColumn<GiftDeliveryRequest, String> giftTypeColumn = new TableColumn("gift Type");
  private TableColumn<GiftDeliveryRequest, String> giftNoteColumn = new TableColumn("Notes");

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
    resetFields();

    selectStatus.getItems().addAll("", "PROC", "DONE");
    selectAssignStaff.getItems().addAll("Staff1", "Staff2", "Staff3", "Staff4");
    selectGiftDestination.getItems().addAll("Room1", "Room2", "Room3");
    selectGiftType.getItems().addAll("Toy", "Flower", "Chocolate");
    selectGiftDestination.setItems(getLocationNames());

    tbView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbView
        .getColumns()
        .addAll(
            idColumn, assigneeColumn, locationColumn, statusColumn, giftTypeColumn, giftNoteColumn);
    idColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    giftTypeColumn.setCellValueFactory(new PropertyValueFactory<>("giftType"));
    giftNoteColumn.setCellValueFactory(new PropertyValueFactory<>("Notes"));
  }

  /**
   * Creates a list of all locations with their short names.
   *
   * @return List of short names of all destinations
   */
  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void resetFields() {
    selectGiftType.setValue("");
    giftNoteField.setText("");
    selectGiftDestination.setValue("");
    selectAssignStaff.setValue("");
    selectStatus.setValue("");
  }

  @FXML
  public void submitButton() {
    GiftDeliveryRequest request = new GiftDeliveryRequest();
    request.setRequestID(request.makeRequestID());
    request.setDestination(
        locations.get(selectGiftDestination.getSelectionModel().getSelectedIndex()));
    request.setAssignee(selectAssignStaff.getValue());
    request.setStatus(selectStatus.getValue());
    request.setGiftType(selectGiftType.getValue());
    request.setNotes(giftNoteField.getText());

    this.resetFields();
    tbView.getItems().add(request);
  }
}
