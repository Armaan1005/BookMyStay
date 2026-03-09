import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * Processes booking requests from the queue and assigns
 * rooms while preventing duplicate allocations.
 *
 * @version 6.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Reservation Allocation System\n");

        // Initialize services
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single"));
        requestQueue.addRequest(new Reservation("Bob", "Double"));
        requestQueue.addRequest(new Reservation("Charlie", "Single"));
        requestQueue.addRequest(new Reservation("David", "Suite"));

        System.out.println("\nProcessing Reservations...\n");

        // Process requests
        while (!requestQueue.isEmpty()) {

            Reservation request = requestQueue.getNextRequest();
            bookingService.allocateRoom(request);

        }

        System.out.println("\nFinal Inventory State:");
        inventory.displayInventory();
    }
}


/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/**
 * =============================================================
 * CLASS - BookingRequestQueue
 * =============================================================
 */

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {

        queue.add(reservation);

        System.out.println("Request received: "
                + reservation.getGuestName()
                + " -> " + reservation.getRoomType());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {

        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {

        inventory.put(type, inventory.get(type) - 1);
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
 * CLASS - BookingService
 * =============================================================
 */

class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRooms;

    // Map room types to allocated room IDs
    private HashMap<String, Set<String>> roomAssignments;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRooms = new HashSet<>();
        roomAssignments = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation) {

        String type = reservation.getRoomType();

        if (inventory.getAvailability(type) <= 0) {

            System.out.println("No rooms available for "
                    + reservation.getGuestName()
                    + " (" + type + ")");
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRooms.add(roomId);

        roomAssignments
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

        inventory.decrement(type);

        System.out.println("Reservation Confirmed: "
                + reservation.getGuestName()
                + " -> Room ID: " + roomId);
    }


    private String generateRoomId(String type) {

        String roomId;

        do {

            roomId = type.substring(0, 1)
                    + (100 + new Random().nextInt(900));

        } while (allocatedRooms.contains(roomId));

        return roomId;
    }
}