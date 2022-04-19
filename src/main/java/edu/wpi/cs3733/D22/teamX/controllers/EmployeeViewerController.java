package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.*;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeViewerController implements Initializable {
    //DAOS
    private final EmployeeDAO employeeDAO = EmployeeDAO.getDAO();
    //service request DAO

    //lists
    private ObservableList<String> employeeIDs;

    //FXML objects
    @FXML private ChoiceBox<String> EmployeeID;
    @FXML private TextField firstName, lastName, clearanceType, jobTitle;
    @FXML private TableView<ServiceRequest> table;
    @FXML private TableColumn<ServiceRequest, String> tableRequestID, tableDestination, tableStatus;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //make Observable employeeList
        for(Employee employee: employeeDAO.getAllRecords()){
            employeeIDs.add(employee.getEmployeeID());
        }
        //clear fields
        resetFields();
        //populate choice box
        EmployeeID.setItems(employeeIDs);
        //setup on action
        EmployeeID.setOnAction((ActionEvent event) -> updateFields());
    }

    private void updateFields() {
        //get Employeee
        Employee employee = employeeDAO.getRecord(EmployeeID.getValue());
        //reset fields
        resetFields();
        //populate textfields
        firstName.setText(employee.getFirstName());
        lastName.setText(employee.getLastName());
        clearanceType.setText(employee.getClearanceType());
        jobTitle.setText(employee.getJobTitle());

        //populate table of service requests
        tableRequestID.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("RequestID"));
        tableDestination.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("LocationShortName"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Status"));
        table.setItems(FXCollections.observableList(SRDAOToOL()));

    }

    private List<ServiceRequest> SRDAOToOL(){
        //replace with service request Facade
        ArrayList<ServiceRequest> requests = new ArrayList<ServiceRequest>();
        requests.addAll(MedicalEquipmentServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(LabServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(GiftDeliveryRequestDAO.getDAO().getAllRecords());
        requests.addAll(LangServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(MealServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(JanitorServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(LaundryServiceRequestDAO.getDAO().getAllRecords());
        requests.addAll(MedicineDeliverServiceRequestDAO.getDAO().getAllRecords());
        return requests;
    }

    private void resetFields() {
        //clear table
        tableRequestID.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("RequestID"));
        tableDestination.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("LocationShortName"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Status"));
        table.setItems(FXCollections.observableList(new ArrayList<ServiceRequest>()));

        //clear textFields
        firstName.setText("");
        lastName.setText("");
        clearanceType.setText("");
        jobTitle.setText("");

        //clearChoiceBox
        EmployeeID.setValue("");

    }
}
