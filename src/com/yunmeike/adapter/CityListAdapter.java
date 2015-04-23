package com.yunmeike.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njk.R;
import com.yunmeike.db.CityModel;

public class CityListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CityModel> list;
    
    public CityListAdapter(Context context, List<CityModel>  list) {
    	this.inflater = LayoutInflater.from(context);
		this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.switch_city_item, null);
			holder = new ViewHolder();
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(list.get(position).getCityName());
		String currentStr = list.get(position).getNameSort();
		String previewStr = (position - 1) >= 0 ? list.get(position - 1)
				.getNameSort() : " ";
		if (!previewStr.equals(currentStr)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder {
		TextView alpha;
		TextView name;
	}
    
}
