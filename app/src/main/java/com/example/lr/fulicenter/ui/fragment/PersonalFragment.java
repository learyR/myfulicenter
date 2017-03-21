package com.example.lr.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.FuLiCenterApplication;
import com.example.lr.fulicenter.model.bean.User;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvCollect)
    TextView tvCollect;

    MainActivity mContext;
    User user;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
//            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivAvatar);
            tvUserName.setText(user.getMuserNick());
        }
    }

}
