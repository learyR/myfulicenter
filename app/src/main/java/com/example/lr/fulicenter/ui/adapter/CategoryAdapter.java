package com.example.lr.fulicenter.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.model.bean.CategoryChildBean;
import com.example.lr.fulicenter.model.bean.CategoryGroupBean;
import com.example.lr.fulicenter.model.utils.ImageLoader;
import com.example.lr.fulicenter.model.utils.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lr on 2017/3/16.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> childList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.context = context;
        this.groupList = new ArrayList<>();
        this.groupList.addAll(groupList);
        this.childList = new ArrayList<>();
        this.childList.addAll(childList);
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && childList.get(groupPosition) != null ?
                childList.get(groupPosition).size() : 0;

    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList != null ? groupList.get(groupPosition) : null;

    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList != null && childList.get(groupPosition) != null ?
                childList.get(groupPosition).get(childPosition) : null;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        CategoryGroupViewHolder holder;
        if (view == null) {
            view = view.inflate(context, R.layout.item_category_group, null);
            holder = new CategoryGroupViewHolder(view);
            view.setTag(holder);
        } else {
            view.getTag();
            holder = (CategoryGroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            holder.ivCategoryGroupDown.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
            ImageLoader.downloadImg(context, holder.ivCategoryGroupImg, group.getImageUrl());
            holder.tvCategoryGroupName.setText(group.getName());
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        CategoryChildViewHolder holder;
        if (view == null) {
            view = view.inflate(context, R.layout.item_category_child, null);
            holder = new CategoryChildViewHolder(view);
            view.setTag(holder);
        } else {
            view.getTag();
            holder = (CategoryChildViewHolder) view.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageLoader.downloadImg(context, holder.ivCategoryChildImg, child.getImageUrl());
            holder.tvCategoryChildName.setText(child.getName());
            holder.layoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<CategoryChildBean> list = childList.get(groupPosition);
                    String groupName = groupList.get(groupPosition).getName();
                    MFGT.gotoCategoryChildActivity(context, child.getId(), groupName, list);
                }
            });
        }
        return view;
    }
    public void initData(ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        if (groupList != null) {
            groupList.clear();
        }
        groupList.addAll(mGroupList);
        if (childList != null) {
            childList.clear();
        }
        childList.addAll(mChildList);
        notifyDataSetChanged();
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class CategoryGroupViewHolder {
        @BindView(R.id.ivCategory_Group_Img)
        ImageView ivCategoryGroupImg;
        @BindView(R.id.tvCategory_Group_Name)
        TextView tvCategoryGroupName;
        @BindView(R.id.ivCategory_Group_Down)
        ImageView ivCategoryGroupDown;
        @BindView(R.id.layout_category_child)
        LinearLayout layoutCategoryChild;

        CategoryGroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class CategoryChildViewHolder {
        @BindView(R.id.ivCategory_child_Img)
        ImageView ivCategoryChildImg;
        @BindView(R.id.tvCategory_child_Name)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        CategoryChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
