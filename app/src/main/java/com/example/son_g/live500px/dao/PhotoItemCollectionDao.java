package com.example.son_g.live500px.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by son_g on 9/5/2017.
 */

public class PhotoItemCollectionDao {
    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private List<PhotoItemDao> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
