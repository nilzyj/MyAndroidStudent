package com.dim.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dim.ui.ChangeActivity;
import com.dim.ui.Http.HttpUtils;
import com.dim.ui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

/**
 * Created by dim on 2017/3/9.
 */

public class ManagementFragment extends Fragment {
    private final String URL = "http://192.168.191.1:8080/Manage/fillInInformationServlet";
    @BindView(R.id.et_1) EditText mEt1;
    @BindView(R.id.et_2) EditText mEt2;
    @BindView(R.id.et_3) EditText mEt3;
    @BindView(R.id.et_4) EditText mEt4;
    @BindView(R.id.et_5) EditText mEt5;
    @BindView(R.id.et_6) EditText mEt6;
    @BindView(R.id.et_7) EditText mEt7;
    @BindView(R.id.et_8) EditText mEt8;
    @BindView(R.id.et_9) EditText mEt9;
    @BindView(R.id.et_10) EditText mEt10;
    @BindView(R.id.et_11) EditText mEt11;
    @BindView(R.id.et_12) EditText mEt12;
    @BindView(R.id.et_13) EditText mEt13;
    @BindView(R.id.et_14) EditText mEt14;
    @BindView(R.id.et_15) EditText mEt15;
    @BindView(R.id.et_16) EditText mEt16;
    @BindView(R.id.et_17) EditText mEt17;
    @BindView(R.id.et_18) EditText mEt18;
    @BindView(R.id.et_19) EditText mEt19;
    @BindView(R.id.et_20) EditText mEt20;
    @BindView(R.id.et_21) EditText mEt21;
    @BindView(R.id.et_22) EditText mEt22;
    @BindView(R.id.et_23) EditText mEt23;
    @BindView(R.id.et_24) EditText mEt24;
    @BindView(R.id.et_25) EditText mEt25;
    @BindView(R.id.et_26) EditText mEt26;
    @BindView(R.id.et_27) EditText mEt27;
    @BindView(R.id.et_28) EditText mEt28;
    @BindView(R.id.et_29) EditText mEt29;
    @BindView(R.id.et_30) EditText mEt30;
    @BindView(R.id.et_31) EditText mEt31;
    @BindView(R.id.et_32) EditText mEt32;
    @BindView(R.id.et_33) EditText mEt33;
    @BindView(R.id.et_34) EditText mEt34;
    @BindView(R.id.et_35) EditText mEt35;
    @BindView(R.id.et_36) EditText mEt36;
    @BindView(R.id.et_37) EditText mEt37;
    @BindView(R.id.et_38) EditText mEt38;
    @BindView(R.id.et_39) EditText mEt39;
    @BindView(R.id.et_40) EditText mEt40;
    @BindView(R.id.et_41) EditText mEt41;
    @BindView(R.id.et_42) EditText mEt42;
    @BindView(R.id.et_43) EditText mEt43;
    @BindView(R.id.et_44) EditText mEt44;
    @BindView(R.id.et_45) EditText mEt45;
    @BindView(R.id.et_46) EditText mEt46;
    @BindView(R.id.et_47) EditText mEt47;
    @BindView(R.id.et_48) EditText mEt48;
    @BindView(R.id.et_49) EditText mEt49;
    @BindView(R.id.et_50) EditText mEt50;
    @BindView(R.id.btn_commit) Button mBtnCommit;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_management, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_commit)
    public void onClick() {
        Log.d(TAG, "onClick: fragment");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String state = "";
                //提交考生填写的信息
                try {
                    String data_json = getDataToJSON();
                    Log.d(TAG, "run: getJSON" + data_json);
                    state = HttpUtils.httpPost(URL, "data=" +
                            URLEncoder.encode(data_json, "UTF-8"));
                    Log.d(TAG, "run: send" + state);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Intent intent = new Intent(getActivity(), ChangeActivity.class);
        startActivity(intent);
    }

    private String getDataToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //获取EditText数据并写成json格式
        //{"a":"data"}
        jsonObject.put("name", mEt1.getText().toString());//考生姓名
        jsonObject.put("document_type", mEt2.getText().toString());//证件类型
        jsonObject.put("document_number", mEt3.getText().toString());//证件号码
        jsonObject.put("minzu", mEt4.getText().toString());//民族
        jsonObject.put("sex", mEt5.getText().toString());//性别
        jsonObject.put("hun_fou", mEt6.getText().toString());//婚否
        jsonObject.put("xianyi_junren", mEt7.getText().toString());//现役军人
        jsonObject.put("zhengzhi_mianmao", mEt8.getText().toString());//政治面貌
        jsonObject.put("jiguan_suozaidi", mEt9.getText().toString());//籍贯所在地
        jsonObject.put("chushendi", mEt10.getText().toString());//出生地
        jsonObject.put("hukou_suozaidi", mEt11.getText().toString());//户口所在地
        jsonObject.put("hukou_suozaidi_xianxidizhi", mEt12.getText().toString());//户口所在地详细地址
        jsonObject.put("kaoshen_dangan_suozaidi", mEt13.getText().toString());//考生档案所在地
        jsonObject.put("kaoshen_dangan_suozaidanwei_dizhi", mEt14.getText().toString());//考生档案所在单位地址
        jsonObject.put("kaoshen_dangan_suozaidanwei_youzhenbianma", mEt15.getText().toString());//考生档案所在单位邮政编码
        jsonObject.put("xianzai_xuexihuo_gongzuodanwei", mEt16.getText().toString());//现在学习或工作单位
        jsonObject.put("xuexiyu_gongzuojinli", mEt17.getText().toString());//学习与工作经历
        jsonObject.put("heshi_hedi_heyuanyin_shouguohezhong_jianlihuochufen", mEt18.getText().toString());//何时何地何原因受过何种奖励或处分
        jsonObject.put("kaoshen_zuobi_qinkuang", mEt19.getText().toString());//考生作弊情况
        jsonObject.put("jiatin_zhuyao_chengyuan", mEt20.getText().toString());//家庭主要成员

        jsonObject.put("kaoshen_tongxun_dizhi", mEt21.getText().toString());//考生通讯地址
        jsonObject.put("kaoshen_tongxun_dizhi_youzhenbianma", mEt22.getText().toString());//考生通讯地址邮政编码
        jsonObject.put("gudin_dianhua", mEt23.getText().toString());//固定电话
        jsonObject.put("yidong_dianhua", mEt24.getText().toString());//移动电话
        jsonObject.put("email", mEt25.getText().toString());//电子邮箱

        jsonObject.put("kaoshen_laiyuan", mEt26.getText().toString());//考生来源
        jsonObject.put("biye_xuexiao", mEt27.getText().toString());//毕业学校
        jsonObject.put("biye_zhuanye", mEt28.getText().toString());//毕业专业
        jsonObject.put("qude_zuihouxuelide_xuexixinshi", mEt29.getText().toString());//取得最后学历的学习形式
        jsonObject.put("zuihouxueli", mEt30.getText().toString());//最后学历
        jsonObject.put("biye_zhenshu_bianhao", mEt31.getText().toString());//毕业证书编号
        jsonObject.put("huode_zuihou_xueli_biyelianyue", mEt32.getText().toString());//获得最后学历毕业年月
        jsonObject.put("zhuce_xuehao", mEt33.getText().toString());//注册学号
        jsonObject.put("zuihou_xuewei", mEt34.getText().toString());//最后学位
        jsonObject.put("xuewei_zhengshu_bianhao", mEt35.getText().toString());//学位证书编号
        jsonObject.put("baokao_danwei", mEt36.getText().toString());//报考单位
        jsonObject.put("baokao_zhuanye", mEt37.getText().toString());//报考专业

        jsonObject.put("kaoshi_fangshi", mEt38.getText().toString());//考试方式
        jsonObject.put("zhuanxiang_jihua", mEt39.getText().toString());//专项计划
        jsonObject.put("baokao_leibie", mEt40.getText().toString());//报考类别
        jsonObject.put("xinxiang_jiuye_suozaidi", mEt41.getText().toString());//定向就业单位所在地
        jsonObject.put("dinxiang_jiuye_danwei", mEt42.getText().toString());//定向就业单位
        jsonObject.put("baokao_yuanxi", mEt43.getText().toString());//报考院系
        jsonObject.put("yanjiufangxiang", mEt44.getText().toString());//研究方向
        jsonObject.put("zhengzhi_lilun", mEt45.getText().toString());//政治理论
        jsonObject.put("waiguoyu", mEt46.getText().toString());//外国语
        jsonObject.put("yewuke1", mEt47.getText().toString());//业务课一
        jsonObject.put("yewuke2", mEt48.getText().toString());//业务课二

        jsonObject.put("beiyong_xinxi1", mEt49.getText().toString());//备用信息1
        jsonObject.put("beiyong_xinxi2", mEt50.getText().toString());//备用信息2
//        jsonObject.put("name", "zyj");
//        json = "{'姓名':zyj, '性别':男}";
        return jsonObject.toString();
    }
}
