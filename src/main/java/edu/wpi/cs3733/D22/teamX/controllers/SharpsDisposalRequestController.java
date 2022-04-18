package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.Employee;
import edu.wpi.cs3733.D22.teamX.entity.EmployeeDAO;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SharpsDisposalRequestController implements Initializable {
    //get all the DAOs
    private LocationDAO locationDAO = LocationDAO.getDAO(); //location DAO
    private EmployeeDAO employeeDAO = EmployeeDAO.getDAO(); //employee DAO
    //put Sharps Disposal Request DAO here

    //lists for drop downs
    private List<Location> locations;
    private List<Employee> employees;

    //FXML things
    @FXML private ChoiceBox<String> roomDropDown, statusChoiceBox, assigneeDropDown, typeDropDown;
    @FXML private JFXButton submitButton, resetButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //populate lists from DAOS
    }
}
