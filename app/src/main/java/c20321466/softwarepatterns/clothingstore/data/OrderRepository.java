package c20321466.softwarepatterns.clothingstore.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import c20321466.softwarepatterns.clothingstore.data.models.Order;

public class OrderRepository {

    private static final String TAG = "OrderRepository";

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<List<Order>> ordersLiveData;

    public OrderRepository() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ordersLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Order>> getAllOrders() {
        loadOrders();
        return ordersLiveData;
    }

    private void loadOrders() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            CollectionReference ordersRef = firestore.collection("orders");

            ordersRef.whereEqualTo("customerId", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Order> orders = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Order order = document.toObject(Order.class);
                                if (order != null) {
                                    orders.add(order);
                                }
                            }
                            ordersLiveData.setValue(orders);
                        } else {
                            Log.e(TAG, "Error getting orders: ", task.getException());
                            ordersLiveData.setValue(null);
                        }
                    });
        } else {
            Log.e(TAG, "User is not authenticated.");
            ordersLiveData.setValue(null);
        }
    }

    public void saveOrder(Order order) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ordersRef = firestore.collection("orders");
        // Generate a new document ID for the order
        String orderId = ordersRef.document().getId();
        // Set the order ID for the order object
        order.setId(orderId);
        // Add the order to the Firestore collection
        ordersRef.document(orderId)
                .set(order)
                .addOnSuccessListener(aVoid -> {
                    // Order saved successfully
                    Log.d(TAG, "Order saved successfully: " + orderId);
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.e(TAG, "Error saving order: " + e.getMessage());
                });
    }

    public void updateOrder(Order order, OnCompleteListener<Void> listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ordersRef = firestore.collection("orders");

        ordersRef.document(order.getId())
                .set(order)
                .addOnCompleteListener(listener);
    }

    public void deleteOrder(String orderId, OnCompleteListener<Void> listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ordersRef = firestore.collection("orders");

        ordersRef.document(orderId)
                .delete()
                .addOnCompleteListener(listener);
    }
}

