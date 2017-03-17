package com.example.lr.fulicenter.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.CategoryChildBean;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.ui.fragment.NewGoodsFragment;
import com.example.lr.fulicenter.ui.view.CatChildFilterButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;
    @BindView(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;
    @BindView(R.id.rrr)
    RelativeLayout rrr;
    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;
    String groupName;
    ArrayList<CategoryChildBean> childList;
    boolean addTimeAsc;
    boolean priceAsc;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    NewGoodsFragment newGoodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        newGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, newGoodsFragment)
                .commit();
        initView();
    }

    private void initView() {
        groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        childList = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.ID);
        btnCatChildFilter.setOnCatFilterClickListener(groupName, childList);
        btnCatChildFilter.setText(groupName);
    }


    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClickSort(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.drawable.arrow_order_down);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.drawable.arrow_order_up);
                }
                priceAsc = !priceAsc;
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.drawable.arrow_order_down);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.drawable.arrow_order_up);
                }
                addTimeAsc = !addTimeAsc;
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                break;
        }
        newGoodsFragment.setSortBy(sortBy);
    }
}
