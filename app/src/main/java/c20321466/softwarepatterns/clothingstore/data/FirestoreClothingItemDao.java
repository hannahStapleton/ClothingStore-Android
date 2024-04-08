package c20321466.softwarepatterns.clothingstore.data;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;

public class FirestoreClothingItemDao implements ClothingItemDao {
    private FirebaseFirestore firestore;

    public FirestoreClothingItemDao(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void addClothingItem(ClothingItem item, OnCompleteListener<DocumentReference> listener) {
        firestore.collection("clothing_items")
                .add(item)
                .addOnCompleteListener(listener);
    }

    @Override
    public void getClothingItemById(String itemId, OnCompleteListener<DocumentSnapshot> listener) {
        firestore.collection("clothing_items")
                .document(itemId)
                .get()
                .addOnCompleteListener(listener);
    }

    @Override
    public void updateClothingItem(String itemId, ClothingItem updatedItem, OnCompleteListener<Void> listener) {
        firestore.collection("clothing_items")
                .document(itemId)
                .set(updatedItem)
                .addOnCompleteListener(listener);
    }

    @Override
    public void updateStockLevel(String itemId, int newStockLevel, OnCompleteListener<Void> listener) {
        firestore.collection("clothing_items")
                .document(itemId)
                .update("stockLevel", newStockLevel)
                .addOnCompleteListener(listener);
    }

    @Override
    public void deleteClothingItem(String itemId, OnCompleteListener<Void> listener) {
        firestore.collection("clothing_items")
                .document(itemId)
                .delete()
                .addOnCompleteListener(listener);
    }

    // Additional methods for querying can be implemented here
}

