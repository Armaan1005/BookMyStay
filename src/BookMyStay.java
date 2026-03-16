import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Demonstrates input validation and structured error handling
 * using custom exceptions to prevent invalid booking states.
 *
 * @version 9.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Validation & Error Handling\n");

        RoomInventory inventory = new RoomInventory();
        BookingValidator validator = new BookingValidator(inventory);

        // Example booking attempts
        try {
            validator.validateBooking("Single");
            inventory.bookRoom("Single");
            System.out.println("Booking confirmed for Single room.\n");
        }
        catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            validator.validateBooking("Suite");
            inventory.bookRoom("Suite");
            System.out.println("Booking confirmed for Suite room.\n");
        }
        catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            validator.validateBooking("Penthouse"); // invalid type
            inventory.bookRoom("Penthouse");
        }
        catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nFinal Inventory State:");
        inventory.displayInventory();
    }
}


/**
 * =============================================================
 * CLASS - BookingValidator
 * =============================================================
 *
 * Validates booking inputs and system constraints.
 */

class BookingValidator {

    private RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validateBooking(String roomType) throws InvalidBookingException {

        if (!inventory.roomExists(roomType)) {

            throw new InvalidBookingException(
                    "Invalid room type: " + roomType
            );
        }

        if (inventory.getAvailability(roomType) <= 0) {

            throw new InvalidBookingException(
                    "No rooms available for type: " + roomType
            );
        }
    }
}


/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 1);
        inventory.put("Double", 2);
        inventory.put("Suite", 0);
    }

    public boolean roomExists(String type) {

        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {

        return inventory.getOrDefault(type, 0);
    }

    public void bookRoom(String type) throws InvalidBookingException {

        int available = inventory.getOrDefault(type, 0);

        if (available <= 0) {

            throw new InvalidBookingException(
                    "Cannot allocate room. Inventory exhausted."
            );
        }

        inventory.put(type, available - 1);
    }

    public void displayInventory() {

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " rooms remaining: "
                    + entry.getValue());
        }
    }
}


/**
 * =============================================================
 * CLASS - InvalidBookingException
 * =============================================================
 *
 * Custom exception for invalid booking scenarios.
 */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}