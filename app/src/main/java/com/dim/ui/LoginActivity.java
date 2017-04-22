package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dim.ui.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//登录界面
public class LoginActivity extends AppCompatActivity {
    private final String URL = "http://10.0.2.2:8080/Manage/LoginServlet";
//    private final String URL = "http://192.168.191.1:8080/Manage/LoginServlet";
    private final String TAG = "LoginActivity";
    private EditText et_name;//用户名输入
    private EditText et_password;//密码输入
    private CheckBox checkBox;//记住用户名和密码
    private String name, password;//获得的用户名和密码
    SharedPreferences sp = null;//记住密码所用
    SharedPreferences spLoginData = null;//全局保存姓名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("useInfo", MODE_PRIVATE);
        spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);
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
    public void loginIn(View view) throws JSONException, IOException {
        String state = "";
        //获取输入用户名和密码
        name = et_name.getText().toString();
        password = et_password.getText().toString();
        //用户名密码都不为空
        if (name != null && password != null) {
            //用户名密码存为json格式
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            String[] data = {URL, jsonObject.toString()};
            LoginTask loginTask = new LoginTask();
            loginTask.execute(data);
        } else {
            Toast.makeText(LoginActivity.this, "请输入用户名或密码",
                    Toast.LENGTH_SHORT).show();
        }
    }

    class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            //登录请求，获得服务器返回状态码
            try {
                state = HttpUtils.httpPost(strings[0], "loginData=" +
                        URLEncoder.encode(strings[1], "UTF-8"));
//                state = HttpUtils.httpPost(strings[0], "loginData=" +
//                URLEncoder.encode("中文", "UTF-8"));
                Log.d(TAG, "doInBackground: " + state);
//                state = "2";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LoginActivity.this, "返回:" + s, Toast.LENGTH_SHORT).show();
            //记住帐号密码
            rememberPwd();
            if ("1".equals(s)) {
                Log.d("服务器返回状态码1，帐号密码正确，进入填写", s);
                //用户名存入
                if (spLoginData.getString("name", "").isEmpty()) {
                    SharedPreferences.Editor editor = spLoginData.edit();
                    editor.putString("name", "zyj");
                    editor.putBoolean("isFill", false);
                    editor.apply();
                }
                Log.d(TAG, "onPostExecute: " + "跳转到FunctionActivity");
                Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                startActivity(intent);
            } else if ("2".equals(s)) {
                Log.d(TAG, "服务器返回状态码2，正确，已填写");
                //用户名存入
                if (spLoginData.getString("name", "").isEmpty()) {
                    SharedPreferences.Editor editor = spLoginData.edit();
                    editor.putString("name", "zyj");
                    editor.putBoolean("ifFill", true);
                    editor.apply();
                }
                Log.d(TAG, "onPostExecute: " + "跳转到FunctionActivity");
                Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                startActivity(intent);
            }
            else if (s == null) {
                Toast.makeText(LoginActivity.this, "未连接到服务器",
                        Toast.LENGTH_SHORT).show();
            } else {
                Log.d("帐号或密码错误，或用户不存在", s);
                Toast.makeText(LoginActivity.this, "帐号或密码错误",
                        Toast.LENGTH_SHORT).show();
            }
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
            Log.d("TAG", "未选中checkbox，清除存储的帐号密码，checkbox状态保存为false");
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
