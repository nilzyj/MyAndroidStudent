package com.dim.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

//成绩查询界面
public class GradeActivity extends AppCompatActivity {

    private final String URL = "http://10.0.2.2:8080/Manage/GradeInfoServlet";
//    private final String URL = "http://192.168.191.1:8080/Manage/GradeInfoServlet";
    @BindView(R.id.tv_grade_shuxue)
    TextView mTvGradeShuxue;
    @BindView(R.id.tv_grade_yinyu)
    TextView mTvGradeYinyu;
    @BindView(R.id.tv_grade_zhengzhi)
    TextView mTvGradeZhengzhi;
    @BindView(R.id.tv_grade_zhuanye)
    TextView mTvGradeZhuanye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        String[] strings = {URL, name};
        GradeInfoTask gradeInfoTask = new GradeInfoTask();
        gradeInfoTask.execute(strings);
    }

    public void gradeBackToFunction(View view) {
        finish();
    }

    class GradeInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            try {
                state = HttpUtils.httpPost(strings[0], "name=" +
                        URLEncoder.encode(strings[1], "UTF-8"));
                Log.d("http", "doInBackground: " + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(GradeActivity.this, "test" + s, Toast.LENGTH_SHORT).show();
                Log.d("test", "onPostExecute: " + s);
                mTvGradeShuxue.setText("数学考试时间：" + jsonObject.get("shuxue_time") +
                        ",地点：" + jsonObject.get("shuxue_addr"));
                mTvGradeYinyu.setText("英语考试时间：" + jsonObject.get("yinyu_time") +
                        ",地点：" + jsonObject.get("yinyu_addr"));
                mTvGradeZhengzhi.setText("政治考试时间：" + jsonObject.get("zhengzhi_time") +
                        ",地点：" + jsonObject.get("zhengzhi_addr"));
                mTvGradeZhuanye.setText("专业课考试时间：" + jsonObject.get("zhuanye_time") +
                        ",地点：" + jsonObject.get("zhuanye_addr"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onResume();
        }
    }
}
