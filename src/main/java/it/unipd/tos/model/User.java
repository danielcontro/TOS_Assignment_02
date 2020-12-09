////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class User {

    private final String firstName;
    private final String lastName;
    private int age;

    public User(final String firstName, final String lastName, final int age) throws IllegalArgumentException {
        if (firstName == null || firstName.length() <= 0) {
            throw new IllegalArgumentException("The first name cannot be null or empty");
        } else if (lastName == null || lastName.length() <= 0) {
            throw new IllegalArgumentException("The last name cannot be null or empty");
        } else if (age < 0) {
            throw new IllegalArgumentException("The age cannot be negative");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isUnderage() {
        return (getAge() < 18 ? true : false);
    }

}
