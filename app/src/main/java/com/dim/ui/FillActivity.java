package com.dim.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PinYinUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dim.ui.model.HttpURL.url;
import static com.dim.ui.util.PickerUtil.chooseArea;
import static com.dim.ui.util.PickerUtil.chooseDate;
import static com.dim.ui.util.PickerUtil.takePhoto;

/**
 * The type Fill activity.
 * 信息填写界面
 *
 * @author dim
 */

public class FillActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 信息填写提交URL
     */
    private final String URL = url + "FillInInformationServlet";
    /**
     * 获取导入信息URL
     */
    private final String getLeandingInInfoURL = url + "GetInformationServlet";
    /**
     * 获得报考号URL
     */
    private final String URL_Baokaohao = url + "baokaohaoServlet";
    /**
     * TAG
     */
    private final String TAG = "FillActivity：";
    @BindView(R.id.et_1)
    TextView mEt1;
    @BindView(R.id.et_2)
    TextView mEt2;
    @BindView(R.id.et_3)
    TextView mEt3;
    @BindView(R.id.et_4)
    TextView mEt4;
    @BindView(R.id.et_5)
    TextView mEt5;
    @BindView(R.id.et_6)
    TextView mEt6;
    @BindView(R.id.et_7)
    TextView mEt7;
    @BindView(R.id.et_8)
    TextView mEt8;
    @BindView(R.id.et_9)
    TextView mEt9;
    @BindView(R.id.et_10)
    TextView mEt10;
    @BindView(R.id.et_11)
    TextView mEt11;
    @BindView(R.id.et_12)
    TextView mEt12;
    @BindView(R.id.et_121)
    TextView mEt121;
    @BindView(R.id.et_13)
    EditText mEt13;
    @BindView(R.id.et_14)
    TextView mEt14;
    @BindView(R.id.et_141)
    EditText mEt141;
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
    TextView mEt21;
    @BindView(R.id.et_211)
    EditText mEt211;
    @BindView(R.id.et_22)
    EditText mEt22;
    @BindView(R.id.et_23)
    EditText mEt23;
    @BindView(R.id.et_24)
    EditText mEt24;
    @BindView(R.id.et_25)
    EditText mEt25;
    @BindView(R.id.et_26)
    TextView mEt26;
    @BindView(R.id.et_27)
    EditText mEt27;
    @BindView(R.id.et_28)
    EditText mEt28;
    @BindView(R.id.et_29)
    TextView mEt29;
    @BindView(R.id.et_30)
    TextView mEt30;
    @BindView(R.id.et_31)
    EditText mEt31;
    @BindView(R.id.et_32)
    TextView mEt32;
    @BindView(R.id.et_33)
    EditText mEt33;
    @BindView(R.id.et_34)
    TextView mEt34;
    @BindView(R.id.et_35)
    EditText mEt35;
    @BindView(R.id.et_36)
    TextView mEt36;
    @BindView(R.id.et_37)
    TextView mEt37;
    @BindView(R.id.et_38)
    TextView mEt38;
    @BindView(R.id.et_39)
    TextView mEt39;
    @BindView(R.id.et_40)
    TextView mEt40;
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
    @BindView(R.id.et_51)
    TextView mEt51;
    @BindView(R.id.et_52)
    TextView mEt52;
    /**
     * 提交报考信息按钮.
     */
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    /**
     * 个人照片text
     */
    @BindView(R.id.tv_photo)
    TextView mTvPhoto;
    /**
     * 个人照片
     */
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    /**
     * 个人照片位于的LinearLayout
     */
    @BindView(R.id.ll_photo)
    LinearLayout mLlPhoto;
    /**
     * 提交确认对话框
     */
    AlertDialog.Builder normalDialog;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String stu_id;
    /**
     * 证件类型
     */
    private String stu_id_type;
    /**
     * 转为json的数据
     */
    private String dataJson = "";
    SharedPreferences sharedPreferences;
    /**
     * 导入获取的jsonarray
     */
    JSONArray jsonArray = null;
    PinYinUtil pyu = new PinYinUtil();

    /**
     * @param savedInstanceState save
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        ButterKnife.bind(this);

        Log.d(TAG, "**********************************");

        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        name = sharedPreferences.getString("name", "");
        stu_id = sharedPreferences.getString("stu_id", "");
        stu_id_type = sharedPreferences.getString("stu_id_type", "");

        Log.d(TAG, "设置姓名、证件类型、证件号码：" + name + ", " + stu_id + ", " + stu_id_type);
        mEt1.setText(name);
        mEt2.setText(stu_id_type);
        mEt3.setText(stu_id);

        mEt41.setEnabled(false);
        mEt42.setEnabled(false);
        mEt40.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: "+s);
                if ("定向".equals(s.toString())) {
                    mEt41.setEnabled(true);
                    mEt42.setEnabled(true);
                } else {
                    mEt41.setEnabled(false);
                    mEt42.setEnabled(false);
                }
            }
        });

        mEt51.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEt52.setHint("点击选择报考点");
                mEt52.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvAlertDialog(getResources().getStringArray(R.array.spinner_baokaodian), mEt52);
                    }
                });
            }
        });

        initSetSingLine();
        initOnClickListener();

        // TODO 专项计划包括：强军计划、援藏计划、少数民族骨干计划、退役大学生士兵专项计划。强军计划、援藏计划对应考试方式中的。
        // TODO 除了单独考试 的其他三项，可选无、少数民族骨干计划（需校验码）、退役大学生计划
    }

    /**
     * 设置EditText输入文字滑动
     */
    private void initSetSingLine() {
        Log.d(TAG, "initSetSingLine: 初始化EditText输入文字滑动");
        mEt121.setSingleLine(true);
        mEt13.setSingleLine(true);
        mEt141.setSingleLine(true);
        mEt211.setSingleLine(true);
    }

    /**
     * 初始化TextView响应
     */
    private void initOnClickListener() {
        Log.d(TAG, "initOnClickListener: 初始化点击响应");
        mLlPhoto.setOnClickListener(this);

        mEt4.setOnClickListener(this);
        mEt5.setOnClickListener(this);
        mEt6.setOnClickListener(this);
        mEt7.setOnClickListener(this);
        mEt8.setOnClickListener(this);
        mEt26.setOnClickListener(this);
        mEt29.setOnClickListener(this);
        mEt30.setOnClickListener(this);
        mEt34.setOnClickListener(this);
        mEt36.setOnClickListener(this);
        mEt37.setOnClickListener(this);
        mEt38.setOnClickListener(this);
        mEt39.setOnClickListener(this);
        mEt40.setOnClickListener(this);

        mEt9.setOnClickListener(this);
        mEt10.setOnClickListener(this);
        mEt11.setOnClickListener(this);
        mEt12.setOnClickListener(this);
        mEt14.setOnClickListener(this);
        mEt21.setOnClickListener(this);
        mEt32.setOnClickListener(this);
        mEt51.setOnClickListener(this);
//        mEt52.setOnClickListener(this);
    }

    /**
     * TextView响应事件
     *
     * @param view view
     */
    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: 选择器响应");
        switch (view.getId()) {
            case R.id.ll_photo:
                takePhoto(this, mIvPhoto);
                break;
            //true：两级联动，false：三级联动
            case R.id.et_9:
                chooseArea(this, view, mEt9, true);
                break;
            case R.id.et_10:
                chooseArea(this, view, mEt10, false);
                break;
            case R.id.et_11:
                chooseArea(this, view, mEt11, false);
                break;
            case R.id.et_12:
                chooseArea(this, view, mEt12, false);
                break;
            case R.id.et_14:
                chooseArea(this, view, mEt14, false);
                break;
            case R.id.et_21:
                chooseArea(this, view, mEt21, false);
                break;
            case R.id.et_32:
                chooseDate(this, view, mEt32);
                break;
            case R.id.et_51:
                chooseArea(this, view, mEt51, true);
                break;
            //TextView弹出对话框选择
            case R.id.et_4:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_minzu), mEt4);
                break;
            case R.id.et_5:
                tvAlertDialog(new String[]{"男", "女"}, mEt5);
                break;
            case R.id.et_6:
                tvAlertDialog(new String[]{"是", "否"}, mEt6);
                break;
            case R.id.et_7:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_xianyi_junren), mEt7);
                break;
            case R.id.et_8:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_zhengzhi_mianmao), mEt8);
                break;
            case R.id.et_26:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_kaoshen_laiyuan), mEt26);
                break;
            case R.id.et_29:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_xuexi_xinshi), mEt29);
                break;
            case R.id.et_30:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_zuihou_xueli), mEt30);
                break;
            case R.id.et_34:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_zuihou_xuewei), mEt34);
                break;
            case R.id.et_36:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_danwei), mEt36);
                break;
            case R.id.et_37:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_zhuanye), mEt37);
                break;
            case R.id.et_38:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_kaoshi_fangshi), mEt38);
                break;
            case R.id.et_39:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_zhuanxiang_jihua), mEt39);
                break;
            case R.id.et_40:
                tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_leibie), mEt40);
                break;

