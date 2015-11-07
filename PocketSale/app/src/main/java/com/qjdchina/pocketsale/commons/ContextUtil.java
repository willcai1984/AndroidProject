package com.qjdchina.pocketsale.commons;

import android.app.Application;
import android.content.Context;

/**
 * 对整个app应用中Context访问提供统一接口具类
 * Created by user2 on 2015/11/3.
 */
public class ContextUtil extends Application {

    private static Context instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}
