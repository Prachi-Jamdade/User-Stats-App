package com.example.userstats.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.userstats.model.Offers;

import java.util.List;

@Dao
public interface OffersDAO {
    @Query("SELECT * FROM Offers_Database")
    LiveData<List<Offers>> getAllOffers();

    @Query("DELETE FROM Offers_Database")
    void deleteAllOffers();

    @Insert
    void insertOffers(Offers... offers);

    @Query("DELETE FROM Offers_Database WHERE id=:id")
    void deleteOffers(int id);

    @Update
    void updateOffers(Offers offers);

}
