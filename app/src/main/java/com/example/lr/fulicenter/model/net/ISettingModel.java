package com.example.lr.fulicenter.model.net;

import android.content.Context;

import java.io.File;

/**
 * Created by Lr on 2017/3/21.
 */

public interface ISettingModel extends IModelBase {
    void updateNick(Context context, String userName, String nick, OnCompleteListener<String> listener);
    void updateAvatar(Context context, String userName, File file, OnCompleteListener<String> listener);

}
