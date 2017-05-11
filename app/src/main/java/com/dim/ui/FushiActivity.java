package com.dim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The type Fushi activity.
 * @author dim
 */
//复试查询界面
public class FushiActivity extends AppCompatActivity {
// TODO delete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fushi);

    }


    /**
     * Fushi back to function.
     *
     * @param view the view
     */
    public void fushiBackToFunction(View view) {
        finish();
    }
}
