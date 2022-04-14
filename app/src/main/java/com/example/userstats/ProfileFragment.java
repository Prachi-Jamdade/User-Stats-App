package com.example.userstats;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.userstats.databinding.ActivityUpdateOfferBinding;
import com.example.userstats.viewmodel.OffersViewModel;
import com.google.android.material.textview.MaterialTextView;

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