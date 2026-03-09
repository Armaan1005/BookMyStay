import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Description:
 * This class demonstrates how booking requests are collected
 * and ordered using a Queue data structure.
 *
 * Booking requests are stored in FIFO order to ensure fairness.
 * No inventory allocation happens at this stage.
 *
 * @author Developer
 * @version 5.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay - Booking Request System\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));
        bookingQueue.addRequest(new Reservation("David", "Single"));

        // Display queued booking requests
        System.out.println("Current Booking Requests in Queue:\n");

        bookingQueue.displayRequests();

        System.out.println("\nAll requests stored successfully.");
    }
}


/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 *
 * Represents a guest booking request.
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/**
 * =============================================================
 * CLASS - BookingRequestQueue
 * =============================================================
 *
 * Manages incoming booking requests using FIFO ordering.
 */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Add booking request to queue
     */
    public void addRequest(Reservation reservation) {

        requestQueue.add(reservation);

        System.out.println("Request added: "
                + reservation.getGuestName()
                + " requested a "
                + reservation.getRoomType() + " room.");
    }

    /**
     * Display queued booking requests
     */
    public void displayRequests() {

        for (Reservation reservation : requestQueue) {

            reservation.display();

        }
    }
}