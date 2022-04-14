package com.example.userstats;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.userstats.adapter.OffersAdapter;
import com.example.userstats.model.Offers;
import com.example.userstats.viewmodel.OffersViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OffersFragment extends Fragment {

    FloatingActionButton newOffersBtn;
    OffersViewModel offersViewModel;
    RecyclerView offersRV;
    OffersAdapter offersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        newOffersBtn = view.findViewById(R.id.newOffersBtn);

        offersRV = view.findViewById(R.id.offersRV);

        offersViewModel = ViewModelProviders.of(this).get(OffersViewModel.class);

        newOffersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InsertOfferActivity.class);
            startActivity(intent);
        });



        offersViewModel.getAllOffers.observe(getViewLifecycleOwner(), offers -> {
            offersRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
            offersAdapter = new OffersAdapter(getContext(), offers);
            offersRV.setAdapter(offersAdapter);
        });

        return view;
    }

}