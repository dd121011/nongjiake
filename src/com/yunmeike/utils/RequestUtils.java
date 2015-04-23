package com.yunmeike.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.StringRequest;

public class RequestUtils {
	private static String TAG="RequestUtils";
	
	
	public static void startStringRequest(int method, RequestQueue mQueue, String url, final ResponseHandlerInterface responseHandlerInterface,final Map<String, String> params) {
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
		
		mQueue.add(stringRequest);
		
	}
	

	
	protected static Map<String, String> initParams(Map<String, String> params) {
		Map<String, String> map = new HashMap<String, String>();  
		//设置基础参数
		map.putAll(params);
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
