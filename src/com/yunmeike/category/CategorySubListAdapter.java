package com.yunmeike.category;

import java.util.List;

import com.njk.R;
import com.yunmeike.view.ViewHolder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategorySubListAdapter extends BaseAdapter {
	Activity context;
	CategoryGroup categoryGroup;
	List<CategoryBean> list;
	private LayoutInflater inflater;

	public CategorySubListAdapter(Activity context,CategoryGroup categoryGroup, List<CategoryBean> list) {
		this.context = context;
		this.categoryGroup = categoryGroup;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.category_list_item, null);
		}

		CategoryBean item = list.get(position);
		
		ImageView icon = ViewHolder.get(convertView, R.id.icon);
		TextView title_text = ViewHolder.get(convertView, R.id.title_text);
		TextView hint_text = ViewHolder.get(convertView, R.id.hint_text);

		title_text.setText(item.name);
		
		return convertView;
	}

}
