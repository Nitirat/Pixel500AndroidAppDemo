package com.example.son_g.live500px.manager.http;

import com.example.son_g.live500px.dao.PhotoItemCollectionDao;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by son_g on 9/5/2017.
 */

public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotolist();

    @POST("list/after/{id}")
    Call<PhotoItemCollectionDao> loadPhotolistAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectionDao> loadPhotolistBeforeId(@Path("id") int id);
}
