package com.example.lr.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.FuLiCenterApplication;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.Result;
import com.example.lr.fulicenter.model.bean.User;
import com.example.lr.fulicenter.model.dao.SharedPreferenceUtils;
import com.example.lr.fulicenter.model.dao.UserDao;
import com.example.lr.fulicenter.model.net.IUserModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.net.UserModel;
import com.example.lr.fulicenter.model.utils.CommonUtils;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.model.utils.ResultUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tvHeadTitle)
    TextView tvHeadTitle;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;

    LoginActivity mContext;
    String userName;
    String password;
    IUserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        model = new UserModel();
        mContext = this;
        tvHeadTitle.setText("账户登录");
    }

    @OnClick({R.id.ivBack, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkedInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }
    private void checkedInput() {
        userName = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (userName.isEmpty()) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            etUserName.requestFocus();
            return;
        } else if (password.isEmpty()) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            etPassword.requestFocus();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        L.i("userName"+userName+"password"+password);
        model.login(mContext, userName, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String str) {
                Result result = ResultUtils.getResultFromJson(str, User.class);
                if (result.getRetCode() !=0) {
                    CommonUtils.showShortToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        User user= (User) result.getRetData();
                        UserDao dao = new UserDao(mContext);
                        boolean isSuccess= dao.saveUser(user);
                        if (isSuccess) {
                            SharedPreferenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUser(user);
                            MFGT.finish(mContext);
                            CommonUtils.showLongToast(R.string.login_success);
                        } else {
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showLongToast(R.string.login_fail_error_password);
                        }else {
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast("login" + error);
                L.e("login error" + error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String name = data.getStringExtra(I.User.USER_NAME);
            etUserName.setText(name);
        }
    }
}
