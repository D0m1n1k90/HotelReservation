package ui;

import api.HotelResource;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    public static void openMainMenu() {
        System.out.println("Welcome to the Hotel Reservation Service");
        Scanner scanner = new Scanner(System.in);
        boolean shouldStop = false;
        do {
            try {
                printMainMenu();
                int userInput = Integer.parseInt(scanner.nextLine());
                System.out.println("User Input: " + userInput);
                switch (userInput) {
                    case 1: {
                        bookReservation(scanner);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter Email format: name@domain.com");
                        String email = scanner.nextLine();
                        Collection<Reservation> reservations = HotelResource.getInstance().getCustomersReservation(email);
                        for (Reservation entry : reservations) {
                            System.out.println(entry.toString());
                        }
                        break;
                    }
                    case 3: {
                        createAccount(scanner);
                        break;
                    }
                    case 4: {
                        AdminMenu.openAdminMenu(scanner);
                        break;
                    }
                    case 5: {
                        shouldStop = true;
                        break;
                    }
                    default: {
                        System.out.println("Error: Invalid number");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        } while (!shouldStop);

        scanner.close();
    }

    private static void bookReservation(Scanner scanner) {
        System.out.println("Enter CheckIn Date: mm/dd/yyyy");
        String checkInDate = scanner.nextLine();
        System.out.println("Enter CheckOut Date: mm/dd/yyyy");
        String checkOutDate = scanner.nextLine();
        Date checkIn = new Date(checkInDate);
        Date checkOut = new Date(checkOutDate);
        Collection<IRoom> rooms = HotelResource.getInstance().findARoom(checkIn, checkOut);
        if (rooms.isEmpty()) {
            long weekInMs = 1000 * 60 * 60 * 24 * 7;
            System.out.println("No rooms available in this period");
            checkIn = new Date(checkIn.getTime() + weekInMs);
            checkOut = new Date(checkOut.getTime() + weekInMs);
            Collection<IRoom> roomsUpdated = HotelResource.getInstance().findARoom(checkIn, checkOut);
            if (roomsUpdated.isEmpty()) {
                return;
            }

            System.out.println("Alternatives are from " + checkIn + " to " + checkOut);
            for (IRoom entry : roomsUpdated) {
                System.out.println(entry.toString());
            }
        } else {
            for (IRoom entry : rooms) {
                System.out.println(entry.toString());
            }
        }

        System.out.println("Do you want to book a room? y/n");
        String roomInput;
        boolean correctRoom = false;
        while (!correctRoom) {
            roomInput = scanner.nextLine();
            if (!roomInput.equals("y") && !roomInput.equals("n")) {
                System.out.println("Please enter y (yes) or n (no)");
            } else if (roomInput.equals("n")) {
                return;
            } else {
                correctRoom = true;
            }
        }

        System.out.println("Do you hava an account with us? y/n");
        String accountInput;
        boolean correctAccount = false;
        while (!correctAccount) {
            accountInput = scanner.nextLine();
            if (!accountInput.equals("y") && !accountInput.equals("n")) {
                System.out.println("Please enter y (yes) or n (no)");
            } else if (accountInput.equals("n")) {
                createAccount(scanner);
                correctAccount = true;
            } else {
                correctAccount = true;
            }
        }

        System.out.println("Enter Email to book a room (format: name@domain.com)");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getInstance().getCustomer(email);
        if (customer == null) {
            System.out.println("Error: Unknown email");
            return;
        }

        System.out.println("What room number do you want to reserve?");
        String roomNr = scanner.nextLine();

        IRoom room = HotelResource.getInstance().getRoom(roomNr);
        if (room != null) {
            Reservation reservation = HotelResource.getInstance().bookARoom(customer, room, checkIn, checkOut);
            if (reservation != null) {
                System.out.println(reservation.toString());
            } else {
                System.out.println("Error: Room " + roomNr + " is not free for this period");
            }
        } else {
            System.out.println("Error: Invalid Room Number");
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine();
        System.out.println("First Name");
        String firstName = scanner.nextLine();
        System.out.println("Last Name");
        String lastName = scanner.nextLine();
        HotelResource.getInstance().createACustomer(email, firstName, lastName);
    }

    private static void printMainMenu() {
        System.out.println("---------------------------------------------------");
        System.out.println("1. Find and reserve Room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }
}
