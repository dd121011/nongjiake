package com.yunmeike.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ProvinceBean implements Serializable {
	public String id;
	public String name;
	public ArrayList<CityBean> citys;
}
