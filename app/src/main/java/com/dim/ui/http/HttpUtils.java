package com.dim.ui.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dim on 2017/3/23.
 */

public class HttpUtils {
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
                InputStream is = conn.getInputStream();
                String state = getStreamFromInputstream(is);
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
        String result = baos.toString();
        baos.close();
        return result;
    }

//    public static String okHttpPost(String url, String json) throws IOException {
//        final MediaType JSON
//                = MediaType.parse("application/json; charset=utf-8");
//
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }
}

