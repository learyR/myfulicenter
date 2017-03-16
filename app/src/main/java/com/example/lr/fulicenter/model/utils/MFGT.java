package com.example.lr.fulicenter.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.BoutiqueBean;
import com.example.lr.fulicenter.ui.activity.BoutiqueChildActivity;
import com.example.lr.fulicenter.ui.activity.MainActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context, Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
    }
    public static void startActivity(Context context, Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    /**
     * 跳转到精选二级页面Activity
     * @param context
     * @param bean 一个精选的实体类
     */
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }

   /*

    */
   /* *//**
     * 跳转到商品详情Activity
     * @param context
     * @param goodsId 商品id
     *//*
    public static void gotoGoodsDetailsActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity(context,intent);
    }



     * 跳转到分类二级页面的Activity中
     *
     * @param context
     * @param catId
     *//*
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID, catId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context, intent);
    }

    *//**
     * 跳转到LoginActivity
     * @param context
     *//*
    public static void gotoLoginActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN);
    }

    public static void gotoLoginActivityFromCart(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_CART);
    }

    *//**
     * 跳转到RegisterActivity
     * @param context
     *//*
    public static void gotoRegisterActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        startActivityForResult(context, intent, I.REQUEST_CODE_REGISTER);
    }

    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    *//**
     * 跳转到SettingActivity
     * @param context
     *//*
    public static void gotoSettingActivity(Activity context) {
        startActivity(context, SettingActivity.class);
    }
    *//**
     * 跳转到CollectionActivity
     * @param context
     *//*
    public static void gotoCollectionActivity(Activity context) {
        startActivity(context, CollectsActivity.class);
    }

    *//**
     * 跳转到提交订单界面
     * @param context
     * @param cartsId
     *//*
    public static void gotoPay(Activity context, String cartsId) {
        Intent intent = new Intent(context, PayActivity.class).putExtra(I.Cart.ID, cartsId);
        startActivity(context,intent);
    }*/

}
