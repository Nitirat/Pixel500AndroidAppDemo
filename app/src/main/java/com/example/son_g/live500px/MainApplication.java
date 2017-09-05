package com.example.son_g.live500px;

import android.app.Application;

import com.example.son_g.live500px.manager.Contextor;

/**
 * Created by son_g on 9/3/2017.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
