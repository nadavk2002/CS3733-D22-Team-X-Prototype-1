package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AboutPageController implements Initializable {
  @FXML JFXButton theo, jordan, markou, moy, spencer, sharon, zhuofan, darren, dylan, nadav;
  @FXML Label description;
  @FXML GridPane pictureGridpane;
  @FXML VBox pictureVBox;

  private int row;
  private int column;

  public void defaultMoveFrom(JFXButton button, int column, int row) {
    description.setVisible(false);
    description.setText("");
    pictureVBox.getChildren().remove(button);
    pictureGridpane.add(button, column, row);
    pictureGridpane.setVisible(true);
  }

  public void defaultMoveTo(JFXButton button) {
    pictureGridpane.setVisible(false);
    description.setVisible(true);
    pictureGridpane.getChildren().remove(button);
    pictureVBox.getChildren().add(0, button);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    description.setVisible(false);
    description.setText("");
  }

  @FXML
  private void showTheoText() {
    if (description.isVisible()) {
      column = 0;
      row = 0;
      defaultMoveFrom(theo, column, row);
    } else {
      defaultMoveTo(theo);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Lead Software Developer\n"
              + "\n"
              + "Contributions\n"
              + "Handled merge conflicts and code organization, service request APIs, embedded/client database options, csv file loading/saving, and music and sounds\n"
              + "\n");
    }
  }

  @FXML
  private void showJordanText() {
    if (description.isVisible()) {
      column = 1;
      row = 0;
      defaultMoveFrom(jordan, column, row);
    } else {
      defaultMoveTo(jordan);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Product Owner\n"
              + "\n"
              + "Contributions\n"
              + "Organized sprint planning meetings, filling/sizing/assigning Jira tasks, collaborating with all front-end developers to achieve a consistent and intuitive UI using stylesheets, implemented a preferences page, and a delighter feature");
    }
  }

  @FXML
  private void showMarkouText() {
    if (description.isVisible()) {
      column = 2;
      row = 0;
      defaultMoveFrom(markou, column, row);
    } else {
      defaultMoveTo(markou);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Assistant Lead Front-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "Graphical Map Editor, Language Interpreter Service Request, Service Request Table, Barcode Scanner Feature, Burndown Service Request Chart\n"
              + "\n");
    }
  }

  @FXML
  private void showSpencerText() {
    if (description.isVisible()) {
      column = 4;
      row = 0;
      defaultMoveFrom(spencer, column, row);
    } else {
      defaultMoveTo(spencer);
      description.setText(
          "Robotics Engineering Major, Astrophysics Minor\n"
              + "Class of 2023\n"
              + "Full Time Back-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "InTransport Service Request, Employee Database, DAO Facade\n"
              + "\n"
              + "\n"
              + "\n");
    }
  }

  @FXML
  private void showMoyText() {
    if (description.isVisible()) {
      column = 3;
      row = 0;
      defaultMoveFrom(moy, column, row);
    } else {
      defaultMoveTo(moy);
      description.setText(
          "Robotics Engineering Major\n"
              + "Class of 2024\n"
              + "Scrum Master, Part Time Back-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "Wrote Several Back-End Data Access objects, and created the Employee Viewer, Sharps Disposal Service Request, and Lighting Control System\n"
              + "\n");
    }
  }

  @FXML
  private void showSharonText() {
    if (description.isVisible()) {
      column = 0;
      row = 1;
      defaultMoveFrom(sharon, column, row);
    } else {
      defaultMoveTo(sharon);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Full Time Front-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "About Us Page, Edit Service Request Pages, Medicine Delivery Request, Janitorial Service Request, Contributed to the UI of the application\n"
              + "\n"
              + "\n");
    }
  }

  @FXML
  private void showZhuofanText() {
    if (description.isVisible()) {
      column = 1;
      row = 1;
      defaultMoveFrom(zhuofan, column, row);
    } else {
      defaultMoveTo(zhuofan);
      description.setText(
          "Robotics Engineering and Mechanical Engineering Major\n"
              + "Class of 2022\n"
              + "Documentation Analyst, Part Time Front-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "Lab Service Request, Gift Delivery Service Request, Outstanding Tasks Table, COVID-19 Page, Help Page\n"
              + "\n"
              + "\n");
    }
  }

  @FXML
  private void showDarrenText() {
    if (description.isVisible()) {
      column = 2;
      row = 1;
      defaultMoveFrom(darren, column, row);
    } else {
      defaultMoveTo(darren);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Assistant Lead Back-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "Several service request DAOs and SQL Table creation, facade and singleton DAO design, speech to text search, Maintenance Service Request controller/fxml\n"
              + "\n");
    }
  }

  @FXML
  private void showDylanText() {
    if (description.isVisible()) {
      column = 3;
      row = 1;
      defaultMoveFrom(dylan, column, row);
    } else {
      defaultMoveTo(dylan);
      description.setText(
          "Computer Science and Robotics Engineering Major\n"
              + "Class of 2024\n"
              + "Full Time Front-End Developer\n"
              + "\n"
              + "Contributions\n"
              + "Lab Service Request, Equipment Delivery Service Request, Meal Service Request, Graphical Map Editor Dashboard\n"
              + "\n"
              + "\n");
    }
  }

  @FXML
  private void showNadavText() {
    if (description.isVisible()) {
      column = 4;
      row = 1;
      defaultMoveFrom(nadav, column, row);
    } else {
      defaultMoveTo(nadav);
      description.setText(
          "Computer Science Major\n"
              + "Class of 2024\n"
              + "Project Manager\n"
              + "\n"
              + "Contributions\n"
              + "Laundry Service Request Page, login capabilities, facial recognition on login, live clock, Contributed to the UI of the application\n"
              + "\n"
              + "\n");
    }
  }
}
