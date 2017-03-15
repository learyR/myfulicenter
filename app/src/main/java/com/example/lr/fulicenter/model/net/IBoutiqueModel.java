package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.model.bean.BoutiqueBean;

/**
 * Created by Lr on 2017/3/15.
 */

public interface IBoutiqueModel extends IModelBase {
    void loadData(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
