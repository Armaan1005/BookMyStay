import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Stores confirmed reservations in a booking history list
 * and allows administrators to generate reports from it.
 *
 * @version 8.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Booking History System\n");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single");
        Reservation r2 = new Reservation("RES102", "Bob", "Double");
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite");

        // Add confirmed bookings to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views booking history
        System.out.println("Booking History:\n");
        reportService.displayAllBookings();

        // Admin views summary report
        System.out.println("\nBooking Summary Report:\n");
        reportService.generateSummaryReport();
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

    public Reservation(String reservationId, String guestName, String roomType) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {

        System.out.println("Reservation ID: " + reservationId
                + " | Guest: " + guestName
                + " | Room Type: " + roomType);
    }
}


/**
 * =============================================================
 * CLASS - BookingHistory
 * =============================================================
 *
 * Stores confirmed reservations in chronological order.
 */

class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {

        reservations = new ArrayList<>();

    }

    public void addReservation(Reservation reservation) {

        reservations.add(reservation);

        System.out.println("Reservation stored: "
                + reservation.getReservationId());
    }

    public List<Reservation> getReservations() {

        return reservations;

    }
}


/**
 * =============================================================
 * CLASS - BookingReportService
 * =============================================================
 *
 * Generates reports from booking history.
 */

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {

        this.history = history;

    }

    /**
     * Display all bookings
     */
    public void displayAllBookings() {

        for (Reservation r : history.getReservations()) {

            r.display();

        }
    }

    /**
     * Generate summary report
     */
    public void generateSummaryReport() {

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : history.getReservations()) {

            roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {

            System.out.println(entry.getKey()
                    + " Rooms Booked: "
                    + entry.getValue());

        }
    }
}