package com.example.posdiasthadjayakediri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.posdiasthadjayakediri.Adapter.AdapterDataBarang;
import com.example.posdiasthadjayakediri.Adapter.AdapterDataBarangKasir;
import com.example.posdiasthadjayakediri.Api.NetworkClient;
import com.example.posdiasthadjayakediri.Api.NetworkService;
import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.example.posdiasthadjayakediri.Model.DataBarang;
import com.example.posdiasthadjayakediri.Model.ResponseModelBarang;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KasirActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterDataBarangKasir.ShopInterface {
    private RecyclerView rvDataBarangKasir;
//    private RecyclerView.Adapter adDataBarangKasir;
    private RecyclerView.LayoutManager lmDataBarangKasir;
    private List<DataBarang> dataBarangListKasir;
    private ImageView imgv, ivcart, ivbarang;
    private TextView hrgbeli;
    private AdapterDataBarangKasir itemAdapter;
    private ImageButton ib_tambah, img_btn_scancode, add_cart;
    private SearchView simpleSearchView;
    private SwipeRefreshLayout refreshLayout;
    private int mNotificationsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);

        //========================component navbar=======================//
        ivbarang = findViewById(R.id.iv_barang);
        hrgbeli = findViewById(R.id.tv_harga_beli);

        img_btn_scancode = findViewById(R.id.img_btn_scancode);
        img_btn_scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

        ib_tambah = findViewById(R.id.img_btn_tambahbarang);
        ib_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, StokBarangActivity.class);
                startActivity(intent);
            }
        });

        simpleSearchView = findViewById(R.id.simpleSearchView);
        simpleSearchView.clearFocus();
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperrefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable(){
            @Override
            public void run() {
                dataBarangListKasir.clear();
                tampilDataBarang();
            }
        });

        imgv = findViewById(R.id.imageViewBack);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        ivcart = findViewById(R.id.iv_cart);
        ivcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

//        add_cart = findViewById(R.id.ib_add_cart);
//        add_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addingToCartList();
//            }
//        });
        //========================component navbar=======================//

        dataBarangListKasir = new ArrayList<>();
        rvDataBarangKasir = findViewById(R.id.rv_data_cart);
        rvDataBarangKasir.setHasFixedSize(true);
        lmDataBarangKasir = new GridLayoutManager(this, 2);
        rvDataBarangKasir.setLayoutManager(lmDataBarangKasir);
        tampilDataBarang();
    }

    private void addingToCartList() {
        NetworkService addingCart = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> tampilData = addingCart.nsDataBarangKasir();
        tampilData.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
//                // Process the cart items
//                for (DataBarang item : ) {
//                    int itemId = item.getId();
//                    String itemName = item.getName();
//                    double itemPrice = item.getPrice();
//
//                    // Perform operations on the cart item
//                    // For example, display the item details in a TextView
//                    String itemDetails = "ID: " + itemId + "\n"
//                            + "Name: " + itemName + "\n"
//                            + "Price: " + itemPrice + "\n";
//
//                    textView.setText(itemDetails);
//
//                    // You can also perform additional operations like calculating the total price of all items in the cart
//                    double totalPrice = 0;
//                    for (CartItem item : cartItems) {
//                        totalPrice += item.getPrice();
//                    }
//                    // Do something with the total price
//                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(KasirActivity.this, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //========================fungsi scan=======================//
    private void scanCode() {

        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(KasirActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
    //========================fungsi scan=======================//

    //========================fungsi searching item=======================//
    private void filterList(String text) {
        List<DataBarang> filteredList = new ArrayList<>();
        for (DataBarang dataBarang : dataBarangListKasir){
            if (dataBarang.getNama_barang().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(dataBarang);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            itemAdapter.setFilteredList(filteredList);
        }
    }
    //========================fungsi searching item=======================//

    //========================ambil data item=======================//
    public void tampilDataBarang() {
        refreshLayout.setRefreshing(true);
        NetworkService dtBarang = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> tampilData = dtBarang.nsDataBarangKasir();
        tampilData.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                boolean success = response.body().isSuccess();
                String message = response.body().getMessage();

                dataBarangListKasir = response.body().getData();
                itemAdapter = new AdapterDataBarangKasir(KasirActivity.this, dataBarangListKasir);
                rvDataBarangKasir.setAdapter(itemAdapter);
                Toast.makeText(KasirActivity.this, "Success : "+success+" | Message : "+message, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(KasirActivity.this, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //========================ambil data item=======================//

    @Override
    public void onRefresh() {
        dataBarangListKasir.clear();
        tampilDataBarang();
    }

    @Override
    public void addItem(DataBarang dataBarang) {

    }

    @Override
    public void onItemClick(DataBarang dataBarang) {

    }
}