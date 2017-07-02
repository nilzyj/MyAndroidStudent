package com.dim.ui;

import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.adapter.InfoAdapter;
import com.dim.ui.fingerprint.FingerprintAuthenticationDialogFragment;
import com.dim.ui.model.HttpURL;
import com.dim.ui.model.Info;
import com.dim.ui.util.HttpUtil;
import com.dim.ui.util.PinYinUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.dim.ui.FingerActivity.DEFAULT_KEY_NAME;

public class ConfirmActivity extends AppCompatActivity {

    private String getImgURL = HttpURL.url + "GetImageServlet";
    private String urlImg = HttpURL.url;
    private final String TAG = "ConfirmActivity";
    private List<Info> infoList = new ArrayList<Info>();
    private ListView listView;
    private ImageView mImageView;
    InfoAdapter infoAdapter;

    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;

    private TextView mTvBaokaohao;
    private TextView mTvConfirmState;
    AlertDialog.Builder normalDialog;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        mImageView = (ImageView) findViewById(R.id.iv_confirm_photo);
        mTvBaokaohao = (TextView) findViewById(R.id.baokaohao);
        mTvConfirmState = (TextView) findViewById(R.id.confirm_state);

        //获取在FunctionActivity请求的数据
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        Log.d(TAG, "从FunctionActivity中提前获取的报考信息：" + json);
        try {
            //将获得的json数据放入list
            if (json != null) {
                getDataToList(json);
            } else {
                Toast.makeText(this, "获取数据错误", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sp = getSharedPreferences("loginData", MODE_PRIVATE);
        String username = sp.getString("username", "");
        String baokaohao = sp.getString("baokaohao", "");
        String confirm = sp.getString("confirm", "");
        mTvBaokaohao.setText(baokaohao);
        Log.d("ConfirmActivity", baokaohao);
        if ("1".equals(confirm)) {
            mTvConfirmState.setText("已确认信息");
        }

        Log.d("ConfirmActivity", confirm);

        String[] strings = {getImgURL, username};

        GetImageTask getImageTask = new GetImageTask();
        getImageTask.execute(strings);

        //绑定数据源
        infoAdapter = new InfoAdapter(ConfirmActivity.this, R.layout.info_item,
                infoList);
        listView = (ListView) findViewById(R.id.list_view_confirm);
        listView.setAdapter(infoAdapter);


        //获取keyStore，keyStore允许存储加密密钥
        try {
            //AndroidKeyStore类型，难以从设备中导出，并且可以指明key的使用规则
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }


        //对称加密，创建 KeyGenerator 对象,根据一些指定的参数，来生成一个新的key。
        try {
            mKeyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        //Cipher 对象是一个按照一定的加密规则，将数据进行加密后的一个对象
        Cipher defaultCipher;
        Cipher cipherNotInvalidated;
        try {
            //创建Cipher对象
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipherNotInvalidated = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get an instance of Cipher", e);
        }

        //锁定和解锁软键盘，getSystemService():获得该类实例
        KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
        //访问指纹硬件的类
        FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
        //开始指纹识别的按钮
        TextView confirmButton = (TextView) findViewById(R.id.btn_confirm_submit);

        if (!keyguardManager.isKeyguardSecure()) {
            // 提示用户没有设置指纹或锁屏
            Toast.makeText(this,
                    "Secure lock screen hasn't set up.\n"
                            + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
                    Toast.LENGTH_LONG).show();
            confirmButton.setEnabled(false);
            return;
        }

        // noinspection ResourceType
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            confirmButton.setEnabled(false);
            // This happens when no fingerprints are registered.
            // 提示至少注册一个指纹信息
            Toast.makeText(this,
                    "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
                    Toast.LENGTH_LONG).show();
            return;
        }
        createKey(DEFAULT_KEY_NAME, true);
        createKey("key_not_invalidated", false);
        confirmButton.setEnabled(true);
        confirmButton.setOnClickListener(
                new PurchaseButtonClickListener(defaultCipher, DEFAULT_KEY_NAME));
    }

    public void createKey(String keyName, boolean invalidatedByBiometricEnrollment) {
        // 指纹识别注册流程。这是整个流程中请求用户设置指纹识别的地方。
        // 如果需要知道已注册指纹是否改变，keys的使用是有必要的。
        try {
            mKeyStore.load(null);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
            }
            mKeyGenerator.init(builder.build());
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class PurchaseButtonClickListener implements View.OnClickListener {

        Cipher mCipher;
        String mKeyName;

        PurchaseButtonClickListener(Cipher cipher, String keyName) {
            mCipher = cipher;
            mKeyName = keyName;
        }

        @Override
        public void onClick(View view) {
            if (sp.getString("confirm", "").equals("0")) {
                normalDialog = new AlertDialog.Builder(ConfirmActivity.this);
                normalDialog.setMessage("是否确认报考信息？确认后无法修改。");
                Log.d(TAG, "fillCommit: 初始化AlertDialog");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(TAG, "onClick: 点击对话框确定按钮");
                                // 设置加密对象供后面使用，该对象被授权使用指纹识别
                                if (initCipher(mCipher, mKeyName)) {
                                    //显示指纹识别对话框。用户能够选择使用加密的指纹识别或者使用服务器端的认证密码
                                    FingerprintAuthenticationDialogFragment fragment
                                            = new FingerprintAuthenticationDialogFragment();
                                    fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                                    fragment.setStage(
                                            FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);

                                    fragment.show(getFragmentManager(), "myFragment");
                                } else {
                                    FingerprintAuthenticationDialogFragment fragment
                                            = new FingerprintAuthenticationDialogFragment();
                                    fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                                    fragment.setStage(
                                            FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                                    fragment.show(getFragmentManager(), "myFragment");
                                }
                                mTvConfirmState.setText("已确认");
                                sp.edit().putString("confirm", "1");
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(TAG, "onClick: 点击对话框取消按钮");
                            }
                        });
                normalDialog.show();
            } else {
                Toast.makeText(ConfirmActivity.this, "信息已确认", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //初始化Cipher对象
    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private void getDataToList(String json) throws JSONException {
        Log.d(TAG, "将获取的报考信息转成JSON：" + json);
        PinYinUtil pyu = new PinYinUtil();

        JSONObject jsonObject = new JSONObject(json);
        String[] infos = {"证件类型", "证件号码", "民族", "性别", "婚否", "现役军人", "政治面貌",
                "籍贯所在地", "出生地", "户口所在地", "户口所在地详细地址", "考生档案所在地", "考生档案所在单位地址",
                "考生档案所在单位邮政编码", "现在学习或工作单位", "学习与工作经历", "何时何地何原因受过何种奖励或处分",
                "考生作弊情况", "家庭主要成员", "考生通讯地址", "考生通讯地址邮政编码", "固定电话", "移动电话",
                "电子邮箱", "考生来源", "毕业学校", "毕业专业", "取得最后学历的学习形式", "最后学历", "毕业证书编号",
                "获得最后学历毕业年月", "注册学号", "最后学位", "学位证书编号", "报考单位", "报考专业", "考试方式",
                "专项计划", "报考类别", "定向就业单位所在地", "定向就业单位", "报考院系", "研究方向", "政治理论",
                "外国语", "业务课一", "业务课二", "备用信息一", "备用信息二", "报考点所在省市", "报考点"};
        Info info_name = new Info("考生姓名", jsonObject.getString("name"));
        infoList.add(info_name);
        for (String key : infos) {
            Log.d(TAG, "JSON键值：" + key + ", " + jsonObject.getString(pyu.getStringPinYin(key)));
            Info info = new Info(key, jsonObject.getString(pyu.getStringPinYin(key)));
            infoList.add(info);
        }
    }

    /**
     * 获取照片
     */
    class GetImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String state = null;
            try {
                state = HttpUtil.httpPost(strings[0],
                        "username=" + URLEncoder.encode(strings[1], "UTF-8"));
                Log.d(TAG, "获取照片数据" + state);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return state;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            urlImg = urlImg + "image/" + s + ".jpg";
            Log.d(TAG, "获取图片的URL：" + urlImg);
            //加载图片
            okHttpImg();
        }
    }

    /**
     * 图片加载
     */
    private void okHttpImg() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        Observable.just(urlImg)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull String s) throws Exception {
                        final okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(s)
                                .build();
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            //从InputStream中得到bitmap
                            return BitmapFactory.decodeStream(response.body().byteStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Error(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        if (bitmap != null) {
                            mImageView.setImageBitmap(bitmap);
                            Log.d(TAG, "图片加载成功");
                        } else
                            Log.d(TAG, "onNext: bitmap is null!!!!!!!");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void confirmBackToFunction(View view) {
        finish();
    }
}
