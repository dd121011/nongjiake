package com.yunmeike.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.njk.R;
import com.yunmeike.BaseActivity;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class MoreActivity extends BaseActivity implements OnClickListener{
	private Context context;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context = this;
		View rootView = LayoutInflater.from(this).inflate(R.layout.more_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "更多", TOP_BTN_MODE.SHOWBACK, "", "");
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;

		default:
			break;
		}
		
	}

}
