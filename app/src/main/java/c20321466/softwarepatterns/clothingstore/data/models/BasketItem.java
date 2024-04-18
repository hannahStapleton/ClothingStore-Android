package c20321466.softwarepatterns.clothingstore.data.models;

public class BasketItem {
    private String userId;
    private ClothingItem clothingItem;
    private int quantity;

    public BasketItem() {
    }

    public BasketItem(ClothingItem clothingItem, String userId, int quantity) {
        this.clothingItem = clothingItem;
        this.userId = userId;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ClothingItem getClothingItem() {
        return clothingItem;
    }
}