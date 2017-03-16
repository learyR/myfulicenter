package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.model.bean.CategoryChildBean;
import com.example.lr.fulicenter.model.bean.CategoryGroupBean;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Lr on 2017/3/16.
 */

public interface ICategoryModel extends IModelBase {

    void downloadCategoryGroup(Context context, OnCompleteListener<CategoryGroupBean[]> listener);

    void downloadCategoryChild(Context context, int parentID, OnCompleteListener<CategoryChildBean[]> listener);

    void downloadCategoryChild(Context context, int catId, int pageId,OnCompleteListener<NewGoodsBean[]> listener);
}
