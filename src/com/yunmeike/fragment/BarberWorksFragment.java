package com.yunmeike.fragment;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yunmeike.Global;
import com.njk.R;
import com.yunmeike.adapter.BarberDetailsWorksAdapter;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class BarberWorksFragment extends Fragment implements OnClickListener{
private static String TAG="BarberFragment";
	
	private View rootView;
	private GridView gridView;
	
	private Activity activity;
	private RequestQueue mQueue;	
    private LinkedList mListItems;    
    private DisplayImageOptions options;
    private BarberDetailsWorksAdapter mAdapter;

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = getActivity();
		mQueue = Volley.newRequestQueue(activity);  
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
				
		if(rootView == null){
			rootView = inflater.inflate(R.layout.barber_details_works_layout, container, false);
			
			gridView = (GridView) rootView.findViewById(R.id.barber_works_layout);
			mListItems = new LinkedList<String>();
			mListItems.addAll(Arrays.asList(Global.mStrings2));
			mAdapter = new BarberDetailsWorksAdapter(getActivity(), mListItems);
			gridView.setAdapter(mAdapter);
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back_btn:
			
			break;

		default:
			break;
		}
		
	}	
	
}