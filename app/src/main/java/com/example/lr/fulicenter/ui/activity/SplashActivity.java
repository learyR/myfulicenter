package com.example.lr.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.FuLiCenterApplication;
import com.example.lr.fulicenter.model.bean.User;
import com.example.lr.fulicenter.model.dao.SharedPreferenceUtils;
import com.example.lr.fulicenter.model.dao.UserDao;
import com.example.lr.fulicenter.model.utils.L;

public class SplashActivity extends AppCompatActivity {
    int time=2000;
    SplashActivity mContext;
    private static final String TAG = SplashActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiCenterApplication.getUser();
                L.e(TAG,"userName" + user);
                String userName = SharedPreferenceUtils.getInstance(mContext).getUser();
                L.e(TAG,"userName" + userName);
                if (user == null && userName != null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(userName);
                    L.e(TAG,"user" + user);
                    if (user != null) {
                        FuLiCenterApplication.setUser(user);
                    }
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        },time);
    }
}
