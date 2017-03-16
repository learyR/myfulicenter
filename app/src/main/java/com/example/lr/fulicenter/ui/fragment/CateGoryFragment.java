package com.example.lr.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.model.bean.CategoryChildBean;
import com.example.lr.fulicenter.model.bean.CategoryGroupBean;
import com.example.lr.fulicenter.model.net.CategoryModel;
import com.example.lr.fulicenter.model.net.ICategoryModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.utils.ConvertUtils;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.ui.activity.MainActivity;
import com.example.lr.fulicenter.ui.adapter.CategoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CateGoryFragment extends Fragment {

    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;
    ICategoryModel mModel;
    int groupCount;
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;

    public CateGoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cate_gory, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        downloadCategoryGroup();
    }

    private void downloadCategoryGroup() {
        mModel.downloadCategoryGroup(mContext, new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.i("downloadCategoryGroup"+result);
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    L.i("groupList" + groupList.size());
                    mGroupList.addAll(groupList);
                    for (int i = 0; i < groupList.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupList.get(i);
                        downloadCategoryChild(g.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("downloadCategoryGroup"+error);
            }
        });
    }
    private void downloadCategoryChild(int parentId, final int index) {
        mModel.downloadCategoryChild(mContext, parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.i("downloadCategoryChild"+result);
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    L.i("groupList" + childList.size());
                    mChildList.set(index, childList);
                }
                if (groupCount == mChildList.size()) {
                    mAdapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {
                L.e("downloadCategoryChild"+error);
            }
        });
    }

    private void initView() {
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        elvCategory.setAdapter(mAdapter);
        elvCategory.setGroupIndicator(null);
        mModel = new CategoryModel();
    }
}
