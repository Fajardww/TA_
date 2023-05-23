package com.example.posdiasthadjayakediri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.posdiasthadjayakediri.Api.NetworkClient;

public class DashboardActivity extends AppCompatActivity {

    private RelativeLayout rl_kasir, rl_stokbarang, rl_pesanan, rl_laporan, rl_supplier, rl_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        rl_kasir = findViewById(R.id.rl_kasir);
        rl_kasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, KasirActivity.class);
                startActivity(intent);
            }
        });
        rl_stokbarang = findViewById(R.id.rl_stokbarang);
        rl_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, StokBarangActivity.class);
                startActivity(intent);
            }
        });
        rl_pesanan = findViewById(R.id.rl_pesanan);
        rl_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        rl_laporan = findViewById(R.id.rl_laporan);
        rl_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, LaporanActivity.class);
                startActivity(intent);
            }
        });
        rl_supplier = findViewById(R.id.rl_supplier);
        rl_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SupplierActivity.class);
                startActivity(intent);
            }
        });

        // Launching News Feed Screen
        rl_logout = findViewById(R.id.rl_logout);
        rl_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(NetworkClient.PREFERENCE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                editor.clear();
                editor.apply();
                finish();
            }
        });

    }
}