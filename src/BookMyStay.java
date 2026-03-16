import java.io.*;
import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * Demonstrates saving system state to a file and restoring
 * it when the application restarts.
 *
 * @version 12.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Data Persistence Demo\n");

        PersistenceService persistenceService = new PersistenceService();

        // Attempt to load saved system state
        SystemState state = persistenceService.loadState();

        if (state == null) {

            System.out.println("No previous state found. Initializing new system.\n");

            state = new SystemState();

            state.inventory.put("Single", 3);
            state.inventory.put("Double", 2);
            state.inventory.put("Suite", 1);

            state.bookingHistory.add(new Reservation("RES101", "Alice", "Single"));
            state.bookingHistory.add(new Reservation("RES102", "Bob", "Double"));

        }

        System.out.println("Current Inventory:");
        state.displayInventory();

        System.out.println("\nBooking History:");
        state.displayBookings();

        // Simulate system shutdown → save state
        persistenceService.saveState(state);

        System.out.println("\nSystem state saved successfully.");
    }
}


/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 */

class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {

        System.out.println("Reservation ID: " + reservationId
                + " | Guest: " + guestName
                + " | Room Type: " + roomType);
    }
}


/**
 * =============================================================
 * CLASS - SystemState
 * =============================================================
 *
 * Stores inventory and booking history.
 */

class SystemState implements Serializable {

    Map<String, Integer> inventory = new HashMap<>();
    List<Reservation> bookingHistory = new ArrayList<>();

    public void displayInventory() {

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " rooms available: "
                    + entry.getValue());
        }
    }

    public void displayBookings() {

        for (Reservation r : bookingHistory)
            r.display();
    }
}


/**
 * =============================================================
 * CLASS - PersistenceService
 * =============================================================
 */

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    public void saveState(SystemState state) {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(state);
            out.close();

        }
        catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    public SystemState loadState() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            SystemState state = (SystemState) in.readObject();

            in.close();

            System.out.println("System state restored from file.\n");

            return state;
        }
        catch (Exception e) {

            System.out.println("No saved state available.");

            return null;
        }
    }
}