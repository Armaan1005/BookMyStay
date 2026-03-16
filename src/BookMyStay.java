import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStay
 * =============================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * Demonstrates how optional services can be attached to
 * reservations without modifying the booking or inventory logic.
 *
 * @version 7.1
 */

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Add-On Service Selection\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Example reservation IDs
        String reservation1 = "RES101";
        String reservation2 = "RES102";

        // Guests select add-on services
        serviceManager.addService(reservation1, new Service("Breakfast", 20));
        serviceManager.addService(reservation1, new Service("Airport Pickup", 40));

        serviceManager.addService(reservation2, new Service("Spa Access", 50));
        serviceManager.addService(reservation2, new Service("Dinner Package", 35));

        // Display selected services
        System.out.println("Services for Reservation " + reservation1);
        serviceManager.displayServices(reservation1);

        System.out.println("\nServices for Reservation " + reservation2);
        serviceManager.displayServices(reservation2);

        // Calculate cost
        System.out.println("\nAdditional Cost for " + reservation1 +
                ": $" + serviceManager.calculateTotal(reservation1));

        System.out.println("Additional Cost for " + reservation2 +
                ": $" + serviceManager.calculateTotal(reservation2));
    }
}


/**
 * =============================================================
 * CLASS - Service
 * =============================================================
 *
 * Represents an optional add-on service.
 */

class Service {

    private String name;
    private double price;

    public Service(String name, double price) {

        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}


/**
 * =============================================================
 * CLASS - AddOnServiceManager
 * =============================================================
 *
 * Manages services associated with reservations.
 */

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {

        reservationServices = new HashMap<>();

    }

    /**
     * Add service to reservation
     */
    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service "
                + service.getName()
                + " to reservation "
                + reservationId);
    }

    /**
     * Display services for reservation
     */
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null) {

            System.out.println("No services selected.");
            return;
        }

        for (Service service : services) {

            System.out.println(service.getName()
                    + " ($" + service.getPrice() + ")");
        }
    }

    /**
     * Calculate additional cost
     */
    public double calculateTotal(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null)
            return 0;

        double total = 0;

        for (Service service : services) {

            total += service.getPrice();

        }

        return total;
    }
}