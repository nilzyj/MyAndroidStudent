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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dim.ui.R.id.et_1;

/**
 * Created by dim on 2017/3/9.
 */

public class ManagementFragment extends Fragment {
    private final String url = "";
    @BindView(et_1)
    EditText mEt1;
    @BindView(R.id.et_2)
    EditText mEt2;
    @BindView(R.id.et_3)
    EditText mEt3;
    @BindView(R.id.et_4)
    EditText mEt4;
    @BindView(R.id.et_5)
    EditText mEt5;
    @BindView(R.id.et_6)
    EditText mEt6;
    @BindView(R.id.et_7)
    EditText mEt7;
    @BindView(R.id.et_8)
    EditText mEt8;
    @BindView(R.id.et_9)
    EditText mEt9;
    @BindView(R.id.et_10)
    EditText mEt10;
    @BindView(R.id.et_11)
    EditText mEt11;
    @BindView(R.id.et_12)
    EditText mEt12;
    @BindView(R.id.et_13)
    EditText mEt13;
    @BindView(R.id.et_14)
    EditText mEt14;
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
    EditText mEt21;
    @BindView(R.id.et_22)
    EditText mEt22;
    @BindView(R.id.et_23)
    EditText mEt23;
    @BindView(R.id.et_24)
    EditText mEt24;
    @BindView(R.id.et_25)
    EditText mEt25;
    @BindView(R.id.et_26)
    EditText mEt26;
    @BindView(R.id.et_27)
    EditText mEt27;
    @BindView(R.id.et_28)
    EditText mEt28;
    @BindView(R.id.et_29)
    EditText mEt29;
    @BindView(R.id.et_30)
    EditText mEt30;
    @BindView(R.id.et_31)
    EditText mEt31;
    @BindView(R.id.et_32)
    EditText mEt32;
    @BindView(R.id.et_33)
    EditText mEt33;
    @BindView(R.id.et_34)
    EditText mEt34;
    @BindView(R.id.et_35)
    EditText mEt35;
    @BindView(R.id.et_36)
    EditText mEt36;
    @BindView(R.id.et_37)
    EditText mEt37;
    @BindView(R.id.et_38)
    EditText mEt38;
    @BindView(R.id.et_39)
    EditText mEt39;
    @BindView(R.id.et_40)
    EditText mEt40;
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
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    private Button btn_to_change;
    private String data = "";
    private Unbinder unbinder;
//    private EditText et_1, et_2, et_3, et_4, et_5, et_6, et_7, et_8, et_9, et_10, et_11, et_12, et_13, et_14,
//            et_15, et_16, et_17, et_18, et_19, et_20, et_21, et_22, et_23, et_24, et_25, et_26, et_27, et_28,
//            et_29, et_30, et_31, et_32, et_33, et_34, et_35, et_36, et_37, et_38, et_39, et_40, et_41, et_42,
//            et_43, et_44, et_45, et_46, et_47, et_48;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_management, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView(view);
        getDataToJSON();

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
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView(View view) {
        btn_to_change = (Button) view.findViewById(R.id.btn_commit);
    }

    private String getDataToJSON() {
        String json = "";
//        json = mEt1.getText().toString() + mEt2.getText().toString();

        return json;
    }
}
