package com.yunmeike.db;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

import com.yunmeike.bean.CityBean;
import com.yunmeike.bean.ProvinceBean;

public class ProvinceDBUtils {

	public static void saveProvinceToDb(Context context, ArrayList<ProvinceBean> dataList) {
		ProvinceDao provinceDao = new ProvinceDao(context);
		for(ProvinceBean bean : dataList){
			provinceDao.add(bean);
			if(bean.getCitys()!=null){
				Collection<CityBean> citys = bean.getCitys();
				CityDao cityDao = new CityDao(context);
				for(CityBean cityBean : citys){
					cityBean.setProvince(bean);
					cityDao.add(cityBean);
				}
			}
		}
		
	}

	public static ProvinceBean getProvince(Context context,int id){
		ProvinceDao provinceDao = new ProvinceDao(context);
		return provinceDao.queryById(id);
	}
	
	public static ProvinceBean getProvince(Context context,String name){
		ProvinceDao provinceDao = new ProvinceDao(context);
		return provinceDao.queryByName(name);
	}
}
