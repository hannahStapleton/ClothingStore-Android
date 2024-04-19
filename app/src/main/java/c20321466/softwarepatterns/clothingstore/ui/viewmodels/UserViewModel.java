package c20321466.softwarepatterns.clothingstore.ui.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import c20321466.softwarepatterns.clothingstore.data.models.Admin;
import c20321466.softwarepatterns.clothingstore.data.models.Customer;
import c20321466.softwarepatterns.clothingstore.data.models.User;

public class UserViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private MutableLiveData<Boolean> isAdminLiveData = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();


    public UserViewModel() {
        mAuth = FirebaseAuth.getInstance();
        userDBRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getIsAdminLiveData(){
        return isAdminLiveData;
    }

    public void registerAdmin(String email, String password, String name, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            Admin admin = new Admin(firebaseUser.getUid(), name, email, password, role);
                            saveUserToFirestore(admin);
                        }
                    } else {
                        // Handle registration failure
                    }
                });
    }

    public void registerCustomer(String email, String password, String name, String phoneNumber, String shippingAddress, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            Customer customer = new Customer(firebaseUser.getUid(), name, email, password, phoneNumber, shippingAddress, role);
                            saveUserToFirestore(customer);
                        }
                    } else {
                        // Handle registration failure
                    }
                });
    }

    private void saveUserToFirestore(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        // Create a document with the user's ID
        usersRef.document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User saved to Firestore"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving user: " + e.getMessage()));
    }
    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            checkUserType(user.getUid());
                        }
                        userLiveData.setValue(mAuth.getCurrentUser());
                    } else {
                        // Handle login failure
                        userLiveData.setValue(null); // Indicate that login failed
                    }
                });
    }

    private void checkUserType(String userId) {
        userDBRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userObj = snapshot.getValue(User.class);
                if (userObj != null) {
                    String userType = userObj.getRole();
                    boolean isAdmin = userType != null && userType.equalsIgnoreCase("admin");
                    isAdminLiveData.setValue(isAdmin);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database read error
                Log.e("UserViewModel", "Database read error: " + error.getMessage());
            }
        });
    }
}
