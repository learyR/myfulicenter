package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.CategoryChildBean;
import com.example.lr.fulicenter.model.bean.CategoryGroupBean;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;
import com.example.lr.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Lr on 2017/3/16.
 */

public class CategoryModel extends ModelBase implements ICategoryModel {
    @Override
    public void downloadCategoryGroup(Context context, OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void downloadCategoryChild(Context context, int parentID, OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentID))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    @Override
    public void downloadCategoryChild(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
