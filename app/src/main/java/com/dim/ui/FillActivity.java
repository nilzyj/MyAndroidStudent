package com.dim.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PinYinUtil;
import com.liji.takephoto.TakePhoto;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Fill activity.
 * 信息填写界面
 * @author dim
 */

public class FillActivity extends AppCompatActivity implements View.OnClickListener {
    /** 信息填写提交URL */
    private final String URL = HttpURL.url + "FillInInformationServlet";
    /** TAG */
    private final String TAG = "FillActivity";
    /**
     * The M et 1.
     */
    @BindView(R.id.et_1)
    TextView mEt1;
    /**
     * The M et 2.
     */
    @BindView(R.id.et_2)
    Spinner mEt2;
    /**
     * The M et 3.
     */
    @BindView(R.id.et_3)
    EditText mEt3;
    /**
     * The M et 4.
     */
    @BindView(R.id.et_4)
    Spinner mEt4;
    /**
     * The M et 5.
     */
    @BindView(R.id.et_5)
    RadioGroup mEt5;
    /**
     * The M et 6.
     */
    @BindView(R.id.et_6)
    RadioGroup mEt6;
    /**
     * The M et 7.
     */
    @BindView(R.id.et_7)
    Spinner mEt7;
    /**
     * The M et 8.
     */
    @BindView(R.id.et_8)
    Spinner mEt8;
    /**
     * The M et 9.
     */
    @BindView(R.id.et_9)
    TextView mEt9;
    /**
     * The M et 10.
     */
    @BindView(R.id.et_10)
    TextView mEt10;
    /**
     * The M et 11.
     */
    @BindView(R.id.et_11)
    TextView mEt11;
    /**
     * The M et 12.
     */
    @BindView(R.id.et_12)
    TextView mEt12;
    /**
     * The M et 121.
     */
    @BindView(R.id.et_121)
    TextView mEt121;
    /**
     * The M et 13.
     */
    @BindView(R.id.et_13)
    EditText mEt13;
    /**
     * The M et 14.
     */
    @BindView(R.id.et_14)
    TextView mEt14;
    /**
     * The M et 141.
     */
    @BindView(R.id.et_141)
    EditText mEt141;
    /**
     * The M et 15.
     */
    @BindView(R.id.et_15)
    EditText mEt15;
    /**
     * The M et 16.
     */
    @BindView(R.id.et_16)
    EditText mEt16;
    /**
     * The M et 17.
     */
    @BindView(R.id.et_17)
    EditText mEt17;
    /**
     * The M et 18.
     */
    @BindView(R.id.et_18)
    EditText mEt18;
    /**
     * The M et 19.
     */
    @BindView(R.id.et_19)
    EditText mEt19;
    /**
     * The M et 20.
     */
    @BindView(R.id.et_20)
    EditText mEt20;
    /**
     * The M et 21.
     */
    @BindView(R.id.et_21)
    TextView mEt21;
    /**
     * The M et 211.
     */
    @BindView(R.id.et_211)
    EditText mEt211;
    /**
     * The M et 22.
     */
    @BindView(R.id.et_22)
    EditText mEt22;
    /**
     * The M et 23.
     */
    @BindView(R.id.et_23)
    EditText mEt23;
    /**
     * The M et 24.
     */
    @BindView(R.id.et_24)
    EditText mEt24;
    /**
     * The M et 25.
     */
    @BindView(R.id.et_25)
    EditText mEt25;
    /**
     * The M et 26.
     */
    @BindView(R.id.et_26)
    Spinner mEt26;
    /**
     * The M et 27.
     */
    @BindView(R.id.et_27)
    EditText mEt27;
    /**
     * The M et 28.
     */
    @BindView(R.id.et_28)
    EditText mEt28;
    /**
     * The M et 29.
     */
    @BindView(R.id.et_29)
    Spinner mEt29;
    /**
     * The M et 30.
     */
    @BindView(R.id.et_30)
    Spinner mEt30;
    /**
     * The M et 31.
     */
    @BindView(R.id.et_31)
    EditText mEt31;
    /**
     * The M et 32.
     */
    @BindView(R.id.et_32)
    TextView mEt32;
    /**
     * The M et 33.
     */
    @BindView(R.id.et_33)
    EditText mEt33;
    /**
     * The M et 34.
     */
    @BindView(R.id.et_34)
    Spinner mEt34;
    /**
     * The M et 35.
     */
    @BindView(R.id.et_35)
    EditText mEt35;
    /**
     * The M et 36.
     */
    @BindView(R.id.et_36)
    EditText mEt36;
    /**
     * The M et 37.
     */
    @BindView(R.id.et_37)
    EditText mEt37;
    /**
     * The M et 38.
     */
    @BindView(R.id.et_38)
    EditText mEt38;
    /**
     * The M et 39.
     */
    @BindView(R.id.et_39)
    EditText mEt39;
    /**
     * The M et 40.
     */
    @BindView(R.id.et_40)
    EditText mEt40;
    /**
     * The M et 41.
     */
    @BindView(R.id.et_41)
    EditText mEt41;
    /**
     * The M et 42.
     */
    @BindView(R.id.et_42)
    EditText mEt42;
    /**
     * The M et 43.
     */
    @BindView(R.id.et_43)
    EditText mEt43;
    /**
     * The M et 44.
     */
    @BindView(R.id.et_44)
    EditText mEt44;
    /**
     * The M et 45.
     */
    @BindView(R.id.et_45)
    EditText mEt45;
    /**
     * The M et 46.
     */
    @BindView(R.id.et_46)
    EditText mEt46;
    /**
     * The M et 47.
     */
    @BindView(R.id.et_47)
    EditText mEt47;
    /**
     * The M et 48.
     */
    @BindView(R.id.et_48)
    EditText mEt48;
    /**
     * The M et 49.
     */
    @BindView(R.id.et_49)
    EditText mEt49;
    /**
     * The M et 50.
     */
    @BindView(R.id.et_50)
    EditText mEt50;
    /**
     * The M btn commit.
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
     * photoLinearLayout
     */
    @BindView(R.id.ll_photo)
    LinearLayout mLlPhoto;
    /** 提交确认对话框 */
    AlertDialog.Builder normalDialog;
    /** 民族spinner */
    private String[] mItems = {"汉族","阿昌族","白族","保安族","布朗族","布依族","朝鲜族","达斡尔族","傣族",
            "德昂族","东乡族","侗族","独龙族","俄罗斯族","鄂伦春族","鄂温克族","仡佬族","哈尼族","哈萨克族",
            "赫哲族","回族","高山族","汉族","基诺族","京族","景颇族的柯尔克孜族","拉祜族","黎族","傈僳族",
            "珞巴族","满族","毛南族","门巴族","蒙古族","苗族","仫佬族","纳西族","怒族","普米族","羌族",
            "撒拉族","畲族","水族","塔吉克族","塔塔尔族","土家族","土族","佤族","维吾尔族","乌兹别克族",
            "锡伯族","瑶族","彝族","裕固族","藏族","壮族"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        mEt1.setText(name);

        ArrayAdapter<String> adapterMinZu = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapterMinZu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEt4.setAdapter(adapterMinZu);

        initSetSingLine();
        initOnClickListener();
    }

