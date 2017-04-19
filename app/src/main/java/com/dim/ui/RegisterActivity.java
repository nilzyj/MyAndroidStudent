package com.dim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dim.ui.http.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private final String URL = "http://10.0.2.2:8080/Manage/RegisterServlet";
//    private final String URL = "http://10.0.2.2:8080/Manage/RegisterServlet";
    private EditText editText3;
    private EditText editText4;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);

        initView();
    }

    private void initView() {
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button);
    }

    public void register(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = null;
                try {
                    str = HttpUtils.httpPost(URL,
                        "username=" + URLEncoder.encode(editText3.getText().toString(), "UTF-8") +
                        "&pwd=" + URLEncoder.encode(editText4.getText().toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.d("TAG", str);
            }
        }).start();
    }
}
