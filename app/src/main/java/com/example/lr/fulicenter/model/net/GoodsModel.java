package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.GoodsDetailsBean;
import com.example.lr.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Lr on 2017/3/16.
 */

public class GoodsModel extends ModelBase implements IGoodsModel {
    @Override
    public void downloadGoodsDetails(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
}
