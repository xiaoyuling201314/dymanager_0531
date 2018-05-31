package com.dayuan.util;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

import com.dayuan.bean.SysRight;

/**
 * 公共属性
 * 
 */
public class AppCommon {

	//此部分类字段值由InitPlugIn初始化********************************************************
	
	/**
	 * 已登录的用户sessionID集合(强制退出某用户时就删除其对应的sessionId)
	 */
	public static Hashtable<String, String> sessionIdHashtable = new Hashtable<String, String>();
	
}