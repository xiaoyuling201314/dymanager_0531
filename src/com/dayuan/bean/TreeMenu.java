package com.dayuan.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeMenu {


	/**
	 * 页面模块列表
	 */
	private List<Map<String, String>> moduleList = new ArrayList<Map<String,String>>();
	/**
	 * 页面列表
	 */
	private List<Map<String, String>> pageList = new ArrayList<Map<String,String>>();
	
	
	public List<Map<String, String>> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Map<String, String>> moduleList) {
		this.moduleList = moduleList;
	}
	public List<Map<String, String>> getPageList() {
		return pageList;
	}
	public void setPageList(List<Map<String, String>> pageList) {
		this.pageList = pageList;
	}

}
