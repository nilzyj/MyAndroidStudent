package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dim.ui.adapter.InfoAdapter;
import com.dim.ui.model.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//信息修改界面
public class ModifyActivity extends AppCompatActivity {
//    private final String URL = "http://10.0.2.2:8080/Manage/GetInformationServlet";
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
        JSONObject jsonObject = new JSONObject(json);
        Info info1 = new Info("考生姓名", jsonObject.getString("name"));
        infoList.add(info1);
        Info info2 = new Info("证件类型", jsonObject.getString("document_type"));
        infoList.add(info2);
        Info info3 = new Info("证件号码", jsonObject.getString("document_number"));
        infoList.add(info3);
        Info info4 = new Info("民族", jsonObject.getString("minzu"));
        infoList.add(info4);
        Info info5 = new Info("性别", jsonObject.getString("sex"));
        infoList.add(info5);
        Info info6 = new Info("婚否", jsonObject.getString("hun_fou"));
        infoList.add(info6);
        Info info7 = new Info("现役军人", jsonObject.getString("xianyi_junren"));
        infoList.add(info7);
        Info info8 = new Info("政治面貌", jsonObject.getString("zhengzhi_mianmao"));
        infoList.add(info8);
        Info info9 = new Info("籍贯所在地", jsonObject.getString("jiguan_suozaidi"));
        infoList.add(info9);
        Info info10 = new Info("出生地", jsonObject.getString("chushendi"));
        infoList.add(info10);
        Info info11 = new Info("户口所在地", jsonObject.getString("hukou_suozaidi"));
        infoList.add(info11);
        Info info12 = new Info("户口所在地详细地址", jsonObject.getString("hukou_suozaidi_xiangxidizhi"));
        infoList.add(info12);
        Info info13 = new Info("考生档案所在地", jsonObject.getString("kaoshen_dangan_suozaidi"));
        infoList.add(info13);
        Info info14 = new Info("考生档案所在单位地址", jsonObject.getString("kaoshen_dangan_suozaidanwei_dizhi"));
        infoList.add(info14);
        Info info15 = new Info("考生档案所在档位邮政编码", jsonObject.getString("kaoshen_dangan_suozaidanwei_youzhenbianma"));
        infoList.add(info15);
        Info info16 = new Info("现在学习或工作单位", jsonObject.getString("xianzai_xuexihuo_gongzuodanwei"));
        infoList.add(info16);
        Info info17 = new Info("学习与工作经历", jsonObject.getString("xuexiyu_gongzuojinli"));
        infoList.add(info17);
        Info info18 = new Info("何时何地何原因受过何种奖励或处分", jsonObject.getString("heshi_hedi_heyuanyin_shouguohezhong_jianglihuochufen"));
        infoList.add(info18);
        Info info19 = new Info("考生作弊情况", jsonObject.getString("kaoshen_zuobi_qinkuang"));
        infoList.add(info19);
        Info info20 = new Info("家庭主要成员", jsonObject.getString("jiatin_zhuyao_chengyuan"));
        infoList.add(info20);
        Info info21 = new Info("考生通讯地址", jsonObject.getString("kaoshen_tongxun_dizhi"));
        infoList.add(info21);
        Info info22 = new Info("考生通讯地址邮政编码", jsonObject.getString("kaoshen_tongxun_dizhi_youzhenbianma"));
        infoList.add(info22);
        Info info23 = new Info("固定电话", jsonObject.getString("gudin_dianhua"));
        infoList.add(info23);
        Info info24 = new Info("移动电话", jsonObject.getString("mobile_phone"));
        infoList.add(info24);
        Info info25 = new Info("电子邮箱", jsonObject.getString("email"));
        infoList.add(info25);
        Info info26 = new Info("考生来源", jsonObject.getString("kaoshen_laiyuan"));
        infoList.add(info26);
        Info info27 = new Info("毕业学校", jsonObject.getString("biye_xuexiao"));
        infoList.add(info27);
        Info info28 = new Info("毕业专业", jsonObject.getString("biye_zhuanye"));
        infoList.add(info28);
        Info info29 = new Info("取得最后学历的学习形式", jsonObject.getString("qude_zuihouxuelide_xuexixinshi"));
        infoList.add(info29);
        Info info30 = new Info("最后学历", jsonObject.getString("zuihouxueli"));
        infoList.add(info30);
        Info info31 = new Info("毕业证书编号", jsonObject.getString("biye_zhenshu_bianhao"));
        infoList.add(info31);
        Info info32 = new Info("获得最后学历毕业年月", jsonObject.getString("huode_zuihou_xueli_biyelianyue"));
        infoList.add(info32);
        Info info33 = new Info("注册学号", jsonObject.getString("zhuce_xuehao"));
        infoList.add(info33);
        Info info34 = new Info("最后学位", jsonObject.getString("zuihou_xuewei"));
        infoList.add(info34);
        Info info35 = new Info("学位证书编号", jsonObject.getString("xuewei_zhengshu_bianhao"));
        infoList.add(info35);
        Info info36 = new Info("报考单位", jsonObject.getString("baokao_danwei"));
        infoList.add(info36);
        Info info37 = new Info("报考专业", jsonObject.getString("baokao_zhuanye"));
        infoList.add(info37);
        Info info38 = new Info("考试方式", jsonObject.getString("kaoshi_fangshi"));
        infoList.add(info38);
        Info info39 = new Info("专项计划", jsonObject.getString("zhuanxiang_jihua"));
        infoList.add(info39);
        Info info40 = new Info("报考类别", jsonObject.getString("baokao_leibie"));
        infoList.add(info40);
        Info info41 = new Info("定向就业所在地", jsonObject.getString("dinxiang_jiuye_suozaidi"));
        infoList.add(info41);
        Info info42 = new Info("定向就业单位", jsonObject.getString("dinxiang_jiuye_danwei"));
        infoList.add(info42);
        Info info43 = new Info("报考院系", jsonObject.getString("baokao_yuanxi"));
        infoList.add(info43);
        Info info44 = new Info("研究方向", jsonObject.getString("yanjiufangxiang"));
        infoList.add(info44);
        Info info45 = new Info("政治理论", jsonObject.getString("zhengzhi_lilun"));
        infoList.add(info45);
        Info info46 = new Info("外国语", jsonObject.getString("waiguoyu"));
        infoList.add(info46);
        Info info47 = new Info("业务课1", jsonObject.getString("yewuke1"));
        infoList.add(info47);
        Info info48 = new Info("业务课2", jsonObject.getString("yewuke2"));
        infoList.add(info48);
        Info info49 = new Info("备用信息1", jsonObject.getString("beiyong_xinxi1"));
        infoList.add(info49);
        Info info50 = new Info("备用信息2", jsonObject.getString("beiyong_xinxi2"));
        infoList.add(info50);
    }

    public void modifyBackToFunction(View view) {
        finish();
    }
}
