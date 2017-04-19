package com.dim.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//复试查询界面
public class FushiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fushi);
    }

    public void fushiBackToFunction(View view) {
        finish();
    }
}
