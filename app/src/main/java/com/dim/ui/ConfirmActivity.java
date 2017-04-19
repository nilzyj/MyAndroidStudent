package com.dim.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//信息核对界面
public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
    }

    public void confirmBackToFunction(View view) {
        finish();
    }
}
