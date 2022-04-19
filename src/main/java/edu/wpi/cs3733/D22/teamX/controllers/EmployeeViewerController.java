package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeViewerController implements Initializable {
  // DAOS
  private final EmployeeDAO employeeDAO = EmployeeDAO.getDAO();
  private ServiceRequestDAO serviceRequestDAO = new ServiceRequestDAO();
  // service request DAO

  // lists
  // private ObservableList<String> employeeIDs;

  // FXML objects
  @FXML private ChoiceBox<String> EmployeeID;
  @FXML private TextField firstName, lastName, clearanceType, jobTitle;
  @FXML private TableView<ServiceRequest> table;
  @FXML private TableColumn<ServiceRequest, String> tableRequestID, tableDestination, tableStatus;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // make Observable employeeList
    // clear fields
    resetFields();
    // populate choice box
    EmployeeID.setItems(getEmployeeIDs());
    // setup on action
    EmployeeID.setOnAction((ActionEvent event) -> updateFields());
  }

  private ObservableList<String> getEmployeeIDs() {
    ObservableList<String> returnList = FXCollections.observableArrayList();
    for (Employee employee : employeeDAO.getAllRecords()) {
      returnList.add(employee.getEmployeeID());
    }
    return returnList;
  }

  private void updateFields() {
    // get Employeee
    //System.out.println(EmployeeID.getValue());
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
  }

  private List<ServiceRequest> SRDAOToOL() {
    // load all requests into a list
    ArrayList<ServiceRequest> requests = serviceRequestDAO.getServiceRequests();
    // filter out requests with other users
    ArrayList<ServiceRequest> returnRequests = new ArrayList<ServiceRequest>();
    for (ServiceRequest SR : requests) {
      if (SR.getAssignee().getEmployeeID().equals(EmployeeID.getValue())) {
        returnRequests.add(SR);
      }
    }

    return returnRequests;
  }

  private void resetFields() {
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
  }
}
