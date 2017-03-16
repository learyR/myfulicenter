package com.example.lr.fulicenter.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lr.fulicenter.R;
import com.example.lr.fulicenter.ui.fragment.NewGoodsFragment;

public class BoutiqueChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
    }
}
