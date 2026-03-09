import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class demonstrates how guests can search for available
 * room types without modifying the system state.
 *
 * The search service retrieves availability from the inventory
 * and displays only room types that are currently available.
 *
 * @author Developer
 * @version 4.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform room search
        System.out.println("Available Rooms:\n");
        searchService.displayAvailableRooms();

        System.out.println("\nSearch completed successfully.");
    }
}


/**
 * =============================================================
 * ABSTRACT CLASS - Room
 * =============================================================
 */

abstract class Room {

    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
    }
}


/**
 * =============================================================
 * ROOM TYPES
 * =============================================================
 */

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single", 1, 100);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double", 2, 180);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite", 3, 350);
    }
}


/**
 * =============================================================
 * INVENTORY CLASS (from UC3)
 * =============================================================
 */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 0);   // Suite unavailable
    }

    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllRooms() {
        return inventory;
    }
}


/**
 * =============================================================
 * ROOM SEARCH SERVICE
 * =============================================================
 *
 * Handles read-only room search operations.
 */

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms() {

        for (Map.Entry<String, Integer> entry : inventory.getAllRooms().entrySet()) {

            String roomType = entry.getKey();
            int availability = entry.getValue();

            // Filter out unavailable rooms
            if (availability > 0) {

                Room room = createRoom(roomType);

                room.displayDetails();
                System.out.println("Available Rooms: " + availability);
                System.out.println("--------------------------");
            }
        }
    }

    private Room createRoom(String type) {

        switch (type) {

            case "Single":
                return new SingleRoom();

            case "Double":
                return new DoubleRoom();

            case "Suite":
                return new SuiteRoom();

            default:
                return null;
        }
    }
}