package com.dim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dim.ui.model.HttpURL;
import com.dim.ui.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Register activity.
 *
 * @author dim
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Register URL
     */
    private final String URL = HttpURL.url + "RegisterServlet";
    /**
     * 输入注册姓名
     */
    @BindView(R.id.et_register_name)
    EditText mEtRegisterName;
    /**
     * 输入注册密码
     */
    @BindView(R.id.et_register_password)
    EditText mEtRegisterPassword;
    /**
     * 注册证件类型spinner
     */
    @BindView(R.id.spinner_register_zhengjian_leixin)
    Spinner mSpinnerRegisterZhengjianLeixin;
    /**
     * 注册证件号码
     */
    @BindView(R.id.et_register_zhengjian_haoma)
    EditText mEtRegisterZhengjianHaoma;
    /**
     * 注册提交按钮
     */
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        // TODO 注册加入证件和证件号信息
        // TODO 是否已注册
        ButterKnife.bind(this);
    }

    /**
     * 注册提交按钮响应
     */
    @OnClick(R.id.btn_register)
    public void register() {
        final String name = mEtRegisterName.getText().toString().trim();
        final String password = mEtRegisterPassword.getText().toString().trim();
        final String zhengjian_leixin = mSpinnerRegisterZhengjianLeixin.getSelectedItem().toString();
        final String zhengjian_haoma = mEtRegisterZhengjianHaoma.getText().toString();
        if (!"".equals(name) && !"".equals(password) && !"".equals(mSpinnerRegisterZhengjianLeixin)
                && !"".equals(mEtRegisterZhengjianHaoma)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String str = null;
                    try {
                        str = HttpUtil.httpPost(URL,
                                "username=" + URLEncoder.encode(name, "UTF-8")
                                        + "&pwd=" + URLEncoder.encode(password, "UTF-8")
                                        + "&leixin=" + URLEncoder.encode(zhengjian_leixin, "UTF-8")
                                        + "&haoma=" + URLEncoder.encode(zhengjian_haoma, "UTF-8"));
                        if (str == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,
                                            "未连接到服务器", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if ("success".equals(str)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,
                                            "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,
                                            "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    Log.d("TAG", str);
                }
            }).start();
        } else {
            Toast.makeText(this, "请输入姓名或密码", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerBackToLogin(View view) {
        finish();
    }
}
