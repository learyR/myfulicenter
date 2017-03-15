package com.example.lr.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;
import com.example.lr.fulicenter.model.net.INewGoodsModel;
import com.example.lr.fulicenter.model.net.NewGoodsModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.utils.ConvertUtils;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.ui.activity.MainActivity;
import com.example.lr.fulicenter.ui.adapter.GoodsAdapter;
import com.example.lr.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    Unbinder bind;
    INewGoodsModel model;
    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mNewGoodsList;
    int pageId = 1;
    GridLayoutManager mLayoutManager;
    int catId;
    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        model = new NewGoodsModel();
        mContext = (MainActivity) getContext();
        mNewGoodsList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mNewGoodsList, mContext);
//        mSrlNewGoods.setColorSchemeColors(
//                getResources().getColor(R.color.google_blue),
//                getResources().getColor(R.color.google_green),
//                getResources().getColor(R.color.google_red),
//                getResources().getColor(R.color.google_yellow)
//        );
        mLayoutManager =new GridLayoutManager(mContext, I.COLUM_NUM);
        rvGoods.setLayoutManager(mLayoutManager);
        rvGoods.setHasFixedSize(true);
        rvGoods.addItemDecoration(new SpaceItemDecoration(15));
        rvGoods.setAdapter(mAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            catId = bundle.getInt(I.Boutique.CAT_ID, 0);
        }
    }

    private void initData() {
        model.loadData(getActivity(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> goodsList = ConvertUtils.array2List(result);
                    mAdapter.initData(goodsList);
                }
            }

            @Override
            public void onError(String error) {
                L.e("leary", "newGoodsFragment" + error);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }
}
