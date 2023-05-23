package com.example.posdiasthadjayakediri.Room;

import androidx.room.ColumnInfo;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

public interface CartKu {
    @Insert
    public void addToCart(Cart cart);

    @Query("SELECT * FROM Barang")
    public List<Cart> getData();

    @Query("SELECT EXISTS (SELECT 1 FROM Barang WHERE id=:id)")
    public int isAddToCart(int id);

    @Query("select COUNT (*) from Barang")
    int countCart();

    @Query("DELETE FROM Barang WHERE id=:id ")
    int deleteItem(int id);
}
