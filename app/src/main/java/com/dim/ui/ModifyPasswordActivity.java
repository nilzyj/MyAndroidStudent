package com.dim.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.dim.ui.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends AppCompatActivity {

    private final String URL = "http://10.0.2.2:8080/Manage/ModifyPasswordServlet";
    //    private final String URL = "http://10.0.2.2:8080/Manage/ModifyPasswordServlet";
    @BindView(R.id.et_old_password)
    AppCompatEditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    AppCompatEditText mEtNewPassword;
    @BindView(R.id.et_new_password_repeat)
    AppCompatEditText mEtNewPasswordRepeat;
    @BindView(R.id.btn_modify_password_commit)
    AppCompatButton mBtnModifyPasswordCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_modify_password_commit)
    public void onClickModifyPassword() {
        String oldPassword = mEtOldPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String newPasswordRepeat = mEtNewPasswordRepeat.getText().toString();
        if (newPasswordRepeat.equals(newPassword)) {
            SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", sp.getString("name", null));
                jsonObject.put("oldPassword", oldPassword);
                jsonObject.put("newPassword", newPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String[] strings = {URL, jsonObject.toString()};
            ModifyPasswordTask modifyPasswordTask = new ModifyPasswordTask();
            modifyPasswordTask.execute(strings);
        } else {
            Toast.makeText(this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
        }
    }

    class ModifyPasswordTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            try {
                state = HttpUtils.httpPost(strings[0], "modifyData=" +
                        URLEncoder.encode(strings[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("1".equals(s)) {
                Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ModifyPasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void ModifyPasswordBackToFunction(View view) {
        finish();
    }
}
