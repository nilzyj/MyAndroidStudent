package com.dim.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.PinYinUtil.PinYinUtil;
import com.dim.ui.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//信息填写界面
public class FillActivity extends AppCompatActivity {
    private final String TAG = "FillActivity";
    private final String URL = "http://10.0.2.2:8080/Manage/FillInInformationServlet";
//    private final String URL = "http://192.168.191.1:8080/Manage/FillInInformationServlet";
    @BindView(R.id.et_1)
    TextView mEt1;
    @BindView(R.id.et_2)
    Spinner mEt2;
    @BindView(R.id.et_3)
    EditText mEt3;
    @BindView(R.id.et_4)
    EditText mEt4;
    @BindView(R.id.et_5)
    RadioGroup mEt5;
    @BindView(R.id.et_6)
    RadioGroup mEt6;
    @BindView(R.id.et_7)
    Spinner mEt7;
    @BindView(R.id.et_8)
    Spinner mEt8;
    @BindView(R.id.et_9)
    EditText mEt9;
    @BindView(R.id.et_10)
    EditText mEt10;
    @BindView(R.id.et_11)
    EditText mEt11;
    @BindView(R.id.et_12)
    EditText mEt12;
    @BindView(R.id.et_13)
    EditText mEt13;
    @BindView(R.id.et_14)
    EditText mEt14;
    @BindView(R.id.et_15)
    EditText mEt15;
    @BindView(R.id.et_16)
    EditText mEt16;
    @BindView(R.id.et_17)
    EditText mEt17;
    @BindView(R.id.et_18)
    EditText mEt18;
    @BindView(R.id.et_19)
    EditText mEt19;
    @BindView(R.id.et_20)
    EditText mEt20;
    @BindView(R.id.et_21)
    EditText mEt21;
    @BindView(R.id.et_22)
    EditText mEt22;
    @BindView(R.id.et_23)
    EditText mEt23;
    @BindView(R.id.et_24)
    EditText mEt24;
    @BindView(R.id.et_25)
    EditText mEt25;
    @BindView(R.id.et_26)
    Spinner mEt26;
    @BindView(R.id.et_27)
    EditText mEt27;
    @BindView(R.id.et_28)
    EditText mEt28;
    @BindView(R.id.et_29)
    Spinner mEt29;
    @BindView(R.id.et_30)
    Spinner mEt30;
    @BindView(R.id.et_31)
    EditText mEt31;
    @BindView(R.id.et_32)
    EditText mEt32;
    @BindView(R.id.et_33)
    EditText mEt33;
    @BindView(R.id.et_34)
    Spinner mEt34;
    @BindView(R.id.et_35)
    EditText mEt35;
    @BindView(R.id.et_36)
    EditText mEt36;
    @BindView(R.id.et_37)
    EditText mEt37;
    @BindView(R.id.et_38)
    EditText mEt38;
    @BindView(R.id.et_39)
    EditText mEt39;
    @BindView(R.id.et_40)
    EditText mEt40;
    @BindView(R.id.et_41)
    EditText mEt41;
    @BindView(R.id.et_42)
    EditText mEt42;
    @BindView(R.id.et_43)
    EditText mEt43;
    @BindView(R.id.et_44)
    EditText mEt44;
    @BindView(R.id.et_45)
    EditText mEt45;
    @BindView(R.id.et_46)
    EditText mEt46;
    @BindView(R.id.et_47)
    EditText mEt47;
    @BindView(R.id.et_48)
    EditText mEt48;
    @BindView(R.id.et_49)
    EditText mEt49;
    @BindView(R.id.et_50)
    EditText mEt50;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        mEt1.setText(name);
    }

    //提交报考信息按钮响应
    @OnClick(R.id.btn_commit)
    public void fillCommit() {
        Log.d(TAG, "fillCommit: 点击提交按钮");
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(FillActivity.this);
        normalDialog.setIcon(R.drawable.more);
        normalDialog.setTitle("请确认信息");
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("报考号：1");
//        stringBuilder.append("\n");
//        stringBuilder.append("报考方式：");
//        stringBuilder.append("\n");
//        normalDialog.setMessage(stringBuilder);
        Log.d(TAG, "fillCommit: 初始化AlertDialog");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: 点击对话框确定按钮");
                        try {
                            Log.d(TAG, "onClick: http请求前");
                            String data_json = getDataToJSON();
                            if ("fail".equals(data_json)) {
                                Toast.makeText(FillActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
                            } else {
                                String[] data = {URL, data_json};
                                FillInfoTask fillInfoTask = new FillInfoTask();
                                fillInfoTask.execute(data);
                                Log.d(TAG, "onClick: http请求后");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: 点击对话框取消按钮");
//                        normalDialog.setCancelable(true);
                    }
                });
        normalDialog.show();
    }

    public void fillBackToFunction(View view) {
        finish();
    }

    //上传报考信息给服务器
    class FillInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            //提交考生填写信息
            try {
                state = HttpUtils.httpPost(strings[0], "fillData=" +
                        URLEncoder.encode(strings[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Intent intent = new Intent(FillActivity.this, FillResulActivity.class);
//            startActivity(intent);
            Toast.makeText(FillActivity.this, "state" + s, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //获取填写的信息并转为json
    private String getDataToJSON() throws JSONException {
        Log.d(TAG, "getDataToJSON: 进入getDataToJSON");
        PinYinUtil pyu = new PinYinUtil();
        JSONObject jsonObject = new JSONObject();
        //获取EditText数据并转换为json格式
        jsonObject.put("name", mEt1.getText().toString());//考生姓名
        jsonObject.put(pyu.getStringPinYin("证件类型"), mEt2.getSelectedItem().toString());//证件类型
        jsonObject.put(pyu.getStringPinYin("证件号码"), mEt3.getText().toString());//证件号码
        jsonObject.put(pyu.getStringPinYin("民族"), mEt4.getText().toString());//民族
        if (mEt5.getCheckedRadioButtonId() == R.id.sex_man) {
            jsonObject.put(pyu.getStringPinYin("性别"), "男");//性别
        } else {
            jsonObject.put(pyu.getStringPinYin("性别"), "女");
        }
        if (mEt6.getCheckedRadioButtonId() == R.id.hunfou_true) {
            jsonObject.put(pyu.getStringPinYin("婚否"), "是");//婚否
        } else {
            jsonObject.put(pyu.getStringPinYin("婚否"), "否");
        }
        jsonObject.put(pyu.getStringPinYin("现役军人"), mEt7.getSelectedItem().toString());//现役军人
        jsonObject.put(pyu.getStringPinYin("政治面貌"), mEt8.getSelectedItem().toString());//政治面貌
        jsonObject.put(pyu.getStringPinYin("籍贯所在地"), mEt9.getText().toString());//籍贯所在地
        jsonObject.put(pyu.getStringPinYin("出生地"),mEt10.getText().toString());//出生地
        jsonObject.put(pyu.getStringPinYin("户口所在地"),mEt11.getText().toString());//户口所在地
        jsonObject.put(pyu.getStringPinYin("户口所在地详细地址"),mEt12.getText().toString());//口所在地详细地址
        jsonObject.put(pyu.getStringPinYin("考生档案所在地"), mEt13.getText().toString());//考生档案所在地
        jsonObject.put(pyu.getStringPinYin("考生档案所在单位地址"), mEt14.getText().toString());//考生档案所在单位地址
        jsonObject.put(pyu.getStringPinYin("考生档案所在单位邮政编码"), mEt15.getText().toString());//考生档案所在单位邮政编码
        jsonObject.put(pyu.getStringPinYin("现在学习或工作单位"), mEt16.getText().toString());//现在学习或工作单位
        jsonObject.put(pyu.getStringPinYin("学习与工作经历"), mEt17.getText().toString());//学习与工作经历
        jsonObject.put(pyu.getStringPinYin("何时何地何原因受过何种奖励或处分"), mEt18.getText().toString());//何时何地何原因受过何种奖励或处分
        jsonObject.put(pyu.getStringPinYin("考生作弊情况"), mEt19.getText().toString());//考生作弊情况
        jsonObject.put(pyu.getStringPinYin("家庭主要成员"), mEt20.getText().toString());//家庭主要成员

        jsonObject.put(pyu.getStringPinYin("考生通讯地址"), mEt21.getText().toString());//考生通讯地址
        jsonObject.put(pyu.getStringPinYin("考生通讯地址邮政编码"), mEt22.getText().toString());//考生通讯地址邮政编码
        jsonObject.put(pyu.getStringPinYin("固定电话"), mEt23.getText().toString());//固定电话
        jsonObject.put(pyu.getStringPinYin("移动电话"), mEt24.getText().toString());//移动电话
        jsonObject.put(pyu.getStringPinYin("电子邮箱"), mEt25.getText().toString());//电子邮箱

        jsonObject.put(pyu.getStringPinYin("考生来源"), mEt26.getSelectedItem().toString());//考生来源
        jsonObject.put(pyu.getStringPinYin("毕业学校"), mEt27.getText().toString());//毕业学校
        jsonObject.put(pyu.getStringPinYin("毕业专业"), mEt28.getText().toString());//毕业专业
        jsonObject.put(pyu.getStringPinYin("取得最后学历的学习形式"), mEt29.getSelectedItem().toString());//取得最后学历的学习形式
        jsonObject.put(pyu.getStringPinYin("最后学历"), mEt30.getSelectedItem().toString());//最后学历
        jsonObject.put(pyu.getStringPinYin("毕业证书编号"), mEt31.getText().toString());//毕业证书编号
        jsonObject.put(pyu.getStringPinYin("获得最后学历毕业年月"), mEt32.getText().toString());//获得最后学历毕业年月
        jsonObject.put(pyu.getStringPinYin("注册学号"), mEt33.getText().toString());//注册学号
        jsonObject.put(pyu.getStringPinYin("最后学位"), mEt34.getSelectedItem().toString());//最后学位
        jsonObject.put(pyu.getStringPinYin("学位证书编号"), mEt35.getText().toString());//学位证书编号
        jsonObject.put(pyu.getStringPinYin("报考单位"), mEt36.getText().toString());//报考单位
        jsonObject.put(pyu.getStringPinYin("报考专业"), mEt37.getText().toString());//报考专业

        jsonObject.put(pyu.getStringPinYin("考试方式"), mEt38.getText().toString());//考试方式
        jsonObject.put(pyu.getStringPinYin("专项计划"), mEt39.getText().toString());//专项计划
        jsonObject.put(pyu.getStringPinYin("报考类别"), mEt40.getText().toString());//报考类别
        jsonObject.put(pyu.getStringPinYin("定向就业单位所在地"), mEt41.getText().toString());//定向就业单位所在地
        jsonObject.put(pyu.getStringPinYin("定向就业单位"), mEt42.getText().toString());//定向就业单位
        jsonObject.put(pyu.getStringPinYin("报考院系"), mEt43.getText().toString());//报考院系
        jsonObject.put(pyu.getStringPinYin("研究方向"), mEt44.getText().toString());//研究方向
        jsonObject.put(pyu.getStringPinYin("政治理论"), mEt45.getText().toString());//政治理论
        jsonObject.put(pyu.getStringPinYin("外国语"), mEt46.getText().toString());//外国语
        jsonObject.put(pyu.getStringPinYin("业务课一"), mEt47.getText().toString());//业务课一
        jsonObject.put(pyu.getStringPinYin("业务课二"), mEt48.getText().toString());//业务课二

        jsonObject.put(pyu.getStringPinYin("备用信息一"), mEt49.getText().toString());//备用信息1
        jsonObject.put(pyu.getStringPinYin("备用信息二"), mEt50.getText().toString());//备用信息2
//        jsonObject.put("name", "zyj");
//        json = "{'姓名':zyj, '性别':男}";
        Log.d(TAG, "getDataToJSON: 获取editText结束");
        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
            Log.d(TAG, "getDataToJSON: 进入 while循环");
            if ("".equals(jsonObject.get(iterator.next()))) {
                Log.d(TAG, "getDataToJSON: " + jsonObject.keys().next());
                return "fail";
            } else {
                Log.d(TAG, "getDataToJSON: " + jsonObject.get(jsonObject.keys().next()));
                Log.d(TAG, "getDataToJSON: 不符合if条件");
            }
        }
        Log.d(TAG, "getDataToJSON: 返回json数据前");
        return jsonObject.toString();
    }
}
