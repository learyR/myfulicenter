package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;
import com.example.lr.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Lr on 2017/3/15.
 */

public class NewGoodsModel  extends ModelBase implements INewGoodsModel {
    @Override
    public void loadData(Context context, int pageId, OnCompleteListener listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID+"")
                .addParam(I.PAGE_ID,I.PAGE_ID+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
