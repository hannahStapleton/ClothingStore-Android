package c20321466.softwarepatterns.clothingstore.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;

public class FirestoreClothingItemDao extends ClothingRepository {
    private FirebaseFirestore firestore;
    private CollectionReference clothingItemsRef;

    public FirestoreClothingItemDao(FirebaseFirestore firestore) {
        this.firestore = firestore;
        this.clothingItemsRef = firestore.collection("clothing_items");
    }

    @Override
    public void addClothingItem(ClothingItem item, OnCompleteListener<DocumentReference> listener) {
        clothingItemsRef.add(item)
                .addOnCompleteListener(listener);
    }

    @Override
    public void getClothingItemById(String itemId, OnCompleteListener<DocumentSnapshot> listener) {
        clothingItemsRef.document(itemId)
                .get()
                .addOnCompleteListener(listener);
    }

    @Override
    public void searchClothingItemsByCategory(String categoryQuery, OnCompleteListener<QuerySnapshot> listener) {
        Query query = clothingItemsRef
                .whereGreaterThanOrEqualTo("category", categoryQuery)
                .whereLessThanOrEqualTo("category", categoryQuery + "\uf8ff");

        query.get()
                .addOnCompleteListener(listener);
    }

    @Override
    public void searchClothingItemsByManufacturer(String manufacturerQuery, OnCompleteListener<QuerySnapshot> listener) {
        Query query = clothingItemsRef
                .whereGreaterThanOrEqualTo("manufacturer", manufacturerQuery)
                .whereLessThanOrEqualTo("manufacturer", manufacturerQuery + "\uf8ff");

        query.get()
                .addOnCompleteListener(listener);
    }

    @Override
    public void searchClothingItemsByTitle(String titleQuery, OnCompleteListener<QuerySnapshot> listener) {
        Query query = clothingItemsRef
                .orderBy("title")
                .startAt(titleQuery)
                .endAt(titleQuery + "\uf8ff");

        query.get()
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
