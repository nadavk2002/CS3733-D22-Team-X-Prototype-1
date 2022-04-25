package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.ServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.ServiceRequestDAO;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class RequestGraphController implements Initializable {
    @FXML private LineChart<Integer, Integer> lineChart;
    private List<ServiceRequest> requests;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requests = ServiceRequestDAO.getDAO().getServiceRequests();
        XYChart.Series<Integer, Integer> series = new XYChart.Series<Integer, Integer>();
        LocalDateTime minTime = requests.get(0).getCreationTime();
        LocalDateTime maxTime = requests.get(0).getCreationTime();
        for (ServiceRequest r : requests) {
            if (r.getCreationTime().isBefore(minTime)) minTime = r.getCreationTime();
            if (r.getCreationTime().isAfter(maxTime)) maxTime = r.getCreationTime();
            series.getData().add(new XYChart.Data(r.getCreationTime().getDayOfYear(), 1));
        }
        lineChart.getData().add(series);
    }
}
