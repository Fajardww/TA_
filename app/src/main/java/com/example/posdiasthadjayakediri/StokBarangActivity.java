package com.example.posdiasthadjayakediri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.posdiasthadjayakediri.Adapter.AdapterDataBarang;
import com.example.posdiasthadjayakediri.Api.NetworkClient;
import com.example.posdiasthadjayakediri.Api.NetworkService;
import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.example.posdiasthadjayakediri.Model.DataBarang;
import com.example.posdiasthadjayakediri.Model.ResponseModelBarang;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokBarangActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_STOK_BARANG = "stok";
    public static final String EXTRA_NAMA_BARANG = "nama";
    public static final String EXTRA_KODE_BARANG = "kode";
    public static final String EXTRA_HARGA_BARANG = "harga";

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvData;
    private AdapterDataBarang itemAdapter;
    private RecyclerView.LayoutManager lmDataBarang;
    private List<DataBarang> dataBarangList = new ArrayList<>();
//    private List<DataBarang> listId = new ArrayList<>();
//    private Context data;

    private EditText edt_nama, edt_kode_barang, edt_harga_beli, edt_harga_jual, edt_stok_barang, edt_kategori_barang, search_barang;
    private ImageView iv_img, back;
    private ImageButton ib_barcode;
    private SearchView simpleSearchView;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);

        //========================component navbar=======================//
        ib_barcode = findViewById(R.id.ib_barcode);
        ib_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
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
                dataBarangList.clear();
                tampilDataBarang();
            }
        });

        back = findViewById(R.id.imageViewBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StokBarangActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        //========================component navbar=======================//

        requestStoragePermissions();

        dataBarangList = new ArrayList<>();
        rvData = findViewById(R.id.rv_data);
        rvData.setHasFixedSize(true);
        lmDataBarang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmDataBarang);
        tampilDataBarang();

        //access image
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK){
//                    Intent data = result.getData();
//                    Uri uri = data.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        iv_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openGall();
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activityResultLauncher.launch(intent);
//            }
//        });
    }

    public void Ftbarang(View view) {
        ImageView iv_img_kamera;

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGall();
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activityResultLauncher.launch(intent);
            }

            private void openGall() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(StokBarangActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
    //========================fungsi scan=======================//

    //========================fungsi searching=======================//
    private void filterList(String text) {
        List<DataBarang> filteredList = new ArrayList<>();
        for (DataBarang dataBarang : dataBarangList){
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
    //========================fungsi searching=======================//

    //========================ambil data json=======================//
    public void tampilDataBarang() {
        refreshLayout.setRefreshing(true);
        NetworkService dtBarang = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> tampilData = dtBarang.nsDataBarang();
        tampilData.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                boolean success = response.body().isSuccess();
                String message = response.body().getMessage();
                
                dataBarangList = response.body().getData();
                itemAdapter = new AdapterDataBarang(StokBarangActivity.this, dataBarangList);
                rvData.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
                Toast.makeText(StokBarangActivity.this, "status : " +success+ " pesan : " +message, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(StokBarangActivity.this, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //========================ambil data json=======================//

    //========================add data barang=======================//
    public void tambah_barang(View view) {
        Button simpan;
        ImageView tutup, addimage;
        ImageButton ib_barcode;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.tambah_barang);

        edt_kode_barang = (EditText) dialog.findViewById(R.id.edt_kode_barang);
        edt_nama = (EditText) dialog.findViewById(R.id.edt_nama);
        edt_harga_beli = (EditText) dialog.findViewById(R.id.edt_harga_beli);
        edt_harga_jual = (EditText) dialog.findViewById(R.id.edt_harga_jual);
        edt_stok_barang = (EditText) dialog.findViewById(R.id.edt_stok_barang);
        edt_kategori_barang = (EditText) dialog.findViewById(R.id.edt_kategori);
//        iv_img = (ImageView) dialog.findViewById(R.id.iv_img_kamera);
//        btn_kamera = (Button) dialog.findViewById(R.id.btn_kamera);

        //        //access image
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK){
//                    Intent data = result.getData();
//                    Uri uri = data.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        iv_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activityResultLauncher.launch(intent);
//            }
//        });



        simpan = dialog.findViewById(R.id.btn_simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kode = edt_kode_barang.getText().toString();
                String nama = edt_nama.getText().toString();
                String hargab = edt_harga_beli.getText().toString();
                String hargaj = edt_harga_jual.getText().toString();
                String stok = edt_stok_barang.getText().toString();
                String kategori = edt_kategori_barang.getText().toString();
//                String foto = iv_img.getTransitionName().toString();

                if(nama.trim().equals("")){
                    edt_nama.setError("nama harus di isi");
//                } else if (kode.trim().equals("")){
//                    edt_kode_barang.setError("kode harus di isi");
                } else if (hargab.trim().equals("")){
                    edt_harga_beli.setError("harus di isi");
                } else if (hargaj.trim().equals("")){
                    edt_harga_jual.setError("harga jjual harus di isi");
                } else if (stok.trim().equals("")){
                    edt_stok_barang.setError("stok harus di isi");
                } else if (kategori.trim().equals("")){
                    edt_kategori_barang.setError("kategirl harus di isi");
//                } else if (iv_img.equals(bitmap)){
//                    edt_kategori_barang.setError("kategirl harus di isi");
                }
                else {
                    simpan(kode, nama, hargab, hargaj, stok, kategori);
                }
            }
        });

        tutup = dialog.findViewById(R.id.imageViewBack);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void simpan(String kode, String nama, String hargab, String hargaj, String stok, String kategori) {
        NetworkService dtBarangBaru = NetworkClient.getClient().create(NetworkService.class);
        Call<ApiResponse> tambahData = dtBarangBaru.addData(kode, nama, hargab, hargaj, stok, kategori);
        tambahData.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                boolean success = response.body().isSuccess();
                String message = response.body().getMessage();
                Toast.makeText(StokBarangActivity.this,"kode : "+success+" | Pesan : "+message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(StokBarangActivity.this, "Gagal Menghubungi Server" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //========================add data barang=======================//

    private void requestStoragePermissions() {
    }

    @Override
    public void onRefresh() {
        dataBarangList.clear();
        tampilDataBarang();
    }
}