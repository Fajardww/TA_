package com.example.posdiasthadjayakediri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.posdiasthadjayakediri.CartActivity;
import com.example.posdiasthadjayakediri.Model.DataBarang;
import com.example.posdiasthadjayakediri.R;

import java.util.List;

public class AdapterDataBarangKasir extends RecyclerView.Adapter<AdapterDataBarangKasir.HolderData>{
    private Context kasir;
    private List<DataBarang> dataBarangKasirList;
    private List<DataBarang> items;

//    ShopInterface shopInterface;

    public AdapterDataBarangKasir(Context kasir, List<DataBarang> dataBarangKasirList) {
        this.kasir = kasir;
        this.dataBarangKasirList = dataBarangKasirList;
        this.items = items;
    }

    public void setFilteredList(List<DataBarang> filteredList){
        this.dataBarangKasirList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_kasir, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataBarang dbKasir = dataBarangKasirList.get(position);

        holder.tvId.setText(String.valueOf(dbKasir.getId()));
        holder.tvNamaBarang.setText(dbKasir.getNama_barang());
        holder.tvHargaJual.setText(String.valueOf(dbKasir.getHarga_jual()));
//        holder.tvStokBarang.setText(String.valueOf(dbKasir.getStok_barang()));
        Glide.with(kasir).load(dbKasir.getFoto_barang()).thumbnail(0.5f)
                .into(holder.tvImage);

//        holder.ib_add_cart.setOnClickListener(v -> {
//            // Add the item to the cart
//            DataBarang dataBarang = dataBarangKasirList.get(position);
////            DataBarang selectedItem = holder.tvId.setText(String.valueOf(dbKasir.getId()));
//            addToCart(selectedItem);
//            // You can access the item using `items.get(position)`
//            // Perform the necessary logic here to add it to the cart
//        });
    }

//    private void addToCart(DataBarang selectedItem) {
//        // Perform logic to add the item to the cart
//        dataBarangKasirList.add(selectedItem);
////        notifyDataSetChanged();
//        Toast.makeText(Item.getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
//        // You can also update UI or perform any other operations based on your app requirements
//        // For example, you can update the cart badge count, display a confirmation message, etc.
//    }

    @Override
    public int getItemCount() {
        return dataBarangKasirList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvId, tvNamaBarang, tvKodeBarang, tvHargaBeli, tvHargaJual, tvStokBarang, tvKategoriBarang;
        ImageView tvImage;
        ImageButton ib_add_cart;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvKodeBarang = itemView.findViewById(R.id.tv_kode_barang);
            tvHargaBeli = itemView.findViewById(R.id.tv_harga_beli);
            tvHargaJual = itemView.findViewById(R.id.tv_harga_jual);
            tvStokBarang = itemView.findViewById(R.id.tv_stok_barang);
            tvKategoriBarang = itemView.findViewById(R.id.tv_kategori);
            tvImage = itemView.findViewById(R.id.iv_barang);

            ib_add_cart = itemView.findViewById(R.id.ib_add_cart);
        }
    }

//    public class ViewHolder extends RecyclerView.ViewHolder{
//        private ImageView primage,deletbtn;
//        private TextView prprice;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            primage=(ImageView)itemView.findViewById(R.id.primage);
//            deletbtn=(ImageView)itemView.findViewById(R.id.deletbtn);
//            prprice=(TextView)itemView.findViewById(R.id.txtprprice);
//        }
//    }

    public interface ShopInterface {
        void addItem(DataBarang dataBarang);
        void onItemClick(DataBarang dataBarang);
    }
}
