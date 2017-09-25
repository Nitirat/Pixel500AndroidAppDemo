package com.example.son_g.live500px.manager;

import android.content.Context;

import com.example.son_g.live500px.dao.PhotoItemCollectionDao;
import com.example.son_g.live500px.dao.PhotoItemDao;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by son_g on 9/3/2017.
 */
public class PhotoListManager {

    private PhotoItemCollectionDao dao;

    private Context mContext;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    public void appendDao(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if(dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());

        dao.getData().addAll(0, newDao.getData());
    }

    public void appendDaoToButtom(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if(dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());

        dao.getData().addAll(dao.getData().size(), newDao.getData());
    }

    public int getMaxId() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        return maxId;
    }

    public int getMinId() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            minId = Math.min(minId, dao.getData().get(i).getId());
        return minId;
    }

    public int getCount() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

}
