package com.yzb.card.king.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScaleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScaleView scaleView = new ScaleView(this, getIntent().getStringExtra("image"));
        setContentView(scaleView);

    }
}
