package com.dim.ui.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liji.takephoto.TakePhoto;
import com.lljjcoder.citypickerview.widget.CityPicker;

/**
 * Created by dim on 2017/5/12.
 */

public class PickerUtil {
    /**
     * 选择地址的Texview的点击事件
     *
     * @param view     the view
     * @param textView 点击的
     * @param flag     true：两级联动，false：三级联动
     */
    public static void chooseArea(Context context, View view, TextView textView, boolean flag) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress(context, textView, flag);//调用CityPicker选取区域
        }
    }

    /**
     * 选择日期的TextView的点击事件
     *
     * @param view     view
     * @param textView 点击的TextView
     */
    public static void chooseDate(Context context, View view, TextView textView) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectDate(context, textView);//调用CityPicker选取区域
        }
    }

    /**
     * 弹出地址选择器，并获取选择结果
     *
     * @param textViewAddress 点击的TextView
     * @param flag            三级或二级联动标志变量
     */
    public static void selectAddress(Context context, final TextView textViewAddress, final boolean flag) {
        CityPicker cityPicker = new CityPicker.Builder(context)
                .textSize(16)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("新疆维吾尔自治区")
                .city("昌吉回族自治州")
                .district("昌吉市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(flag)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
//                String code = citySelected[3];
                //为TextView赋值
                if (flag) {
                    textViewAddress.setText(province.trim() + city.trim());
                } else {
                    textViewAddress.setText(province.trim() + city.trim() + district.trim());
                }

            }
        });
    }

    /**
     * 弹出日期选择器，并获取选择结果
     *
     * @param textView 点击的TextView
     */
    public static void selectDate(Context context, final TextView textView) {

        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(year + "." + monthOfYear + "." + dayOfMonth);
            }
        }, 2017, 7, 1);
        datePicker.show();
    }

    /**
     * 获取个人照片：相册或拍照
     */
    public static void takePhoto(final Context context, final ImageView imageView) {
        TakePhoto takePhoto = new TakePhoto(context);
        takePhoto.setOnPictureSelected(new TakePhoto.onPictureSelected() {
            @Override
            public void select(String path) {
//                mTvPhoto.setText("选择的图片地址：" + path);
                Glide.with(context).load("file://" + path).into(imageView);
            }
        });
        takePhoto.show();
    }
}
