package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.passGen.provider.SimpleAdminLockerService;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestAdminLockerServiceGoodCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestAdminLockerServiceGoodCases.class);

  private SimpleAdminLockerService service;

  @BeforeEach
  public void setup() {
    service = new SimpleAdminLockerService();
  }

  @Test
  @DisplayName("Test create locker successfully")
  public void testCreateLockerSuccessfully() throws IllegalParameterException {
    logger.debug("Creating a new locker");
    int id = service.createLockerCabinet("Location A");
    int lockerId = service.createLocker(service.getLockerCabinet(id), "Location A");
    assertEquals(1, lockerId);
    logger.debug("Locker created with ID: {}", lockerId);
    assertNotNull(SingletonLockerRepository.getInstance().getLockers().get(lockerId));
  }

  @Test
  @DisplayName("Test create locker unite successfully")
  public void testCreateLockerCabinetSuccessfully() {
    logger.debug("Creating a new lockerUnite");
    int lockerUniteId = service.createLockerCabinet("Location A");
    assertEquals(1, lockerUniteId);
    logger.debug("LockerUnite created with ID: {}", lockerUniteId);
    assertNotNull(SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId));
  }

  @Test
  @DisplayName("Test create locker with unique ID")
  public void testCreateLockerWithUniqueId() throws IllegalParameterException {
    logger.debug("Creating a new locker with unique ID");
    int id = service.createLockerCabinet("Location A");
    int lockerId = service.createLocker(service.getLockerCabinet(id), "Location A");
    logger.debug("First Locker with ID: {}", lockerId);
    int lockerId2 = service.createLocker(service.getLockerCabinet(id), "Location A");
    logger.debug("Second Locker with ID: {}", lockerId2);
    assertNotEquals(lockerId, lockerId2);
    assertNotNull(SingletonLockerRepository.getInstance().getLockers().get(lockerId));
    assertNotNull(SingletonLockerRepository.getInstance().getLockers().get(lockerId2));
  }

  @Test
  @DisplayName("Test create locker unite with unique ID")
  public void testCreateLockerCabinetWithUniqueId() {
    logger.debug("Creating a new lockerUnite with unique ID");
    int lockerUniteId = service.createLockerCabinet("Location A");
    logger.debug("First LockerUnite with ID: {}", lockerUniteId);
    int lockerUniteId2 = service.createLockerCabinet("Location A");
    logger.debug("Second LockerUnite with ID: {}", lockerUniteId2);
    assertNotEquals(lockerUniteId, lockerUniteId2);
    assertNotNull(SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId));
    assertNotNull(SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId2));
  }

  @Test
  @DisplayName("Test remove locker successfully")
  public void testRemoveLockerSuccessfully() throws IllegalParameterException {
    logger.debug("Removing a locker");
    int id = service.createLockerCabinet("Location A");
    int lockerId = service.createLocker(service.getLockerCabinet(id), "Location A");
    service.removeLocker(lockerId);
    assertNull(SingletonLockerRepository.getInstance().getLockers().get(lockerId));
  }

  @Test
  @DisplayName("Test remove LockerUnite successfully")
  public void testRemoveLockerCabinetSuccessfully() throws IllegalParameterException {
    logger.debug("Removing a lockerUnite");
    int lockerUniteId = service.createLockerCabinet("Location A");
    service.removeLockerCabinet(lockerUniteId);
    assertNull(SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId));
  }

  @Test
  @DisplayName("Add locker to lockerUnite successfully")
  public void testAddLockerToLockerCabintSuccessfully() throws IllegalParameterException {
    logger.debug("Adding a locker to a lockerUnite");
    int lockerUniteId = service.createLockerCabinet("Location A");
    int lockerId = service.createLocker(service.getLockerCabinet(lockerUniteId), "Location A");
    service.addLockerToLockerCabint(lockerUniteId, SingletonLockerRepository.getInstance().getLockers().get(lockerId));
    int size = service.getLockerCabinet(lockerUniteId).getLockerCabinet().size();
    assertEquals(1, size);
  }
}
