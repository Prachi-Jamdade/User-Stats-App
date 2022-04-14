package com.example.userstats.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.userstats.dao.OffersDAO;
import com.example.userstats.database.OffersDatabase;
import com.example.userstats.model.Offers;

import java.util.List;

public class OffersRepository {
    public OffersDAO offersDAO;
    public LiveData<List<Offers>> getAllOffers;

    public OffersRepository(Application application) {
        OffersDatabase database = OffersDatabase.getDatabaseInstance(application);

        offersDAO = database.offersDAO();
        getAllOffers = offersDAO.getAllOffers();
    }

    public void insertOffers(Offers offers) {
        offersDAO.insertOffers(offers);
    }

    public void deleteOffers(int id) {
        offersDAO.deleteOffers(id);
    }

    public void updateOffers(Offers offers) {
        offersDAO.updateOffers(offers);
    }

    public void deleteAllOffers() {
        offersDAO.deleteAllOffers();
    }

}
