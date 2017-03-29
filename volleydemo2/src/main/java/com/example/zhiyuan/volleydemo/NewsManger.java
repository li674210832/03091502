package com.example.zhiyuan.volleydemo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.zhiyuan.volleydemo.interfac.RequestCallBack;
import com.example.zhiyuan.volleydemo.utils.CacheUtils;
import com.example.zhiyuan.volleydemo.utils.MD5Encoder;
import com.example.zhiyuan.volleydemo.utils.MD5Utils;
import com.example.zhiyuan.volleydemo.utils.NetUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static android.R.attr.alertDialogIcon;
import static android.R.attr.tag;

/**
 * Created by zhiyuan on 17/3/22.
 */

public class NewsManger {
    private static final String TAG = "NewsManger";

    public static void getNews(final Context context, final String url, HashMap<String, String> map, final RequestCallBack requestCallBack) {

        //做判断--是否有网
        boolean networkConnected = NetUtils.isNetworkConnected(context);
        //没有网络---去缓存中获取信息
        if (!networkConnected) {
            String cache = CacheUtils.getFile(context, url);
            if (!TextUtils.isEmpty(cache)) {
                //有缓存-并且未过期
                requestCallBack.onSuccess(cache);
            } else {
                requestCallBack.onSuccess("没有缓存信息");
            }


        } else {
            //获取缓存的文件信息--
            String cache = CacheUtils.getFile(context, url);
            if (!TextUtils.isEmpty(cache)) {
                //有缓存-并且未过期
                requestCallBack.onSuccess(cache);
            } else {
                //也可以先去获取本地信息，再去请求网路-----
                //去网路获取信息 http://www.baidu.com  MD5--sjsjsdbfakefhawkenfddd
                HttpManger.postRequest(context, url, map, new RequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        requestCallBack.onSuccess(result);
                        //存到缓存中
                        CacheUtils.saveFile(context, url, result);
                    }
                    @Override
                    public void onError(VolleyError error) {
                        requestCallBack.onError(error);
                    }
                });
            }

        }
    }
}
