package com.yunmeike.fragment;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.njk.R;
import com.yunmeike.adapter.ComboListAdapter;
import com.yunmeike.adapter.NearListAdapter;
import com.yunmeike.fragment.NearFragmentPage.NearListOnItemClickListener;
import com.yunmeike.utils.Utils;
import com.yunmeike.view.CustomListView;

public final class ShopDetailsComboFragment extends Fragment {
    private String TAG = "ShopDetailsInfoFragment";

    private View rootView;
    private CustomListView listView;
    LinkedList mListItems;
    
    private Activity context;
    private DisplayImageOptions options;	
    private ComboListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = getActivity();
        
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(rootView == null){
			rootView = inflater.inflate(R.layout.shop_details_combo, container, false);

			listView = (CustomListView) rootView.findViewById(R.id.list_layout);
			mListItems = new LinkedList<String>();
			mListItems.addAll(Arrays.asList(mStrings));
			mAdapter = new ComboListAdapter(getActivity(), mListItems);
			listView.setAdapter(mAdapter);
//			Utils.setListViewHeightBasedOnChildren(listView);
		}
		
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
	    ViewGroup parent = (ViewGroup) rootView.getParent();
	    if (parent != null)
	    {
	      parent.removeView(rootView);
	    }
        return rootView;
    }

    String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi" };
}
