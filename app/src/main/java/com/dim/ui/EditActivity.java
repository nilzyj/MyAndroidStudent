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

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PinYinUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Edit activity.
 * @author dim
 */
public class EditActivity extends AppCompatActivity {

    /** 修改并更新报考信息URL. */
    private String URL = HttpURL.url + "UpdateJsonDataServlet";
    /** 传递数据的intent. */
    private Intent dataIntent;
    /** 将修改的数据名称. */
    private String infoName;
    /** 将修改的数据内容. */
    private String info;
    /**  */
    private String infoUpdate;
    /** 信息内容. */
    @BindView(R.id.etInfo)
    EditText mEtInfo;
    /** 标题即信息名称. */
    @BindView(R.id.tvInfoName)
    TextView mTvInfoName;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

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

    /**
     * Save edit.
     * 保存更改按钮点击
     * @param view the view
     * @throws JSONException the json exception
     */
    public void saveEdit(View view) throws JSONException {
        //获取修改后的信息
        infoUpdate = mEtInfo.getText().toString();
        //获取sharedPreferences中的name，便于更新数据库
        SharedPreferences sharedPreferences =
                getSharedPreferences("loginData", MODE_PRIVATE);
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

    /**
     * The type Edit task.
     * 修改后提交更新
     */
    class EditTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0],
                        "updateData=" + URLEncoder.encode(strings[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                Toast.makeText(EditActivity.this, "未连接服务器",
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent updateIntent = new Intent();
                updateIntent.putExtra("info", infoUpdate);
                setResult(2, updateIntent);
                finish();
            }

        }
    }

    /**
     * Back to change.
     * 返回按钮点击-LinearLayout
     * @param view the view
     */
    public void backToChange(View view) {
        finish();
    }
}
