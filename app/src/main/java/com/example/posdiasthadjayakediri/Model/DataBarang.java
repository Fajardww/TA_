package com.example.posdiasthadjayakediri.Model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
//import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

public class DataBarang {

    private int id;
    private int kode_barang;
    private String nama_barang;
    private int harga_beli;
    private int harga_jual;
    private int stok_barang;
    private int kategori_barang;
    private String foto_barang;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(int kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(int harga_beli) {
        this.harga_beli = harga_beli;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getStok_barang() {
        return stok_barang;
    }

    public void setStok_barang(int stok_barang) {
        this.stok_barang = stok_barang;
    }

    public int getKategori_barang() {
        return kategori_barang;
    }

    public void setKategori_barang(int kategori_barang) {
        this.kategori_barang = kategori_barang;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }

//    @Override
//    public String toString() {
//        return "DataBarang{" +
//                "id='" + id + '\'' +
//                ", nama_barang='" + nama_barang + '\'' +
//                ", harga_jual=" + harga_jual +
////                ", isAvailable=" + isAvailable +
//                ", foto_barang='" + foto_barang + '\'' +
//                '}';
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DataBarang dataBarang = (DataBarang) o;
//        return Double.compare(dataBarang.getHarga_jual(), getHarga_jual()) == 0 &&
////                isAvailable() == product.isAvailable() &&
//                getId().equals(dataBarang.getId()) &&
//                getNama_barang().equals(dataBarang.getNama_barang()) &&
//                getFoto_barang().equals(dataBarang.getFoto_barang());
//    }

//    public static DiffUtil.ItemCallback<DataBarang> itemCallback = new DiffUtil.ItemCallback<DataBarang>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull DataBarang oldItem, @NonNull DataBarang newItem) {
//            return oldItem.getId().equals(newItem.getId());
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull DataBarang oldItem, @NonNull DataBarang newItem) {
//            return oldItem.equals(newItem);
//        }
//
////        @Override
////        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
////            return oldItem.getId().equals(newItem.getId());
////        }
////
////        @Override
////        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
////            return oldItem.equals(newItem);
////        }
//    };

//    @BindingAdapter("android:productImage")
//    public static void loadImage(ImageView imageView, String imageUrl) {
//        Glide.with(imageView)
//                .load(imageUrl)
//                .fitCenter()
//                .into(imageView);
//    }
}
