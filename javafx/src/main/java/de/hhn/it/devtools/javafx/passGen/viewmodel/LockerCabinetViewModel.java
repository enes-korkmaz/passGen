package de.hhn.it.devtools.javafx.passGen.viewmodel;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.AdminLockerService;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A view model for the LockerCabinet view.
 */
public class LockerCabinetViewModel {
  private AdminLockerService adminLockerService;
  private SingletonLockerRepository singleton;
  private ObservableList<LockerCabinet> lockerCabinets = FXCollections.observableArrayList();
  private ObjectProperty<String> cabinetLocation = new SimpleObjectProperty<String>();

  public LockerCabinetViewModel(AdminLockerService adminLockerService,
                                SingletonLockerRepository singleton) {
    this.adminLockerService = adminLockerService;
    this.singleton = singleton;
  }

  /**
   * Initializes the lockerCabinets list with the locker cabinets from the repository.
   */
  public void removeLockerCabinet(LockerCabinet lockerCabinet) throws IllegalParameterException {
    int id = lockerCabinet.getCabinetId();
    adminLockerService.removeLockerCabinet(id);
    lockerCabinets.remove(lockerCabinet);
  }

  /**
   * Adds a new locker cabinet to the repository.
   *
   * @param location   the location of the locker cabinet
   * @param numLockers the number of lockers in the cabinet
   */
  public void addLockerCabinet(String location, int numLockers) throws IllegalParameterException {
    int lockerCabinetId = adminLockerService.createLockerCabinet(location);
    for (int i = 0; i < numLockers; i++) {
      adminLockerService.createLocker(
              adminLockerService.getLockerCabinet(lockerCabinetId), location);
    }
    lockerCabinets.add(adminLockerService.getLockerCabinet(lockerCabinetId));
  }

  /**
   * Removes a locker from the cabinet.
   */
  public void removeLockerFromCabinet(SimpleLockerCabinet lockerCabinet, Locker locker)
          throws IllegalParameterException {
    int lockerId = locker.getId();
    adminLockerService.removeLocker(lockerId);
    lockerCabinet.removeLocker(lockerId);
  }

  public ObservableList<LockerCabinet> getLockerCabinets() {
    return lockerCabinets;
  }

  /*
   * Returns the cabinet location property.
   */
  public ObjectProperty<String> cabinetLocationProperty() {
    return cabinetLocation;
  }
}

