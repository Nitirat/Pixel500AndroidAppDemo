package com.example.son_g.live500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.son_g.live500px.view.PhotoListItem;

/**
 * Created by son_g on 9/4/2017.
 */

public class PhotoListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 200;
    }

    @Override
    public Object getItem(int i) {
        return null;
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
        return item;
    }
}
