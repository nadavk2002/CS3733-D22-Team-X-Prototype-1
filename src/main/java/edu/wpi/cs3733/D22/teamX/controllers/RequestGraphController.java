package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.ServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.ServiceRequestDAO;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

public class RequestGraphController implements Initializable {
  @FXML private LineChart<Number, Number> lineChart;
  private List<ServiceRequest> requests;
  @FXML private BorderPane pane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Days in the year");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Unfulfilled Jobs");
    lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    lineChart.setCreateSymbols(false);
    lineChart.setTitle("Unfulfilled Service Request on Each Day");
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName("Incomplete Service Requests");
    requests = ServiceRequestDAO.getDAO().getServiceRequests();
    int[] days = new int[365];
    for (ServiceRequest r : requests) {
      if (r.getCreationTime().getYear() != LocalDate.now().getYear()
          && (r.getDONETime().getYear() != LocalDate.now().getYear()
              || r.getDONETime().getYear() != 1970)) continue;
      int finalDay = 365;
      if (r.getDONETime().toEpochSecond(ZoneOffset.UTC) != 0)
        finalDay = r.getDONETime().getDayOfYear();
      // System.out.println(r.getCreationTime() + " - " + r.getDONETime());
      for (int i = r.getCreationTime().getDayOfYear(); i < finalDay; i++) {
        days[i]++;
      }
    }
    for (int i = 0; i < days.length; i++) {
      series.getData().add(new XYChart.Data<Number, Number>(i, days[i]));
    }
    lineChart.getData().add(series);
    pane.setCenter(lineChart);
  }
}
