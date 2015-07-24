package com.yunmeike.net.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.StringRequest;
import com.yunmeike.Global;
import com.yunmeike.utils.Logger;

public class RequestUtils {
	private static String TAG="RequestUtils";
	private static String base = Global.base_url;
	
	
	public static void startStringRequest(int method, RequestQueue mQueue, RequestCommandEnum urlEnum, final ResponseHandlerInterface responseHandlerInterface,final Map<String, String> params) {
		String url = getUrl(method, urlEnum, params);
		
		StringRequest stringRequest = new StringRequest(method,url,  
                new Response.Listener<String>() {  
                    @Override  
                    public void onResponse(String response) {  
                        Log.d(TAG, response);  
                        responseHandlerInterface.handlerSuccess(response);
                    }  
                }, new Response.ErrorListener() {  
                    @Override  
                    public void onErrorResponse(VolleyError error) {  
                        Log.e(TAG, error.getMessage(), error);  
                        responseHandlerInterface.handlerError("error");
                    }  
                }){

					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						if(params!=null){
							
							return initParams(params);
						}else{
							return super.getParams();
						}
					}
			
		};
		stringRequest.setShouldCache(true);
		mQueue.add(stringRequest);
		Logger.d(TAG, "url = "+stringRequest.getUrl() +"KEY = "+stringRequest.getCacheEntry());
	}
	

	protected static String getUrl(int method,RequestCommandEnum urlEnum,Map<String, String> params){
		StringBuffer url = new StringBuffer(base + urlEnum.command);
		url.append("?");
		switch (method) {
		case Method.GET:
			Map<String, String> map = initParams(params);
			Set<Entry<String, String>> set = map.entrySet();
			for(Entry<String, String> entry : set){
				url.append(entry.getKey()+"="+entry.getValue()+"&");			
			}
			char c = url.charAt(url.length()-1);
			if(c == '&'){
				url.deleteCharAt(url.length()-1);
			}

			break;
		case Method.POST:
			break;
		default:
			break;
		}
		return url.toString();
	}
	
	protected static Map<String, String> initParams(Map<String, String> params) {
		//设置基础参数
		Map<String, String> map = new HashMap<String, String>();  
		map.put("source", "2");
		if(params!=null){
			map.putAll(params);
		}
		return map;
	}


	public static void clearCatchData(RequestQueue mQueue,Context context,final ResponseHandlerInterface response){
		File cacheFile = new File(context.getCacheDir(), "volley");
		new DiskBasedCache(cacheFile);
		
		ClearCacheRequest clearCacheRequest = new ClearCacheRequest(new DiskBasedCache(cacheFile), new Runnable() {			
			@Override
			public void run() {			
				response.handlerSuccess("1");
			}
		});
		mQueue.add(clearCacheRequest);
		
	}

	public interface ResponseHandlerInterface{
		public void handlerSuccess(String response);
		public void handlerError(String error);
	}
}
