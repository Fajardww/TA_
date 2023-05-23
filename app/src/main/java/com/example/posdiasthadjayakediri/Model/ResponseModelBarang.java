package com.example.posdiasthadjayakediri.Model;

import java.util.List;

public class ResponseModelBarang {
    private int success;
    private String message;
    private List<DataBarang> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBarang> getData() {
        return data;
    }

    public void setData(List<DataBarang> data) {
        this.data = data;
    }


}
