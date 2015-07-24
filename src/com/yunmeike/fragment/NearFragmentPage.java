package com.yunmeike.fragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njk.R;
import com.yunmeike.MApplication;
import com.yunmeike.activity.ShopDetailsActivity;
import com.yunmeike.activity.ShopMapListActivity;
import com.yunmeike.activity.SwitchCityActivity;
import com.yunmeike.adapter.NearListAdapter;
import com.yunmeike.bean.NearBean;
import com.yunmeike.manager.CurrCityManager;
import com.yunmeike.manager.CurrCityManager.OnChangerCurrCityListener;
import com.yunmeike.net.utils.RequestCommandEnum;
import com.yunmeike.net.utils.RequestUtils;
import com.yunmeike.net.utils.RequestUtils.ResponseHandlerInterface;
import com.yunmeike.utils.Config;

public class NearFragmentPage extends Fragment implements OnClickListener{
	private static String TAG="NearFragmentPage";
	public final static int UPDATE_DATA_LIST = 1;
	public final static int MORE_DATE_LIST = 2;
	public final static int UPATE_LIST_LAYOUT = 3;
	public final static int GET_DATE_FAIL = 100;
	
	private View rootView;
	private ListView listView ;
	private TextView currCity;
	private List<NearBean> nearBeanList;
	private PtrClassicFrameLayout mPtrFrame;
	private NearListAdapter mAdapter;
	
	private ViewGroup switch_near_btn,switch_hot_btn,switch_sort_btn;

	private Activity activity;
	private RequestQueue mQueue;
	private LocationClient mLocationClient;
	
	private CurrCityManager cityManger;
	
