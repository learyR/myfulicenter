package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Lr on 2017/3/15.
 */

public interface INewGoodsModel extends IModelBase {
     void loadData(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);

}
