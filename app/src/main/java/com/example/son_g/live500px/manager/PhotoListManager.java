package com.example.son_g.live500px.manager;

import android.content.Context;

import com.example.son_g.live500px.dao.PhotoItemCollectionDao;

/**
 * Created by son_g on 9/3/2017.
 */
public class PhotoListManager {

    private static PhotoListManager instance;
    private PhotoItemCollectionDao dao;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    private Context mContext;

    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

}
