package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

//登录界面
public class LoginActivity extends AppCompatActivity {

    //    private final String DB_NAME = "zyjdata.db";//数据库名称
//    private final int VERSION = 1;//数据库版本
//    private final String URL = "http://10.0.2.2:8080/Manage/test/LoginServlet";
    private final String URL = "http://192.168.191.1:8080/Manage/test/LoginServlet";
    private final String TAG = "LoginActivity";
    private EditText et_name;//用户名输入
    private EditText et_password;//密码输入
    private CheckBox checkBox;//记住用户名和密码
    private String name, password;
    SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("useInfo", MODE_PRIVATE);
        initView();

    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        //初始化checkbox状态和帐号密码
        if (sp.getBoolean("checkBoxBoolean", false)) {
            et_name.setText(sp.getString("name", null));
            et_password.setText(sp.getString("password", null));
            checkBox.setChecked(true);
            Log.d("TAG", "true");
        }
    }

    //登录按钮响应
    public void loginIn(View view) {
        name = et_name.getText().toString();
        Log.d(TAG, "onCreate: " + name);
        password = et_password.getText().toString();
        Log.d(TAG, "onCreate: " + password);
        if (name != null && password != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String state = "";
//                    try {
                        //登录请求，获得服务器返回状态码
//                        state = HttpUtils.httpPost(URL,
//                                "username=" + URLEncoder.encode("zyj", "UTF-8") +
//                                        "&pwd=" + URLEncoder.encode("1", "UTF-8"));
                        state = "1";
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }

                    //记住帐号密码
                    rememberPwd();

                    if ("1".equals(state)) {
                        Log.d("服务器返回状态码1，帐号密码正确", state);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (state == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "未连接到服务器",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Log.d("帐号或密码错误，或用户不存在", state);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "帐号或密码错误",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        } else {
            Toast.makeText(LoginActivity.this, "请输入用户名或密码",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void rememberPwd() {
        boolean CheckBoxLogin = checkBox.isChecked();
        if (CheckBoxLogin) {
            Log.d("TAG", "选中checkbox，将帐号密码存入，checkbox状态保存为true");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", name);
            editor.putString("password", password);
            editor.putBoolean("checkBoxBoolean", true);
            editor.apply();
        } else {
            Log.d("TAG", "未选中checkbox，清除存储的帐号密码，checkbox状态保存为true");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", null);
            editor.putString("password", null);
            editor.putBoolean("checkBoxBoolean", false);
            editor.apply();
        }
    }

    //注册按钮响应
    public void loginUp(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
