package com.yunmeike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yunmeike.BaseActivity;
import com.yunmeike.MApplication;
import com.njk.R;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class RegisterActivity extends BaseActivity implements OnClickListener{
	
	private static final int GET_VERIFY_CODE = 1;
	
	private TextView getVerifyCodeBtn;
	
	private int timeMax = 6;
	private int offsetTime = timeMax;
	
	Context context;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_VERIFY_CODE:
				if(offsetTime>0){
					getVerifyCodeBtn.setText((offsetTime--)+"s");
					handler.sendEmptyMessageDelayed(GET_VERIFY_CODE, 1000);
				}else{
					getVerifyCodeBtn.setText("获取验证码");
					offsetTime = timeMax;
				}
				break;

			default:
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		View rootView = LayoutInflater.from(this).inflate(R.layout.register_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "注册", TOP_BTN_MODE.SHOWBACK, "", "");
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		
		getVerifyCodeBtn = (TextView) findViewById(R.id.get_verify_code);
		getVerifyCodeBtn.setOnClickListener(this);;
	        
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.next_btn:
			intent = new Intent(context, SetPasswordActivity.class);
			startActivity(intent);
			MApplication app = (MApplication) getApplication();
			app.addLoginAcitivity(this);
			break;
		case R.id.get_verify_code:
			if(offsetTime == timeMax ){
				handler.sendEmptyMessageDelayed(GET_VERIFY_CODE, 1000);
			}
			break;
		default:
			break;
		}
		
	}

}
