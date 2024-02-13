package model.customer;

import java.util.regex.Pattern;

public class Customer {
    public String firstName;
    public String lastName;
    public String email;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        verifyEmailFormat();
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + " Last Name: " + lastName + " Email: " + email;
    }

    public void verifyEmailFormat() {
        String emailRegex = "^[A-Za-z0-9]+@[A-Za-z0-9]+.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email does not match expected format");
        }
    }
}
