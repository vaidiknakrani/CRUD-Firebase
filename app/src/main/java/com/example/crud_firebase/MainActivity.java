//package com.example.crud_firebase;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    Button btnAddData;
//
//    EditText edtName , edtEmail;
//    FirebaseFirestore db;// creating an instance of the firebase firestore
//    ArrayList<UserModel> allDataList;
//    RecyclerView rcvData;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        btnAddData = findViewById(R.id.btnAddData);
//        edtEmail=findViewById(R.id.edtEmail);
//        edtName=findViewById(R.id.edtName);
//        rcvData=findViewById(R.id.rcvData);
//
//        db= FirebaseFirestore.getInstance();
//
//        btnAddData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 String name = edtName.getText().toString();
//                 String email = edtEmail.getText().toString();
//
//                 UserModel data = new UserModel(email , name);
//                 db.collection("user").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                     @Override
//                     public void onComplete(@NonNull Task<Void> task) {
//                         if(task.isSuccessful())
//                         {
//                             Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
//                         }
//                         else
//                         {
//                             Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                         }
//                     }
//                 });
//
//            }
//        });
//    }
//
//    // read
//    public void getAllData()
//    {
//        allDataList = new ArrayList<>();
//        allDataList.clear();
//        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error == null)
//                {
//                    List<UserModel> data = value.toObjects(UserModel.class);
//                    allDataList.addAll(data);// because we are adding list we have to write addAll
//                    rcvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    rcvData.setAdapter(new ReadDataAdapter(MainActivity.this,allDataList));
//                }
//            }
//        });
//    }
//
//}
package com.example.crud_firebase;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnAddData;
    EditText edtName, edtEmail;
    FirebaseFirestore db;
    ArrayList<UserModel> allDataList;  // Initialize once here
    RecyclerView rcvData;
    ReadDataAdapter adapter;  // Reuse single adapter instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddData = findViewById(R.id.btnAddData);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        rcvData = findViewById(R.id.rcvData);

        db = FirebaseFirestore.getInstance();

        // Initialize list and adapter ONCE
        allDataList = new ArrayList<>();
        rcvData.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReadDataAdapter(this, allDataList);
        rcvData.setAdapter(adapter);  // Set adapter immediately[web:29]

        // Start real-time listening
        getAllData();

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String uid = db.collection("user").document().getId();
                UserModel data = new UserModel(email, name ,uid);
                db.collection("user").document(uid).set(data).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                        edtName.setText("");  // Clear inputs
                        edtEmail.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Read data with real-time updates
    public void getAllData() {
        db.collection("user").addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (value != null) {
                allDataList.clear();  // Clear old data first[web:28]
                allDataList.addAll(value.toObjects(UserModel.class));
                adapter.notifyDataSetChanged();  // Update existing adapter[web:24]
            }
        });
    }
}
