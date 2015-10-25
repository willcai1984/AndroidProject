package com.qjdchina.qjdsale;

import android.os.Environment;
import android.util.Log;

/**
 * Created by Administrator on 2015/10/24.
 */
public class Utils {
    private static String  TAG = "Utils";
    /**
     * 检查存储卡是否插入
     * @return
     */
    public static boolean isHasSdcard()
    {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)){
            Log.d(TAG, "SD is ready");
            return true;
        } else {
            Log.d(TAG, "SD is not ready");
            return false;
        }
    }
}
