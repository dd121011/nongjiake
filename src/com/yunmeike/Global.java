package com.yunmeike;

public class Global {
	//http://m.playappgame.com/HelloWorld/json/orders.html
	public static String server_url ="http://liujunbin.com/HelloWorld/";
//	public final static String server_url = "http://172.16.36.139/HelloWorld/";  //test
	
	public final static String default_search_url = "http://r.m.taobao.com/s?p=mm_36033400_4200951_14448904&q=关键词";
	public static String search_url = default_search_url;
	public final static String category_url = "json/category.html";
	public final static String orders_url = "json/orders.html";
	public final static String menus_url = "json/menus.html";
	public final static String searchurls_url = "json/searchurls.html";
	public final static String shortcuts_url = "json/shortcuts.html";
	public final static String favorites_url = "my_favoritess.php";	
	
	
//	public final static String category_url = server_url+"json/category.html";
//	public final static String orders_url = server_url+"json/orders.html";
//	public final static String menus_url = server_url+"json/menus.html";
//	public final static String searchurls_url = server_url+"json/searchurls.html";
//	public final static String shortcuts_url = server_url+"json/shortcuts.html";
//	public final static String favorites_url = server_url+"my_favoritess.php";
	
//	public final static String shortcuts_url = "http://ljb-oray.oicp.net/helloworld/json/shortcuts.html";// server_url+"json/shortcuts.html";
//	public final static String favorites_url = "http://ljb-oray.oicp.net/helloworld/my_favoritess.php";
	
	
	public final static int pageSize = 20;
	public final static int WEBVIEW_ACTION = 1;   //点击菜单跳转到webview界面
	public final static int LIST_MENU = 2;
	


	public final static String location_city = "location_city"; //保持在本地的当前城市
	public final static String hide_guide = "hide_guide"; //显示引导页key
	
	
	public static String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
	
	public static String[] mStrings2 = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi" };
	
	public static String[] mStrings3 = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
		"Acorn"};
}
