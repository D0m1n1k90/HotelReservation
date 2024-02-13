package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.util.Date;

public class Reservation {
    public Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return customer.firstName + " " + customer.lastName + "\nRoom: " + room.getRoomNumber() + " - " + room.getRoomType() + " bed\nPrice: $" + room.getRoomPrice() + " price per night\nCheckin Date: " + checkInDate + "\nCheckout Date: " + checkOutDate;
    }
}
