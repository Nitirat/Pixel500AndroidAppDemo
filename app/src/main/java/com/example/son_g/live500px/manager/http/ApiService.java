package com.example.son_g.live500px.manager.http;

import com.example.son_g.live500px.dao.PhotoItemCollectionDao;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by son_g on 9/5/2017.
 */

public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotolist();
}
