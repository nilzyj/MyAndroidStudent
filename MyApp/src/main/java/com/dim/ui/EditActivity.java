package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        Intent intent = getIntent();
        String mes = intent.getStringExtra("info") + ":" + intent.getStringExtra("content");
        editText.setText(mes);
        Toast.makeText(EditActivity.this, mes, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.et_str);
    }
}
