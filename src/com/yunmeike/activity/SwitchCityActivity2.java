package com.yunmeike.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yunmeike.BaseActivity;
import com.yunmeike.Global;
import com.yunmeike.MApplication;
import com.njk.R;
import com.yunmeike.adapter.CityListAdapter;
import com.yunmeike.db.CityModel;
import com.yunmeike.db.DBManager;
import com.yunmeike.manager.CurrCityManager;
import com.yunmeike.manager.CurrCityManager.OnChangerCurrCityListener;
import com.yunmeike.utils.Config;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;
import com.yunmeike.view.MyLetterListView;
import com.yunmeike.view.MyLetterListView.OnTouchingLetterChangedListener;

public class SwitchCityActivity2 extends BaseActivity implements
		OnClickListener {

	private ListView listView;
	private View hint_city_layout;
	private TextView overlay,alpha,name;
	private MyLetterListView letterListView;
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread;

	private Activity context;
	private Handler mHandler = new Handler();
	private CityListAdapter adapter;
	private ArrayList<CityModel> mCityNames;
	
	private SQLiteDatabase database;
	
	private CurrCityManager cityManger;
	
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(context).inflate(
				R.layout.switch_city_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "城市切换", TOP_BTN_MODE.SHOWBACK,"","");
		rootView.findViewById(R.id.back_btn).setOnClickListener(this);
		
		listView = (ListView) rootView.findViewById(R.id.city_list);
		listView.setOnScrollListener(scrollListener);
		
		//列表头部提示索引
		hint_city_layout = rootView.findViewById(R.id.hint_city_layout);
		alpha = (TextView) hint_city_layout.findViewById(R.id.alpha);
		name = (TextView) hint_city_layout.findViewById(R.id.name);
		
		overlay = (TextView) rootView.findViewById(R.id.overlay);
		letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		
		DBManager dbManager = new DBManager(this);
		dbManager.openDateBase();
		dbManager.closeDatabase();
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		
		mCityNames = getCityNames();
		
		// database.close();
		
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
//		initOverlay();
		setAdapter(mCityNames);
		listView.setOnItemClickListener(new CityListOnItemClick());				
		
		cityManger = CurrCityManager.getInstance();
		cityManger.registerChangerCurrCityListener(currCityListener);
	}

	private void initLocationCity() {	
        String city = Config.getLocationCity(context);
        if(TextUtils.isEmpty(city)){
    		mLocationClient = ((MApplication)getApplication()).mLocationClient;
    		InitLocation();
    		mLocationClient.registerLocationListener(bdLocationListener);
    		mLocationClient.start();
        };

	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span=1000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 为ListView设置适配器
	 * 
	 * @param list
	 */
	private void setAdapter(List<CityModel> list) {
		if (list != null) {
			initAlphoIndex(list);
			adapter = new CityListAdapter(this, list);
			listView.setAdapter(adapter);
		}

	}

	private void initAlphoIndex(List<CityModel> list) {
		alphaIndexer = new HashMap<String, Integer>();
		sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			// 当前汉语拼音首字母
			// getAlpha(list.get(i));
			String currentStr = list.get(i).getNameSort();
			// 上一个汉语拼音首字母，如果不存在为“ ”
			String previewStr = (i - 1) >= 0 ? list.get(i - 1)
					.getNameSort() : " ";
			if (!previewStr.equals(currentStr)) {
				String name = list.get(i).getNameSort();
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
		}
	}

	private ArrayList<CityModel> getCityNames() {
		ArrayList<CityModel> names = new ArrayList<CityModel>();
		
		CityModel locCity = new CityModel();
		locCity.setNameSort("定位城市");
		String city = Config.getLocationCity(context);
        if(TextUtils.isEmpty(city)){
        	locCity.setCityName("点击开始定位当前位置");
        	initLocationCity();
        }else{
        	locCity.setCityName(city);
        };
		
		names.add(locCity);
		names.addAll(Config.getHotCityList());
		
		Cursor cursor = database.rawQuery(
				"SELECT * FROM T_city ORDER BY CityName", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityModel cityModel = new CityModel();
			cityModel.setCityName(cursor.getString(cursor
					.getColumnIndex("AllNameSort")));
			cityModel.setNameSort(cursor.getString(cursor
					.getColumnIndex("CityName")));
			names.add(cityModel);
		}
		cursor.close();
		return names;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if(mLocationClient!=null){
			mLocationClient.stop();
		}
		super.onStop();
	}

	/**
	 * 城市列表点击事件
	 * 
	 */
	class CityListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			CityModel cityModel = (CityModel) listView.getAdapter()
					.getItem(pos);
			cityManger.setCurrCity(context, cityModel.getCityName());
			context.finish();
		}

	}

	private OnScrollListener scrollListener = new OnScrollListener(){

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {


//			if((firstVisibleItem - 1) >= 0){
//				CityModel item = mCityNames.get(firstVisibleItem);
//				CityModel preItem = mCityNames.get(firstVisibleItem-1);
//				String currentStr = item.getNameSort();
//				String previewStr = (firstVisibleItem - 1) >= 0 ? mCityNames.get(firstVisibleItem - 1)
//						.getNameSort() : " ";
//				if (!previewStr.equals(currentStr)) {
//					hint_city_layout.setVisibility(View.VISIBLE);
//					alpha.setVisibility(View.VISIBLE);
//					name.setVisibility(View.GONE);
//					alpha.setText(item.getNameSort());
//				} else {
//					hint_city_layout.setVisibility(View.GONE);
//				}
//			}

	
		}
		
	};
	
	private class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				listView.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}
	
	private BDLocationListener bdLocationListener = new BDLocationListener(){

		@Override
		public void onReceiveLocation(final BDLocation arg0) {
			runOnUiThread(new Runnable() {				
				@Override
				public void run() {
					if(arg0!=null){
						cityManger.setCurrCity(context, arg0.getCity());
					}
				}
			});			
		}		
	};
	
	private OnChangerCurrCityListener currCityListener = new OnChangerCurrCityListener(){
		@Override
		public void onChangeCurrCity(final String currCity) {	
			runOnUiThread(new Runnable() {
				public void run() {
					if(mCityNames!=null && mCityNames.size()>0){
						mCityNames.get(0).setCityName(currCity);
						adapter.notifyDataSetChanged();
					}
				}
			});			
		}	
	};

	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.share_btn:

			break;
		default:
			break;
		}

	}

	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return Global.mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {


			super.onPostExecute(result);
		}
	}
}
