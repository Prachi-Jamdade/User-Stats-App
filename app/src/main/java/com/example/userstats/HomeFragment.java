package com.example.userstats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.userstats.model.Users;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    MaterialTextView totalUsers, verifiedUsers;
    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalUsers = view.findViewById(R.id.totalUsers);
        verifiedUsers = view.findViewById(R.id.verifiedUsers);

        Query query = FirebaseDatabase.getInstance()
                .getReference("Users");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    Users user = data.getValue(Users.class);
                    counter++;
                }
                totalUsers.setText(Integer.toString(counter));
                verifiedUsers.setText(Integer.toString(counter));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        };

        query.addValueEventListener(valueEventListener);

        return view;
    }
}