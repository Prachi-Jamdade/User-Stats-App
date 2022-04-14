package com.example.userstats.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.userstats.model.Offers;
import com.example.userstats.repository.OffersRepository;

import java.util.List;

public class OffersViewModel extends AndroidViewModel {

    public OffersRepository repository;
    public LiveData<List<Offers>> getAllOffers;

    public OffersViewModel(Application application) {
        super(application);

        repository = new OffersRepository(application);
        getAllOffers = repository.getAllOffers;
    }

    public void insertOffer(Offers offers) {
        repository.insertOffers(offers);
    }

    public void deleteOffer(int id) {
        repository.deleteOffers(id);
    }

    public void updateOffer(Offers offers) {
        repository.updateOffers(offers);
    }

    public void deleteAllOffer() {
        repository.deleteAllOffers();
    }
}
