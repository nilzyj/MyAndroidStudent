package com.dim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The type Confirm activity.
 * @author dim
 */
//信息核对界面
public class ConfirmActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState save
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
    }

    /**
     * Confirm back to function.
     *
     * @param view the view
     */
    public void confirmBackToFunction(View view) {
        finish();
    }
}
