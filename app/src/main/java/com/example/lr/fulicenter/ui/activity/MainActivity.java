package com.example.lr.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.FuLiCenterApplication;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.ui.fragment.BoutiqueFragment;
import com.example.lr.fulicenter.ui.fragment.CateGoryFragment;
import com.example.lr.fulicenter.ui.fragment.NewGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.personal)
    RadioButton personal;
    Unbinder bind = null;
    Fragment[] mFragment;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CateGoryFragment mCateGoryFragment;
    int mIndex;
    int mCurrentIndex;
    RadioButton[] mRbArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initView() {
        mRbArray=new RadioButton[5];
        mRbArray[0] = layoutNewGood;
        mRbArray[1] = layoutBoutique;
        mRbArray[2] = layoutCategory;
        mRbArray[3] = layoutCart;
        mRbArray[4] = personal;
    }

    private void initFragment() {
        mFragment = new Fragment[3];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCateGoryFragment = new CateGoryFragment();
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFragment;
        mFragment[2] = mCateGoryFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueFragment)
                .add(R.id.fragment_container,mCateGoryFragment)
                .hide(mBoutiqueFragment)
                .hide(mCateGoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                mIndex = 0;
                break;
            case R.id.layout_boutique:
                mIndex = 1;
                break;
            case R.id.layout_category:
                mIndex = 2;
                break;
            case R.id.layout_cart:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLoginActivityFromCart(this);
                } else {
                    mIndex = 3;
                }
                break;
            case R.id.personal:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLoginActivity(this);
                } else {
                    mIndex = 4;
                }
                break;

        }
        setFragment();
    }

    private void setFragment() {
        if (mIndex != mCurrentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragment[mCurrentIndex]);
            if (!mFragment[mIndex].isAdded()) {
                ft.add(R.id.fragment_container, mFragment[mIndex]);
            }
            ft.show(mFragment[mIndex]).commitAllowingStateLoss();
        }
        setRadioButtonStatus();
        mCurrentIndex=mIndex;
    }
    private void setRadioButtonStatus() {

        for (int i=0;i<mRbArray.length;i++) {
            if (i==mIndex) {
                mRbArray[i].setChecked(true);
            } else {
                mRbArray[i].setChecked(false);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mIndex == 4 && FuLiCenterApplication.getUser() == null) {
            mIndex = 0;
        }
        setFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
