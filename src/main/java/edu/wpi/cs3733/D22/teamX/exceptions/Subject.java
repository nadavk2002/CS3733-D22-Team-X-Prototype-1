package edu.wpi.cs3733.D22.teamX.exceptions;

import edu.wpi.cs3733.D22.teamX.Observer;
import java.util.ArrayList;
import java.util.List;

public class Subject {
  protected List<Observer> observers = new ArrayList<>();

  public void attach(Observer observer) {
    observers.add(observer);
  }

  public void notifyAllObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
