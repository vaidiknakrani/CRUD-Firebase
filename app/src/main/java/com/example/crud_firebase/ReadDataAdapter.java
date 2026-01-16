package com.example.crud_firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReadDataAdapter extends RecyclerView.Adapter<ReadDataAdapter.ReadDataHolder> {
    MainActivity mainActivity;
    ArrayList<UserModel> allDataList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ReadDataAdapter(MainActivity mainActivity, ArrayList<UserModel> allDataList) {
        this.allDataList = allDataList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ReadDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.item_data, parent, false);
        return new ReadDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadDataHolder holder, int position) {
        UserModel currentUser = allDataList.get(position);

        holder.itemName.setText(currentUser.getName());
        holder.itemEmail.setText(currentUser.getEmail());

        // Item click to update
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(mainActivity, updateActivity.class);
            i.putExtra("key", currentUser.getUid());
            mainActivity.startActivity(i);
        });

        // Update button click
        holder.btnUpdate.setOnClickListener(v -> {
            Intent i = new Intent(mainActivity, updateActivity.class);
            i.putExtra("key", currentUser.getUid());
            mainActivity.startActivity(i);
        });

        // Delete button click - Fixed position issue
        holder.btnDelete.setOnClickListener(v -> {
            final int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            builder.setMessage("Are you sure you want to delete this user?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                String uid = allDataList.get(currentPosition).getUid();
                deleteUser(uid, currentPosition);
            });

            builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        });
    }

    private void deleteUser(String uid, int position) {
        db.collection("user").document(uid).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Remove from local list and notify adapter
                        allDataList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, allDataList.size());
                        Toast.makeText(mainActivity, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mainActivity, "Error deleting user", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return allDataList.size();
    }

    // ViewHolder class
    static class ReadDataHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemEmail;
        Button btnUpdate, btnDelete;

        public ReadDataHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemEmail = itemView.findViewById(R.id.itemEmail);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Method to update data from MainActivity after successful delete
    public void updateData(ArrayList<UserModel> newData) {
        this.allDataList.clear();
        this.allDataList.addAll(newData);
        notifyDataSetChanged();
    }
}