package c20321466.softwarepatterns.clothingstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import c20321466.softwarepatterns.clothingstore.data.models.User;
import c20321466.softwarepatterns.clothingstore.ui.HomeActivity;
import c20321466.softwarepatterns.clothingstore.ui.viewmodels.ClothingViewModel;
import c20321466.softwarepatterns.clothingstore.ui.viewmodels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        Button btnRegister = findViewById(R.id.buttonRegister);
        Button btnLogin = findViewById(R.id.buttonLogin);

        // Initialize the ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btnRegister.setOnClickListener(v -> showRegistrationDialog());
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void showRegistrationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_registration, null);
        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.editTextName);
        EditText phoneNumberEditText = dialogView.findViewById(R.id.editTextPhoneNumber);
        EditText shippingAddressEditText = dialogView.findViewById(R.id.editTextShippingAddress);

        builder.setPositiveButton("Register", (dialog, which) -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String shippingAddress = shippingAddressEditText.getText().toString().trim();

            registerUser(email, password, name, phoneNumber, shippingAddress);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void registerUser(String email, String password, String name, String phoneNumber, String shippingAddress) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(shippingAddress)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String response;
        if (TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(shippingAddress)) {
            // Create admin account if phone number and shipping address are blank
           userViewModel.registerAdmin(email, password, name, "admin");
        } else {
            // Create customer account if phone number or shipping address is provided
            userViewModel.registerCustomer(email, password, name, phoneNumber, shippingAddress, "customer");
        }
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        userViewModel.loginUser(email, password);

        // Observe the userLiveData for successful login
        userViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                // Login successful
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startHomeActivity();
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

