package com.example.lr.fulicenter.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lr.fulicenter.R;

/**
 * Created by Lr on 2017/3/15.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    TextView tvFooter;
    public FooterViewHolder(View itemView) {
        super(itemView);
        tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
    }
}
