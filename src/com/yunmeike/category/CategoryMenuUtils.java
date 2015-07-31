package com.yunmeike.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMenuUtils {
	public static List<CategoryMenuBean> getTestMenuData(){
		String[] strArr = {"全城","距离","排序"};
		
		List<CategoryMenuBean> list = new ArrayList<CategoryMenuBean>();
		for(int  i = 0;i<strArr.length;i++){
			CategoryMenuBean item = new CategoryMenuBean();
			item.setTitle(strArr[i]);
			item.setSelected(false);
			item.setIndex(i);
			if(i==0){
				item.setSelected(true);
			}
			List<CategoryBean> listData = CategoryBeanUtils.getTestDada();			
			CategoryGroup categoryGroup = new CategoryGroup(listData);
			CategoryBean categoryBean = listData.get(0);
			categoryBean.name=strArr[i];
			categoryGroup.setTmpCategory(categoryBean);
			categoryGroup.setTmpSubCategory(categoryBean);
			
			item.setCategoryGroup(categoryGroup);
			
			list.add(item);
		}
		return list;
	}
}
