package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dim.ui.Adapter.InfoAdapter;
import com.dim.ui.Http.HttpUtils;
import com.dim.ui.Model.Account;
import com.dim.ui.Model.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ChangeActivity extends AppCompatActivity {
    private final String URL = "http://192.168.0.1:8080/Manage/GetInformationServlet";
    private LinearLayout ll_change;
    private List<Info> infoList = new ArrayList<Info>();
    private Account mAccount;
    private String name;
    private JSONObject jsonObject;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        ll_change = (LinearLayout) findViewById(R.id.ll_change);
        ll_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        try {
            initInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final InfoAdapter infoAdapter = new InfoAdapter(ChangeActivity.this, R.layout.info_item,
                infoList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(infoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str_info = infoList.get(i).getInfo();
                String str_content = infoList.get(i).getContent();
                Intent intent = new Intent(ChangeActivity.this, EditActivity.class);
                intent.putExtra("info", str_info);
                intent.putExtra("content", str_content);
                startActivity(intent);
            }
        });
    }

    private void initInfo() throws JSONException {
//        SharedPreferences spLoginData = this.getSharedPreferences("", MODE_PRIVATE);
//        String name = spLoginData.getString("name", "");
        name = mAccount.getName();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = "";
                try {
                    json = HttpUtils.httpPost(URL, "name=" + URLEncoder.encode(name,"UTF-8"));
                    jsonObject = new JSONObject(json);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Info info1 = new Info("考生姓名", jsonObject.getString("name"));
        infoList.add(info1);
        Info info2 = new Info("b", "b");
        infoList.add(info2);
    }
}
