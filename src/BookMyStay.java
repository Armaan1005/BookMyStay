/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * This class demonstrates object modeling using abstraction
 * and inheritance for different hotel room types.
 *
 * The application:
 * - Creates room objects for different room categories
 * - Stores room availability using simple variables
 * - Displays room details and availability information
 *
 * @author Developer
 * @version 2.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Welcome to the Book My Stay Hotel Booking System");
        System.out.println("Displaying available room types...\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 10;
        int doubleAvailability = 6;
        int suiteAvailability = 3;

        // Display room details
        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailability + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailability + "\n");

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailability + "\n");

        System.out.println("System initialized successfully.");
    }
}


/**
 * =============================================================
 * ABSTRACT CLASS - Room
 * =============================================================
 *
 * Represents a generic hotel room.
 * Concrete room types must extend this class.
 */
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq ft");
        System.out.println("Price per Night: $" + price);
    }
}


/**
 * =============================================================
 * CLASS - SingleRoom
 * =============================================================
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100.0);
    }
}


/**
 * =============================================================
 * CLASS - DoubleRoom
 * =============================================================
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180.0);
    }
}


/**
 * =============================================================
 * CLASS - SuiteRoom
 * =============================================================
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 350.0);
    }
}