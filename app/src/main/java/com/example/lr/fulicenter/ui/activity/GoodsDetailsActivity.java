package com.example.lr.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.application.I;
import com.example.lr.fulicenter.model.bean.AlbumsBean;
import com.example.lr.fulicenter.model.bean.GoodsDetailsBean;
import com.example.lr.fulicenter.model.net.GoodsModel;
import com.example.lr.fulicenter.model.net.IGoodsModel;
import com.example.lr.fulicenter.model.net.OnCompleteListener;
import com.example.lr.fulicenter.model.utils.CommonUtils;
import com.example.lr.fulicenter.model.utils.L;
import com.example.lr.fulicenter.model.utils.MFGT;
import com.example.lr.fulicenter.ui.view.FlowIndicator;
import com.example.lr.fulicenter.ui.view.SlideAutoLoopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailsActivity extends AppCompatActivity {
    int goodsId;
    IGoodsModel model;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvGoodsDetails)
    TextView tvGoodsDetails;
    @BindView(R.id.ivGoodsDetailCart)
    ImageView ivGoodsDetailCart;
    @BindView(R.id.ivGoodsDetailCollect)
    ImageView ivGoodsDetailCollect;
    @BindView(R.id.ivGoodsDetailShare)
    ImageView ivGoodsDetailShare;
    @BindView(R.id.tvEnglishName)
    TextView tvEnglishName;
    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;
    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.wbGoodsBrief)
    WebView wbGoodsBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.i("details" + goodsId);
        if (goodsId == 0) {
            MFGT.finish(this);
            return;
        }
        initView();
        initData();
    }

    private void initView() {
        model = new GoodsModel();
    }

    private void initData() {
        model.downloadGoodsDetails(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        tvEnglishName.setText(details.getGoodsEnglishName());
        tvGoodsName.setText(details.getGoodsName());
        tvGoodsPrice.setText(details.getCurrencyPrice());
        salv.startPlayLoop(indicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        wbGoodsBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
