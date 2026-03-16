import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * Allows cancellation of confirmed bookings while safely
 * restoring inventory and maintaining system consistency.
 *
 * @version 10.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Booking Cancellation System\n");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService =
                new CancellationService(inventory, history);

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single", "S101");
        Reservation r2 = new Reservation("RES102", "Bob", "Double", "D201");

        history.addReservation(r1);
        history.addReservation(r2);

        System.out.println("\nCurrent Bookings:");
        history.displayBookings();

        // Cancel reservation
        System.out.println("\nCancelling reservation RES101...\n");
        cancellationService.cancelReservation("RES101");

        System.out.println("\nUpdated Booking History:");
        history.displayBookings();

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}


/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId,
                       String guestName,
                       String roomType,
                       String roomId) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {

        System.out.println("Reservation ID: " + reservationId
                + " | Guest: " + guestName
                + " | Room: " + roomId
                + " (" + roomType + ")");
    }
}


/**
 * =============================================================
 * CLASS - BookingHistory
 * =============================================================
 */

class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {

        reservations.add(reservation);

    }

    public Reservation findReservation(String reservationId) {

        for (Reservation r : reservations) {

            if (r.getReservationId().equals(reservationId))
                return r;

        }

        return null;
    }

    public void removeReservation(Reservation reservation) {

        reservations.remove(reservation);

    }

    public void displayBookings() {

        if (reservations.isEmpty()) {

            System.out.println("No active reservations.");
            return;
        }

        for (Reservation r : reservations)
            r.display();
    }
}


/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 */

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void increment(String type) {

        inventory.put(type, inventory.get(type) + 1);
    }

    public void displayInventory() {

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " rooms available: "
                    + entry.getValue());

        }
    }
}


/**
 * =============================================================
 * CLASS - CancellationService
 * =============================================================
 *
 * Handles booking cancellation and rollback.
 */

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack used for rollback tracking
    private Stack<String> releasedRooms = new Stack<>();

    public CancellationService(RoomInventory inventory,
                               BookingHistory history) {

        this.inventory = inventory;
        this.history = history;
    }

    public void cancelReservation(String reservationId) {

        Reservation reservation = history.findReservation(reservationId);

        if (reservation == null) {

            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        // Push released room ID to rollback stack
        releasedRooms.push(reservation.getRoomId());

        // Restore inventory
        inventory.increment(reservation.getRoomType());

        // Remove reservation from history
        history.removeReservation(reservation);

        System.out.println("Reservation "
                + reservationId
                + " cancelled successfully.");
    }
}