package com.dim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dim.ui.Adapter.InfoAdapter;
import com.dim.ui.Model.Info;

import java.util.ArrayList;
import java.util.List;

public class ChangeActivity extends AppCompatActivity {
    private LinearLayout ll_change;
    private List<Info> infoList = new ArrayList<Info>();
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

        initInfo();
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

    private void initInfo() {
        Info info1 = new Info("a", "a");
        infoList.add(info1);
        Info info2 = new Info("b", "b");
        infoList.add(info2);
    }
}
