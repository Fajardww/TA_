package com.example.posdiasthadjayakediri.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.posdiasthadjayakediri.Api.NetworkClient;
import com.example.posdiasthadjayakediri.Api.NetworkService;
import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.example.posdiasthadjayakediri.Model.DataBarang;
import com.example.posdiasthadjayakediri.Model.ResponseModelBarang;
import com.example.posdiasthadjayakediri.R;
import com.example.posdiasthadjayakediri.StokBarangActivity;
import com.example.posdiasthadjayakediri.UbahDataBarangActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataBarang extends RecyclerView.Adapter<AdapterDataBarang.HolderData> {
    private Context ctx;
    private List<DataBarang> dataBarangList;
    private List<DataBarang> listId;
    private int id;

    public AdapterDataBarang(Context ctx, List<DataBarang> dataBarangList) {
        this.ctx = ctx;
        this.dataBarangList = dataBarangList;
    }

    public void setFilteredList(List<DataBarang> filteredList){
        this.dataBarangList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang, parent, false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataBarang dtBarang = dataBarangList.get(position);

        holder.tvId.setText(String.valueOf(dtBarang.getId()));
        holder.tvNamaBarang.setText(dtBarang.getNama_barang());
//        holder.tvKodeBarang.setText(String.valueOf(dtBarang.getKode_barang()));
        holder.tvHargaBeli.setText(String.valueOf(dtBarang.getHarga_beli()));
//        holder.tvHargaJual.setText(String.valueOf(dtBarang.getHarga_jual()));
        holder.tvStokBarang.setText(String.valueOf(dtBarang.getStok_barang()));
        holder.tvKategoriBarang.setText(String.valueOf(dtBarang.getKategori_barang()));
//        holder.tvImage.setImageResource(String.valueOf(dtBarang.getStok_barang()));
//        Glide.with(ctx).load(dtBarang.getFoto_barang()).apply(option).into(holder.tvImage);
        Glide.with(ctx).load(dtBarang.getFoto_barang()).thumbnail(0.5f)
                .into(holder.tvImage);
    }

    @Override
    public int getItemCount() {
        return dataBarangList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNamaBarang, tvKodeBarang, tvHargaBeli, tvHargaJual, tvStokBarang, tvKategoriBarang;
        ImageView tvImage;

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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi yang akan Dilakukan");
                    dialogPesan.setIcon(R.drawable.ic_warning);
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setCancelable(true);

                    id = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hapusData();
                            dialogInterface.dismiss();
                            ((StokBarangActivity) ctx).tampilDataBarang();
                        }
                    });
                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getDataBarang();
                            dialogInterface.dismiss();
                        }
                    });
                    dialogPesan.show();
                    return false;
                }
            });
        }

        private void hapusData(){
            NetworkService hapusData = NetworkClient.getClient().create(NetworkService.class);
            Call<ApiResponse> deleteData = hapusData.apiDeleteData(id);
            deleteData.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
//                    assert response.body() != null;
                    boolean success = response.body().isSuccess();
                    String message = response.body().getMessage();
                    Toast.makeText(ctx, success+""+message, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ctx, "Nyapo Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            });
        }}

        private void getDataBarang(){
            NetworkService getDataBarang = NetworkClient.getClient().create(NetworkService.class);
            Call<ApiResponse> ambilData = getDataBarang.apiGetData(id);
            ambilData.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
//                    assert response.body() != null;
                    boolean success = response.body().isSuccess();
                    String message = response.body().getMessage();
                    listId = response.body().getData();

                    int varId = listId.get(0).getId();
                    String varNamaBarang = listId.get(0).getNama_barang();
                    int varKodeBarang = listId.get(0).getKode_barang();
                    int varHargaBeli = listId.get(0).getHarga_beli();
                    int varHargaJual = listId.get(0).getHarga_jual();
                    int varStokBarang = listId.get(0).getStok_barang();
                    int varKategoriBarang = listId.get(0).getStok_barang();
//                    String varFotoBarang = listId.get(0).getFoto_barang();

                    Intent kirim = new Intent(ctx, UbahDataBarangActivity.class);
                    kirim.putExtra("xId",varId);
                    kirim.putExtra("xNamaBarang",varNamaBarang);
                    kirim.putExtra("xKodeBarang",varKodeBarang);
                    kirim.putExtra("xHargaBeli",varHargaBeli);
                    kirim.putExtra("xHargaJual",varHargaJual);
                    kirim.putExtra("xStokBarang",varStokBarang);
                    kirim.putExtra("xKategoriBarang",varKategoriBarang);
//                    kirim.putExtra("xFotoBarang",varFotoBarang);
                    ctx.startActivity(kirim);
                    Toast.makeText(ctx, "success : "+success+" | message : "+message, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

