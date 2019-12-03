package com.nujitha.project.classes;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WestminsterRentalVehicleManagerTest {
    private static WestminsterRentalVehicleManager westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();

    @Test
        //test method to verify that duplicate entries will not be entered
    void checkDuplicateVehicles() throws InterruptedException, ExecutionException, IOException {
        List<Vehicle> list = westminsterRentalVehicleManager.getVehicleCollection();
        Car car1 = new Car("plateNo", "make", "model", 123, true, 4, true);
        Car car2 = new Car("plateNo", "make", "model", 123, true, 4, true);
        list.add(car1);
        list.add(car2);
        assertTrue(westminsterRentalVehicleManager.checkDuplicates(car2));
    }

    @Test
        //check for invalid plate numbers
    void searchInvalidVehicle() throws ExecutionException, InterruptedException {
        assertFalse(westminsterRentalVehicleManager.searchVehicle("invalidPlateNumber"));
    }

    @Test
        //check if data will be successfully removed from all the lists
    void removeBooking() {
        List<Vehicle> vehicles = westminsterRentalVehicleManager.getVehicleCollection();
        List<Schedule> bookigns = westminsterRentalVehicleManager.getBookedVehicles();
        List refs = westminsterRentalVehicleManager.getBookingReferenceList();

        String thisRefNo = "Pezj0UnL3DuGYh3mHRCM";
        String thisPlateNo = "ko3544";

        Vehicle vehicleObj = null;
        for (Vehicle currVehicle : vehicles) {
            if (currVehicle.getPlateNo().equals(thisPlateNo)) {
                vehicleObj = currVehicle;
                break;
            }
        }
        Schedule bookingObj = null;
        for (Schedule currBooking : bookigns) {
            if (currBooking.getPlateNo().equals(thisPlateNo)) {
                bookingObj = currBooking;
                break;
            }
        }
        int vehicleIndex = vehicles.indexOf(vehicleObj);
        vehicles.get(vehicleIndex).setAvailability(true);
        bookigns.remove(bookingObj);
        refs.remove(thisRefNo);

        boolean finalStatusUpdated = false;
        boolean finalFalseBooking = true;
        boolean finalHasRef = false;
        for (Vehicle currVehicle : vehicles) {
            if (currVehicle.getPlateNo().equals(thisPlateNo)) {
                if (!currVehicle.isAvailability()) {
                    finalStatusUpdated = true;
                    break;
                }
            }
        }
        for (Schedule currBooking : bookigns) {
            if (currBooking.getPlateNo().equals(thisPlateNo)) {
                finalFalseBooking = false;
                break;
            }
        }
        for (Object currRef : refs) {
            if (currRef.equals(thisRefNo)) {
                finalHasRef = true;
                break;
            }
        }

        assertFalse(finalStatusUpdated);
        assertTrue(finalFalseBooking);
        assertFalse(finalHasRef);


    }

    @Nested
            //test method that verifies capacity limit
    class capacityCheck {

        @Test
        void capacityFull() {
            List<Vehicle> cap1 = westminsterRentalVehicleManager.getVehicleCollection();
            for (int i = 0; i < 51; i++) {
                cap1.add(new Car("plateNo" + i, "make", "model", 123, true, 4, true));
            }
            assertFalse(westminsterRentalVehicleManager.isCapacityAvailable());
            cap1.clear();
        }

        @Test
        void capacityAvailable() {
            List<Vehicle> cap2 = westminsterRentalVehicleManager.getVehicleCollection();
            for (int i = 0; i < 25; i++) {
                cap2.add(new Car("plateNo" + i, "make", "model", 123, true, 4, true));
            }
            assertTrue(westminsterRentalVehicleManager.isCapacityAvailable());
        }

    }

}
