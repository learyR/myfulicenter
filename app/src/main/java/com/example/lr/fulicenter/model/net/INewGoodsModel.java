package com.example.lr.fulicenter.model.net;

import android.content.Context;

/**
 * Created by Lr on 2017/3/15.
 */

public interface INewGoodsModel extends IModelBase {
     void loadData(Context context, int pageId, OnCompleteListener listener);
}
