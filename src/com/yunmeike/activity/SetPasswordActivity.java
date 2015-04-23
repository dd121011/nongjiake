package com.yunmeike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.njk.R;
import com.yunmeike.BaseActivity;
import com.yunmeike.MApplication;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class SetPasswordActivity extends BaseActivity implements OnClickListener{
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(this).inflate(R.layout.set_password_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "设置密码", TOP_BTN_MODE.SHOWBACK,"","");
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.finish_btn).setOnClickListener(this);

	        
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.finish_btn:
			intent = new Intent(this, SelectUserTypeActivity.class);
			startActivity(intent);
			MApplication app = (MApplication) getApplication();
			app.addLoginAcitivity(this);
			break;
		
		default:
			break;
		}
		
	}

	
}
