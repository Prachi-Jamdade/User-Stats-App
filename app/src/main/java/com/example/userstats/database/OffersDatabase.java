package com.example.userstats.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.userstats.model.Offers;
import com.example.userstats.dao.OffersDAO;

@Database(entities = {Offers.class}, version = 1, exportSchema = false)
public abstract class OffersDatabase extends RoomDatabase {
    public abstract OffersDAO offersDAO();

    public static OffersDatabase INSTANCE;

    public static OffersDatabase getDatabaseInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    OffersDatabase.class,
                    "Offers_Database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
