package com.yunmeike.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yunmeike.BaseActivity;
import com.njk.R;
import com.yunmeike.adapter.BarberFaceAdapter;
import com.yunmeike.fragment.ShopDetailsComboFragment;
import com.yunmeike.fragment.ShopDetailsInfoFragment;
import com.yunmeike.fragment.ShopDetailsRemarkFragment;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class ShopDetailsActivity extends BaseActivity implements OnClickListener,OnItemClickListener {
	
	private ImageView topImg;
	private RadioGroup swithRadio;
	private GridView barberFaceLayout;
	
	private Activity context;
	private FragmentManager fm;
	private List<Fragment> fragmentlist;
	private LinkedList<String> mListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(context).inflate(R.layout.shop_details_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "店铺详情", TOP_BTN_MODE.SHOWBOTH,"","");
		rootView.findViewById(R.id.back_btn).setOnClickListener(this);
		
		fm = getSupportFragmentManager();  		
		swithRadio = (RadioGroup) rootView.findViewById(R.id.swith_shop_details);		
		swithRadio.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		swithRadio.check(R.id.radio_btn2);
		
		
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		barberFaceLayout = (GridView) rootView.findViewById(R.id.shop_barber_face_layout);
		barberFaceLayout.setOnItemClickListener(this);
		BarberFaceAdapter adapter = new BarberFaceAdapter(context, mListItems);
		barberFaceLayout.setAdapter(adapter);
		
		int[] wh = Utils.getDisplayMetrics(context);
		topImg = (ImageView) findViewById(R.id.shop_top_img);
		topImg.getLayoutParams().width = wh[0];
		topImg.getLayoutParams().height = wh[0]*4/9;
		
		
		findViewById(R.id.shop_adress_layout).setOnClickListener(this);;
	}
	
	private void changeFragment(String mTag,Class mClass){  
		Fragment mFragment = fm.findFragmentByTag(mTag);
        FragmentTransaction ft = fm.beginTransaction();  
        if (mFragment == null) {
            mFragment = Fragment.instantiate(context, mClass.getName(), null);
            ft.add(R.id.fragment_content, mFragment, mTag);
            if(fragmentlist == null){
            	fragmentlist = new ArrayList<Fragment>();
            }
            fragmentlist.add(mFragment);
        } 
        
        for(Fragment f: fragmentlist){
        	if(!mFragment.equals(f)){
        		ft.hide(f);
        	}else{
        		ft.show(f); 
        	}
        }
        
        ft.commit();
    }  

	class MyOnCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
//			Toast.makeText(context, "checkedId = "+checkedId, 1000).show();
			switch (checkedId) {
			case R.id.radio_btn1:
				changeFragment(checkedId+"",ShopDetailsRemarkFragment.class);
				break;
			case R.id.radio_btn2:
				changeFragment(checkedId+"",ShopDetailsInfoFragment.class);
				break;
			case R.id.radio_btn3:
				changeFragment(checkedId+"",ShopDetailsComboFragment.class);
				break;
			default:
				break;
			}
		}
		
	}
	
	String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi" };

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.shop_adress_layout:
			intent = new Intent(context,ShopRouteSearchActivity.class);
			context.startActivity(intent);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(context,BarberDetailsActivity.class);
		context.startActivity(intent);
		
	}
}
