package com.example.userstats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userstats.databinding.ActivityUpdateOfferBinding;
import com.example.userstats.model.Offers;
import com.example.userstats.viewmodel.OffersViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UpdateOfferActivity extends AppCompatActivity {

    ActivityUpdateOfferBinding binding;
    String sOfferName, sCompanyName, sOfferDesc;
    int iID;
    OffersViewModel offersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iID = getIntent().getIntExtra("id", 0);
        sOfferName = getIntent().getStringExtra("offer");
        sCompanyName = getIntent().getStringExtra("company");
        sOfferDesc = getIntent().getStringExtra("desc");

        binding.updateOfferName.getEditText().setText(sOfferName);
        binding.updateCompanyName.getEditText().setText(sCompanyName);
        binding.updateOfferDesc.getEditText().setText(sOfferDesc);

        offersViewModel = ViewModelProviders.of(this).get(OffersViewModel.class);

        binding.updateOffersBtn.setOnClickListener(v -> {
            String offerName = binding.updateOfferName.getEditText().getText().toString().trim();
            String company = binding.updateCompanyName.getEditText().getText().toString().trim();
            String offerDesc = binding.updateOfferDesc.getEditText().getText().toString().trim();

            updateNote(offerName, company, offerDesc);
        });

    }

    public void updateNote(String offerName, String company, String offerDesc) {
        if(offerName.matches("") || offerName.isEmpty()) {
            Toast.makeText(this, "You did not enter an offer name" + " : " + offerName, Toast.LENGTH_SHORT).show();
        }
        else if(company.matches("")) {
            Toast.makeText(this, "You did not enter a Company name", Toast.LENGTH_SHORT).show();
        }
        else if(offerDesc.matches("")) {
            Toast.makeText(this, "Enter desc", Toast.LENGTH_SHORT).show();
        }
        else {
            Offers updateOffer = new Offers();
            updateOffer.id = iID;
            updateOffer.offerName = offerName;
            updateOffer.company = company;
            updateOffer.offerDesc = offerDesc;

            offersViewModel.updateOffer(updateOffer);

            Toast.makeText(this, "Offer Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.ic_delete) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateOfferActivity.this,
                    R.style.BottomSheetStyle);
            View view = LayoutInflater.from(UpdateOfferActivity.this).inflate(R.layout.delete_bottom,
                    (LinearLayout) findViewById(R.id.bottom_sheet));

            bottomSheetDialog.setContentView(view);

            TextView yes, no;
            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                offersViewModel.deleteOffer(iID);
                finish();
            });

            no.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
            });
            bottomSheetDialog.show();
        }
        return true;
    }
}