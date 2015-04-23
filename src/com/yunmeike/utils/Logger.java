package com.yunmeike.utils;

import android.text.TextUtils;
import android.util.Log;
/**
 * 日志打印工具类，封装到一起，是为了调用时方便
 * @author Administrator
 *
 */
public class Logger {
    private static final String TAG = "yunmeike";
    private static final boolean DEBUG = true;

    public static void v(String message) {
    	if(DEBUG)
        Log.v(TAG, message);
    }

    public static void d(String message) {
    	if(DEBUG)
        Log.d(TAG, message);
    }

    public static void i(String message) {
    	if(DEBUG)
        Log.i(TAG, message);
    }

    public static void w(String message) {
    	if(DEBUG)
        Log.w(TAG, message);
    }

    public static void e(String message) {
    	if(DEBUG)
        Log.e(TAG, message);
    }
    
    public static void d(String tag, String message) {
    		if (!TextUtils.isEmpty(message)) {
    			if(DEBUG)
    		        Log.d(TextUtils.isEmpty(tag) ? TAG : tag, message);
		}
    }
}
