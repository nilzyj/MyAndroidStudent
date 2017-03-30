package com.dim.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dim.ui.fragment.ConfirmFragment;
import com.dim.ui.fragment.ManagementFragment;


public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_management, ll_confirm;
    private FrameLayout frame;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, new ManagementFragment());
        transaction.commit();

        ll_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content, new ManagementFragment());
                transaction.commit();
            }
        });

        ll_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content, new ConfirmFragment());
                transaction.commit();
            }
        });

    }

    private void initView() {
        ll_management = (LinearLayout) findViewById(R.id.ll_management);
        ll_confirm = (LinearLayout) findViewById(R.id.ll_confirm);
        frame = (FrameLayout) findViewById(R.id.fl_content);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }
}
