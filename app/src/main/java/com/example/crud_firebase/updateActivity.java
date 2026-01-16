package com.example.crud_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class updateActivity extends AppCompatActivity {
    private String key;
    private EditText edtName, edtEmail;
    private Button btnAddData;  // Matches your XML ID
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize views - EXACTLY matching your XML IDs
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnAddData = findViewById(R.id.btnAddData);  // Your button ID

        db = FirebaseFirestore.getInstance();

        // Get the key from intent (passed from RecyclerView)
        key = getIntent().getStringExtra("key");
        if (key == null || key.isEmpty()) {
            Toast.makeText(this, "No user selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load existing user data automatically
        loadUserData();

        // Update button click listener
        btnAddData.setOnClickListener(v -> updateUser());
    }

    private void loadUserData() {
        db.collection("user").document(key)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("updateActivity", "Error: " + error.getMessage());
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            UserModel user = snapshot.toObject(UserModel.class);
                            if (user != null) {
                                // Load data into your EditTexts
                                edtName.setText(user.getName());
                                edtEmail.setText(user.getEmail());
                            }
                        }
                    }
                });
    }

    private void updateUser() {
        // Get input values
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading state
        btnAddData.setEnabled(false);
        btnAddData.setText("Updating...");

        // Create updated user object
        UserModel updatedUser = new UserModel(name, email, key);

        // Update in Firestore
        db.collection("user").document(key)
                .set(updatedUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(updateActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                            // Go back to main activity
                            finish();
                        } else {
                            Log.e("updateActivity", "Update failed: " + task.getException());
                            Toast.makeText(updateActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }

                        // Reset button
                        btnAddData.setEnabled(true);
                        btnAddData.setText("Update");
                    }
                });
    }
}