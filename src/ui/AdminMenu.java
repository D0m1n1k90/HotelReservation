package ui;

import api.AdminResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;

import java.util.*;

public class AdminMenu {
    public static void openAdminMenu(Scanner scanner) {
        boolean shouldStop = false;
        do {
            try {
                printAdminMenu();
                int userInput = Integer.parseInt(scanner.nextLine());
                System.out.println("User Input: " + userInput);

                if (userInput == 1) {
                    Collection<Customer> customers = AdminResource.getInstance().getAllCustomers();
                    for (Customer entry : customers) {
                        System.out.println(entry.toString());
                    }
                } else if (userInput == 2) {
                    Collection<IRoom> rooms = AdminResource.getInstance().getAllRooms();
                    for (IRoom entry : rooms) {
                        System.out.println(entry.toString());
                    }
                } else if (userInput == 3) {
                    AdminResource.getInstance().displayAllReservations();
                } else if (userInput == 4) {
                    addRoom(scanner);
                } else if (userInput == 5) {
                    shouldStop = true;
                } else {
                    System.out.println("Error: Invalid number");
                }
            } catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        } while (!shouldStop);
    }

    private static void printAdminMenu() {
        System.out.println("\n---------------------------------------------------");
        System.out.println("1. See all Customer");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    private static void addRoom(Scanner scanner) {
        boolean shouldContinue = false;
        List<IRoom> rooms = new ArrayList<>();

        while (!shouldContinue) {
            System.out.println("Enter room number");
            String roomNumber = scanner.nextLine();
            try {
                Integer.parseInt(roomNumber);
            } catch (NumberFormatException e) {
                System.out.println("Error: Value was not a number");
                return;
            }
            Collection<IRoom> existingRooms =  AdminResource.getInstance().getAllRooms();
            for (IRoom entry : existingRooms) {
                if(Objects.equals(entry.getRoomNumber(), roomNumber)) {
                    System.out.println("Error: Room number already exists");
                    return;
                }
            }

            System.out.println("Enter price per night");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            int type;
            RoomType roomType = null;
            boolean correctType = false;
            while (!correctType) {
                try {
                    type = Integer.parseInt(scanner.nextLine());
                    if (type == 1) {
                        roomType = RoomType.SINGLE;
                        correctType = true;
                    } else if (type == 2) {
                        roomType = RoomType.DOUBLE;
                        correctType = true;
                    } else {
                        System.out.println("Please enter 1 (single) or 2 (double)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter 1 (single) or 2 (double)");
                }
            }

            boolean rightInput = false;
            String selection = null;
            System.out.println("Would you like to add another room y/n");
            while (!rightInput) {
                String choice = scanner.nextLine();
                if (!"y".equals(choice) && !"n".equals(choice)) {
                    System.out.println("Please enter y (yes) or n (no)");
                } else {
                    rightInput = true;
                    selection = choice;
                }
            }

            if (Objects.equals(selection, "n")) {
                shouldContinue = true;
            }

            rooms.add(new Room(roomNumber, price, roomType));
            AdminResource.getInstance().addRooms(rooms);
            rooms.clear();
        }
    }
}
