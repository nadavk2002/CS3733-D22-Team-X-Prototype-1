package edu.wpi.cs3733.D22.teamX.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditLocationMenuController implements Initializable {

    @FXML
    private TextField buildingText;

    @FXML
    private TextField floorText;

    @FXML
    private TextField longNameText;

    @FXML
    private TextField nodeIdText;

    @FXML
    private TextField nodeTypeText;

    @FXML
    private TextField shortNameText;

    @FXML
    private TextField xCordText;

    @FXML
    private TextField yCordText;

    @FXML
    void updateLocation(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
