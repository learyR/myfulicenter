package com.example.lr.fulicenter.model.net;

import android.content.Context;

import com.example.lr.fulicenter.model.bean.Result;

/**
 * Created by Lr on 2017/3/20.
 */

public interface IUserModel extends IModelBase {
    void register(Context context, String userName, String nickName, String password,
                  OnCompleteListener<Result> listener);

    void login(Context context, String userName, String password,OnCompleteListener<String> listener);
}
