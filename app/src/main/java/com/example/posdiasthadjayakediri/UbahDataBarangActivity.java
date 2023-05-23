package com.example.posdiasthadjayakediri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.posdiasthadjayakediri.Api.NetworkClient;
import com.example.posdiasthadjayakediri.Api.NetworkService;
import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.example.posdiasthadjayakediri.Model.ResponseModelBarang;
import com.google.protobuf.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahDataBarangActivity extends AppCompatActivity {

    private int xId;
    private String xKodeBarang, xNamaBarang, xHargaBeli, xHargaJual, xStokBarang, xKategoriBarang, xFotoBarang;
    private String yKodeBarang, yNamaBarang, yHargaBeli, yHargaJual, yStokBarang, yKategoriBarang, yFotoBarang;
    private EditText edtkodeBarang,edtnamaBarang, edthargaBeli, edthargaJual, edtstokBarang, edtkategoriBarang;
    private Button btn_simpan;
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_barang);

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UbahDataBarangActivity.this, StokBarangActivity.class);
                startActivity(intent);
            }
        });

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xKodeBarang = terima.getStringExtra("xKodeBarang");
        xNamaBarang = terima.getStringExtra("xNamaBarang");
        xHargaBeli = terima.getStringExtra("xHargaBeli");
        xHargaJual = terima.getStringExtra("xHargaJual");
        xStokBarang = terima.getStringExtra("xStokBarang");
        xKategoriBarang = terima.getStringExtra("xKategoriBarang");
//        xFotoBarang = terima.getStringExtra("xFotoBarang");

        edtkodeBarang = findViewById(R.id.edt_kode_barang);
        edtnamaBarang = findViewById(R.id.edt_nama);
        edthargaBeli = findViewById(R.id.edt_harga_beli);
        edthargaJual = findViewById(R.id.edt_harga_jual);
        edtstokBarang = findViewById(R.id.edt_stok_barang);
        edtkategoriBarang = findViewById(R.id.edt_kategori);
        btn_simpan = findViewById(R.id.btn_simpan);

        edtkodeBarang.setText(xKodeBarang);
        edtnamaBarang.setText(xNamaBarang);
        edthargaBeli.setText(xHargaBeli);
        edthargaJual.setText(xHargaJual);
        edtstokBarang.setText(xStokBarang);
        edtkategoriBarang.setText(xKategoriBarang);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yKodeBarang = edtkodeBarang.getText().toString();
                yNamaBarang = edtnamaBarang.getText().toString();
                yHargaBeli = edthargaBeli.getText().toString();
                yHargaJual = edthargaJual.getText().toString();
                yStokBarang = edtstokBarang.getText().toString();
                yKategoriBarang = edtkategoriBarang.getText().toString();
                updateData();
            }
        });
    }

    private void updateData() {
        NetworkService ubahDataBarang = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> updateDataBarang = ubahDataBarang.apiUpdateData(xId, yKodeBarang, yNamaBarang, yHargaBeli, yHargaJual, yStokBarang, yKategoriBarang);
        updateDataBarang.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                boolean success = response.body().isSuccess();
                String message = response.body().getMessage();
                Toast.makeText(UbahDataBarangActivity.this, "status : "+success+" | pesan : "+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(UbahDataBarangActivity.this, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}