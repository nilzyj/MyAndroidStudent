package com.dim.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Grade activity.
 * @author dim
 */
//成绩查询界面
public class GradeActivity extends AppCompatActivity {
// TODO delete
    /**
     *
     */
    private final String URL = HttpURL.url + "GradeInfoServlet";
    /**
     * The M tv grade shuxue.
     */
    @BindView(R.id.tv_grade_shuxue)
    TextView mTvGradeShuxue;
    /**
     * The M tv grade yinyu.
     */
    @BindView(R.id.tv_grade_yinyu)
    TextView mTvGradeYinyu;
    /**
     * The M tv grade zhengzhi.
     */
    @BindView(R.id.tv_grade_zhengzhi)
    TextView mTvGradeZhengzhi;
    /**
     * The M tv grade zhuanye.
     */
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

    /**
     * Grade back to function.
     *
     * @param view the view
     */
    public void gradeBackToFunction(View view) {
        finish();
    }

    /**
     * The type Grade info task.
     */
    class GradeInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            try {
                state = HttpUtil.httpPost(strings[0], "name=" +
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
                mTvGradeShuxue.setText("数学：" + jsonObject.get("shuxue"));
                mTvGradeYinyu.setText("英语：" + jsonObject.get("yinyu"));
                mTvGradeZhengzhi.setText("政治：" + jsonObject.get("zhengzhi"));
                mTvGradeZhuanye.setText("专业课：" + jsonObject.get("zhuanye"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onResume();
        }
    }
}
