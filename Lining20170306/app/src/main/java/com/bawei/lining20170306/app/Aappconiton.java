package com.bawei.lining20170306.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;

import org.xutils.x;

/**
 * Created by Lenovo on 2017/3/6.
 * author ：李宁
 * 类注释：初始化
 */

public class Aappconiton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        ImageLoaderConfiguration aDefault = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(aDefault);

        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
