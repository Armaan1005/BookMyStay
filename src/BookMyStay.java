import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Description:
 * Demonstrates thread-safe booking processing when multiple
 * guests submit booking requests at the same time.
 *
 * @version 11.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Concurrent Booking Simulation\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue bookingQueue = new BookingQueue();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Single"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));
        bookingQueue.addRequest(new Reservation("David", "Single"));

        // Create booking processors (threads)
        Thread processor1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));
        Thread processor2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));

        processor1.start();
        processor2.start();

        try {
            processor1.join();
            processor2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
 * CLASS - BookingQueue
 * =============================================================
 */

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation reservation) {

        queue.add(reservation);

        System.out.println("Request received from "
                + reservation.getGuestName()
                + " for " + reservation.getRoomType());
    }

    public synchronized Reservation getNextRequest() {

        return queue.poll();
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

        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0)
            return false;

        inventory.put(roomType, available - 1);

        return true;
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
 * CLASS - ConcurrentBookingProcessor
 * =============================================================
 *
 * Processes booking requests using multiple threads.
 */

class ConcurrentBookingProcessor implements Runnable {

    private BookingQueue queue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingQueue queue,
                                      RoomInventory inventory) {

        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request = queue.getNextRequest();

            if (request == null)
                break;

            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {

                System.out.println(Thread.currentThread().getName()
                        + " confirmed booking for "
                        + request.getGuestName()
                        + " (" + request.getRoomType() + ")");

            } else {

                System.out.println(Thread.currentThread().getName()
                        + " failed booking for "
                        + request.getGuestName()
                        + " (No availability)");
            }
        }
    }
}