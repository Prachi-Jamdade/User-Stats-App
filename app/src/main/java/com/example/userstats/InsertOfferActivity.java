package com.example.userstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.userstats.databinding.ActivityInsertOfferBinding;
import com.example.userstats.model.Offers;
import com.example.userstats.viewmodel.OffersViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class InsertOfferActivity extends AppCompatActivity {

    ActivityInsertOfferBinding binding;
    String name, company, desc;
    OffersViewModel offersViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        offersViewModel = ViewModelProviders.of(this).get(OffersViewModel.class);

        binding.doneOffersBtn.setOnClickListener(v -> {
            // the main problem is here
            name = binding.offerNameIF.getEditText().getText().toString().trim();
            company = binding.companyIF.getEditText().getText().toString().trim();
            desc = binding.offerDescIF.getEditText().getText().toString().trim();

            createOffers(name, company, desc);
        });
    }

     public void createOffers(String name, String company, String desc) {

        if(name.matches("") || name.isEmpty()) {
            Toast.makeText(this, "You did not enter an offer name" + " : " + name, Toast.LENGTH_SHORT).show();
        }
        else if(company.matches("")) {
            Toast.makeText(this, "You did not enter a Company name", Toast.LENGTH_SHORT).show();
        }
        else if(desc.matches("")) {
            Toast.makeText(this, "Enter desc", Toast.LENGTH_SHORT).show();
        }
        else {
            Offers offers = new Offers();
            offers.offerName = name;
            offers.company = company;
            offers.offerDesc = desc;

            offersViewModel.insertOffer(offers);

            Toast.makeText(this, "Offer Created Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}