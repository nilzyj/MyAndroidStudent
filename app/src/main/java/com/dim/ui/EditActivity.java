package com.dim.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PickerUtil;
import com.dim.ui.util.PinYinUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Edit activity.
 *
 * @author dim
 */
public class EditActivity extends AppCompatActivity {

    private final String TAG = "EditActivity";
    /**
     * 修改并更新报考信息URL.
     */
    private String URL = HttpURL.url + "UpdateJsonDataServlet";
    /**
     * 传递数据的intent.
     */
    private Intent dataIntent;
    /**
     * 将修改的数据名称.
     */
    private String infoName;
    /**
     * 将修改的数据内容.
     */
    private String info;
    /**  */
    private String infoUpdate;
    /**
     * 标题,即信息名称.
     */
    @BindView(R.id.tv_info_name)
    TextView mTvInfoName;
    /**
     * 信息内容.
     */
    @BindView(R.id.et_info)
    EditText mEtInfo;
    /**
     * 编辑数字或电子邮箱
     */
    @BindView(R.id.et_number_and_email)
    EditText mEtNumberAndEmail;
    /**
     * 编辑地址
     */
    @BindView(R.id.tv_info_address)
    TextView mTvInfoAddress;
    @BindView(R.id.et_info_address)
    EditText mEtInfoAddress;
    @BindView(R.id.v_info_address)
    View mVInfoAddress;
    @BindView(R.id.ll_info_address)
    LinearLayout mLlInfoAddress;

    private String[] tvAndEtInfo = {"户口所在地详细地址", "考生档案所在单位地址", "考生通讯地址"};
    private String[] etInfoNumberAndEmail = {"考生档案所在单位邮政编码", "考生通讯地址邮政编码", "固定电话"
            , "移动电话", "电子邮箱", "毕业证书编号", "注册学号", "学位证书编号"};
    private String[] etInfo = {"考生档案所在地", "现在学习或工作单位", "学习与工作经历"
            , "何时何地何原因受过何种奖励或处分", "考生作弊情况", "家庭主要成员", "毕业学校", "毕业专业"};

    //报考单位及以下未添加
    private List<String> mListTvAndEtInfo = Arrays.asList(tvAndEtInfo);
    private List<String> mListEtInfoNumberAndEmail = Arrays.asList(etInfoNumberAndEmail);
    private List<String> mListEtInfo = Arrays.asList(etInfo);

    private int flag = 0;

    /**
     * @param savedInstanceState save
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        // TODO editActivity点击保存返回modifyactivity时标题栏有问题、、、、
        // TODO 定向就业单位EditText填写
        // TODO 报考院系、研究方向、政治理论、外国语、业务课、备用信息数据源
        // TODO 电子邮箱格式判断
        //获得ChangeActivity传来的数据
        dataIntent = getIntent();
        infoName = dataIntent.getStringExtra("infoName");
        info = dataIntent.getStringExtra("info");
        //初始化EditActivity数据
        mTvInfoName.setText(infoName);
//      "考生作弊情况", "家庭主要成员", "毕业学校", "毕业专业"
        if (mListEtInfoNumberAndEmail.contains(infoName)) {
            //            "考生档案所在单位邮政编码", "考生通讯地址邮政编码", "固定电话"
//                    , "移动电话", "电子邮箱", "毕业证书编号", "注册学号", "学位证书编号"
            flag = 1;
            Log.d(TAG, "onCreate: flag1");
            mEtNumberAndEmail.setVisibility(View.VISIBLE);
            mEtNumberAndEmail.setText(info);
            switch (infoName) {
                case "考生档案所在单位邮政编码":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    break;
                case "考生通讯地址邮政编码":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    break;
                case "毕业证书编号":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                    break;
                case "学位证书编号":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                    break;
                case "注册学号":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                    break;
                case "移动电话":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    break;
                case "固定电话":
                    mEtNumberAndEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    break;
                case "电子邮箱":
                    mEtNumberAndEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
            }

//            mEtNumberAndEmail.setInputType();
        } else if (mListTvAndEtInfo.contains(infoName)) {
            flag = 2;
            Log.d(TAG, "onCreate: flag2");
            mLlInfoAddress.setVisibility(View.VISIBLE);
            mTvInfoAddress.setVisibility(View.VISIBLE);
            mVInfoAddress.setVisibility(View.VISIBLE);
            mEtInfoAddress.setVisibility(View.VISIBLE);
            mTvInfoAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PickerUtil.chooseArea(EditActivity.this, v, mTvInfoAddress, false);
                }
            });
        } else {
            //      "考生档案所在地", "现在学习或工作单位", "学习与工作经历", "何时何地何原因受过何种奖励或处分",
            flag = 3;
            Log.d(TAG, "onCreate: flag3");
            mEtInfo.setVisibility(View.VISIBLE);
            mEtInfo.setText(info);
            Log.d(TAG, "mEtInfo: " + info);
        }
        //提示信息(temp)
        Toast.makeText(EditActivity.this, info + "flag:" + flag, Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存更改按钮点击
     *
     * @param view the view
     * @throws JSONException the json exception
     */
    public void saveEdit(View view) throws JSONException {
        //获取修改后的信息
        //数字或电子邮箱
        //TODO 为空不能提交？？？？
//        if (!"".equals(mEtNumberAndEmail.getText().toString())) {
        if (flag == 1) {
            infoUpdate = mEtNumberAndEmail.getText().toString();
            Log.d(TAG, "saveEditmEtNumberAndEmail: " + infoUpdate);
            Log.d(TAG, "saveEdit: 1");
        }
        //详细地址类的信息
//        if (!"".equals(mTvInfoAddress) && !"".equals(mEtInfoAddress)) {
        if (flag == 2) {
            infoUpdate = mTvInfoAddress.getText().toString()
                    + mEtInfoAddress.getText().toString();
            Log.d(TAG, "saveEdit: TvInfoAddress:" + infoUpdate);
            Log.d(TAG, "saveEdit: 2");
        }
        //需要编辑的信息
//        if (!"".equals(mEtInfo.getText().toString())) {
        if (flag == 3) {
            infoUpdate = mEtInfo.getText().toString();
            Log.d(TAG, "saveEdit: EtInfo" + infoUpdate);
            Log.d(TAG, "saveEdit: 3");
        }
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
                Toast.makeText(EditActivity.this, infoUpdate, Toast.LENGTH_SHORT).show();
                updateIntent.putExtra("info", infoUpdate);
                setResult(2, updateIntent);
                finish();
            }

        }
    }

    /**
     * Back to change.
     * 返回按钮点击-LinearLayout
     *
     * @param view the view
     */
    public void backToChange(View view) {
        finish();
    }
}
