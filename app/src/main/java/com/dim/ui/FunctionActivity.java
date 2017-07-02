package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Function activity.
 *
 * @author dim
 */
public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 获取已填写信息URL
     */
    private static final String URL = HttpURL.url + "GetInformationServlet";
    /**
     * TAG
     */
    private static String TAG = "FunctionActivity:";
    /**
     * 用于保存从SharedPreference中获取的username
     */
    private String username;

    /**
     * The M btn fill.
     */
    @BindView(R.id.btn_fill)
    LinearLayout mBtnFill;
    /**
     * The M btn modify.
     */
    @BindView(R.id.btn_modify)
    LinearLayout mBtnModify;
    /**
     * The M btn confirm.
     */
    @BindView(R.id.btn_confirm)
    LinearLayout mBtnConfirm;

    SharedPreferences spLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);

        Log.d(TAG, "**********************************");

        spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);


        mBtnFill.setOnClickListener(this);
        mBtnModify.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fill:
                Log.d(TAG, "进入FillActivity");
                if (spLoginData.getString("systemState", "").equals("1")) {
                    if (spLoginData.getBoolean("isFill", false)) {
                        Toast.makeText(this, "已填写报考信息", Toast.LENGTH_SHORT).show();
                    } else if (spLoginData.getString("invalid", "").equals("有违规行为，无法参加考试")) {
                        Toast.makeText(this, "因具有违规行为，无法参加报考", Toast.LENGTH_SHORT).show();
                    } else {
                        functionToOtherActivity(FillActivity.class);
                    }
                } else {
                    Toast.makeText(this, "报考系统关闭", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_modify:
                Log.d(TAG, "进入ModifyActivity");
                if (spLoginData.getString("systemState", "").equals("1")) {
                    if (spLoginData.getString("confirm", "").equals("0")) {
                        if (spLoginData.getBoolean("isFill", false)) {
                            username = spLoginData.getString("username", null);
                            String[] stringsModify = {URL, username};
                            ShowTask showTask = new ShowTask();
                            showTask.execute(stringsModify);
                        } else {
                            Toast.makeText(this, "未提交报考信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "报考信息已确认，无法修改", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "报考系统关闭", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_confirm:
                Log.d(TAG, "进入FingerActivity");
                if (spLoginData.getString("systemState", "").equals("1")) {
                    if (spLoginData.getBoolean("isFill", false)) {
                        username = spLoginData.getString("username", null);
                        String[] stringsConfirm = {URL, username};
                        ConfirmTask confirmTask = new ConfirmTask();
                        confirmTask.execute(stringsConfirm);
                    } else {
                        Toast.makeText(this, "未提交报考信息", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "报考系统关闭", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * @param otherActivity 跳转到其他Activity
     */
    private void functionToOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(FunctionActivity.this, otherActivity);
        startActivity(intent);
    }

    /**
     * Modify password.
     * 修改密码按钮响应
     */
    @OnClick(R.id.btn_modify_password)
    public void modifyPassword() {
        Log.d(TAG, "点击修改密码");
        Intent intent = new Intent(FunctionActivity.this, ModifyPasswordActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * On click log off.
     */
    @OnClick(R.id.btn_log_off)
    public void onClickLogOff() {
        finish();
    }

    /**
     * The type Show task.
     * 请求报考信息，并传给ModifyActivity
     */
    class ShowTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0], "username=" + URLEncoder.encode(strings[1],
                        "UTF-8"));
                Log.d(TAG, "从服务器获取的报考信息：" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(FunctionActivity.this, ModifyActivity.class);
            intent.putExtra("json", s);
            startActivity(intent);
        }
    }

    class ConfirmTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0], "username=" + URLEncoder.encode(strings[1],
                        "UTF-8"));
                Log.d(TAG, "从服务器获取的报考信息：" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(FunctionActivity.this, ConfirmActivity.class);
            intent.putExtra("json", s);
            startActivity(intent);
        }
    }
}