//            case R.id.et_52:
//                tvAlertDialog(getResources().getStringArray(R.array.spinner_baokaodian), mEt52);
//                break;
        }
    }

    /**
     * 提交报考信息按钮响应
     * Fill commit.
     */
    @OnClick(R.id.btn_commit)
    public void fillCommit() {
        Log.d(TAG, "点击提交按钮");
        try {
            dataJson = getDataToJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if ("fail".equals(dataJson)) {
            Toast.makeText(FillActivity.this, "信息填写不完整，除户口所在地详细地址, " +
                    "出生地详细地址, 电子邮箱, 学历证书编号, 学位证书编号, 备用信息一, " +
                    "备用信息二外必填", Toast.LENGTH_SHORT).show();
        } else if ("photo".equals(dataJson)){
            Toast.makeText(this, "未选择照片", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            normalDialog = new AlertDialog.Builder(FillActivity.this);
            normalDialog.setIcon(R.drawable.more);
            normalDialog.setTitle("请确认信息");
            stringBuilder.append("报考单位：" + mEt36.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("报考专业：" + mEt37.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("考试方式：" + mEt38.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("报考点：" + mEt52.getText().toString());
            normalDialog.setMessage(stringBuilder);
            Log.d(TAG, "初始化AlertDialog");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: 点击对话框确定按钮");

                            String[] data = {URL, dataJson};
                            FillInfoTask fillInfoTask = new FillInfoTask();
                            fillInfoTask.execute(data);
                            Log.d(TAG, "onClick: http请求后");
                            //上传图片
                            SharedPreferences loginData = getSharedPreferences("loginData", MODE_PRIVATE);
                            HttpUtil.okHttpUploadFile(loginData.getString("filePath", "")
                                    , loginData.getString("name", ""));
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: 点击对话框取消按钮");
                        }
                    });
            normalDialog.show();
        }
    }

    /**
     * 上传报考信息给服务器
     * The type Fill info task.
     */
    class FillInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            //提交考生填写信息
            try {
                state = HttpUtil.httpPost(strings[0], "fillData=" +
                        URLEncoder.encode(strings[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // TODO 报考系统关闭时不能报考和修改信息
            // 已提交报考信息，若重新报考才能再次填写；
            // 已提交报考信息，并获取了报考号
            sharedPreferences.edit().putBoolean("isFill", true).apply();
            sharedPreferences.edit().putString("baokaohao", s).apply();
            Toast.makeText(FillActivity.this, "state：" + s, Toast.LENGTH_SHORT).show();
            normalDialog = new AlertDialog.Builder(FillActivity.this);
            normalDialog.setIcon(R.drawable.more);
            normalDialog.setTitle("提交报考信息成功");
            normalDialog.setMessage("报考号:" + s);
            Log.d(TAG, "fillCommit: 初始化AlertDialog");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: 点击对话框确定按钮");
                            finish();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: 点击对话框取消按钮");
                        }
                    });
            normalDialog.show();
        }
    }

    /**
     * 获取填写的信息并转为json
     *
     * @return 转换为String的json数据
     * @throws JSONException json
     */
    private String getDataToJSON() throws JSONException {
        Log.d(TAG, "getDataToJSON: 获取输入报考信息，并转为JSON");
        JSONObject jsonObject = new JSONObject();
        //获取EditText数据并转换为json格式
        jsonObject.put("username", username);
        jsonObject.put("name", mEt1.getText().toString());//考生姓名
        jsonObject.put(pyu.getStringPinYin("证件类型"), mEt2.getText().toString());//证件类型
        jsonObject.put(pyu.getStringPinYin("证件号码"), mEt3.getText().toString());//证件号码
        jsonObject.put(pyu.getStringPinYin("民族"), mEt4.getText().toString());//民族
        jsonObject.put(pyu.getStringPinYin("性别"), mEt5.getText().toString());//性别
        jsonObject.put(pyu.getStringPinYin("婚否"), mEt6.getText().toString());//婚否
        jsonObject.put(pyu.getStringPinYin("现役军人"), mEt7.getText().toString());//现役军人
        jsonObject.put(pyu.getStringPinYin("政治面貌"), mEt8.getText().toString());//政治面貌
        jsonObject.put(pyu.getStringPinYin("籍贯所在地"), mEt9.getText().toString());//籍贯所在地
        jsonObject.put(pyu.getStringPinYin("出生地"), mEt10.getText().toString());//出生地
        jsonObject.put(pyu.getStringPinYin("户口所在地"), mEt11.getText().toString());//户口所在地
        jsonObject.put(pyu.getStringPinYin("户口所在地详细地址"), mEt12.getText().toString() +
                mEt121.getText().toString());//户口所在地详细地址
        jsonObject.put(pyu.getStringPinYin("考生档案所在地"), mEt13.getText().toString());//考生档案所在地
        jsonObject.put(pyu.getStringPinYin("考生档案所在单位地址"), mEt14.getText().toString() +
                mEt141.getText().toString());//考生档案所在单位地址
        jsonObject.put(pyu.getStringPinYin("考生档案所在单位邮政编码"), mEt15.getText().toString());//考生档案所在单位邮政编码
        jsonObject.put(pyu.getStringPinYin("现在学习或工作单位"), mEt16.getText().toString());//现在学习或工作单位
        jsonObject.put(pyu.getStringPinYin("学习与工作经历"), mEt17.getText().toString());//学习与工作经历
        jsonObject.put(pyu.getStringPinYin("何时何地何原因受过何种奖励或处分"), mEt18.getText().toString());//何时何地何原因受过何种奖励或处分
        jsonObject.put(pyu.getStringPinYin("考生作弊情况"), mEt19.getText().toString());//考生作弊情况
        jsonObject.put(pyu.getStringPinYin("家庭主要成员"), mEt20.getText().toString());//家庭主要成员

        jsonObject.put(pyu.getStringPinYin("考生通讯地址"), mEt21.getText().toString() +
                mEt211.getText().toString());//考生通讯地址
        jsonObject.put(pyu.getStringPinYin("考生通讯地址邮政编码"), mEt22.getText().toString());//考生通讯地址邮政编码
        jsonObject.put(pyu.getStringPinYin("固定电话"), mEt23.getText().toString());//固定电话
        jsonObject.put(pyu.getStringPinYin("移动电话"), mEt24.getText().toString());//移动电话
        jsonObject.put(pyu.getStringPinYin("电子邮箱"), mEt25.getText().toString());//电子邮箱

        jsonObject.put(pyu.getStringPinYin("考生来源"), mEt26.getText().toString());//考生来源
        jsonObject.put(pyu.getStringPinYin("毕业学校"), mEt27.getText().toString());//毕业学校
        jsonObject.put(pyu.getStringPinYin("毕业专业"), mEt28.getText().toString());//毕业专业
        jsonObject.put(pyu.getStringPinYin("取得最后学历的学习形式"), mEt29.getText().toString());//取得最后学历的学习形式
        jsonObject.put(pyu.getStringPinYin("最后学历"), mEt30.getText().toString());//最后学历
        jsonObject.put(pyu.getStringPinYin("毕业证书编号"), mEt31.getText().toString());//毕业证书编号
        jsonObject.put(pyu.getStringPinYin("获得最后学历毕业年月"), mEt32.getText().toString());//获得最后学历毕业年月
        jsonObject.put(pyu.getStringPinYin("注册学号"), mEt33.getText().toString());//注册学号
        jsonObject.put(pyu.getStringPinYin("最后学位"), mEt34.getText().toString());//最后学位
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

        jsonObject.put(pyu.getStringPinYin("报考点所在省市"), mEt51.getText().toString());
        jsonObject.put(pyu.getStringPinYin("报考点"), mEt52.getText().toString());
        String[] notNullInfos = {"户口所在地详细地址", "出生地详细地址", "电子邮箱", "毕业证书编号", "学位证书编号"
                , "备用信息一", "备用信息二", "定向就业单位所在地", "定向就业单位"};
        String[] str = {"", "", "", "", "", "", "", "", ""};
        for (int i = 0; i < notNullInfos.length; i++) {
            str[i] = pyu.getStringPinYin(notNullInfos[i]);
            Log.d(TAG, str[i]);
        }
        List<String> infosList = Arrays.asList(str);
        //判断是否填写全部信息
        Iterator<String> iterator;
        for (iterator = jsonObject.keys(); iterator.hasNext(); ) {
            String temp = iterator.next();
            if (!infosList.contains(temp)) {
                if ("".equals(jsonObject.get(temp))) {
                    return "fail";
                }
            }
        }
        if (sharedPreferences.getString("fillPath", "") == null) {
            return "photo";
        }
        return jsonObject.toString();
    }

    @OnClick(R.id.leading_in)
    public void leadingIn() {
        Log.d(TAG, "leadingIn: 导入报考信息");
        String[] stringsModify = {getLeandingInInfoURL, username};
        LeadingInShowTask leadingInShowTask = new LeadingInShowTask();
        leadingInShowTask.execute(stringsModify);
    }

    class LeadingInShowTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = "";
            try {
                state = HttpUtil.httpPost(strings[0],
                        "username=" + URLEncoder.encode(strings[1], "UTF-8")
                                + "&flag=leadingin");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            String[] leadingInInfos = {"民族", "性别", "婚否", "现役军人", "政治面貌", "籍贯所在地", "出生地"
//                    , "户口所在地", "户口所在地详细地址", "考生档案所在地", "考生档案所在单位地址", "考生档案所在单位邮政编码"
//                    , "现在学习或工作单位", "何时何地何原因受过何种奖励或处分", "考生作弊情况", "家庭主要成员"
//                    , "考生通讯地址", "考生通讯地址邮政编码", "固定电话", "移动电话", "电子邮箱", "考生来源"
//                    , "毕业学校", "毕业专业", "取得最后学历的学习形式", "最后学历", "毕业证书编号"
//                    , "获得最后学历毕业年月", "注册学号", "最后学位", "学位证书编号", "报考单位", "报考专业"
//                    , "考试方式", "专项计划", "报考类别", "定向就业单位所在地", "定向就业单位", "报考院系"
//                    , "研究方向", "政治理论", "外国语", "业务课一", "业务课二", "备用信息一", "备用信息二"
//                    , "报考点所在省市", "报考点"};

            Log.d(TAG, "导入获取的信息：" + s.toString());
            if ("无可导入报考信息".equals(s)) {
                Toast.makeText(FillActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    jsonArray = new JSONArray(s);
                    Log.d(TAG, "jsonarray长度：" + jsonArray.getJSONObject(0).length());
                    Log.d(TAG, "0:" + String.valueOf(jsonArray.getJSONObject(0).keys()));
                    Log.d(TAG, "1:" + String.valueOf(jsonArray.getJSONObject(0).get("2016")));
                    final List<String> leadinfInList = new ArrayList<String>();
                    final List<String> leadinfInDataList = new ArrayList<String>();
                    for (Iterator<String> iterator = jsonArray.getJSONObject(0).keys(); iterator.hasNext(); ) {
                        String temp = iterator.next();
                        leadinfInList.add(temp);
                        leadinfInDataList.add(String.valueOf(jsonArray.getJSONObject(0).get(temp)));
                    }

                    StringBuilder builder = new StringBuilder();

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(FillActivity.this,
                                    android.R.layout.select_dialog_item, leadinfInList);

                    Dialog alertDialog = new AlertDialog.Builder(FillActivity.this)
                            .setTitle("请选择导入数据的年份：")
//                            .setIcon(R.mipmap.ic_launcher)
                            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(leadinfInDataList.get(which));

                                        mEt4.setText(jsonObject.getString(pyu.getStringPinYin("民族")));
                                        mEt5.setText(jsonObject.getString(pyu.getStringPinYin("性别")));
                                        mEt6.setText(jsonObject.getString(pyu.getStringPinYin("婚否")));
                                        mEt7.setText(jsonObject.getString(pyu.getStringPinYin("现役军人")));
                                        mEt8.setText(jsonObject.getString(pyu.getStringPinYin("政治面貌")));
                                        mEt9.setText(jsonObject.getString(pyu.getStringPinYin("籍贯所在地")));
                                        mEt10.setText(jsonObject.getString(pyu.getStringPinYin("出生地")));
                                        mEt11.setText(jsonObject.getString(pyu.getStringPinYin("户口所在地")));
                                        mEt13.setText(jsonObject.getString(pyu.getStringPinYin("考生档案所在地")));
                                        mEt15.setText(jsonObject.getString(pyu.getStringPinYin("考生档案所在单位邮政编码")));
                                        mEt16.setText(jsonObject.getString(pyu.getStringPinYin("现在学习或工作单位")));
                                        mEt17.setText(jsonObject.getString(pyu.getStringPinYin("学习与工作经历")));
                                        mEt18.setText(jsonObject.getString(pyu.getStringPinYin("何时何地何原因受过何种奖励或处分")));
                                        mEt19.setText(jsonObject.getString(pyu.getStringPinYin("考生作弊情况")));
                                        mEt20.setText(jsonObject.getString(pyu.getStringPinYin("家庭主要成员")));
                                        mEt22.setText(jsonObject.getString(pyu.getStringPinYin("考生通讯地址邮政编码")));
                                        mEt23.setText(jsonObject.getString(pyu.getStringPinYin("固定电话")));
                                        mEt24.setText(jsonObject.getString(pyu.getStringPinYin("移动电话")));
                                        mEt26.setText(jsonObject.getString(pyu.getStringPinYin("考生来源")));
                                        mEt27.setText(jsonObject.getString(pyu.getStringPinYin("毕业学校")));
                                        mEt28.setText(jsonObject.getString(pyu.getStringPinYin("毕业专业")));
                                        mEt29.setText(jsonObject.getString(pyu.getStringPinYin("取得最后学历的学习形式")));
                                        mEt30.setText(jsonObject.getString(pyu.getStringPinYin("最后学历")));
                                        mEt31.setText(jsonObject.getString(pyu.getStringPinYin("毕业证书编号")));
                                        mEt32.setText(jsonObject.getString(pyu.getStringPinYin("获得最后学历毕业年月")));
                                        mEt33.setText(jsonObject.getString(pyu.getStringPinYin("注册学号")));
                                        mEt34.setText(jsonObject.getString(pyu.getStringPinYin("最后学位")));
                                        mEt35.setText(jsonObject.getString(pyu.getStringPinYin("学位证书编号")));
                                        mEt36.setText(jsonObject.getString(pyu.getStringPinYin("报考单位")));
                                        mEt37.setText(jsonObject.getString(pyu.getStringPinYin("报考专业")));
                                        mEt38.setText(jsonObject.getString(pyu.getStringPinYin("考试方式")));
                                        mEt39.setText(jsonObject.getString(pyu.getStringPinYin("专项计划")));
                                        mEt40.setText(jsonObject.getString(pyu.getStringPinYin("报考类别")));
                                        mEt43.setText(jsonObject.getString(pyu.getStringPinYin("报考院系")));
                                        mEt44.setText(jsonObject.getString(pyu.getStringPinYin("研究方向")));
                                        mEt45.setText(jsonObject.getString(pyu.getStringPinYin("政治理论")));
                                        mEt46.setText(jsonObject.getString(pyu.getStringPinYin("外国语")));
                                        Toast.makeText(FillActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).create();
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onPostExecute: 导入成功");
            }
        }
    }

    private void tvAlertDialog(final String[] strings, final TextView mTextView) {
        Dialog alertDialog = new AlertDialog.Builder(this)
//                            .setTitle("你喜欢吃哪种水果？")
//                            .setIcon(R.mipmap.ic_launcher)
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTextView.setText(strings[which]);
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 返回到上一个activity
     * Fill back to function.
     *
     * @param view the view
     */
    public void fillBackToFunction(View view) {
        finish();
    }

}
