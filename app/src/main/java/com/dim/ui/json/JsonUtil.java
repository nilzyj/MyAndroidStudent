package com.dim.ui.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dim on 2017/3/28.
 */

public class JsonUtil {

    /*
    * 将JSON字符串转换成JSONObject
    * */
    public JSONObject ToJSONObeject(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * 将JSON字符串转换成JSONArray
    * */
    public JSONArray ToJSONArray(String jsonString) {
        try {
            return new JSONArray(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 将ArrayList数组转换成JSON字符串
    * */
    public String ToJSONString(ArrayList arrayList) {

        JSONObject jsonObject = new JSONObject();
        // 遍历ArrayList数组，并将数组的值转换成jsonObject
        for(int i = 0; i < arrayList.size(); i++) {
            try {
                jsonObject.put(i + "", arrayList.get(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject.toString();
    }
}
