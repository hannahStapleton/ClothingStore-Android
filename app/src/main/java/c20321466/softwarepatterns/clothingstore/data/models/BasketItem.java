package c20321466.softwarepatterns.clothingstore.data.models;

public class BasketItem {
    private String itemId;
    private int quantity;
    private double pricePerItem;

    public BasketItem() {
    }

    public BasketItem(String itemId, int quantity, double pricePerItem) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }
}