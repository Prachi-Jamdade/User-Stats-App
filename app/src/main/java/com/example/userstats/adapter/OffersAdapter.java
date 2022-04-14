package com.example.userstats.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userstats.R;
import com.example.userstats.UpdateOfferActivity;
import com.example.userstats.model.Offers;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {
    Context context;
    List<Offers> offers;

//    public OffersAdapter(FragmentActivity activity, List<Offers> offers) {
//        this.activity = activity;
//        this.offers = offers;
//    }

    public OffersAdapter(Context context, List<Offers> offers) {
        this.context = context;
        this.offers = offers;
    }


    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OffersViewHolder(LayoutInflater.from(context).inflate(R.layout.item_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {

        Offers offer = offers.get(position);

        holder.name.setText(offer.offerName);
        holder.company.setText(offer.company);
        holder.desc.setText(offer.offerDesc);

        holder.itemView.setOnClickListener(v -> {
            Intent i  = new Intent(context, UpdateOfferActivity.class);
            i.putExtra("id", offer.id);
            i.putExtra("offer", offer.offerName);
            i.putExtra("company", offer.company);
            i.putExtra("desc", offer.offerDesc);

            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return offers.size()    ;
    }

    static class OffersViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView name, company, desc;

        public OffersViewHolder(View itemView) {
            super(itemView);
            name = (MaterialTextView) itemView.findViewById(R.id.offerName);
            company = (MaterialTextView) itemView.findViewById(R.id.companyName);
            desc = (MaterialTextView) itemView.findViewById(R.id.offerDesc);
        }
    }
}
