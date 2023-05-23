package com.example.posdiasthadjayakediri.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Barang")
public class Cart {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "imageid")
    public String imageid;

    @ColumnInfo(name = "price")
    public String price;
}
