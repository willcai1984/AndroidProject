package com.qjdchina.pocketsale.commons;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件操作工具类
 * Created by user2 on 2015/11/3.
 */
public class PropertiesUtil {

    private static final String TAG = "PropertiesUtil";

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = ContextUtil.getInstance().getAssets().open("appConfig.properties", AssetManager.ACCESS_BUFFER);
            properties.load(inputStream);
            Log.v(TAG, properties.getProperty("cif_fnt_server_base_url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据配置项获取应用中的配置
     *
     * @param name 配置项
     * @return
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }


}
