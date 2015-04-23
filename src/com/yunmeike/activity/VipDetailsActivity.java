package com.yunmeike.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yunmeike.BaseActivity;
import com.yunmeike.Global;
import com.njk.R;
import com.yunmeike.adapter.VipListAdapter;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class VipDetailsActivity extends BaseActivity implements
		OnClickListener {

	private ListView listView;

	private Activity context;
	private LinkedList<String> mListItems;
	private PtrClassicFrameLayout mPtrFrame;
	private VipListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(context).inflate(
				R.layout.vip_details_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "会员卡详情", TOP_BTN_MODE.SHOWBOTH,"","");
		rootView.findViewById(R.id.back_btn).setOnClickListener(this);
		rootView.findViewById(R.id.share_btn).setOnClickListener(this);
		
		
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

}
