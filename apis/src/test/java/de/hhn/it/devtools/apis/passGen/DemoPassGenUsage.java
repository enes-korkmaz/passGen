package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;

/**
 * Demo class to show the usage of the LockerService. It is not runnable because in this module
 * there is no possibility to access the implementation.
 */
public class DemoPassGenUsage {

    public static void main(String[] args) throws IllegalParameterException, WrongLoginCredentialsException {
        //<editor-fold desc="Initialisation">
        AdminLockerService adminService = null;
        LockerService lockerService = null;
        PasscodeGenerator passcodeGenerator = null;
        LockerListener listener1 = null;
        LockerListener listener2 = null;
        User user = null;
        UserManagementService userManagementService = null;
        //</editor-fold>

        //<editor-fold desc="Admin-Interface">
        // Create a new locker unit
        int id = adminService.createLockerCabinet("location");
        LockerCabinet lockerCabinet = adminService.getLockerCabinet(id);
        // Register two new lockers - ideally via admin interface
        int id1 = adminService.createLocker(lockerCabinet, "location");
        int id2 = adminService.createLocker(lockerCabinet, "location");

        // Add a listener to each of the registered lockers
        lockerService.addCallback(id1, listener1);
        lockerService.addCallback(id2, listener2);

        // Unlocking locker1 via admin interface, to place object into it - latter has to be done manually
        adminService.setLockerState(id1, LockerState.UNLOCKED);

        // Set locker state to locked - an object is now stored in locker1
        adminService.setLockerState(id1, LockerState.LOCKED);

        // Deactivate locker2 - in this state the locker can't be interacted with by users.
        // State is used due to the locker not being used as storage yet or else.
        adminService.setLockerState(id2, LockerState.DISABLED);
        //</editor-fold>

        //<editor-fold desc="User-Interface">
        // Now the user can interact with the lockers
        // Login to the system
        userManagementService.login("EMAIL", "PASSWORD");

        // Choosing locker - state change, activation of locker1
        lockerService.activateLocker(id1);

        // Generate one-time passcode and pass it to locker1
        int passCode = passcodeGenerator.createPasscode();

        // Unlock locker1 by inputting passcode on physical locker
        lockerService.unlockLocker(id1, passCode);

        // After unlocking locker1, user closes locker1 - state change, locker1 is now locked
        lockerService.lockLocker(id1);

        // Unlock locker1, ideally to return lend object
        userManagementService.login("EMAIL", "PASSWORD");

        lockerService.activateLocker(id1);
        int passCodeNew = passcodeGenerator.createPasscode();
        lockerService.unlockLocker(id1, passCodeNew);
        lockerService.lockLocker(id1);
        //</editor-fold>
    }

}