    /**
     * 设置EditText
     */
    private void initSetSingLine() {
        mEt121.setSingleLine(true);
        mEt13.setSingleLine(true);
        mEt141.setSingleLine(true);
        mEt211.setSingleLine(true);
    }

    /**
     * 初始化TextView响应
     */
    private void initOnClickListener() {
//        mIvPhoto.setOnClickListener(this);
//        mTvPhoto.setOnClickListener(this);
        mLlPhoto.setOnClickListener(this);
        mEt9.setOnClickListener(this);
        mEt10.setOnClickListener(this);
        mEt11.setOnClickListener(this);
        mEt12.setOnClickListener(this);
        mEt14.setOnClickListener(this);
        mEt21.setOnClickListener(this);
        mEt32.setOnClickListener(this);
    }

    /**
     * TextView响应事件
     * @param view view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //true：两级联动，false：三级联动
//            case R.id.tv_photo:
//                takePhoto();
//                break;
//            case R.id.iv_photo:
//                takePhoto();
//                break;
            case R.id.ll_photo:
                takePhoto();
                break;
            case R.id.et_9:
                chooseArea(view, mEt9, true);
                break;
            case R.id.et_10:
                chooseArea(view, mEt10, false);
                break;
            case R.id.et_11:
                chooseArea(view, mEt11, false);
                break;
            case R.id.et_12:
                chooseArea(view, mEt12, false);
                break;
            case R.id.et_14:
                chooseArea(view, mEt14, false);
                break;
            case R.id.et_21:
                chooseArea(view, mEt21, false);
                break;
            case R.id.et_32:
                chooseDate(view, mEt32);
                break;
        }
    }

    /**
     * 提交报考信息按钮响应
     * Fill commit.
     */
    @OnClick(R.id.btn_commit)
    public void fillCommit() {
        Log.d(TAG, "fillCommit: 点击提交按钮");
        normalDialog = new AlertDialog.Builder(FillActivity.this);
        normalDialog.setIcon(R.drawable.more);
        normalDialog.setTitle("请确认信息");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("报考号：1");
        stringBuilder.append("\n");
        stringBuilder.append("报考方式：");
        stringBuilder.append("\n");
        normalDialog.setMessage(stringBuilder);
        Log.d(TAG, "fillCommit: 初始化AlertDialog");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: 点击对话框确定按钮");
                        try {
                            Log.d(TAG, "onClick: http请求前");
                            String dataJson = getDataToJSON();
                            if ("fail".equals(dataJson)) {
                                Toast.makeText(FillActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
                            } else {
                                String[] data = {URL, dataJson};
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
//            Intent intent = new Intent(FillActivity.this, FillResulActivity.class);
//            startActivity(intent);
            Toast.makeText(FillActivity.this, "state" + s, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 获取填写的信息并转为json
     * @return 转换为String的json数据
     * @throws JSONException json
     */
    private String getDataToJSON() throws JSONException {
        Log.d(TAG, "getDataToJSON: 进入getDataToJSON");
        PinYinUtil pyu = new PinYinUtil();
        JSONObject jsonObject = new JSONObject();
        //获取EditText数据并转换为json格式
        jsonObject.put("name", mEt1.getText().toString());//考生姓名
        jsonObject.put(pyu.getStringPinYin("证件类型"), mEt2.getSelectedItem().toString());//证件类型
        jsonObject.put(pyu.getStringPinYin("证件号码"), mEt3.getText().toString());//证件号码
        jsonObject.put(pyu.getStringPinYin("民族"), mEt4.getSelectedItem().toString());//民族
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
        jsonObject.put(pyu.getStringPinYin("户口所在地详细地址"),mEt12.getText().toString() +
                mEt121.getText().toString());//口所在地详细地址
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
        //判断是否填写全部信息
//        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
//            if ("".equals(jsonObject.get(iterator.next()))) {
//                return "fail";
//            }
//        }
        Log.d(TAG, "getDataToJSON: 返回json数据前");
        return jsonObject.toString();
    }

    /**
     * 选择地址的Texview的点击事件
     * @param view     the view
     * @param textView the text view
     * @param flag     the flag
     */
    public void chooseArea(View view, TextView textView, boolean flag) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress(textView, flag);//调用CityPicker选取区域
        }
    }

    /**
     * 选择日期的TextView的点击事件
     * @param view  view
     * @param textView    点击的TextView
     */
    private void chooseDate(View view, TextView textView) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectDate(textView);//调用CityPicker选取区域
        }
    }

    /**
     * 弹出地址选择器，并获取选择结果
     * @param textViewAddress 点击的TextView
     * @param flag 三级或二级联动标志变量
     */
    private void selectAddress(final TextView textViewAddress, final boolean flag) {
        CityPicker cityPicker = new CityPicker.Builder(FillActivity.this)
                .textSize(16)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("新疆维吾尔自治区")
                .city("昌吉回族自治州")
                .district("昌吉市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(flag)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
//                String code = citySelected[3];
                //为TextView赋值
                if (flag) {
                    textViewAddress.setText(province.trim() + city.trim());
                } else {
                    textViewAddress.setText(province.trim() + city.trim() + district.trim());
                }

            }
        });
    }

    /**
     * 弹出日期选择器，并获取选择结果
     * @param textView 点击的TextView
     */
    private void selectDate(final TextView textView) {

        DatePickerDialog datePicker = new DatePickerDialog(FillActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(year + "." + monthOfYear + "." + dayOfMonth);
            }
        }, 2017, 7, 1);
        datePicker.show();
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

    /**
     * 获取个人照片：相册或拍照
     */
    private void takePhoto() {
        TakePhoto takePhoto = new TakePhoto(FillActivity.this);
        takePhoto.setOnPictureSelected(new TakePhoto.onPictureSelected() {
            @Override
            public void select(String path) {
//                mTvPhoto.setText("选择的图片地址：" + path);
                Glide.with(FillActivity.this).load("file://" + path).into(mIvPhoto);
            }
        });
        takePhoto.show();
    }
}
