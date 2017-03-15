package com.example.lr.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.BoutiqueBean;
import com.example.lr.fulicenter.model.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lr on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    List<BoutiqueBean> newGoodsList = new ArrayList<>();
    Context context;

    RecyclerView parent;
    String textFooter;
    boolean isMore;

    public BoutiqueAdapter(List<BoutiqueBean> newGoodsList, Context context) {
        this.newGoodsList = newGoodsList;
        this.context = context;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = (RecyclerView) parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                view = inflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case I.TYPE_ITEM:
                view = inflater.inflate(R.layout.iten_boutique, parent, false);
                holder = new BoutiqueViewHolder(view);
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getFooter());
            return;
        }
        BoutiqueViewHolder boutiqueViewHolder = (BoutiqueViewHolder) holder;
        BoutiqueBean boutiqueBean = newGoodsList.get(position);
        boutiqueViewHolder.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
        boutiqueViewHolder.tvBoutiqueName.setText(boutiqueBean.getName());
        boutiqueViewHolder.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
        ImageLoader.downloadImg(context, boutiqueViewHolder.ivBoutique, boutiqueBean.getImageurl());
    }

    /**
     * 提示加载更多或者没有更多数据了
     */
    private int getFooter() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return newGoodsList != null ? newGoodsList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initData(ArrayList<BoutiqueBean> goodsList) {
        if (newGoodsList != null) {
            this.newGoodsList.clear();
        }
        this.newGoodsList.addAll(goodsList);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> goodsList) {
        this.newGoodsList.addAll(goodsList);
        notifyDataSetChanged();
    }


    static class BoutiqueViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_boutique)
        ImageView ivBoutique;
        @BindView(R.id.tv_boutique_title)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tv_boutique_name)
        TextView tvBoutiqueName;
        @BindView(R.id.tv_boutique_description)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_boutique)
        LinearLayout layoutBoutique;

        BoutiqueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
