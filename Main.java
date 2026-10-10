import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Customer {
    String name;
    String phone;

    Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

class Booking {
    int bookingId;
    Customer customer;
    Room room;
    String paymentMethod;

    Booking(int bookingId, Customer customer, Room room, String paymentMethod) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.paymentMethod = paymentMethod;
    }
}

public class Main {

    static ArrayList<Room> rooms = new ArrayList<>();

    static ArrayList<Booking> bookings = new ArrayList<>();
    static int nextBookingId = 1001;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Creating rooms
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));

        loadBookings();

        while (true) {

            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Show Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // OPTION 1: Show Rooms
            if (choice == 1) {

                System.out.println("\n----- ROOM DETAILS -----");

                for (Room r : rooms) {

                    System.out.println(
                            "Room: " + r.roomNumber +
                                    " | Category: " + r.category +
                                    " | Price: ₹" + r.price +
                                    " | Available: " + r.available);
                }
            }

            // OPTION 2: Book Room
            else if (choice == 2) {

                System.out.print("Enter room number: ");
                int roomNumber = sc.nextInt();
                sc.nextLine();

                boolean found = false;

                for (Room r : rooms) {

                    if (r.roomNumber == roomNumber) {

                        found = true;

                        if (r.available) {

                            System.out.print("Enter customer name: ");
                            String name = sc.nextLine();

                            System.out.print("Enter phone number: ");
                            String phone = sc.nextLine();

                            Customer customer = new Customer(name, phone);

                            System.out.println("\nSelect Payment Method:");
                            System.out.println("1. UPI");
                            System.out.println("2. Card");
                            System.out.println("3. Cash");

                            System.out.print("Enter your choice: ");
                            int paymentChoice = sc.nextInt();
                            sc.nextLine();

                            String paymentMethod;

                            if (paymentChoice == 1) {
                                paymentMethod = "UPI";
                            } else if (paymentChoice == 2) {
                                paymentMethod = "Card";
                            } else if (paymentChoice == 3) {
                                paymentMethod = "Cash";
                            } else {
                                paymentMethod = "Unknown";
                            }

                            r.available = false;

                            Booking booking = new Booking(
                                    nextBookingId,
                                    customer,
                                    r,
                                    paymentMethod);

                            bookings.add(booking);

                            nextBookingId++;

                            saveBookings();

                            System.out.println("\nPayment successful!");
                            System.out.println("Payment Method: " + paymentMethod);

                            


                            System.out.println("\nRoom booked successfully!");

                            System.out.println("----- BOOKING DETAILS -----");
                            System.out.println("Customer Name: "
                                    + customer.name);
                            System.out.println("Phone: "
                                    + customer.phone);
                            System.out.println("Room Number: "
                                    + r.roomNumber);
                            System.out.println("Category: "
                                    + r.category);
                            System.out.println("Price: ₹"
                                    + r.price);

                        } else {

                            System.out.println(
                                    "Sorry, room is already booked.");
                        }

                        break;
                    }
                }

                if (!found) {
                    System.out.println("Room not found.");
                }
            }

            // OPTION 3: Cancel Booking
            else if (choice == 3) {

                System.out.print("Enter room number to cancel booking: ");
                int roomNumber = sc.nextInt();

                boolean found = false;

                for (Room r : rooms) {

                    if (r.roomNumber == roomNumber) {

                        found = true;

                        if (!r.available) {
                            r.available = true;

                            Booking bookingToRemove = null;

                            for (Booking b : bookings) {
                                if (b.room.roomNumber == r.roomNumber) {
                                    bookingToRemove = b;
                                    break;
                                }
                            }

                            if (bookingToRemove != null) {
                                bookings.remove(bookingToRemove);
                            }

                            saveBookings();

                            System.out.println("Booking cancelled successfully!");
                            System.out.println("Room " + r.roomNumber
                                    + " is now available.");
                        } else {
                            System.out.println("This room is not booked.");
                        }

                        break;
                    }
                }

                if (!found) {
                    System.out.println("Room not found.");
                }
            }

            // OPTION 4: View Booking Details
            else if (choice == 4) {

                if (bookings.isEmpty()) {

                    System.out.println("No bookings available.");

                } else {

                    System.out.println("\n===== BOOKING DETAILS =====");

                    for (Booking b : bookings) {

                        System.out.println("Booking ID: " + b.bookingId);
                        System.out.println("Customer Name: " + b.customer.name);
                        System.out.println("Phone: " + b.customer.phone);
                        System.out.println("Room Number: " + b.room.roomNumber);
                        System.out.println("Category: " + b.room.category);
                        System.out.println("Price: ₹" + b.room.price);
                        System.out.println("Payment Method: " + b.paymentMethod);
                        System.out.println("---------------------------");
                    }
                }
            }

            // OPTION 5: Exit
            else if (choice == 5) {

                System.out.println(
                        "Thank you for using Hotel Reservation System!");

                break;
            }

            else {
                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }

    static void saveBookings() {

        try {

            FileWriter writer = new FileWriter("bookings.txt");

            for (Booking b : bookings) {

                writer.write(
                        b.bookingId + "|" +
                                b.customer.name + "|" +
                                b.customer.phone + "|" +
                                b.room.roomNumber + "|" +
                                b.room.category + "|" +
                                b.room.price + "|" +
                                b.paymentMethod + "\n");
            }

            writer.close();

            System.out.println("Bookings saved successfully!");

        } catch (IOException e) {

            System.out.println("Error saving bookings.");
        }
    }

    static void loadBookings() {

        File file = new File("bookings.txt");

        if (!file.exists()) {
            return;
        }

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                int bookingId = Integer.parseInt(data[0]);
                String name = data[1];
                String phone = data[2];
                int roomNumber = Integer.parseInt(data[3]);
                String paymentMethod = data[6];

                for (Room r : rooms) {

                    if (r.roomNumber == roomNumber) {

                        Customer customer = new Customer(name, phone);

                        Booking booking = new Booking(
                                bookingId,
                                customer,
                                r,
                                paymentMethod);

                        bookings.add(booking);

                        r.available = false;

                        if (bookingId >= nextBookingId) {
                            nextBookingId = bookingId + 1;
                        }

                        break;
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading bookings.");
        }
    }

}