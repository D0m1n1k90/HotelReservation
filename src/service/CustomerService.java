package service;

import model.customer.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CustomerService {

    private static final CustomerService reference = new CustomerService();
    private final Set<Customer> mCustomers = new HashSet<>();

    public void addCustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        mCustomers.add(newCustomer);
    }

    public Customer getCustomer(String customerEmail) {
        for(Customer entry : mCustomers) {
            if (Objects.equals(entry.email, customerEmail)) {
                return entry;
            }
        }

        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return mCustomers;
    }

    public static CustomerService getInstance() {
        return reference;
    }
}
