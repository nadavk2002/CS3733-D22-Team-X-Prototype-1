package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class GraphicalMapEditorEquipmentTableOverlayController implements Initializable {
  @FXML private TableView<EquipmentUnit> table;
  @FXML private TableColumn<EquipmentUnit, String> unitID;
  @FXML private TableColumn<EquipmentUnit, String> type;
  @FXML private TableColumn<EquipmentUnit, String> availability;
  @FXML private TableColumn<EquipmentUnit, String> currLoc;

  private EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  @FXML private TextField searchEquipmentField;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    unitID.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    availability.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLoc.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));
    table.setRowFactory(
        new Callback<TableView<EquipmentUnit>, TableRow<EquipmentUnit>>() {
          @Override
          public TableRow<EquipmentUnit> call(TableView<EquipmentUnit> param) {
            TableRow<EquipmentUnit> equipmentRow = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem genBarCode = new MenuItem("Generate Barcode");
            genBarCode.setOnAction(
                event -> {
                  try {
                    generateBarCode(table.getSelectionModel().getSelectedItem().getUnitID());
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                });
            menu.getItems().add(genBarCode);
            equipmentRow.setOnContextMenuRequested(
                event -> {
                  menu.show(equipmentRow, event.getScreenX(), event.getScreenY());
                });
            return equipmentRow;
          }
        });

    searchEquipment();
  }

  void searchEquipment() {
    FilteredList<EquipmentUnit> filteredData = new FilteredList<>(equipmentListFill(), b -> true);
    searchEquipmentField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  equipmentUnit -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (equipmentUnit.getUnitID().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (equipmentUnit
                        .getType()
                        .getModel()
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    } else if (equipmentUnit
                        .getCurrLocationShortName()
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    } else {
                      return false;
                    }
                  });
            });
    SortedList<EquipmentUnit> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(table.comparatorProperty());
    populateTable(sortedData);
  }

  private ObservableList<EquipmentUnit> equipmentListFill() {
    ObservableList<EquipmentUnit> tableList = FXCollections.observableArrayList();
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllRecords();
    tableList.addAll(equipmentUnitList);
    return tableList;
  }

  private void populateTable(ObservableList<EquipmentUnit> equipmentUnits) {
    table.setItems(equipmentUnits);
  }

  private void generateBarCode(String barcode) throws IOException {
    Code128Bean bean = new Code128Bean();
    File file = new File("barcodes/" + barcode + ".png");
    File dir = file.getParentFile();
    if (!dir.exists()) dir.mkdir();
    if (file.exists()) return;
    OutputStream output = new FileOutputStream(file);
    BitmapCanvasProvider canvas =
        new BitmapCanvasProvider(
            output, "image/x-png", 150, BufferedImage.TYPE_BYTE_BINARY, false, 0);
    bean.generateBarcode(canvas, barcode);
    canvas.finish();
    output.close();
  }
}
