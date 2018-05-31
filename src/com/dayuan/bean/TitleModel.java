package com.dayuan.bean;

import java.io.Serializable;
import java.util.List;

public class TitleModel implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private List pageMode;       //一级所包含的二级菜单列表
	private String title;        //一级菜单的名称
	private String title1;       //tr的名称
	private String title2;       //tr二级的名称
	private String title3;      //div的名称
	private String bgsrc;		//背景图片
	public String getBgsrc() {
		return bgsrc;
	}
	public void setBgsrc(String bgsrc) {
		this.bgsrc = bgsrc;
	}
	public List getPageMode() {
		return pageMode;
	}
	public void setPageMode(List pageMode) {
		this.pageMode = pageMode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getTitle3() {
		return title3;
	}
	public void setTitle3(String title3) {
		this.title3 = title3;
	}

}
