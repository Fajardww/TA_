package com.example.posdiasthadjayakediri.Api;

import com.example.posdiasthadjayakediri.Model.ApiResponse;
import com.example.posdiasthadjayakediri.Model.DataBarang;
import com.example.posdiasthadjayakediri.Model.ResponseModelBarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NetworkService {
//    @FormUrlEncoded
//    @POST("register.php")
//    Call<ApiResponse> registerUser(@Field("nama") String nama,
//                                   @Field("username") String username,
//                                   @Field("password") String password,
//                                   @Field("level") String level);

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(@Field("username") String username,
                                @Field("password") String password);

    //data barang in stok barang
    @GET("data.php")
    Call<ApiResponse> nsDataBarang();

    //data barang in kasir
    @GET("data.php")
    Call<ApiResponse> nsDataBarangKasir();

    @POST("cart/add")
    Call<Void> addToCart(@Body DataBarang dataBarang);

    @POST("cart/remove")
    Call<Void> removeFromCart(@Body DataBarang dataBarang);

    @FormUrlEncoded
    @POST("data.php")
    Call<ApiResponse> addData(
            @Field("kode_barang") String kode_barang,
            @Field("nama_barang") String nama_barang,
            @Field("harga_beli") String harga_beli,
            @Field("harga_jual") String harga_jual,
            @Field("stok_barang") String stok_barang,
            @Field("kategori_barang") String kategori_barang);

    @FormUrlEncoded
    @POST("delete.php")
    Call<ApiResponse> apiDeleteData(
            @Field("id") int id);

    @FormUrlEncoded
    @POST("getdatabarang.php")
    Call<ApiResponse> apiGetData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ApiResponse> apiUpdateData(
            @Field("id") int id,
            @Field("kode_barang") String kode_barang,
            @Field("nama_barang") String nama_barang,
            @Field("harga_beli") String harga_beli,
            @Field("harga_jual") String harga_jual,
            @Field("stok_barang") String stok_barang,
            @Field("kategori_barang") String kategori_barang
//            @Field("foto_barang") String foto_barang
    );


}
