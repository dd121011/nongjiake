package com.yunmeike.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yunmeike.utils.Config;

public class CurrCityManager {
	// Private constructor prevents instantiation from other classes
    private CurrCityManager() { 
    	changeCurrCityListeners = new ArrayList<OnChangerCurrCityListener>();
    }

    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class CurrCityHolder { 
            public static final CurrCityManager INSTANCE = new CurrCityManager();
    }

    public static CurrCityManager getInstance() {
            return CurrCityHolder.INSTANCE;
    }
    
    
    private static List<OnChangerCurrCityListener> changeCurrCityListeners = null;
    
    public static void setCurrCity(Context context, String currCity){
    	Config.setLocationCity(context,currCity);
    	for(OnChangerCurrCityListener listenre : changeCurrCityListeners){
    		listenre.onChangeCurrCity(currCity);
    	}
    }
    
    public static void registerChangerCurrCityListener(OnChangerCurrCityListener listener){
    	changeCurrCityListeners.add(listener);
    }
    
    public interface OnChangerCurrCityListener{
    	void onChangeCurrCity(String currCity);
    }
}
