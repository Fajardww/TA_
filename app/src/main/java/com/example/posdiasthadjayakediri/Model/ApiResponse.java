package com.example.posdiasthadjayakediri.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private int Id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("username")
    private String username;

    @SerializedName("level")
    private String level;

    @SerializedName("data")
    private List<DataBarang> data;

    public List<DataBarang> getData() {
        return data;
    }

    public void setData(List<DataBarang> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return Id;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getLevel() {
        return level;
    }
}
