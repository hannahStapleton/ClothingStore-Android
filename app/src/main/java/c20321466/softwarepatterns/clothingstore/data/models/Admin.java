package c20321466.softwarepatterns.clothingstore.data.models;

public class Admin extends User {
    //TODO Add additional attributes specific to Admin (if any)

    public Admin(String id, String name, String email, String password, String role) {
        super(id, name, email, password, role);
    }
}