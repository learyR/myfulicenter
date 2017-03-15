package com.example.lr.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;
import com.example.lr.fulicenter.model.net.INewGoodsModel;
import com.example.lr.fulicenter.model.net.NewGoodsModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.utils.CommonUtils;
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
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

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
        setListener();
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                downLoad(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void setPullUpListener() {
        rvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastPosition == mAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore()) {
                    pageId++;
                    downLoad(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
                srl.setEnabled(firstPosition == 0);
            }
        });
    }

    private void initView() {
        model = new NewGoodsModel();
        mContext = (MainActivity) getContext();
        mNewGoodsList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mNewGoodsList, mContext);
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
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
        downLoad(I.ACTION_DOWNLOAD);
    }

    private void downLoad(final int action) {
        model.loadData(getActivity(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> goodsList = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(goodsList);
                    } else {
                        mAdapter.addData(goodsList);
                    }

                    if (goodsList.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showShortToast(error);
                L.e("error"+error);
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
