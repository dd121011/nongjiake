package com.yunmeike.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryBeanUtils {
   public static List<CategoryBean> getTestDada(){
	   List<CategoryBean> list = new ArrayList<CategoryBean>();
	   for(int i=0;i<20;i++){
		   CategoryBean item = new CategoryBean();
		   item.id = i+"";
		   item.name = i+"北京";
		   item.leve = 1;
		   
		   List<CategoryBean> subList = new ArrayList<CategoryBean>();
		   for(int j=0;j<5;j++){
			   CategoryBean subItem = new CategoryBean();
			   subItem.id = j+"";
			   subItem.name = j+"北京sub";
			   subItem.leve = 1;
			   
			   subList.add(subItem);
		   }
		   item.subList = subList;
		   list.add(item);
	   }
	   return list;
   }
}
