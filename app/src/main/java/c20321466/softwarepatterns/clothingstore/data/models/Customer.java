package c20321466.softwarepatterns.clothingstore.data.models;

public class Customer extends User {
    private String shippingAddress;
    private String paymentMethod;

    public Customer() {
    }

    public Customer(String id, String name, String email, String password, String shippingAddress, String paymentMethod) {
        super(id, name, email, password);
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}