package com.example.lr.fulicenter.application;

import android.app.Application;

/**
 * Created by Lr on 2017/3/14.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;

    public static FuLiCenterApplication getInstance(){
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new FuLiCenterApplication();
                }
            }
        }
        return instance;
    }
}
