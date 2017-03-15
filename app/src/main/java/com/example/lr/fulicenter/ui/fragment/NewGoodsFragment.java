package com.example.lr.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.model.bean.NewGoodsBean;
import com.example.lr.fulicenter.model.net.NewGoodsModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.utils.L;

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
    NewGoodsModel model;
    int pageId = 1;
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
        model = new NewGoodsModel();
        initData();
    }

    private void initData() {
        model.loadData(getActivity(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e("leary", "newGoodsFragment+HAHHAH" );
                if (result != null && result.length > 0) {
                    L.e("leary", "newGoodsFragment" + result.length);
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
