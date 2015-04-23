package com.yunmeike.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.yunmeike.BaseActivity;
import com.yunmeike.Global;
import com.yunmeike.MApplication;
import com.njk.R;
import com.yunmeike.adapter.MyClientGuestbookAdapter;
import com.yunmeike.adapter.MyClientListAdapter;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class MyClientDetailsActivity extends BaseActivity implements OnClickListener{
	
	private ListView listView;
	private LinkedList<String> mListItems;
	private PtrClassicFrameLayout mPtrFrame;
	private MyClientGuestbookAdapter mAdapter;
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(this).inflate(R.layout.my_client_details_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "我的美客", TOP_BTN_MODE.SHOWBACK,"","");
		findViewById(R.id.back_btn).setOnClickListener(this);
		

		listView = (ListView) rootView.findViewById(R.id.list_layout);
        listView.setOnItemClickListener(null);
        
        mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(Global.mStrings));
		mAdapter = new MyClientGuestbookAdapter(this, mListItems);
		
		listView.setAdapter(mAdapter);
	        
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;

		default:
			break;
		}
		
	}
	
}
