package com.yunmeike.category;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njk.R;
import com.yunmeike.BaseActivity;

public class CategoryTestActivity extends BaseActivity {
	private ListView list,sublist;
	private Activity context;
	
	private List<CategoryBean> listData;;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context = this;
		setContentView(R.layout.category_test_layout);
		
		listData = CategoryBeanUtils.getTestDada();
		list = (ListView) findViewById(R.id.category_test_list);

		sublist = (ListView) findViewById(R.id.category_test_sublist);
		
		list.setAdapter(new CategoryListAdapter(context,listData));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CategoryListAdapter adapter = (CategoryListAdapter) parent.getAdapter();
				CategoryBean item = (CategoryBean) adapter.getItem(position);
				sublist.setAdapter(new CategoryListAdapter(context,item.subList));
				
			}
		});
		
		
		
		
	}

}
