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

import static com.dim.ui.util.HttpUtil.httpPost;

/**
 * The type Login activity.
 * @author dim
 */
//登录界面
public class LoginActivity extends AppCompatActivity {
    /**
     * 登录URL.
     */
    private static final String URL = HttpURL.url + "LoginServlet";
    /**
     * TAG.
     */
    private static final String TAG = "LoginActivity：";
    /**
     * 用户名输入.
     */
    @BindView(R.id.et_username)
    EditText mEtUsername;
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
    /**
     * 保存从EditText获得的用户名.
     */
    private String username;
    /** 保存从EditText获得的密码. */
    private String password;
    /**
     * 保存姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String stu_id;
    /**
     * 证件类型
     */
    private String stu_id_type;
    /** 用于保存密码. */
    private SharedPreferences sp = null;
    /** 登录后保存姓名. */
    private SharedPreferences spLoginData = null;
    /** 用于保存违规信息 */
    String invalidInfo = null;
    /** 报考系统状态 */
    String systemState;
    /** 是否确认报考信息 */
    String confirm;
    /** 报考号 */
    String baokaohao;

    /**
     * @param savedInstanceState save
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Log.d(TAG, "**********************************");

        // 记住用户名和密码
        sp = this.getSharedPreferences("useInfo", MODE_PRIVATE);
        // 保存登录数据
        spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);
        init();
    }

    /**
     * 初始化checkbox状态和帐号密码.
     */
    private void init() {
        Log.d(TAG, "初始化checkbox状态、用户名和密码");
        Log.d(TAG, "初始化的数值：username=" + sp.getString("username", ""));
        Log.d(TAG, "初始化的数值：password=" + sp.getString("password", ""));
        if (sp.getBoolean("checkBoxBoolean", false)) {
            mEtUsername.setText(sp.getString("username", ""));
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
        username = mEtUsername.getText().toString();
        password = mEtPassword.getText().toString();
        Log.d(TAG, "输入用户名和密码为：" + username + "," + password);
        //用户名密码都不为空
        if (!"".equals(username) && !"".equals(password)) {
            try {
                //用户名密码存为json格式
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username);
                jsonObject.put("password", password);

                Log.d(TAG, "登录上传json信息：" + jsonObject.toString());
                String[] data = {URL, jsonObject.toString()};
                // 登录Task
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
     * The type Login task.
     */
    class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            //登录请求，获得服务器返回状态码
            try {
                state = httpPost(strings[0], "loginData="
                        + URLEncoder.encode(strings[1], "UTF-8"));
                Log.d(TAG, "登录，服务器返回数据：" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            //记住帐号密码
            rememberPwd();
            //获取违规信息
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        invalidInfo = HttpUtil.httpPost(HttpURL.url + "GetInvalidInfoServlet",
                                "username=" + URLEncoder.encode(username, "UTF-8"));
                        Log.d(TAG, "返回违规信息：" + invalidInfo);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //根据服务器返回数据判断是否登录成功
                    try {
                        if (s == null) {
                            Log.d(TAG, "未连接到服务器");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "未连接到服务器",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            //返回数据转为json
                            Log.d(TAG, "登录服务器返回数据：" + s);
                            JSONObject jsonObject = new JSONObject(s);
                            name = jsonObject.getString("name");
                            password = jsonObject.getString("password");
                            stu_id = jsonObject.getString("stu_id");
                            stu_id_type = jsonObject.getString("stu_id_type");
                            systemState = jsonObject.getString("systemState");
                            confirm = jsonObject.getString("confirm");
                            baokaohao = jsonObject.getString("baokaohao");

                            if ("1".equals(jsonObject.getString("state"))) {
                                Log.d(TAG, "返回1，正确，未填写");
                                putInfoToSP(name, username, systemState, confirm, stu_id, stu_id_type
                                        , password, invalidInfo, false, baokaohao);
                                Log.d(TAG, "登录成功，跳转到FunctionActivity");
                                loginToOtherActivity(FunctionActivity.class);
                            } else if ("2".equals(jsonObject.getString("state"))) {
                                Log.d(TAG, "返回2，正确，已填写");
                                putInfoToSP(name, username, systemState, confirm, stu_id, stu_id_type
                                        , password, invalidInfo, true, baokaohao);
                                Log.d(TAG, "登录成功，跳转到FunctionActivity");
                                loginToOtherActivity(FunctionActivity.class);
                            } else {
                                Log.d(TAG, "帐号或密码错误");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "帐号或密码错误",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * SharedPreferences记住密码
     */
    private void rememberPwd() {
        boolean checkBoxLogin = mCheckBox.isChecked();
        if (checkBoxLogin) {
            Log.d(TAG, "选中checkbox，将帐号密码存入，checkbox状态保存为true");
            saveUsernameAndPassword(username, password, true);
        } else {
            Log.d(TAG, "未选中checkbox，清除存储的帐号密码，checkbox状态保存为false");
            saveUsernameAndPassword(null, null, false);
        }
    }

    /**
     * 保存username和password到SharedPreference，记住密码用
     * @param username            保存的name
     * @param password        保存的password
     * @param checkBoxBoolean 保存的CheckBox状态
     */
    private void saveUsernameAndPassword(String username, String password, boolean checkBoxBoolean) {
        SharedPreferences.Editor editor = sp.edit();
        Log.d(TAG, "保存的用户名和密码：" + username + ", " + password);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("checkBoxBoolean", checkBoxBoolean);
        editor.apply();
    }

    /**
     * 将姓名和是否填写信息存入SharedPreference，保存用户名、填写信息状态、违规行为状态用
     * @param name
     * @param username   用户名
     * @param isFill 是否填写信息
     */
    private void putInfoToSP(String name, String username, String systemState, String confirm
            , String stu_id, String stu_id_type, String password, String invalid, boolean isFill
            , String baokaohao) {
        SharedPreferences.Editor editor = spLoginData.edit();
        Log.d(TAG, "保存到sp中的信息：name=" + name + ", username=" + username
                + ", invalid=" + invalid + ", isFill=" + isFill);
        editor.putString("name", name);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("confirm", confirm);
        Log.d(TAG, "putInfoToSP: " + confirm);
        editor.putString("systemState", systemState);
        editor.putString("stu_id", stu_id);
        editor.putString("stu_id_type", stu_id_type);
        editor.putString("invalid", invalid);
        editor.putString("baokaohao", baokaohao);
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

    /**
     * 注册按钮响应，跳转到注册页面
     */
    @OnClick(R.id.btn_login_up)
    public void loginUp() {
        loginToOtherActivity(RegisterActivity.class);
    }
}
