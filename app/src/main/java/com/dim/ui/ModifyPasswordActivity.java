package com.dim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_old_password)
    AppCompatEditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    AppCompatEditText mEtNewPassword;
    @BindView(R.id.et_new_password_repeat)
    AppCompatEditText mEtNewPasswordRepeat;
    @BindView(R.id.btn_modify_password_commit)
    AppCompatButton mBtnModifyPasswordCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
    }
}
