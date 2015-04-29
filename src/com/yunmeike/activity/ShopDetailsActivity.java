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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.njk.R;
import com.yunmeike.BaseActivity;
import com.yunmeike.fragment.ShopDetailsComboFragment;
import com.yunmeike.fragment.ShopDetailsInfoFragment;
import com.yunmeike.fragment.ShopDetailsRemarkFragment;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;
import com.yunmeike.view.CustomListView;
import com.yunmeike.view.ViewHolder;

public class ShopDetailsActivity extends BaseActivity implements OnClickListener {
	
	private ImageView topImg;
	private RadioGroup swithRadio;
	
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
		swithRadio.check(R.id.radio_btn1);
		
		
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		
		int[] wh = Utils.getDisplayMetrics(context);
		topImg = (ImageView) findViewById(R.id.shop_top_img);
		topImg.getLayoutParams().width = wh[0];
		topImg.getLayoutParams().height = wh[0]*4/9;
		
		
		findViewById(R.id.shop_adress_layout).setOnClickListener(this);;
		
		CustomListView list = (CustomListView) findViewById(R.id.list_layout);
		list.setAdapter(new TextAdapter(context, textArra));
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
				changeFragment(checkedId+"",ShopDetailsInfoFragment.class);
				break;
			case R.id.radio_btn2:
				changeFragment(checkedId+"",ShopDetailsRemarkFragment.class);
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

	String[] textArra = {"采摘","垂钓","祈福","农家"};
	class TextAdapter extends BaseAdapter{
		String[] texts = null;
		Activity context = null;
		public TextAdapter(Activity context, String[] textArra) {
			texts = textArra;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return texts.length;
		}

		@Override
		public Object getItem(int position) {

			return texts[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.near_item_text_layout, null);
			}
			TextView t = ViewHolder.get(convertView, R.id.item_text);
			t.setText(texts[position]);
			
			switch (position) {
			case 0:
				t.setTextColor(context.getResources().getColor(R.color.angling_text_color));
				t.setBackgroundResource(R.color.angling_text_bg_color);
				break;
			case 1:
				t.setTextColor(context.getResources().getColor(R.color.pick_text_color));
				t.setBackgroundResource(R.color.pick_text_bg_color);		
				break;
			case 2:
				t.setTextColor(context.getResources().getColor(R.color.angling2_text_color));
				t.setBackgroundResource(R.color.angling2_text_bg_color);
				break;
			case 3:
				t.setTextColor(context.getResources().getColor(R.color.angling_text_color));
				t.setBackgroundResource(R.color.angling_text_bg_color);
				break;

			default:
				break;
			}
			
			return convertView;
		}
		
	}
}
