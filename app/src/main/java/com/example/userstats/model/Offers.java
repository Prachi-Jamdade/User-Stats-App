package com.example.userstats.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Offers_Database")
public class Offers {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "offer_name")
    public String offerName;

    @ColumnInfo(name = "company")
    public String company;

    @ColumnInfo(name = "offer_desc")
    public String offerDesc;
}
