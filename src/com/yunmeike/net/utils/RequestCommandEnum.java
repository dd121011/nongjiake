package com.yunmeike.net.utils;

public enum RequestCommandEnum {
	FAMILY_LIST("/api/family/familylist"),
	SEND_CODE_DO("/api/register/send_code_do"),
	CHECK_MOBILE_DO("/api/register/check_mobile_do"),
	LOGIN("/api/account/login"),
	APPINFOS_AREAS("/api/appinfos/areas"),
	FAMILY_GETSCENIC("/api/family/getscenic"),
	FAMILY_INFO("/api/family/family_info"),
	FINDFAMILY_LISTLBS("/api/family/findfamilyListlbs"),
	FAMILY_FAV_DO("/api/family/fav_do"),
	FAMILY_CANCEL_FAV("/api/family/cancel_fav"),
	FAMILY_REVIEW_DO("/api/family/review_do"),
	FAMILY_DETAIL("/api/family/detail");
	

	public String command;
	RequestCommandEnum(String name) {
		this.command = name;
	}
	

}
