package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dim.ui.adapter.InfoAdapter;
import com.dim.ui.model.Info;
import com.dim.ui.util.PinYinUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Modify activity.
 */
//信息修改界面
public class ModifyActivity extends AppCompatActivity {
    private List<Info> infoList = new ArrayList<Info>();
    private int position;
    private String strInfoName, strInfo;
    private ListView listView;
    /**
     * The Info adapter.
     */
    InfoAdapter infoAdapter;
    private final String TAG = "ModifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //获取在FunctionActivity请求的数据
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        try {
            //将获得的数据放入list
            getDataToList(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Log.d(TAG, "onItemClick: " + strInfoName + ":" + strInfo);
                //记录更改数据
                position = i;
                //将InfoName，Info传递到EditActivity
                Intent intent = new Intent(ModifyActivity.this, EditActivity.class);
                intent.putExtra("infoName", strInfoName);
                intent.putExtra("info", strInfo);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            String udpateData = data.getStringExtra("info");
            infoList.set(position, new Info(strInfoName, udpateData));
            infoAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param json json
     * @throws JSONException
     */
    private void getDataToList(String json) throws JSONException {
        Log.d("json", "getDataFromOtherActivity: " + json);
        PinYinUtil pyu = new PinYinUtil();
        JSONObject jsonObject = new JSONObject(json);
//        List<Info> infos = new ArrayList<Info>();
        String[] infos = {"证件类型", "证件号码", "民族", "性别", "婚否", "现役军人", "政治面貌",
                "籍贯所在地", "出生地", "户口所在地", "户口所在地详细地址", "考生档案所在地", "考生档案所在单位地址",
                "考生档案所在单位邮政编码", "现在学习或工作单位", "学习与工作经历", "何时何地何原因受过何种奖励或处分",
                "考生作弊情况", "家庭主要成员", "考生通讯地址", "考生通讯地址邮政编码", "固定电话", "移动电话",
                "电子邮箱", "考生来源", "毕业学校", "毕业专业", "取得最后学历的学习形式", "最后学历", "毕业证书编号",
                "获得最后学历毕业年月", "注册学号", "最后学位", "学位证书编号", "报考单位", "报考专业", "考试方式",
                "专项计划", "报考类别", "定向就业单位所在地", "定向就业单位", "报考院系", "研究方向", "政治理论", "外国语",
                "业务课一", "业务课二", "备用信息一", "备用信息二"};
        Info info_name = new Info("考生姓名", jsonObject.getString("name"));
        infoList.add(info_name);
        Log.d(TAG, "getDataToList: " + jsonObject.getString("name"));
        for (String key : infos) {
            Log.d(TAG, "getDataToList: " + key);
            Info info = new Info(key, jsonObject.getString(pyu.getStringPinYin(key)));
            infoList.add(info);
        }
    }

    /**
     * Modify back to function.
     * @param view the view
     */
    public void modifyBackToFunction(View view) {
        finish();
    }
}
