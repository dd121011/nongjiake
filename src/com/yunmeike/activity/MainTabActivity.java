package com.yunmeike.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yunmeike.BaseActivity;
import com.yunmeike.MApplication;
import com.njk.R;
import com.yunmeike.fragment.DiscoverFragmentPage;
import com.yunmeike.fragment.EncircleFragmentPage;
import com.yunmeike.fragment.NearFragmentPage;
import com.yunmeike.fragment.PersonalFragmentBarberPage;
import com.yunmeike.fragment.PersonalFragmentPage;
import com.yunmeike.photo.util.Res;
import com.yunmeike.utils.DialogUtil;
import com.yunmeike.utils.LocalDisplay;

/**
 * @author junbin
 *	功能描述：自定义TabHost
 */
public class MainTabActivity extends BaseActivity{	
	private static String TAG="MainTabActivity";
	
	//定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	
	//定义一个布局
	private LayoutInflater layoutInflater;
		
//	//定义数组来存放Fragment界面
//	private Class[] fragmentArray = {NearFragmentPage.class,DiscoverFragmentPage.class,EncircleFragmentPage.class,SubscribeFragmentPage.class,PersonalFragmentBarberPage.class};
//	//定义数组来存放按钮图片
//	private int[] mImageViewArray = {R.drawable.tab_near_btn,R.drawable.tab_discover_btn,R.drawable.tab_encircle_btn,
//									 R.drawable.tab_subscribe_btn,R.drawable.tab_user_btn};
//	//Tab选项卡的文字
//	private String[] mTextviewArray = {"附近", "发现", "炫美圈", "预约单", "我的"};
	private Class[] fragmentArray = {NearFragmentPage.class,DiscoverFragmentPage.class,EncircleFragmentPage.class,PersonalFragmentPage.class};
	private int[] mImageViewArray = {R.drawable.tab_near_btn,R.drawable.tab_discover_btn,R.drawable.tab_encircle_btn,R.drawable.tab_user_btn};
	private String[] mTextviewArray = {"附近", "发现", "晒农家", "我的"};
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        
        MApplication app = (MApplication) getApplication();
        app.finishLoginActivity();
        
		Res.init(this);
        
		LocalDisplay.init(this);
        
        initTabData();
        
        initView();
    }
	 
	private void initTabData() {
		
		
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化布局对象
		layoutInflater = LayoutInflater.from(this);
				
		//实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);	
		
		//得到fragment的个数
		int count = fragmentArray.length;	
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			//将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
//			//设置Tab按钮的背景
//			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
		}
		
		
				
	}
				
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
	
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);		
		textView.setText(mTextviewArray[index]);
		
		return view;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
				DialogUtil.appExitDialog(this);			
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
