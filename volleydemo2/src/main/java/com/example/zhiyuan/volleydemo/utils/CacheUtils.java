package com.example.zhiyuan.volleydemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhiyuan on 17/3/23.
 */

public class CacheUtils {
    public static void saveFile(Context context, String url, String result) {
        File cacheDir = context.getCacheDir();
        //拼接成对应的文件名称
        File targetFile = null;
        try {
            targetFile = new File(cacheDir, MD5Encoder.encode(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //往文件中存储信息
        BufferedWriter bufferedWriter = null;
        //存储一个有效时间
        try {

            bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            //写入到期时间
            bufferedWriter.write(System.currentTimeMillis() + 1000 * 60 * 3 + "\r\n");
            bufferedWriter.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getFile(Context context, String url) {
        //缓存怎么处理---
        File cacheDir = context.getCacheDir();
        //拼接成对应的文件名称
        File targetFile = null;
        try {
            targetFile = new File(cacheDir, MD5Encoder.encode(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(targetFile));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            String s = bufferedReader.readLine();

            //不为空--转成数字
            if (!TextUtils.isEmpty(s)) {
                long time = Long.parseLong(s);

                //已经过期了
                if (System.currentTimeMillis() > time) {
                    //告诉用户没有信息
                    return null;
                } else {
                    while ((line = bufferedReader.readLine()) != null) {
                        //读到信息
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
