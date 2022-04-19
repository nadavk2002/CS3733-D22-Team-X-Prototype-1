package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeViewerController implements Initializable {
  // DAOS
  private final EmployeeDAO employeeDAO = EmployeeDAO.getDAO();
  public JFXButton addNew, update, resetFields;
  public Label errorText;
  private ServiceRequestDAO serviceRequestDAO = new ServiceRequestDAO();
  // service request DAO

  // lists
  // private ObservableList<String> employeeIDs;

  // FXML objects
  @FXML private ChoiceBox<String> EmployeeID;
  @FXML private TextField firstName, lastName, clearanceType, jobTitle;
  @FXML private TableView<ServiceRequest> table;
  @FXML private TableColumn<ServiceRequest, String> tableRequestID, tableDestination, tableStatus;

  // flag to make sure that the page does not update itself during certain operations
  private boolean allowUpdateFields = true;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // make Observable employeeList
    // clear fields
    resetFields();
    // populate choice box
    EmployeeID.setItems(getEmployeeIDs());
    // setup on action
    EmployeeID.setOnAction((ActionEvent event) -> updateFields());
    //    firstName.setOnAction((ActionEvent event) -> enableAddUpdate());
    //    lastName.setOnAction((ActionEvent event) -> enableAddUpdate());
    //    clearanceType.setOnAction((ActionEvent event) -> enableAddUpdate());
    //    jobTitle.setOnAction((ActionEvent event) -> enableAddUpdate());
  }

  @FXML
  private void enableAddUpdate() {
    boolean isDisabled =
        firstName.getText().equals("")
            || lastName.getText().equals("")
            || clearanceType.getText().equals("")
            || jobTitle.getText().equals("");
    try {
      isDisabled =
          firstName.getText().equals(employeeDAO.getRecord(EmployeeID.getValue()).getFirstName())
                  && lastName
                      .getText()
                      .equals(employeeDAO.getRecord(EmployeeID.getValue()).getLastName())
                  && clearanceType
                      .getText()
                      .equals(employeeDAO.getRecord(EmployeeID.getValue()).getClearanceType())
                  && jobTitle
                      .getText()
                      .equals(employeeDAO.getRecord(EmployeeID.getValue()).getJobTitle())
              || firstName.getText().equals("")
              || lastName.getText().equals("")
              || clearanceType.getText().equals("")
              || jobTitle.getText().equals("");
    } catch (NoSuchElementException e) {
    }

    addNew.setDisable(isDisabled);
    update.setDisable(isDisabled);
  }

  private ObservableList<String> getEmployeeIDs() {
    ObservableList<String> returnList = FXCollections.observableArrayList();
    for (Employee employee : employeeDAO.getAllRecords()) {
      returnList.add(employee.getEmployeeID());
    }
    return returnList;
  }

  private void updateFields() {
    if (allowUpdateFields) {
      // get Employeee
      // System.out.println(EmployeeID.getValue());
      Employee employee = new Employee();
      employee = employeeDAO.getRecord(EmployeeID.getValue());
      // populate textfields
      firstName.setText(employee.getFirstName());
      lastName.setText(employee.getLastName());
      clearanceType.setText(employee.getClearanceType());
      jobTitle.setText(employee.getJobTitle());

      // populate table of service requests
      tableRequestID.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, String>("RequestID"));
      tableDestination.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, String>("LocationShortName"));
      tableStatus.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Status"));
      table.setItems(FXCollections.observableList(SRDAOToOL()));

      // reset the buttons
      enableAddUpdate();
    }
  }

  private List<ServiceRequest> SRDAOToOL() {
    // load all requests into a list
    ArrayList<ServiceRequest> requests = serviceRequestDAO.getServiceRequests();
    // filter out requests with other users
    ArrayList<ServiceRequest> returnRequests = new ArrayList<ServiceRequest>();
    for (ServiceRequest SR : requests) {
      if (SR.getAssignee().getEmployeeID().equals(EmployeeID.getValue())
          && !SR.getStatus().equals("DONE")) {
        returnRequests.add(SR);
      }
    }

    return returnRequests;
  }

  @FXML
  private void resetFields() {
    // disable choicebox
    allowUpdateFields = false;
    // clear table
    tableRequestID.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("RequestID"));
    tableDestination.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("LocationShortName"));
    tableStatus.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Status"));
    table.setItems(FXCollections.observableList(new ArrayList<ServiceRequest>()));

    // clear textFields
    firstName.setText("");
    lastName.setText("");
    clearanceType.setText("");
    jobTitle.setText("");

    // clearChoiceBox
    EmployeeID.setValue("");

    // disable add and update buttons
    addNew.setDisable(true);
    update.setDisable(true);

    // enable choicebox updates
    allowUpdateFields = true;
  }

  public void addEmployee(ActionEvent actionEvent) {}

  public void updateEmployee(ActionEvent actionEvent) {}
}
