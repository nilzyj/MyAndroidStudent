package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.PinYinUtil.PinYinUtil;
import com.dim.ui.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EditActivity extends AppCompatActivity {
    private String URL = "http://10.0.2.2:8080/Manage/UpdateJsonDataServlet";
//    private String URL = "http://192.168.191.1:8080/Manage/UpdateJsonDataServlet";
    private EditText mEtInfo;//信息内容
    private TextView mTvInfoName;//标题即信息名称
    private Intent dataIntent;//传递数据的intent
    private String infoName;//将修改的数据名称
    private String info;//将修改的数据内容
    private String infoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        //获得ChangeActivity传来的数据
        dataIntent = getIntent();
        infoName = dataIntent.getStringExtra("infoName");
        info = dataIntent.getStringExtra("info");
        //初始化EditActivity数据
        mEtInfo.setText(info);
        mTvInfoName.setText(infoName);
        //提示信息(temp)
        Toast.makeText(EditActivity.this, info, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mEtInfo = (EditText) findViewById(R.id.etInfo);
        mTvInfoName = (TextView) findViewById(R.id.tvInfoName);
    }

    //保存更改按钮点击
    public void saveEdit(View view) throws JSONException {
        //获取修改后的信息
        infoUpdate = mEtInfo.getText().toString();
        //获取sharedPreferences中的name，便于更新数据库
        SharedPreferences sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        //将要更新的数据转为json
        JSONObject jsonObject = new JSONObject();
        PinYinUtil pinyin = new PinYinUtil();
        jsonObject.put("infoName", pinyin.getStringPinYin(infoName));
        jsonObject.put("info", infoUpdate);
        jsonObject.put("name", sharedPreferences.getString("name", null));

        String[] strings = {URL, jsonObject.toString()};
        //更新数据库
        EditTask editTask = new EditTask();
        editTask.execute(strings);
    }

    class EditTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtils.httpPost(strings[0], "updateData=" + URLEncoder.encode(strings[1],
                        "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent updateIntent = new Intent();
            updateIntent.putExtra("info", infoUpdate);
            setResult(2, updateIntent);
            finish();
        }
    }

    //返回按钮点击-LinearLayout
    public void backToChange(View view) {
        finish();
    }
}
