package c20321466.softwarepatterns.clothingstore.data;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;

public interface ClothingItemDao {
    void addClothingItem(ClothingItem item, OnCompleteListener<DocumentReference> listener);
    void getClothingItemById(String itemId, OnCompleteListener<DocumentSnapshot> listener);
    void updateClothingItem(String itemId, ClothingItem updatedItem, OnCompleteListener<Void> listener);
    void updateStockLevel(String itemId, int newStockLevel, OnCompleteListener<Void> listener);
    void deleteClothingItem(String itemId, OnCompleteListener<Void> listener);
    //TODO Additional methods as needed for querying
}
