package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.LangServiceRequest;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReqLangController {

  private LocationDAO locationDAO;
  private List<Location> locations;

  public ReqLangController() {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
  }

  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  public void submitRequest(int locationIndex, String status, String language) {
    LangServiceRequest request = new LangServiceRequest();

    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(locationIndex));
    request.setStatus(status);
    request.setLanguage(language);
  }
}
