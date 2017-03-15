package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.BoutiqueBean;
import com.example.lr.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Lr on 2017/3/15.
 */

public class BoutiqueModel extends ModelBase implements IBoutiqueModel {
    @Override
    public void loadData(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
