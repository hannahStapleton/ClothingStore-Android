package c20321466.softwarepatterns.clothingstore.data.models;

import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private List<String> clothingItemIds;
    private Date orderDate;
    private double totalAmount;

    public Order() {
    }

    public Order(String id, String customerId, List<String> clothingItemIds, Date orderDate, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.clothingItemIds = clothingItemIds;
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

    public List<String> getClothingItemIds() {
        return clothingItemIds;
    }

    public void setClothingItemIds(List<String> clothingItemIds) {
        this.clothingItemIds = clothingItemIds;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}