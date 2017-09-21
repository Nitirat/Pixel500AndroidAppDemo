package com.example.son_g.live500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.example.son_g.live500px.R;
import com.example.son_g.live500px.dao.PhotoItemCollectionDao;
import com.example.son_g.live500px.dao.PhotoItemDao;
import com.example.son_g.live500px.manager.PhotoListManager;
import com.example.son_g.live500px.view.PhotoListItem;

/**
 * Created by son_g on 9/4/2017.
 */

public class PhotoListAdapter extends BaseAdapter{

    PhotoItemCollectionDao dao;
    int lastPosition = -1;

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return dao.getData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PhotoListItem item;
        if(view != null){
            item = (PhotoListItem) view;
        }else{
            item = new PhotoListItem(viewGroup.getContext());
        }

        PhotoItemDao dao = (PhotoItemDao) getItem(i);

        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername()+"\n"+dao.getCamera());
        item.setImgUrl(dao.getImageUrl());

        if(i > lastPosition){
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = i;
        }
        return item;
    }
}
