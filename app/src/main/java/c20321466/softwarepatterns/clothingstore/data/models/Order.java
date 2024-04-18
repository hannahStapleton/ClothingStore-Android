package c20321466.softwarepatterns.clothingstore.data.models;

import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private List<BasketItem> basketItems;
    private String orderDate;
    private double totalAmount;

    public Order() {
    }

    public Order(String customerId, List<BasketItem> basketItems, String orderDate, double totalAmount) {
        this.customerId = customerId;
        this.basketItems = basketItems;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}