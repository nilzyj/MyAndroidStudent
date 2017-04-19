package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dim.ui.http.HttpUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {

    private final String URL = "http://10.0.2.2:8080/Manage/GetInformationServlet";
//    private final String URL = "http://192.168.191.1:8080/Manage/GetInformationServlet";
    private JSONObject jsonObject;
    private String name;

    @BindView(R.id.btn_fill)
    Button mBtnFill;
    @BindView(R.id.btn_modify)
    Button mBtnModify;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.btn_exam)
    Button mBtnExam;
    @BindView(R.id.btn_grade)
    Button mBtnGrade;
    @BindView(R.id.btn_fushi)
    Button mBtnFushi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
        mBtnFill.setOnClickListener(this);
        mBtnModify.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnExam.setOnClickListener(this);
        mBtnGrade.setOnClickListener(this);
        mBtnFushi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fill:
                toOtherActivity(FillActivity.class);
                break;
            case R.id.btn_modify:
                SharedPreferences spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);
                name = spLoginData.getString("name", null);
                String[] strings_modify = {URL, name};
                ShowTask showTask = new ShowTask();
                showTask.execute(strings_modify);
                break;
            case R.id.btn_confirm:
                toOtherActivity(ConfirmActivity.class);
                break;
            case R.id.btn_exam:
                toOtherActivity(ExamActivity.class);
                break;
            case R.id.btn_grade:
                toOtherActivity(GradeActivity.class);
                break;
            case R.id.btn_fushi:
                toOtherActivity(FushiActivity.class);
                break;
        }
    }

    private void toOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(FunctionActivity.this, otherActivity);
        startActivity(intent);
    }

    //修改密码按钮响应
    @OnClick(R.id.btn_modify_password)
    public void modifyPassword() {
        Intent intent = new Intent(FunctionActivity.this, ModifyPasswordActivity.class);
        startActivity(intent);
    }

    //请求报考信息，并传给ModifyActivity
    class ShowTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtils.httpPost(strings[0], "name=" + URLEncoder.encode(strings[1],
                        "UTF-8"));
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
}
