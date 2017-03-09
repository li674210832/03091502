package com.example.lenovo.test2.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Lenovo on 2017/3/5.
 * author ：李宁
 * 类注释：
 */

public class Aappp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
    }
}
