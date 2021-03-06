package com.yunmeike.pinnedheaderlistView;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class CityDao {
	public static String APP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/njk/";
	private DBHelper helper;

	public CityDao(DBHelper helper) {
		this.helper = helper;
	}
	
	public List<City> getHotCities() {

		SQLiteDatabase db = helper.getReadableDataBase(APP_DIR, "city.db");

		List<City> list = new ArrayList<City>();

		Cursor cursor = null;

		try {
			if (db.isOpen()) {
				String sql = "SELECT id,name,pyf,pys FROM city where hot = 1";
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {

					City city = new City();
					city.setId(cursor.getString(0));
					city.setName(cursor.getString(1));
					city.setPyf(cursor.getString(2));
					city.setPys(cursor.getString(3));
					
					list.add(city);
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.close();
		}

		return list;
	}
	
	public List<City> getAllCities() {

		SQLiteDatabase db = helper.getReadableDataBase(APP_DIR, "city.db");

		List<City> list = new ArrayList<City>();

		Cursor cursor = null;

		try {
			if (db.isOpen()) {
				String sql = "SELECT id,name,pyf,pys FROM city";
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {

					City city = new City();
					city.setId(cursor.getString(0));
					city.setName(cursor.getString(1));
					city.setPyf(cursor.getString(2));
					city.setPys(cursor.getString(3));
					
					list.add(city);
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.close();
		}

		return list;
	}

}
