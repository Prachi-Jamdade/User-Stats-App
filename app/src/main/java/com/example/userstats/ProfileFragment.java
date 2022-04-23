package com.example.userstats;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.userstats.databinding.ActivityUpdateOfferBinding;
import com.example.userstats.model.Users;
import com.example.userstats.viewmodel.OffersViewModel;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ProfileFragment extends Fragment {

    OffersViewModel offersViewModel;
    Button clearAll, logoutBtn;
    MaterialTextView phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        clearAll = view.findViewById(R.id.clearAll);
        logoutBtn = view.findViewById(R.id.logOutBtn);
        phone = view.findViewById(R.id.phone);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users");

        ChildEventListener childEventListener  = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Map<String,String> map =(Map) snapshot.getValue();
                phone.setText("+91 - " + map.get("phoneNumber"));
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        };

        query.addChildEventListener(childEventListener);

        offersViewModel = ViewModelProviders.of(this).get(OffersViewModel.class);

        clearAll.setOnClickListener(v -> {
            offersViewModel.deleteAllOffer();
            Toast.makeText(getContext(), "All Offers are Cleared", Toast.LENGTH_SHORT).show();
        });

        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
        return view;
    }

}