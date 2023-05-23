package com.example.posdiasthadjayakediri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.posdiasthadjayakediri.Api.NetworkClient;
import com.example.posdiasthadjayakediri.Api.NetworkService;
import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageButton button_masuk;
    private TextInputEditText edtUsername, edtPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        // Mengambil instance SharedPreferences
//        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        button_masuk = findViewById(R.id.btn_masuk);
        button_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Masukan Username", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Masukan Password", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }

    private void login() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Harap tunggu");
        progressDialog.setMessage("Login in...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> login = networkService.loginUser(edtUsername.getText().toString(), edtPassword.getText().toString());
        login.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
//                    if (!response.body().isSuccess()){
                        SharedPreferences preferences = getSharedPreferences(NetworkClient.PREFERENCE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(NetworkClient.KEY_ISE_LOGGED_IN, true);
                        editor.putString(NetworkClient.KEY_LEVEL, response.body().getLevel());
                        editor.apply();
                        if (response.body().getLevel().equals("1")) {
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            Toast.makeText(LoginActivity.this, response.body().getMessage() + "login admin", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), KasirActivity.class));
                            Toast.makeText(LoginActivity.this, response.body().getMessage() + "login kasir", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, "Error tdk valid", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}