package service;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.*;

public class ReservationService {

    private static final ReservationService reference = new ReservationService();
    private final Set<Reservation> mReservations = new HashSet<>();
    private final Set<IRoom> mRooms = new HashSet<>();

    public void addRoom(IRoom room) {
        if (!mRooms.add(room)) {
            System.out.println("Error: Room Number already in use");
        }
    }

    public IRoom getARoom(String roomId) {
        for (IRoom entry : mRooms) {
            if (Objects.equals(entry.getRoomNumber(), roomId)) {
                return entry;
            }
        }
        return null;
    }

    public Collection<IRoom> getAllRooms() {
        return mRooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> freeRooms = findRooms(checkInDate, checkOutDate);

        for (IRoom roomEntry : freeRooms) {
            if (Objects.equals(roomEntry.getRoomNumber(), room.getRoomNumber())) {
                Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
                mReservations.add(reservation);
                return reservation;
            }
        }

        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> validRooms = new HashSet<>(mRooms);

        for (Reservation entry : mReservations) {
            if (checkInDate.after(entry.checkOutDate) || checkOutDate.before(entry.checkInDate)) {
                continue;
            } else {
                validRooms.remove(entry.room);
            }
        }

        return validRooms;
    }

    public Collection<Reservation> getCustomersReservations(String email) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation entry : mReservations) {
            if (Objects.equals(entry.customer.email, email)) {
                reservations.add(entry);
            }
        }
        return reservations;
    }

    public void printAllReservations() {
        for (Reservation entry : mReservations) {
            System.out.println(entry.toString());
        }
    }

    public static ReservationService getInstance() {
        return reference;
    }
}
