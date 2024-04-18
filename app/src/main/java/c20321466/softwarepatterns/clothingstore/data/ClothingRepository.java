package c20321466.softwarepatterns.clothingstore.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import c20321466.softwarepatterns.clothingstore.data.models.ClothingItem;

public class ClothingRepository {

    private FirebaseFirestore firestore;
    private CollectionReference clothingItemsRef;

    public ClothingRepository() {
        firestore = FirebaseFirestore.getInstance();
        clothingItemsRef = firestore.collection("clothing_items");
    }

    public LiveData<List<ClothingItem>> getAllClothingItems() {
        MutableLiveData<List<ClothingItem>> clothingItemsLiveData = new MutableLiveData<>();
        clothingItemsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ClothingItem> clothingItems = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            ClothingItem clothingItem = document.toObject(ClothingItem.class);
                            if (clothingItem != null) {
                                clothingItems.add(clothingItem);
                            }
                        }
                        clothingItemsLiveData.setValue(clothingItems);
                    } else {
                        // Handle error
                    }
                });
        return clothingItemsLiveData;
    }

    public LiveData<List<ClothingItem>> searchClothingItems(String searchText, String filter) {
        MutableLiveData<List<ClothingItem>> filteredItemsLiveData = new MutableLiveData<>();
        Query query;
        List<ClothingItem> searchResults = new ArrayList<>();

        switch (filter.toLowerCase()) {
            case "title":
                query = clothingItemsRef.whereGreaterThanOrEqualTo("title", searchText)
                        .whereLessThanOrEqualTo("title", searchText + "\uf8ff");
                break;
            case "category":
                query = clothingItemsRef.whereEqualTo("category", searchText);
                break;
            case "manufacturer":
                query = clothingItemsRef.whereEqualTo("manufacturer", searchText);
                break;
            case "":
                Query titleQuery = clothingItemsRef.whereGreaterThanOrEqualTo("title", searchText)
                        .whereLessThanOrEqualTo("title", searchText + "\uf8ff");

                Query categoryQuery = clothingItemsRef.whereEqualTo("category", searchText);

                Query manufacturerQuery = clothingItemsRef.whereEqualTo("manufacturer", searchText);

                // Create a list of Tasks for each query
                List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                tasks.add(titleQuery.get());
                tasks.add(categoryQuery.get());
                tasks.add(manufacturerQuery.get());

                // Combine results from all queries
                Tasks.whenAllComplete(tasks).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (Task<QuerySnapshot> querySnapshotTask : tasks) {
                            QuerySnapshot querySnapshot = querySnapshotTask.getResult();
                            if (querySnapshot != null) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    ClothingItem clothingItem = document.toObject(ClothingItem.class);
                                    if (clothingItem != null) {
                                        searchResults.add(clothingItem);
                                    }
                                }
                            }
                        }
                        // Set search results to LiveData
                        filteredItemsLiveData.setValue(searchResults);
                    } else {
                        // Handle query failure
                        filteredItemsLiveData.setValue(null); // or handle error state
                    }
                });
                return filteredItemsLiveData;
            default:
                query = clothingItemsRef;
                break;
        }

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ClothingItem> filteredItems = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            ClothingItem clothingItem = document.toObject(ClothingItem.class);
                            if (clothingItem != null) {
                                filteredItems.add(clothingItem);
                            }
                        }
                        filteredItemsLiveData.setValue(filteredItems);
                    } else {
                        filteredItemsLiveData.setValue(null);
                    }
                });

        return filteredItemsLiveData;
    }

    public LiveData<Boolean> addClothingItem(ClothingItem newItem) {
        MutableLiveData<Boolean> operationResult = new MutableLiveData<>();

        clothingItemsRef.add(newItem)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        operationResult.setValue(true); // Item added successfully
                    } else {
                        operationResult.setValue(false); // Failed to add item
                    }
                });

        return operationResult;
    }

    public LiveData<Boolean> updateClothingItem(String itemId, ClothingItem updatedItem) {
        MutableLiveData<Boolean> operationResultLiveData = new MutableLiveData<>();

        clothingItemsRef.document(itemId)
                .set(updatedItem)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        operationResultLiveData.setValue(true); // Operation successful
                    } else {
                        operationResultLiveData.setValue(false); // Operation failed
                    }
                });

        return operationResultLiveData;
    }

    public LiveData<Boolean> deleteClothingItem(String itemId) {
        MutableLiveData<Boolean> operationResultLiveData = new MutableLiveData<>();

        clothingItemsRef.document(itemId)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        operationResultLiveData.setValue(true); // Operation successful
                    } else {
                        operationResultLiveData.setValue(false); // Operation failed
                    }
                });

        return operationResultLiveData;
    }
}