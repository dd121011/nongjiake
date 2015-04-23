package com.yunmeike.adapter;

import java.util.LinkedList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.njk.R;
import com.yunmeike.utils.Utils;
import com.yunmeike.view.ViewHolder;

public class BarberDetailsFaceAdapter extends BaseAdapter {
	private Activity context;
	private LinkedList mListItems;
	
	private DisplayImageOptions options;	
	
	private ViewGroup.LayoutParams layoutParams;
	
	public BarberDetailsFaceAdapter(Activity context, LinkedList mListItems) {
		this.context = context;
		this.mListItems = mListItems;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.face_test1)
		.showImageForEmptyUri(R.drawable.face_test1)
		.showImageOnFail(R.drawable.face_test1)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new SimpleBitmapDisplayer())
		.build();
		
		int[] wh = Utils.getDisplayMetrics(context);

		layoutParams = new LayoutParams(wh[0], wh[0]*4/9);

		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mListItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1 == null){
			arg1 = LayoutInflater.from(context).inflate(R.layout.barber_details_face_item, null);
		}
		ImageView faceImg = ViewHolder.get(arg1, R.id.face_img);
		TextView nameText = ViewHolder.get(arg1, R.id.name_text);
		if(arg0==0){
			faceImg.setImageResource(R.drawable.barber_zan_d);
			nameText.setVisibility(View.VISIBLE);
		}else{
//			faceImg.setImageResource(R.drawable.test1);
//			ImageView faceImg = ViewHolder.get(arg1, R.id.face_img);
//			ImageLoader.getInstance().displayImage("drawable://" + R.drawable.face_test1, faceImg, options);	
			ImageLoader.getInstance().displayImage("http://img0.bdstatic.com/img/image/4a75a05f8041bf84df4a4933667824811426747915.jpg", faceImg, options);
			nameText.setVisibility(View.INVISIBLE);
		}
		

		
		return arg1;
	}

}
