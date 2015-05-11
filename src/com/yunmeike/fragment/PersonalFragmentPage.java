package com.yunmeike.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.njk.R;
import com.yunmeike.activity.AddNjyActivity;
import com.yunmeike.activity.FavoritesNjyActivity;
import com.yunmeike.activity.MoreActivity;
import com.yunmeike.activity.TravelNotesActivity;

public class PersonalFragmentPage extends Fragment implements OnClickListener{
	private static String TAG="PersonalFragmentPage";
	
	private View rootView;
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		if(rootView == null){
			rootView = LayoutInflater.from(activity).inflate(R.layout.personal_fragment_layout, container, false);
			
			rootView.findViewById(R.id.face_layout).setOnClickListener(this);
			rootView.findViewById(R.id.nongjia_you).setOnClickListener(this);
			rootView.findViewById(R.id.favorites_nongjia).setOnClickListener(this);
			rootView.findViewById(R.id.add_nongjiayuan).setOnClickListener(this);
			rootView.findViewById(R.id.more_btn).setOnClickListener(this);
			
		}
		
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
	    ViewGroup parent = (ViewGroup) rootView.getParent();
	    if (parent != null)
	    {
	      parent.removeView(rootView);
	    }
		
		return rootView;		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.face_layout:
			
			break;
		case R.id.nongjia_you:
			intent = new Intent(activity,TravelNotesActivity.class);
			startActivity(intent);
			break;
		case R.id.favorites_nongjia:
			intent = new Intent(activity,FavoritesNjyActivity.class);
			startActivity(intent);
			break;
		case R.id.add_nongjiayuan:
			intent = new Intent(activity,AddNjyActivity.class);
			startActivity(intent);
			break;
		case R.id.more_btn:
			intent = new Intent(activity,MoreActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}	
}