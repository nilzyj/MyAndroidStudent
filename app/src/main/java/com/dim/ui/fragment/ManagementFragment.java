package com.dim.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dim.ui.ChangeActivity;
import com.dim.ui.R;

/**
 * Created by dim on 2017/3/9.
 */

public class ManagementFragment extends Fragment {
    private final String url = "";
    private Button btn_to_change;
    private String data = "";
    private EditText et_1,et_2,et_3,et_4,et_5,et_6,et_7,et_8,et_9,et_10,et_11,et_12,et_13,et_14,
            et_15,et_16,et_17,et_18,et_19,et_20,et_21,et_22,et_23,et_24,et_25,et_26,et_27,et_28,
            et_29,et_30,et_31,et_32,et_33,et_34,et_35,et_36,et_37,et_38,et_39,et_40,et_41,et_42,
            et_43,et_44,et_45,et_46,et_47,et_48;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_management, container, false);

        initView(view);

        btn_to_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataToJSON();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //提交考生填写的信息
//                        HttpUtils.httpPost(url,
//                                );
                    }
                }).start();
                Intent intent = new Intent(getActivity(), ChangeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initView(View view) {
        btn_to_change = (Button) view.findViewById(R.id.btn_commit);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
        et_1 = (EditText) view.findViewById(R.id.et_1);
    }

    private String getDataToJSON() {
        String json = "";

        return json;
    }
}
