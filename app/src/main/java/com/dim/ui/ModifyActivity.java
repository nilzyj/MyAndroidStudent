package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dim.ui.PinYinUtil.PinYinUtil;
import com.dim.ui.adapter.InfoAdapter;
import com.dim.ui.model.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//信息修改界面
public class ModifyActivity extends AppCompatActivity {
    private final String URL = "http://10.0.2.2:8080/Manage/GetInformationServlet";
//    private final String URL = "http://192.168.191.1:8080/Manage/GetInformationServlet";
    private List<Info> infoList = new ArrayList<Info>();
    private String str = "";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        try {
            getDataFromOtherActivity(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InfoAdapter infoAdapter = new InfoAdapter(ModifyActivity.this, R.layout.info_item,
                infoList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(infoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strInfoName = infoList.get(i).getInfo();
                String strInfo = infoList.get(i).getContent();
                Intent intent = new Intent(ModifyActivity.this, EditActivity.class);
                intent.putExtra("infoName", strInfoName);
                intent.putExtra("info", strInfo);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getDataFromOtherActivity(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InfoAdapter infoAdapter = new InfoAdapter(ModifyActivity.this, R.layout.info_item,
                infoList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(infoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            str = data.getStringExtra("update");
        }
    }

    private void getDataFromOtherActivity(String json) throws JSONException {
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
        for (String key : infos) {
            Info info = new Info(key, jsonObject.getString(pyu.getStringPinYin(key)));
            infoList.add(info);
        }
    }

    public void modifyBackToFunction(View view) {
        finish();
    }
}
