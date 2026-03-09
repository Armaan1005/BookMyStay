import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates centralized inventory management
 * using a HashMap to store room availability.
 *
 * The inventory component becomes the single source of truth
 * for room counts across the system.
 *
 * @author Developer
 * @version 3.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to the Book My Stay Hotel Booking System\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current availability
        System.out.println("Current Room Inventory:");
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating inventory (1 Single Room booked)...");

        inventory.updateAvailability("Single", -1);

        System.out.println("\nUpdated Room Inventory:");
        inventory.displayInventory();

        System.out.println("\nSystem initialized successfully.");
    }
}


/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 *
 * Responsible for maintaining room availability using
 * a centralized HashMap.
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 10);
        inventory.put("Double", 6);
        inventory.put("Suite", 3);
    }

    /**
     * Retrieve availability for a room type.
     */
    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type.
     */
    public void updateAvailability(String roomType, int change) {

        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    /**
     * Display the full inventory state.
     */
    public void displayInventory() {

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());

        }
    }
}