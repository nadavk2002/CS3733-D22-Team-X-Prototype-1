package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.LangServiceRequest;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReqLangController {

  public ReqLangController() {}

  public void getDestinations() {
    LocationDAO locationDAO = new LocationDAOImpl();
    List<Location> locations = locationDAO.getAllLocations();
  }

  public ObservableList<String> getLocationNames() {
    LocationDAO locationsDAO = new LocationDAOImpl();
    List<Location> locations = locationsDAO.getAllLocations();
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  private String locationToNodeId(String shortName) {
    LocationDAO locationsDAO = new LocationDAOImpl();
    List<Location> locations = locationsDAO.getAllLocations();
    for (int i = 0; i < locations.size(); i++) {
      if (locations.get(i).getShortName().equals(shortName)) return locations.get(i).getNodeID();
    }
    return "ERROR";
  }

  public void submitRequest(String location, String status, String language) {
    LangServiceRequest request = new LangServiceRequest();
    LocationDAO locationDAO = new LocationDAOImpl();

    request.setRequestID(request.makeRequestID());
    request.setDestination(locationDAO.getLocation(locationToNodeId(location)));
    request.setStatus(status);
    request.setLanguage(language);
  }
}
