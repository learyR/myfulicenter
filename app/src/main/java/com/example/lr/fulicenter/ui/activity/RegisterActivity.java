package com.example.lr.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.Result;
import com.example.lr.fulicenter.model.net.IUserModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.net.UserModel;
import com.example.lr.fulicenter.model.utils.CommonUtils;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.model.utils.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.tvHeadTitle)
    TextView tvHeadTitle;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etCheckedPassword)
    EditText etCheckedPassword;
    String userName;
    String userNick;
    String password;
    String checkedPassword;
    RegisterActivity mContext;
    IUserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        model = new UserModel();
        mContext = this;
        tvHeadTitle.setText("账户注册");
    }

    @OnClick({R.id.ivBack, R.id.btnRegister_free})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnRegister_free:
                userName = etUserName.getText().toString().trim();
                userNick = etNick.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                checkedPassword = etCheckedPassword.getText().toString().trim();
                if (userName == null || userName.length() == 0) {
                    CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
                    etUserName.requestFocus();
                    return;
                } else if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
                    CommonUtils.showShortToast(R.string.illegal_user_name);
                    etUserName.requestFocus();
                    return;
                }else if (userNick.isEmpty()) {
                    CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                    etNick.requestFocus();
                    return;
                }else if (password.isEmpty()) {
                    CommonUtils.showShortToast(R.string.password_connot_be_empty);
                    etPassword.requestFocus();
                    return;
                } else if (checkedPassword.isEmpty()) {
                    CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
                    etCheckedPassword.requestFocus();
                    return;
                }else if (!password.equals(checkedPassword)) {
                    CommonUtils.showShortToast(R.string.two_input_password);
                    etCheckedPassword.requestFocus();
                    return;
                }
                register(userName, userNick, password);
                break;
        }
    }

    private void register(final String userName, String userNick, String password) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        model.register(mContext, userName, userNick, password, new OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result.getRetCode() != 0) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK, new Intent().putExtra(I.User.USER_NAME, userName));
                        MFGT.finish(mContext);
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        etUserName.requestFocus();
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e("register error"+error);
            }
        });
    }
}
