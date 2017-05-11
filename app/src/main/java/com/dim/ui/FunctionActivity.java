package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Function activity.
 * @author dim
 */
public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {
    /** 获取已填写信息URL */
    private static final String URL = HttpURL.url + "GetInformationServlet";
    /** TAG */
    private static String TAG = "function";
    /** 用于保存从SharedPreference中获取的name */
    private String name;

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
    /**
     * The M btn exam.
     */
//    @BindView(R.id.btn_exam)
//    Button mBtnExam;
    /**
     * The M btn grade.
     */
//    @BindView(R.id.btn_grade)
//    Button mBtnGrade;
    /**
     * The M btn fushi.
     */
//    @BindView(R.id.btn_fushi)
//    Button mBtnFushi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);

        mBtnFill.setOnClickListener(this);
        mBtnModify.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
//        mBtnExam.setOnClickListener(this);
//        mBtnGrade.setOnClickListener(this);
//        mBtnFushi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fill:
                Log.d(TAG, "onClick: fill");
                // TODO 若已经填写信息，则不能跳转。
//                SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
//                if (sp.getBoolean("isFill", false)) {
                    functionToOtherActivity(FillActivity.class);
//                } else {
//                    Toast.makeText(this, "已填写报考信息", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.btn_modify:
                Log.d(TAG, "onClick: modify");
                SharedPreferences spLoginData = this.getSharedPreferences("loginData", MODE_PRIVATE);
                name = spLoginData.getString("name", null);
                String[] stringsModify = {URL, name};
                ShowTask showTask = new ShowTask();
                showTask.execute(stringsModify);
                break;
            case R.id.btn_confirm:
                Log.d(TAG, "onClick: confirm");
//                functionToOtherActivity(ConfirmActivity.class);
                functionToOtherActivity(FingerActivity.class);

                break;
//            case R.id.btn_exam:
//                Log.d(TAG, "onClick: exam");
//                functionToOtherActivity(ExamActivity.class);
//                break;
//            case R.id.btn_grade:
//                Log.d(TAG, "onClick: grade");
//                functionToOtherActivity(GradeActivity.class);
//                break;
//            case R.id.btn_fushi:
//                Log.d(TAG, "onClick: fushi");
//                functionToOtherActivity(FushiActivity.class);
//                break;
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
        Log.d(TAG, "modifyPassword: 修改密码");
        Intent intent = new Intent(FunctionActivity.this, ModifyPasswordActivity.class);
        startActivity(intent);
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
                state = HttpUtil.httpPost(strings[0], "name=" + URLEncoder.encode(strings[1],
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