	private int offset = 0;
	private int per_page = 5;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_DATA_LIST:
				List<NearBean> dataList = (List<NearBean>) msg.getData().getSerializable("data");
				if(offset == 0){
					nearBeanList.clear();
				}
				offset = per_page;
				nearBeanList.addAll(dataList);
				handler.sendEmptyMessage(UPATE_LIST_LAYOUT);
				break;
			case MORE_DATE_LIST:
				offset += per_page;
				List<NearBean> moreList = (List<NearBean>) msg.getData().getSerializable("data");
				nearBeanList.addAll(moreList);
				handler.sendEmptyMessage(UPATE_LIST_LAYOUT);
				break;
			case UPATE_LIST_LAYOUT:				 
				mPtrFrame.refreshComplete();
				mAdapter.notifyDataSetChanged();
				break;
			case GET_DATE_FAIL:
				mPtrFrame.refreshComplete();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = getActivity();
		mQueue = Volley.newRequestQueue(activity);  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootView == null){
			rootView = inflater.inflate(R.layout.near_fragment_layout, container, false);
			
	        listView = (ListView) rootView.findViewById(R.id.rotate_header_list_view);
	        listView.setOnItemClickListener(new NearListOnItemClickListener());
	        
	        mLocationClient = ((MApplication)activity.getApplication()).mLocationClient;
			mLocationClient.registerLocationListener(new BDLocationListener() {				
				@Override
				public void onReceiveLocation(final BDLocation arg0) {
					// TODO Auto-generated method stub
					activity.runOnUiThread(new Runnable() {						
						@Override
						public void run() {
							if(arg0!=null){
								currCity.setText(arg0.getCity());
							}
						}
					});
				}
			});
	        
	        rootView.findViewById(R.id.switch_adress_btn).setOnClickListener(this);
	        rootView.findViewById(R.id.map_btn).setOnClickListener(this);
	        
	        currCity = (TextView) rootView.findViewById(R.id.curr_city_text);
	        String city = Config.getLocationCity(activity);
	        if(!TextUtils.isEmpty(city)){
	        	currCity.setText(city);
	        };
	        
			switch_near_btn = (ViewGroup) rootView.findViewById(R.id.switch_near_btn);
			switch_hot_btn = (ViewGroup) rootView.findViewById(R.id.switch_hot_btn);
			switch_sort_btn = (ViewGroup)rootView.findViewById(R.id.switch_sort_btn);
			switch_near_btn.setOnClickListener(new SwitchOnClickListener());
			switch_hot_btn.setOnClickListener(new SwitchOnClickListener());
			switch_sort_btn.setOnClickListener(new SwitchOnClickListener());
			
			cityManger = CurrCityManager.getInstance();
			cityManger.registerChangerCurrCityListener(currCityListener);
			
			nearBeanList = new ArrayList<NearBean>();
			mAdapter = new NearListAdapter(getActivity(), nearBeanList);
			
			listView.setAdapter(mAdapter);
			
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					if(firstVisibleItem == (totalItemCount - 2)){
						startGetData();
					}
					
				}
			});
			
			mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.rotate_header_list_view_frame);
			mPtrFrame.setLastUpdateTimeRelateObject(this);
			mPtrFrame.setPtrHandler(new PtrHandler() {
			   @Override
			   public void onRefreshBegin(PtrFrameLayout frame) {
				   offset = 0;
				   startGetData();
			   }
			
			   @Override
			   public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
			         return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			   }
			});
			    // the following are default settings
			mPtrFrame.setResistance(1.7f);
			mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
			mPtrFrame.setDurationToClose(200);
			mPtrFrame.setDurationToCloseHeader(1000);
			// default is false
			mPtrFrame.setPullToRefresh(false);
			// default is true
			mPtrFrame.setKeepHeaderWhenRefresh(true);
			mPtrFrame.postDelayed(new Runnable() {
			    @Override
			    public void run() {
//			        mPtrFrame.autoRefresh();
			    }
			}, 100);
			
			
			startGetData();
		}
		
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
	    ViewGroup parent = (ViewGroup) rootView.getParent();
	    if (parent != null)
	    {
	      parent.removeView(rootView);
	    }
		
		return rootView;		
	}	
	
	
	private void handleSwitch(int id) {
		final RadioButton nearRadio =(RadioButton) switch_near_btn.getChildAt(0);
		final RadioButton hotRadio =(RadioButton) switch_hot_btn.getChildAt(0);
		final RadioButton sortRadio =(RadioButton) switch_sort_btn.getChildAt(0);

		
		switch (id) {
		case R.id.switch_near_btn:
			nearRadio.setChecked(true);
			hotRadio.setChecked(false);
			sortRadio.setChecked(false);
			break;
		case R.id.switch_hot_btn:
			nearRadio.setChecked(false);
			hotRadio.setChecked(true);
			sortRadio.setChecked(false);
			break;
		case R.id.switch_sort_btn:
			nearRadio.setChecked(false);
			hotRadio.setChecked(false);
			sortRadio.setChecked(true);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onStop() {
		mLocationClient.stop();
		super.onStop();
	}

	private boolean isStart = false;
	public void startGetData(){
		if(isStart){
			return;
		}
		isStart = true;
		Map<String, String> params = new HashMap<String, String>(); 
		params.put("offset", offset+"");
		params.put("per_page", per_page+"");
		
		RequestUtils.startStringRequest(Method.GET,mQueue, RequestCommandEnum.FAMILY_LIST,new ResponseHandlerInterface(){

			@Override
			public void handlerSuccess(String response) {
				// TODO Auto-generated method stub
				 Log.d(TAG, response); 
				 isStart = false;
				 try {
					if(!TextUtils.isEmpty(response)){
						 JSONObject obj = new JSONObject(response);
						 if(obj.has("code") && obj.getString("code").equals("0")){
							 String jsonArray = obj.getString("data");
							 Gson gson = new Gson();
							 ArrayList<NearBean> dataList = gson.fromJson(jsonArray, new TypeToken<List<NearBean>>(){}.getType());
							 Bundle data = new Bundle();
							 data.putSerializable("data", dataList);
							 Message msg = null;
							 if(offset == 0){
								 msg = handler.obtainMessage(UPDATE_DATA_LIST);
							 }else{
								 msg = handler.obtainMessage(MORE_DATE_LIST);
							 }
							 msg.setData(data);
							 msg.sendToTarget();
						 }
					 }
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void handlerError(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, error);  
				isStart = false;
				handler.sendEmptyMessage(GET_DATE_FAIL);
			}
			
		},params);

	}
	
	
	class SwitchOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.switch_near_btn:
				handleSwitch(R.id.switch_near_btn);
				break;
			case R.id.switch_hot_btn:
				handleSwitch(R.id.switch_hot_btn);
				break;
			case R.id.switch_sort_btn:
				handleSwitch(R.id.switch_sort_btn);
				break;
			default:
				break;
			}
			
		}
		
	}
	
	class NearListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(activity,ShopDetailsActivity.class);
			activity.startActivity(intent);
		}
		
	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.switch_adress_btn:
			intent = new Intent(activity,SwitchCityActivity.class);
			activity.startActivity(intent);
			break;
		case R.id.map_btn:
			intent = new Intent(activity,ShopMapListActivity.class);
			activity.startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	private OnChangerCurrCityListener currCityListener = new OnChangerCurrCityListener(){
		@Override
		public void onChangeCurrCity(final String city) {	
			activity.runOnUiThread(new Runnable() {
				public void run() {
					if(!TextUtils.isEmpty(city)){
			        	currCity.setText(city);
			        };
				}
			});			
		}	
	};
}
