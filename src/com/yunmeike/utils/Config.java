package com.yunmeike.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.yunmeike.Global;
import com.yunmeike.pinnedheaderlistView.City;


public class Config {
	
	/**
	 * 设置更新城区列表的时间
	 * @param context
	 * @param value
	 */
	public static void setUpdateProvinceTime(Context context, String value){
		setPreferences(context, Global.update_province_time, value);
	}
	
	/**
	 * 获取更新城区列表的时间
	 * @param context
	 * @param value
	 */
	public static String getUpdateProvinceTime(Context context){
		return getStringPreferences(context,Global.update_province_time);
	}
	
	/**
	 * 设置定位城市
	 * @param context
	 * @param value
	 */
	public static void setLocationCity(Context context, String value){
		setPreferences(context, Global.location_city, value);
	}
	/**
	 * 获取定位城市
	 * @param context
	 * @return
	 */
	public static String getLocationCity(Context context){
		return getStringPreferences(context,Global.location_city);
	}
	
	/**
	 * 设置当前城市
	 * setLocationCity() 
	 * @param context
	 * @param value  
	 * @return void
	 * @author liujunbin
	 */
	public static void setCurrCity(Context context, String value){
		setPreferences(context, Global.curr_city, value);
	}
	/**
	 * 获取本地保存当前城市
	 * getLocationCity() 
	 * @param context  
	 * @return void
	 * @author liujunbin
	 */
	public static String getCurrCity(Context context){
		return getStringPreferences(context,Global.curr_city);
	}
	
	/**
	 * 设置是否显示引导页面
	 * setHideGuided() 
	 * @param context
	 * @param value  
	 * @return void
	 * @author liujunbin
	 */
	public static void setHideGuided(Context context, boolean value){
		setPreferences(context, Global.hide_guide, value);
	}
	/**
	 * 是否显示引导页面
	 * isHideGuided() 
	 * @param context  
	 * @return void
	 * @author liujunbin
	 */
	public static boolean isHideGuided(Context context){
		return getBooleanPreferences(context,Global.hide_guide);
	}
	
	
	/************************************************************************************/

	/**
	 * 保存设置（String）
	 * @param context
	 * @param key
	 * @param value
	 */
    public static void setPreferences(Context context, String key, String value){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = sp.edit();
    	editor.putString(key, value);
    	editor.commit();
    }
    
    /**
     * 读取设置（String）
     * @param context
     * @param key
     * @return
     */
    public static String getStringPreferences(Context context, String key){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	return sp.getString(key, "");
    }
    
    /**
	 * 保存设置（Boolean）
	 * @param context
	 * @param key
	 * @param value
	 */
    public static void setPreferences(Context context, String key, boolean value){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = sp.edit();    	
    	editor.putBoolean(key, value);
    	editor.commit();
    }
    
    /**
     * 读取设置（Boolean）
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanPreferences(Context context, String key){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);    	
    	return sp.getBoolean(key, false);
    }
    
    /**
	 * 保存设置（Integer）
	 * @param context
	 * @param key
	 * @param value
	 */
    public static void setPreferences(Context context, String key, int value){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = sp.edit();
    	editor.putInt(key, value);
    	editor.commit();
    }
    
    /**
     * 读取设置（Integer）
     * @param context
     * @param key
     * @return
     */
    public static int getIntPreferences(Context context, String key){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	return sp.getInt(key, -1);
    }
    
    /**
	 * 保存设置（Long）
	 * @param context
	 * @param key
	 * @param value
	 */
    public static void setPreferences(Context context, String key, long value){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = sp.edit();
    	editor.putLong(key, value);
    	editor.commit();
    }
    
    /**
     * 读取设置（Long）
     * @param context
     * @param key
     * @return
     */
    public static long getLongPreferences(Context context, String key){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	return sp.getLong(key, -1);
    }
    
    /**
	 * 保存设置（Float）
	 * @param context
	 * @param key
	 * @param value
	 */
    public static void setPreferences(Context context, String key, float value){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = sp.edit();
    	editor.putFloat(key, value);
    	editor.commit();
    }
    
    /**
     * 读取设置（Float）
     * @param context
     * @param key
     * @return
     */
    public static float getFloatPreferences(Context context, String key){
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    	return sp.getFloat(key, -1);
    }
}
