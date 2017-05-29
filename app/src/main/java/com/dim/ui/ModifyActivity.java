package com.dim.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dim.ui.adapter.InfoAdapter;
import com.dim.ui.model.HttpURL;
import com.dim.ui.model.Info;
import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PinYinUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;



/**
 * The type Modify activity.
 */
//信息修改界面
public class ModifyActivity extends AppCompatActivity {
    /**
     * 修改并更新报考信息URL.
     */
    private String URL = HttpURL.url + "UpdateJsonDataServlet";
    private String getImgURL = HttpURL.url + "GetImageServlet";
    //    private String urlImg = HttpURL.url + "image/052d95f5-47ed-4144-94c6-716acdc2a20b.jpg";
    private String urlImg = HttpURL.url;
    //        private String urlImg = "http://www.feizl.com/upload2007/2012_10/121007235981441.jpg";
    private final String TAG = "ModifyActivity";
    private List<Info> infoList = new ArrayList<Info>();
    /**
     * 用于记录点击的Item，并在修改信息后对此Item进行更新
     */
    private int position;
    /**
     * 信息名称
     */
    private String strInfoName;
    /**
     * 信息内容
     */
    private String strInfo;
    /**
     * 获取并保存所有信息内容，将其传到EditActivity
     */
    private ListView listView;

