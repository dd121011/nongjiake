package com.yunmeike.fragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yunmeike.Global;
import com.njk.R;
import com.yunmeike.activity.ShopDetailsActivity;
import com.yunmeike.adapter.EncircleListAdapter;
import com.yunmeike.net.utils.RequestCommandEnum;
import com.yunmeike.net.utils.RequestUtils;
import com.yunmeike.net.utils.RequestUtils.ResponseHandlerInterface;

public class EncircleHotFragmentPage extends Fragment {

	private static String TAG = "EncircleHotFragmentPage";

	private View rootView;
	private GridView listView;
	private LinkedList<String> mListItems;
	private PtrClassicFrameLayout mPtrFrame;
	private EncircleListAdapter mAdapter;

	private Activity activity;
	private RequestQueue mQueue;

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
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.encircle_list_fragment_layout,
					container, false);

			listView = (GridView) rootView
					.findViewById(R.id.rotate_header_grid_view);
			listView.setOnItemClickListener(new NearListOnItemClickListener());

			mListItems = new LinkedList<String>();
			mListItems.addAll(Arrays.asList(Global.mStrings));
			mAdapter = new EncircleListAdapter(getActivity(), mListItems);

			listView.setAdapter(mAdapter);

			mPtrFrame = (PtrClassicFrameLayout) rootView
					.findViewById(R.id.rotate_header_grid_view_frame);
			mPtrFrame.setLastUpdateTimeRelateObject(this);
			mPtrFrame.setPtrHandler(new PtrHandler() {
				@Override
				public void onRefreshBegin(PtrFrameLayout frame) {
					new GetDataTask().execute();
				}

				@Override
				public boolean checkCanDoRefresh(PtrFrameLayout frame,
						View content, View header) {
					return PtrDefaultHandler.checkContentCanBePulledDown(frame,
							content, header);
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
					// mPtrFrame.autoRefresh();
				}
			}, 100);
//			Toast.makeText(activity, TAG, 1000).show();
			startGetData();
		}

		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void startGetData() {
		Map<String, String> params = new HashMap<String, String>(); 
		params.put("offset", 1+"");
		params.put("per_page", 1+"");
		
		RequestUtils.startStringRequest(Method.GET, mQueue,
				RequestCommandEnum.FAMILY_LIST, new ResponseHandlerInterface() {

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

				}, params);

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
			mListItems.addFirst("Added after refresh...");
			mPtrFrame.refreshComplete();
			mAdapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}
	}

	class NearListOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(activity, ShopDetailsActivity.class);
			activity.startActivity(intent);
		}

	}

}
