package com.example.lr.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.FuLiCenterApplication;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.Result;
import com.example.lr.fulicenter.model.bean.User;
import com.example.lr.fulicenter.model.dao.SharedPreferenceUtils;
import com.example.lr.fulicenter.model.dao.UserDao;
import com.example.lr.fulicenter.model.net.ISettingModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.net.SettingModel;
import com.example.lr.fulicenter.model.utils.CommonUtils;
import com.example.lr.fulicenter.model.utils.ImageLoader;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.model.utils.OnSetAvatarListener;
import com.example.lr.fulicenter.model.utils.ResultUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tvHeadTitle)
    TextView tvHeadTitle;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserNick)
    TextView tvUserNick;

    User user;
    OnSetAvatarListener mOnSetAvatarListener;
    SettingActivity mContext;
    ISettingModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        user= FuLiCenterApplication.getUser();
        if (user != null) {
            tvUserName.setText(user.getMuserName());
            tvUserNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivAvatar);
        } else {
            MFGT.finish(mContext);
        }
    }

    private void initView() {
        mContext = this;
        tvHeadTitle.setText("个人资料");
        model = new SettingModel();
    }

    @OnClick({R.id.ivBack, R.id.linearAvatar, R.id.linearUserName, R.id.linearUserNick, R.id.btnExit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(mContext);
                break;
            case R.id.linearAvatar:
                mOnSetAvatarListener= new OnSetAvatarListener(mContext, R.id.setting,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.linearUserName:
                CommonUtils.showShortToast("亲，不能更改用户名哦！");
                break;
            case R.id.linearUserNick:
                changeNick();
                break;
            case R.id.btnExit:
                if (user != null) {
                    SharedPreferenceUtils.getInstance(mContext).removeUser();
                    FuLiCenterApplication.setUser(null);
                    MFGT.gotoLoginActivity(mContext);
                }
                MFGT.finish(mContext);
                break;
        }
    }

    private void changeNick() {
        View view = View.inflate(mContext, R.layout.custom_dialog, null);
        final EditText etCustom = (EditText) view.findViewById(R.id.tvCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("设置昵称")
                .setIcon(R.drawable.icon_account)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (user != null) {
                            String nick = etCustom.getText().toString().trim();
                            if (nick.equals(user.getMuserNick())) {
                                CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                            } else if (TextUtils.isEmpty(nick)) {
                                CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
                            } else {
                                updateNick(nick);
                            }
                        }
                    }
                }).setNegativeButton("取消",null).create().show();
    }

    private void updateNick(final String nick) {
        {
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setMessage(getResources().getString(R.string.logining));
            pd.show();
            model.updateNick(mContext, user.getMuserName(), nick, new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String str) {
                    Result result = ResultUtils.getResultFromJson(str, User.class);
                    if (result.getRetCode() !=0) {
                        CommonUtils.showShortToast(R.string.update_nick_fail);
                    } else {
                        if (result.isRetMsg()) {
                            User user= (User) result.getRetData();
                            UserDao dao = new UserDao(mContext);
                            boolean isSuccess= dao.updateUser(user);
                            if (isSuccess) {
//                            SharedPreferenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(user);
                                tvUserNick.setText(nick);
                                CommonUtils.showLongToast(R.string.update_nick_success);
                            } else {
                                CommonUtils.showLongToast(R.string.update_database_error);
                            }
                        }
                    }
                    pd.dismiss();
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast("updateNick" + error);
                    L.e("updateNick error" + error);
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode, data, ivAvatar);
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file =new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath() + "/" + user.getMuserName()
                        + I.AVATAR_SUFFIX_JPG));
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        model.updateAvatar(mContext, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String str) {
                L.e("s" + str);
                Result result = ResultUtils.getResultFromJson(str, User.class);
                L.e("result+"+result);
                if (result.getRetCode() !=0) {
                    CommonUtils.showShortToast(R.string.update_avatar_fail);
                } else {
                    User user= (User) result.getRetData();
                    if (result.isRetMsg()) {
                        FuLiCenterApplication.setUser(user);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivAvatar);
//                            SharedPreferenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                        CommonUtils.showLongToast(R.string.update_avatar_success);
                    }else {
                        CommonUtils.showLongToast(R.string.update_avatar_fail);
                    }
                }
                pd.dismiss();

            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast("updateNick" + error);
                L.e("updateNick error" + error);
            }
        });
    }
}
