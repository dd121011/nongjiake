package com.yunmeike.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njk.R;
import com.yunmeike.BaseActivity;
import com.yunmeike.MApplication;
import com.yunmeike.bean.NearBean;
import com.yunmeike.net.utils.RequestCommandEnum;
import com.yunmeike.net.utils.RequestUtils;
import com.yunmeike.net.utils.RequestUtils.ResponseHandlerInterface;
import com.yunmeike.utils.Config;
import com.yunmeike.utils.Utils;
import com.yunmeike.utils.Utils.TOP_BTN_MODE;

public class SetPasswordActivity extends BaseActivity implements OnClickListener{
	private final String TAG = "SetPasswordActivity";
	private EditText phone_text,affirm_new_psd;
	private Context context;
	private RequestQueue mQueue; 
	private String verify_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		mQueue = Volley.newRequestQueue(this);
		View rootView = LayoutInflater.from(this).inflate(R.layout.set_password_layout, null);
		setContentView(rootView);
		Utils.showTopBtn(rootView, "设置密码", TOP_BTN_MODE.SHOWBACK,"","");
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.finish_btn).setOnClickListener(this);
		
		phone_text = (EditText)findViewById(R.id.phone_text);
		affirm_new_psd = (EditText)findViewById(R.id.affirm_new_psd);
		
		Intent intent = getIntent();
		verify_code = intent.getStringExtra("verify_code");
		phone_text.setText(intent.getStringExtra("mobile"));
   
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.finish_btn:
			if(!TextUtils.isEmpty(phone_text.getText().toString()) && !TextUtils.isEmpty(affirm_new_psd.getText().toString()) && !TextUtils.isEmpty(verify_code)){
				startGetData();
			}
			break;	
		default:
			break;
		}
		
	}
	
	private void toLoginActivity(){
//		Intent intent = new Intent(this, SelectUserTypeActivity.class);
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		MApplication app = (MApplication) getApplication();
		app.addLoginAcitivity(this);
	}

	private boolean isStart = false;
	public void startGetData(){
		if(isStart){
			return;
		}
		isStart = true;
		Map<String, String> params = new HashMap<String, String>(); 
		params.put("mobile", phone_text.getText().toString());
		params.put("code", verify_code+"");
		params.put("pwd", affirm_new_psd.getText().toString());
		
		RequestUtils.startStringRequest(Method.POST,mQueue, RequestCommandEnum.CHECK_MOBILE_DO,new ResponseHandlerInterface(){

			@Override
			public void handlerSuccess(String response) {
				// TODO Auto-generated method stub
				 Log.d(TAG, response); 
				 isStart = false;
				 try {
					if(!TextUtils.isEmpty(response)){
						 JSONObject obj = new JSONObject(response);
						 if(obj.has("code")){
							String code = obj.getString("code");
							 if("10006".equals(code)){
								 final String msg = obj.getString("message");
								 runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(context, msg+"", Toast.LENGTH_SHORT).show();
										}
									 });
							 }else if("0".equals(code)){
								 JSONObject json2 = obj.getJSONObject("data");
								 Config.setUserId(context, json2.getString("user_id"));
								 Config.setUserToken(context, json2.getString("token"));
								 runOnUiThread(new Runnable() {
									public void run() {
										toLoginActivity();
									}
								 });
							 }
							 
						 }
					 }
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void handlerError(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, error);  
				isStart = false;
			}
			
		},params);

	}
}
