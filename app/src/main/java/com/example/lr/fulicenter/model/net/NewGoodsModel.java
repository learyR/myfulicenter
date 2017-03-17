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
    public void loadData(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener) {
        String url = I.REQUEST_FIND_NEW_BOUTIQUE_GOODS;
        if (catId > 0) {
            url = I.REQUEST_FIND_GOODS_DETAILS;
        }
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
