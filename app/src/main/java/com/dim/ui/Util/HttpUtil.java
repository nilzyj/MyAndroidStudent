package com.dim.ui.util;

import android.util.Log;

import com.dim.ui.model.HttpURL;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dim on 2017/3/23.
 * @author dim
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    /**
     * 用于post方式的http请求
     * @param url_para url
     * @param data 上传的数据
     * @return 服务器返回数据
     */
    public static String httpPost(String url_para, String data) {
        HttpURLConnection conn = null;
        try {
            //请求的地址，并创建URL对象
            URL url = new URL(url_para);
            //打开连接
            conn = (HttpURLConnection) url.openConnection();
            //设置请求参数（请求方式，请求超时时间）
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            //传递的数据
//            String body = "username=" + URLEncoder.encode("zyj", "UTF-8") + "&pwd=" +
//                    URLEncoder.encode("1", "UTF-8");

            //请求头的信息
            conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setRequestProperty("Origin", "http://192.168.191.1:8080");
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            //设置conn可以写请求的内容
            // 发送POST请求必须设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true); // 发送POST请求必须设置允许输入,setDoInput的默认值就是true

            //获取输出流
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            int code = conn.getResponseCode();
            if (code == 200) {
                //获取相应的输入流对象
                String state = "";
                InputStream is = conn.getInputStream();
                state = getStreamFromInputstream(is);
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(conn.getInputStream()));

//                while ((str = reader.readLine()) != null) {
//                    state += str;
//                }
//                String state = getStreamFromInputstream(reader);
//                reader.close();
//                state = new String(state.getBytes("ISO8859-1"), "UTF-8");
                state = URLDecoder.decode(state, "UTF-8");
//                ParseEncoding parseEncoding = new ParseEncoding();
//                state = parseEncoding.getEncoding(state);

//                state = getEncodingNew(state);
//                state = getEncoding(state);
//                state = new String(state.getBytes("GB2312"), "UTF-8");
//                state = new String(state.getBytes("GBK"), "UTF-8");
//                state = new String(state.getBytes("UTF-8"), "ISO8859-1");
//                        state = getEncoding(state);
                return state;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * @param is 输入流，服务器返回
     * @return 服务器的输入流转为的String数据
     * @throws Exception
     */
    //根据输入流返回字符串
    private static String getStreamFromInputstream(InputStream is) throws Exception {
        //创建字节输出流对象
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //定义缓冲区
        byte[] buffer = new byte[1024];
        //定义读取的长度
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
//        String result = new String(baos.toString().getBytes("ISO8859-1"), "UTF-8");
        baos.close();
//        String str = null;
//        while ((str = is.readLine()) != null) {
//            str = is.readLine();
//        }
//        return str;
        String result = baos.toString();
//        result = new String(result.getBytes("GBK"), "UTF-8");
        return result;
    }

    /**
     * 上传个人照片
     *
     * @param path 上传文件路径
     */
    public static void okHttpUploadFile(String path, String name) {
        final String url = HttpURL.url + "PhotoServlet";
        File file = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("stu", name,
                        RequestBody.create(MediaType.parse("image/jpg"), file))//添加上传文件
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "uploadMultiFile() e=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "uploadMultiFile() response=" + response.body().string());
            }
        });
    }


    /**
     * @param str str
     * @return encode
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * @param str str
     * @return encode
     */
    public static String getEncodingNew(String str) {
        String[] encode = new String[]{
                "UTF-8",
                "ISO-8859-1",
                "GB2312",
                "GBK",
                "GB18030",
                "Big5",
                "Unicode",
                "ASCII"
        };
        for (int i = 0; i < encode.length; i++){
            try {
                if (str.equals(new String(str.getBytes(encode[i]), encode[i]))) {
                    return encode[i];
                }
            } catch (Exception ex) {
            }
        }

        return "";
    }
}

