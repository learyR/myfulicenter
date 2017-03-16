package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.model.bean.GoodsDetailsBean;

/**
 * Created by Lr on 2017/3/16.
 */

public interface IGoodsModel extends IModelBase{
    void downloadGoodsDetails(Context context, int goodsId,
                              OnCompleteListener<GoodsDetailsBean> listener);
}
