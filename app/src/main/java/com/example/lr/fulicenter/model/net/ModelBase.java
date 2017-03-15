package com.example.lr.fulicenter.model.net;


import com.example.lr.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Lr on 2016/11/22.
 */

public class ModelBase implements IModelBase {
    @Override
    public void release() {
        OkHttpUtils.release();
    }
}
