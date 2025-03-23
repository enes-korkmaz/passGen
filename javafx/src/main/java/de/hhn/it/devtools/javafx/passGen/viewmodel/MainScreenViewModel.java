package de.hhn.it.devtools.javafx.passGen.viewmodel;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.AdminLockerService;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * View model for the main screen of the application.
 */
public class MainScreenViewModel {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(MainScreenViewModel.class);
  private SingletonLockerRepository lockerRepository;
  private final AdminLockerService adminService;
  private SimpleUserManagementService userManagemanet;
  private ListProperty<SimpleLockerCabinet> cabinets;
  private ListProperty<SimpleLocker> lockers;
  private StringProperty location = new SimpleStringProperty();
  private StringProperty numLockers = new SimpleStringProperty();
  private ObjectProperty<SimpleLockerCabinet> selectedCabinet = new SimpleObjectProperty<>();

  /**
   * Constructor for the MainScreenViewModel.
   *
   * @param lockerRepository the locker repository.
   * @param adminService the admin service.
   * @param userManagemanet the user management service.
   */
  public MainScreenViewModel(SingletonLockerRepository lockerRepository,
                             AdminLockerService adminService,
                             SimpleUserManagementService userManagemanet) {
    this.lockerRepository = lockerRepository;
    this.adminService = adminService;
    this.userManagemanet = userManagemanet;
    cabinets = new SimpleListProperty<>(FXCollections.observableArrayList());
    lockers = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  public void setSelectedCabinet(SimpleLockerCabinet cabinet) {
    this.selectedCabinet.set(cabinet);
  }

  public ObjectProperty<SimpleLockerCabinet> selectedCabinetProperty() {
    return selectedCabinet;
  }


  /**
   * Method to set the cabinets list with the cabinets from the repository.
   */
  public void setCabinets() {
    cabinets.clear();
    for (LockerCabinet cabinet : lockerRepository.getLockerCabinets().values()) {
      cabinets.add((SimpleLockerCabinet) cabinet);
      logger.info("Cabinet added: {}", cabinet);
    }
  }

  /**
   * Method to set the lockers list with the lockers from the repository.
   *
   * @param cabinetId the id of the cabinet to get the lockers from.
   */
  public void setLockers(Integer cabinetId) {
    lockers.clear();
    logger.info("setLockers called with cabinetId: {}", cabinetId);
    SimpleLockerCabinet cabinet =
        (SimpleLockerCabinet) lockerRepository.getLockerCabinets().get(cabinetId);
    if (cabinet != null) {
      logger.info("Cabinet found: {}", cabinet);
      for (Locker locker : cabinet.getLockerCabinet().values()) {
        lockers.add((SimpleLocker) locker);
        logger.info("Locker added: {}", locker);
      }
    }
    logger.warn("No cabinet found with ID: {}", cabinetId);
  }

  public ListProperty<SimpleLocker> lockersProperty() {
    return lockers;
  }

  public ListProperty<SimpleLockerCabinet> cabinetsProperty() {
    return cabinets;
  }

  public StringProperty locationProperty() {
    return location;
  }

  public void setCabinetLocation(String cabinetsLocation) {
    this.location.set(cabinetsLocation);
  }

  public StringProperty numLockersProperty() {
    return numLockers;
  }

  public void setNumLockers(String lockerId) {
    this.numLockers.set(lockerId);
  }

  public String getNumLocker() {
    return numLockers.get();
  }

  public SimpleLockerCabinet getSelectedCabinet() {
    return selectedCabinet.get();
  }

  /**
   * Method to create a new cabinet with a given location and number of lockers.
   *
   * @param location    the location of the new cabinet.
   * @param numLockers the number of lockers in the new cabinet.
   */
  public void createCabinet(String location, int numLockers) {
    int cabinetId = adminService.createLockerCabinet(location);
    for (int i = 0; i < numLockers; i++) {
      try {
        int lockerId =
            adminService.createLocker(adminService.getLockerCabinet(cabinetId), location);
        adminService.addLockerToLockerCabint(cabinetId,
            lockerRepository.getLockers().get(lockerId));
      } catch (IllegalParameterException e) {
        logger.error("Exception when adding lockers to new cabinet", e);
      }
    }
    setCabinets();
  }

  /**
   * Method to remove all lockers of a cabinet and the cabinet itself from the repository.
   *
   * @param cabinetId the id of the cabinet to remove.
   */
  public void deleteCabinet(Integer cabinetId) {
    for (LockerCabinet cabinet : lockerRepository.getLockerCabinets().values()) {
      if (cabinet.getCabinetId() == cabinetId) {
        for (Locker locker : cabinet.getLockerCabinet().values()) {
          try {
            adminService.removeLocker(locker.getId());
            logger.info("Removed locker with id {} from cabinet at {}",
                    locker.getId(), cabinet.getLocation());
          } catch (IllegalParameterException e) {
            logger.error("Exception when removing lockers from cabinet", e);
          }
        }
        lockerRepository.removeLockerCabinet(cabinetId);
        cabinets.remove((SimpleLockerCabinet) cabinet);
        logger.info("Cabinet removed: {}", cabinet);
      }
    }
  }

  /**
   * Method to add a locker to a specific cabinet.
   *
   * @param cabinId the id of the cabinet to add the locker to.
   * @throws IllegalArgumentException if the cabinet does not exist or if the cabinet is full.
   */
  public void addLockerToCabinet(int cabinId) {
    SimpleLockerCabinet cabinet = (SimpleLockerCabinet)
            lockerRepository.getLockerCabinets().get(cabinId);
    if (cabinet != null) {
      if (cabinet.getLockerCabinet().size() == 18) {
        logger.error("Cabinet is full. Max capacity (18 lockers) reached.");
      } else {
        try {
          int lockerId =
                  adminService.createLocker(adminService.getLockerCabinet(cabinId), location.get());
          adminService.addLockerToLockerCabint(cabinId,
                  lockerRepository.getLockers().get(lockerId));
        } catch (IllegalParameterException e) {
          logger.error("Exception when adding lockers to cabinet", e);
        }
      }
    }
  }
}