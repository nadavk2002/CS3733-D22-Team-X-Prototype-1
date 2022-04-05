package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReqInTransportController implements Initializable {
  @FXML private Label l1, l2, l3, l4, l5, l6;
  @FXML private Button ToMainMenu, submitButton, resetFields;
  @FXML private ChoiceBox<String> transportFrom, transportTo, addAccommodation, serviceStatus;
  @FXML private TextField patientName, assignStaff;
  @FXML private TableView<InTransportServiceRequest> table;
  @FXML private VBox inputVBox;
  @FXML private VBox labelVBox;
  @FXML private HBox upperHBox;
  @FXML private VBox leftSideVBox;
  @FXML private HBox pageHBox;

  private LocationDAO locationDAO;
  private List<Location> locations;
  private TableColumn<InTransportServiceRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<InTransportServiceRequest, String> assigneeColumn =
      new TableColumn("Assignee");
  private TableColumn<InTransportServiceRequest, String> transportFromColumn =
      new TableColumn("Transport From");
  private TableColumn<InTransportServiceRequest, String> transportToColumn =
      new TableColumn("Transport To");
  private TableColumn<InTransportServiceRequest, String> statusColumn =
      new TableColumn("Request Status");
  private TableColumn<InTransportServiceRequest, String> patientNameColumn =
      new TableColumn("Patient Name");
  private TableColumn<InTransportServiceRequest, String> addAccommodationColumn =
      new TableColumn("Additional Accommodations");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
    resetFields();
    submitButton.setDisable(true);
    transportFrom.setItems(getLocationNames());
    transportTo.setItems(getLocationNames());
    addAccommodation
        .getItems()
        .addAll(
            new String[] {"Bed", "Recliner", "Intubator", "Extra Nurse", "Language Interpreter"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    transportFrom.setOnAction((ActionEvent event) -> enableSubmitButton());
    transportTo.setOnAction((ActionEvent event) -> enableSubmitButton());
    addAccommodation.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());
    patientName.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table
        .getColumns()
        .addAll(
            idColumn,
            assigneeColumn,
            transportFromColumn,
            transportToColumn,
            statusColumn,
            patientNameColumn,
            addAccommodationColumn);

    idColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    transportFromColumn.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
    transportToColumn.setCellValueFactory(new PropertyValueFactory<>("transportToShortName"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
    addAccommodationColumn.setCellValueFactory(new PropertyValueFactory<>("addAccommodation"));

    labelVBox.getChildren().addAll(l1, l2, l3, l4, l5, l6);
    inputVBox
        .getChildren()
        .addAll(
            patientName, transportFrom, transportTo, addAccommodation, assignStaff, serviceStatus);
    upperHBox.getChildren().addAll(labelVBox, inputVBox);
    leftSideVBox.getChildren().addAll(upperHBox, submitButton, resetFields);
    pageHBox.getChildren().addAll(leftSideVBox, table);
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

  /** Checks if the submit button can be enabled depending on the inputs in fields on the page. */
  public void enableSubmitButton() {
    submitButton.setDisable(
        transportTo.getValue().equals("")
            || transportFrom.getValue().equals("")
            || addAccommodation.getValue().equals("")
            || serviceStatus.getValue().equals("")
            || patientName.getText().equals("")
            || assignStaff.getText().equals(""));
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  public void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    assignStaff.setText("");
    transportFrom.setValue("");
    transportTo.setValue("");
    serviceStatus.setValue("");
    patientName.setText("");
    addAccommodation.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitRequest() {
    InTransportServiceRequest request = new InTransportServiceRequest();
    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(transportFrom.getSelectionModel().getSelectedIndex()));
    request.setTransportTo(locations.get(transportTo.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setPatientName(patientName.getText());
    request.setAddAccommodation(addAccommodation.getValue());
    request.setAssignee(assignStaff.getText());
    this.resetFields();
    table.getItems().add(request);
  }
}
