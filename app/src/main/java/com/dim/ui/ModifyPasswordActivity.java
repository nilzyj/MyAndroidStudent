package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
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
 * The type Modify password activity.
 */
public class ModifyPasswordActivity extends AppCompatActivity {

    private final String URL = HttpURL.url + "ModifyPasswordServlet";
    private final String TAG = "ModifyPasswordActivity:";
    /**
     * The M et old password.
     */
    @BindView(R.id.et_old_password)
    AppCompatEditText mEtOldPassword;
    /**
     * The M et new password.
     */
    @BindView(R.id.et_new_password)
    AppCompatEditText mEtNewPassword;
    /**
     * The M et new password repeat.
     */
    @BindView(R.id.et_new_password_repeat)
    AppCompatEditText mEtNewPasswordRepeat;
    /**
     * The M btn modify password commit.
     */
    @BindView(R.id.btn_modify_password_commit)
    AppCompatButton mBtnModifyPasswordCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        Log.d(TAG, "**********************************");
    }

    /**
     * On click modify password.
     */
    @OnClick(R.id.btn_modify_password_commit)
    public void onClickModifyPassword() {
        String oldPassword = mEtOldPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String newPasswordRepeat = mEtNewPasswordRepeat.getText().toString();
        if (newPasswordRepeat.equals(newPassword)) {
            SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", sp.getString("username", null));
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

    /**
     * The type Modify password task.
     */
    class ModifyPasswordTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            try {
                state = HttpUtil.httpPost(strings[0], "modifyData=" +
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
                Intent intent = getIntent();
                setResult(1, intent);
                finish();
            } else {
                Toast.makeText(ModifyPasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * Modify password back to function.
     *
     * @param view the view
     */
    public void modifyPasswordBackToFunction(View view) {
        finish();
    }
}