    private ImageView mImageView;
    /**
     * The Info Adapter.
     * ListView Adapter.
     */
    InfoAdapter infoAdapter;
    private String[] notModifyInfo = {"考生姓名", "证件类型", "证件号码", "考试方式", "报考点所在省市",
            "报考点", "招生单位"};
    private String[] tvDialogInfo = {"性别", "婚否", "民族", "现役军人", "政治面貌", "考生来源"
            , "取得最后学历的学习形式", "最后学历", "最后学位", "报考单位", "报考专业", "报考类别", "专项计划"};
    private String[] tvInfoAddress = {"籍贯所在地", "出生地", "户口所在地"};
    private String[] tvInfoTime = {"获得最后学历毕业年月"};
    private List<String> tvInfoAddressList = Arrays.asList(tvInfoAddress);
    private List<String> tvInfoTimeList = Arrays.asList(tvInfoTime);
    private List notModifyInfoList = Arrays.asList(notModifyInfo);
    private List tvDialogInfoList = Arrays.asList(tvDialogInfo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Log.d(TAG, "**********************************");

        mImageView = (ImageView) findViewById(R.id.iv_modify_photo);

        //获取在FunctionActivity请求的数据
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        Log.d(TAG, "从FunctionActivity中提前获取的报考信息：" + json);
        try {
            //将获得的json数据放入list
            if (json != null) {
                getDataToList(json);
            } else {
                Toast.makeText(this, "获取数据错误", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
        String username = sp.getString("username", "");

        String[] strings = {getImgURL, username};

        GetImageTask getImageTask = new GetImageTask();
        getImageTask.execute(strings);

        //绑定数据源
        infoAdapter = new InfoAdapter(ModifyActivity.this, R.layout.info_item,
                infoList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(infoAdapter);

        //设置ListView响应
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //根据点击参数获取对应list中的数据
                strInfoName = infoList.get(i).getInfo();
                strInfo = infoList.get(i).getContent();

                //记录更改数据
                position = i;

                Log.d(TAG, "点击的Item信息：" + strInfoName + ":" + strInfo);
                //是否以及怎样修改
                ifModifyOrHowModify(strInfoName, view);
            }
        });
    }

    /**
     * 接收EditActivity返回的修改信息，并更新数据
     *
     * @param requestCode 请求code
     * @param resultCode  返回code
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            String udpateData = data.getStringExtra("info");
            //对list中数据进行更新
            infoList.set(position, new Info(strInfoName, udpateData));
            //更新Item
            infoAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 将json数据放入list中作为ListView数据源
     *
     * @param json json
     * @throws JSONException
     */
    private void getDataToList(String json) throws JSONException {
        Log.d(TAG, "将获取的报考信息转成JSON：" + json);
        PinYinUtil pyu = new PinYinUtil();

        JSONObject jsonObject = new JSONObject(json);
        String[] infos = {"证件类型", "证件号码", "民族", "性别", "婚否", "现役军人", "政治面貌",
                "籍贯所在地", "出生地", "户口所在地", "户口所在地详细地址", "考生档案所在地", "考生档案所在单位地址",
                "考生档案所在单位邮政编码", "现在学习或工作单位", "学习与工作经历", "何时何地何原因受过何种奖励或处分",
                "考生作弊情况", "家庭主要成员", "考生通讯地址", "考生通讯地址邮政编码", "固定电话", "移动电话",
                "电子邮箱", "考生来源", "毕业学校", "毕业专业", "取得最后学历的学习形式", "最后学历", "毕业证书编号",
                "获得最后学历毕业年月", "注册学号", "最后学位", "学位证书编号", "报考单位", "报考专业", "考试方式",
                "专项计划", "报考类别", "定向就业单位所在地", "定向就业单位", "报考院系", "研究方向", "政治理论",
                "外国语", "业务课一", "业务课二", "备用信息一", "备用信息二", "报考点所在省市", "报考点"};
        Info info_name = new Info("考生姓名", jsonObject.getString("name"));
        infoList.add(info_name);
        for (String key : infos) {
            Log.d(TAG, "JSON键值：" + key + ", " + jsonObject.getString(pyu.getStringPinYin(key)));
            Info info = new Info(key, jsonObject.getString(pyu.getStringPinYin(key)));
            infoList.add(info);
        }
    }

    /**
     * 根据infoName判断是否能够修改以及怎样修改
     * @param infoName 信息名称
     */
    private void ifModifyOrHowModify(String infoName, View view) {
        Log.d(TAG, "点击在ModifyActivity进行修改的信息：");
        if (notModifyInfoList.contains(infoName)) {
            Toast.makeText(this, "此项不可修改", Toast.LENGTH_SHORT).show();
        } else if (tvDialogInfoList.contains(infoName)) {
//            "性别", "婚否", "民族", "现役军人", "政治面貌", "考生来源", "取得最后学历的学习形式"
//              , "最后学历", "最后学位""报考单位", "报考专业", "报考类别", "专项计划"
            switch (infoName) {
                case "性别":
                    tvAlertDialog(new String[]{"男", "女"});
                    break;
                case "婚否":
                    tvAlertDialog(new String[]{"是", "否"});
                    break;
                case "民族":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_minzu));
                    break;
                case "现役军人":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_xianyi_junren));
                    break;
                case "政治面貌":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_zhengzhi_mianmao));
                    break;
                case "考生来源":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_kaoshen_laiyuan));
                    break;
                case "取得最后学历的学习形式":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_xuexi_xinshi));
                    break;
                case "最后学历":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_zuihou_xueli));
                    break;
                case "最后学位":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_zuihou_xuewei));
                    break;
                case "报考单位":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_danwei));
                    break;
                case "报考专业":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_zhuanye));
                    break;
                case "报考类别":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_baokao_leibie));
                    break;
                case "专项计划":
                    tvAlertDialog(getResources().getStringArray(R.array.spinner_zhuanxiang_jihua));
                    break;
            }
        } else if (tvInfoAddressList.contains(infoName)) {
//            "籍贯所在地", "出生地", "户口所在地"
            switch (infoName) {
                case "籍贯所在地":
                    chooseAreaOrDate(this, view, true, "address");
                    break;
                case "出生地":
                    chooseAreaOrDate(this, view, false, "address");
                    break;
                case "户口所在地":
                    chooseAreaOrDate(this, view, false, "address");
                    break;
            }
        } else if (tvInfoTimeList.contains(infoName)) {
            chooseAreaOrDate(this, view, true, "date");
        } else {
            //将InfoName，Info传递到EditActivity，并能够获取EditActivity返回信息
            Intent intent = new Intent(ModifyActivity.this, EditActivity.class);
            intent.putExtra("infoName", strInfoName);
            intent.putExtra("info", strInfo);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * 弹出对话框选择信息项内容，即给对话框设置数据源
     * @param strings 对话框数据源
     */
    private void tvAlertDialog(final String[] strings) {
        Dialog alertDialog = new AlertDialog.Builder(this)
//                            .setTitle("你喜欢吃哪种水果？")
//                            .setIcon(R.mipmap.ic_launcher)
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateListAndUploadData(strings[which]);
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * The type Edit task.
     * 修改后提交更新
     */
    class ModifySaveTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0],
                        "updateData=" + URLEncoder.encode(strings[1], "UTF-8"));
                Log.d(TAG, "提交更新获取的返回数据：" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                Toast.makeText(ModifyActivity.this, "未连接服务器",
                        Toast.LENGTH_SHORT).show();
            } else if ("success".equals(s)) {
                Toast.makeText(ModifyActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取照片
     */
    class GetImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0],
                        "username=" + URLEncoder.encode(strings[1], "UTF-8"));
                Log.d(TAG, "获取照片数据" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            urlImg = urlImg + "image/" + s + ".jpg";
            Log.d(TAG, "获取图片的URL：" + urlImg);
            //加载图片
            okHttpImg();
        }
    }

    //选择器
    private void chooseAreaOrDate(Context context, View view, boolean flag, String str) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if ("date".equals(str)) {
            selectDate(context);
        } else {
            selectAddress(context, flag);//调用CityPicker选取区域
        }

    }

    private void selectAddress(Context context, final boolean flag) {
        CityPicker cityPicker = new CityPicker.Builder(context)
                .textSize(16)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("上海市")
                .city("上海市")
                .district("宝山区")
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
                String str;
                //获得选择地址
                if (flag) {
                    str = province.trim() + city.trim();
                } else {
                    str = province.trim() + city.trim() + district.trim();
                }
                updateListAndUploadData(str);
            }
        });
    }

    private void selectDate(Context context) {

        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateListAndUploadData(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
            }
        }, 2017, 6, 1);
        datePicker.show();
    }

    private void updateListAndUploadData(String data) {
        Log.d(TAG, "更新并上传的数据：" + strInfoName + ", " + data);
        //对list中数据进行更新
        infoList.set(position, new Info(strInfoName,
                data));
        //更新Item
        infoAdapter.notifyDataSetChanged();
        //获取sharedPreferences中的username，便于更新数据库
        SharedPreferences sharedPreferences =
                getSharedPreferences("loginData", MODE_PRIVATE);
        //将要更新的数据转为json
        JSONObject jsonObject = new JSONObject();
        PinYinUtil pinyin = new PinYinUtil();
        try {
            jsonObject.put("infoName", pinyin.getStringPinYin(strInfoName));
            jsonObject.put("info", data);
            jsonObject.put("username", sharedPreferences.getString("username", null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] strings = {URL, jsonObject.toString()};
        //更新数据库
        ModifySaveTask modifySaveTask = new ModifySaveTask();
        modifySaveTask.execute(strings);
        Toast.makeText(ModifyActivity.this
                , data
                , Toast.LENGTH_SHORT).show();
    }

    /**
     * 图片加载
     */
    private void okHttpImg() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        Observable.just(urlImg)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull String s) throws Exception {
                        final okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(s)
                                .build();
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            //从InputStream中得到bitmap
                            return BitmapFactory.decodeStream(response.body().byteStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Error(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        if (bitmap != null) {
                            mImageView.setImageBitmap(bitmap);
                            Log.d(TAG, "图片加载成功");
                        }
                        else
                            Log.d(TAG, "onNext: bitmap is null!!!!!!!");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private byte[] inputStream2ByteArr(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * Modify back to function.
     *
     * @param view the view
     */

    public void modifyBackToFunction(View view) {
        finish();
    }
}
