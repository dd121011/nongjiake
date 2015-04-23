package com.yunmeike.fragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.yunmeike.MApplication;
import com.njk.R;
import com.yunmeike.activity.ShopMapListActivity;
import com.yunmeike.activity.ShopDetailsActivity;
import com.yunmeike.activity.SwitchCityActivity;
import com.yunmeike.adapter.NearListAdapter;
import com.yunmeike.manager.CurrCityManager;
import com.yunmeike.manager.CurrCityManager.OnChangerCurrCityListener;
import com.yunmeike.utils.Config;
import com.yunmeike.utils.RequestUtils;
import com.yunmeike.utils.RequestUtils.ResponseHandlerInterface;

public class NearFragmentPage extends Fragment implements OnClickListener{
	private static String TAG="NearFragmentPage";
	
	private View rootView;
	private ListView listView ;
	private TextView currCity;
	private LinkedList<String> mListItems;
	private PtrClassicFrameLayout mPtrFrame;
	private NearListAdapter mAdapter;
	
	private ViewGroup switch_near_btn,switch_hot_btn;

	private Activity activity;
	private RequestQueue mQueue;
	private LocationClient mLocationClient;
	
	private CurrCityManager cityManger;
	
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
			switch_near_btn.setOnClickListener(new SwitchOnClickListener());
			switch_hot_btn.setOnClickListener(new SwitchOnClickListener());
			
			cityManger = CurrCityManager.getInstance();
			cityManger.registerChangerCurrCityListener(currCityListener);
			
			mListItems = new LinkedList<String>();
			mListItems.addAll(Arrays.asList(mStrings));
			mAdapter = new NearListAdapter(getActivity(), mListItems);
			
			listView.setAdapter(mAdapter);
			
			
			mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.rotate_header_list_view_frame);
			mPtrFrame.setLastUpdateTimeRelateObject(this);
			mPtrFrame.setPtrHandler(new PtrHandler() {
			   @Override
			   public void onRefreshBegin(PtrFrameLayout frame) {
			        new GetDataTask().execute();
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
	
	
	private void handleSwitch() {
		final RadioButton nearRadio =(RadioButton) switch_near_btn.getChildAt(0);
		final RadioButton hotRadio =(RadioButton) switch_hot_btn.getChildAt(0);
		if(nearRadio.isChecked()){
			nearRadio.setChecked(false);
			hotRadio.setChecked(true);
		}else{
			nearRadio.setChecked(true);
			hotRadio.setChecked(false);
		}
	}
	
	@Override
	public void onStop() {
		mLocationClient.stop();
		super.onStop();
	}

	public void startGetData(){
		
		
		
		RequestUtils.startStringRequest(Method.GET,mQueue, "http://www.baidu.com",new ResponseHandlerInterface(){

			@Override
			public void handlerSuccess(String response) {
				// TODO Auto-generated method stub
				 Log.d(TAG, response); 
				 mPtrFrame.refreshComplete();
                 mAdapter.notifyDataSetChanged();
			}

			@Override
			public void handlerError(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, error);  
			}
			
		},null);

	}
	
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mPtrFrame.refreshComplete();
            mAdapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}
	}
	
	class SwitchOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.switch_near_btn:
				handleSwitch();
				break;
			case R.id.switch_hot_btn:
				handleSwitch();
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
	
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };


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
