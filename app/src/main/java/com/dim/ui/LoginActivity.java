package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Login activity.
 * @author dim
 */
//登录界面
public class LoginActivity extends AppCompatActivity {
    /** 登录URL. */
    private static final String URL = HttpURL.url + "LoginServlet";
    /** TAG. */
    private static final String TAG = "LoginActivity";
    /** 用户名输入. */
    @BindView(R.id.et_name)
    EditText mEtName;
    /** 密码输入. */
    @BindView(R.id.et_password)
    EditText mEtPassword;
    /** 记住用户名和密码. */
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    /** 登录按钮 */
    @BindView(R.id.btn_login_in)
    Button mLoginIn;
    /** 注册按钮 */
    @BindView(R.id.btn_login_up)
    Button mLoginUp;
    /** 从EditText获得的用户名. */
    private String name;
    /** 从EditText获得的密码. */
    private String password;
    /** 用于保存密码. */
    private SharedPreferences sp = null;
    /** 登录后保存姓名. */
    private SharedPreferences spLoginData = null;

    /**
     * @param savedInstanceState save
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sp = this.getSharedPreferences("useInfo", MODE_PRIVATE);
        spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);
        Log.d(TAG, "onCreate: init()");
        init();
    }

    /**
     * 初始化checkbox状态和帐号密码.
     */
    private void init() {
        //checkBoxBoolean为false，不保存密码
//        sp.edit().putBoolean("checkBoxBoolean", false).apply();
        if (sp.getBoolean("checkBoxBoolean", false)) {
            mEtName.setText(sp.getString("name", ""));
            mEtPassword.setText(sp.getString("password", ""));
            mCheckBox.setChecked(true);
        }
    }

    /**
     * 登录按钮响应
     */
    @OnClick(R.id.btn_login_in)
    public void loginIn() {
        //获取输入用户名和密码
        name = mEtName.getText().toString();
        password = mEtPassword.getText().toString();
        //用户名密码都不为空
        if (!"".equals(name) && !"".equals(password)) {
            try {
                //用户名密码存为json格式
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("password", password);
                String[] data = {URL, jsonObject.toString()};
                LoginTask loginTask = new LoginTask();
                loginTask.execute(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(LoginActivity.this, "请输入用户名或密码",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册按钮响应，跳转到注册页面
     */
    @OnClick(R.id.btn_login_up)
    public void loginUp() {
        loginToOtherActivity(RegisterActivity.class);
    }



    /**
     * The type Login task.
     */
    class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            //登录请求，获得服务器返回状态码
            try {
                state = HttpUtil.httpPost(strings[0], "loginData="
                        + URLEncoder.encode(strings[1], "UTF-8"));
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
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            //记住帐号密码
            rememberPwd();
            if ("1".equals(s)) {
                Log.d("返回1，正确，未填写", s);
                if (spLoginData.getString("name", "").isEmpty()) {
                    putInfoToSP(name, false);
                }
                loginToOtherActivity(FunctionActivity.class);
            } else if ("2".equals(s)) {
                Log.d(TAG, "返回2，正确，已填写");
                if (spLoginData.getString("name", "").isEmpty()) {
                    putInfoToSP(name, true);
                }
                loginToOtherActivity(FunctionActivity.class);
            } else if (s == null) {
                Toast.makeText(LoginActivity.this, "未连接到服务器",
                        Toast.LENGTH_SHORT).show();
            } else {
                Log.d("帐号或密码错误，或用户不存在", s);
                Toast.makeText(LoginActivity.this, "帐号或密码错误",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * SharedPreferences记住密码
     */
    private void rememberPwd() {
        boolean checkBoxLogin = mCheckBox.isChecked();
        if (checkBoxLogin) {
            Log.d("TAG", "选中checkbox，将帐号密码存入，checkbox状态保存为true");
            saveNameAndPassword(name, password, true);
        } else {
            Log.d("TAG", "未选中checkbox，清除存储的帐号密码，checkbox状态保存为false");
            saveNameAndPassword(null, null, false);
        }
    }

    /**
     * 保存name和password到SharedPreference
     * @param name 保存的name
     * @param password 保存的password
     * @param checkBoxBoolean 保存的CheckBox状态
     */
    private void saveNameAndPassword(String name, String password, boolean checkBoxBoolean) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putBoolean("checkBoxBoolean", checkBoxBoolean);
        editor.apply();
    }

    /**
     * 将姓名和是否填写信息存入SharedPreference
     * @param name 用户名
     * @param isFill 是否填写信息
     */
    private void putInfoToSP(String name, boolean isFill) {
        SharedPreferences.Editor editor = spLoginData.edit();
        editor.putString("name", name);
        //isFill，false未填写报考信息，true已填写
        editor.putBoolean("isFill", isFill);
        editor.apply();
    }

    /**
     * LoginActivity跳转到其他Activity
     * @param otherActivity 其他界面
     */
    private void loginToOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(LoginActivity.this, otherActivity);
        startActivity(intent);
    }
}
