package com.yunmeike.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationClientUtils {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;

	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";
	
	private List<LocatonListener> locatonListeners = new ArrayList<LocatonListener>();	
	
	private static LocationClientUtils instance;
	private LocationClientUtils() {}
	public static LocationClientUtils getInstance(){
		if(instance == null){
			instance =new LocationClientUtils();
		}
		return instance;
	} 

	public void init(Context context){
		mLocationClient = new LocationClient(context);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(context);
		
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 100;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	public void start(){
		if(mLocationClient.isStarted()){
			mLocationClient.stop();
		}
		mLocationClient.start();
	}
	
	public void stop(){
		mLocationClient.stop();
	}
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			for(LocatonListener listener : locatonListeners){
				listener.onReceiveLocation(location);
			}
			
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}

			Log.i("MApplication", sb.toString());
			stop();
		}

	}
	
	public void addListenter(LocatonListener listener){
		if(listener!=null){
			locatonListeners.add(listener);
		}
	}
	
	public void removeListener(LocatonListener listener){
		if(listener!=null && locatonListeners.contains(listener)){
			locatonListeners.remove(listener);
		}
	}
	
	public interface LocatonListener{
		void onReceiveLocation(BDLocation location);
	}
}
