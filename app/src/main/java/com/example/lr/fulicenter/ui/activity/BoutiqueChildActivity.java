package com.example.lr.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.BoutiqueBean;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.ui.fragment.NewGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoutiqueChildActivity extends AppCompatActivity {

    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
        BoutiqueBean bean = (BoutiqueBean) getIntent().getSerializableExtra(I.Boutique.CAT_ID);
        if (bean != null) {
            tvGoodsName.setText(bean.getTitle());
        }
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
