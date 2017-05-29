/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.dim.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ui.fingerprint.FingerprintAuthenticationDialogFragment;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
/**
 * 使用指纹识别的对称加密功能的主要流程如下：
 * 1. 使用 KeyGenerator 创建一个对称密钥，存放在 KeyStore 里。
 * 2. 设置 KeyGenParameterSpec.Builder.setUserAuthenticationRequired() 为true，
 * 3. 使用创建好的对称密钥初始化一个Cipher对象，并用该对象调用 FingerprintManager.authenticate() 方法
 * 启动指纹传感器并开始监听。
 * 4. 重写 FingerprintManager.AuthenticationCallback 的几个回调方法，以处理指纹识别成功
 * （onAuthenticationSucceeded()）、失败（onAuthenticationFailed() 和 onAuthenticationError()）等情况。
 */
public class FingerActivity extends Activity {

    /** TAG */
    private static final String TAG = FingerActivity.class.getSimpleName();
    /**  */
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    /**  */
    private static final String SECRET_MESSAGE = "Very secret message";
    /**  */
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    /**
     * The constant DEFAULT_KEY_NAME.
     */
    public static final String DEFAULT_KEY_NAME = "default_key";

    /**  */
    private KeyStore mKeyStore;
    /**  */
    private KeyGenerator mKeyGenerator;
    /**  */
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);

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

        // 获取是否使用指纹识别的设置参数
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //锁定和解锁软键盘，getSystemService():获得该类实例
        KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
        //访问指纹硬件的类
        FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
        //开始指纹识别的按钮
        Button purchaseButton = (Button) findViewById(R.id.purchase_button);
        Button purchaseButtonNotInvalidated = (Button) findViewById(
                R.id.purchase_button_not_invalidated);
        //N = 24，api < 24，隐藏purchaseButtonNotInvalidated
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            purchaseButtonNotInvalidated.setEnabled(true);
            purchaseButtonNotInvalidated.setOnClickListener(
                    new PurchaseButtonClickListener(cipherNotInvalidated,
                            KEY_NAME_NOT_INVALIDATED));
        } else {
            purchaseButtonNotInvalidated.setVisibility(View.GONE);
            findViewById(R.id.purchase_button_not_invalidated_description)
                    .setVisibility(View.GONE);
        }


        if (!keyguardManager.isKeyguardSecure()) {
            // 提示用户没有设置指纹或锁屏
            Toast.makeText(this,
                    "Secure lock screen hasn't set up.\n"
                            + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
                    Toast.LENGTH_LONG).show();
            purchaseButton.setEnabled(false);
            purchaseButtonNotInvalidated.setEnabled(false);
            return;
        }

        // noinspection ResourceType
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            purchaseButton.setEnabled(false);
            // This happens when no fingerprints are registered.
            // 提示至少注册一个指纹信息
            Toast.makeText(this,
                    "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
                    Toast.LENGTH_LONG).show();
            return;
        }
        createKey(DEFAULT_KEY_NAME, true);
        createKey(KEY_NAME_NOT_INVALIDATED, false);
        purchaseButton.setEnabled(true);
        purchaseButton.setOnClickListener(
                new PurchaseButtonClickListener(defaultCipher, DEFAULT_KEY_NAME));
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

    public void onPurchased(boolean withFingerprint,
                            @Nullable FingerprintManager.CryptoObject cryptoObject) {
        if (withFingerprint) {
            // If the user has authenticated with fingerprint, verify that using cryptography and
            // then show the confirmation message.
            assert cryptoObject != null;
            tryEncrypt(cryptoObject.getCipher());
        } else {
            // Authentication happened with backup password. Just show the confirmation message.
            showConfirmation(null);
        }
    }

    /**
     * @param encrypted encrypted
     */
    // Show confirmation, if fingerprint was used show crypto information.
    private void showConfirmation(byte[] encrypted) {
        findViewById(R.id.confirmation_message).setVisibility(View.VISIBLE);
        if (encrypted != null) {
            TextView v = (TextView) findViewById(R.id.encrypted_message);
            v.setVisibility(View.VISIBLE);
            v.setText(Base64.encodeToString(encrypted, 0 /* flags */));
        }
    }

    private void tryEncrypt(Cipher cipher) {
        try {
            byte[] encrypted = cipher.doFinal(SECRET_MESSAGE.getBytes());
            showConfirmation(encrypted);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            Toast.makeText(this, "Failed to encrypt the data with the generated key. "
                    + "Retry the purchase", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
        }
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
            findViewById(R.id.confirmation_message).setVisibility(View.GONE);
            findViewById(R.id.encrypted_message).setVisibility(View.GONE);

            // 设置加密对象供后面使用，该对象被授权使用指纹识别
            if (initCipher(mCipher, mKeyName)) {
                //显示指纹识别对话框。用户能够选择使用加密的指纹识别或者使用服务器端的认证密码
                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();
                fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                //获取是否使用指纹识别的参数
                boolean useFingerprintPreference = mSharedPreferences
                        .getBoolean(getString(R.string.use_fingerprint_to_authenticate_key),
                                true);
                if (useFingerprintPreference) {
                    fragment.setStage(
                            FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
                } else {
                    fragment.setStage(
                            FingerprintAuthenticationDialogFragment.Stage.PASSWORD);
                }
                fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            } else {
                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();
                fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                fragment.setStage(
                        FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        }
    }
}

